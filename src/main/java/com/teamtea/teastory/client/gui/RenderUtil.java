package com.teamtea.teastory.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import org.joml.Matrix4f;

public class RenderUtil {
    /**
     * 渲染顶点
     *
     * @param matrix  渲染矩阵
     * @param builder builder
     * @param x       顶点x坐标
     * @param y       顶点y坐标
     * @param z       顶点z坐标
     * @param u       顶点对应贴图的u坐标
     * @param v       顶点对应贴图的v坐标
     * @param overlay 覆盖
     * @param light   光照
     */
    public static void buildMatrix(Matrix4f matrix, VertexConsumer builder, float x, float y, float z, float u, float v, int overlay, int RGBA, float alpha, int light) {
        float red = ((RGBA >> 16) & 0xFF) / 255f;
        float green = ((RGBA >> 8) & 0xFF) / 255f;
        float blue = ((RGBA) & 0xFF) / 255f;

        builder.addVertex(matrix, x, y, z)
                .setColor(red, green, blue, alpha)
                .setUv(u, v)
                .setOverlay(overlay)
                .setLight(light)
                .setNormal(0f, 1f, 0f)
        ;
    }

    public static void buildMatrix(Matrix4f matrix, VertexConsumer builder, float x, float y, float z, float u, float v, int RGBA) {
        float red = ((RGBA >> 16) & 0xFF) / 255f;
        float green = ((RGBA >> 8) & 0xFF) / 255f;
        float blue = ((RGBA >> 0) & 0xFF) / 255f;
        int alpha = 1;

        builder.addVertex(matrix, x, y, z)
                .setColor(red, green, blue, alpha)
                .setUv(u, v)
        ;
    }

    public static TextureAtlasSprite getBlockSprite(ResourceLocation sprite) {
        return Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(sprite);
    }

    /**
     * 在GUI中渲染流体
     *
     * @param matrix 渲染矩阵
     * @param fluid  需要渲染的流体（FluidStack）
     * @param width  需要渲染的流体宽度
     * @param height 需要渲染的流体高度
     * @param x      x（绝对）
     * @param y      y（绝对）
     */
    public static void renderFluidStackInGUI(Matrix4f matrix, FluidStack fluid, int width, int height, float x, float y) {
        // 正常渲染透明度
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        // 获取sprite
        FluidType attributes = fluid.getFluid().getFluidType();
        TextureAtlasSprite FLUID = getBlockSprite(IClientFluidTypeExtensions.of(fluid.getFluid()).getStillTexture(fluid));

        // 绑atlas
        //        Minecraft.getInstance().getTextureManager().bindForSetup(InventoryMenu.BLOCK_ATLAS);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);

        //        注意color要这样写，后面的是无效的
        // int color = IClientFluidTypeExtensions.of(fluid.getFluid()).getTintColor();
        int color = IClientFluidTypeExtensions.of(fluid.getFluid()).getTintColor(fluid);
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;
        float a = ((color >> 24) & 0xFF) / 255f;
        RenderSystem.setShaderColor(r, g, b, a);


        /*
         * 获取横向和纵向层数
         * 每16像素为1层，通过将给定渲染长宽不加类型转换除16来获取层数
         * 通过取余获取数值大小在16以下的额外数值
         */
        int wFloors = width / 16;
        int extraWidth = wFloors == 0 ? width : width % 16;
        int hFloors = height / 16;
        int extraHeight = hFloors == 0 ? height : height % 16;
        extraHeight = Math.max(1, extraHeight);
        // add it to avoid too much
        if (height == 16) extraHeight = 0;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        float u0 = FLUID.getU0();
        float v0 = FLUID.getV0();
        Tesselator tessellator = Tesselator.getInstance();
        // BufferBuilder builder = tessellator.getBuilder();
        var builder = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);


        /*
         * 渲染循环
         * 该循环通过两个嵌套循环完成
         * 外层循环处理 y 和 v 的变更（高度层），内层处理 x 和 u 的变更（宽度层），渲染主代码存于内层
         * 渲染逻辑是 [先从最下面的高度层开始，向右渲染此高度层含的所有宽度层并渲染额外宽度层]
         * [第一层（高）渲染完毕后渲染第二层，依此类推渲染所有高度层和额外高度层，以达成渲染任意长宽的流体矩形的目的]
         * 对于层，若层数为0（渲染数值小于16），则直接将渲染数值设为额外层数值。
         */
        for (int i = hFloors; i >= 0; i--) {
            // i为流程控制码，若i=0则代表高度层已全部渲染完毕，此时若额外层高度为0（渲染高度参数本来就是16的整数倍）则跳出
            if (i == 0 && extraHeight == 0)
                break;
            float yStart = y - ((hFloors - i) * 16);
            // 获取本层/额外层的高度，若高度层渲染完毕则设为额外层高度
            float yOffset = i == 0 ? (float) extraHeight : 16;
            // 获取v1
            float v1 = i == 0 ? FLUID.getV0() + ((FLUID.getV1() - v0) * ((float) extraHeight / 16f)) : FLUID.getV1();

            // x层以此类推
            for (int j = wFloors; j >= 0; j--) {
                if (j == 0 && extraWidth == 0)
                    break;
                float xStart = x + (wFloors - j) * 16;
                float xOffset = j == 0 ? (float) extraWidth : 16;
                float u1 = j == 0 ? FLUID.getU0() + ((FLUID.getU1() - u0) * ((float) extraWidth / 16f)) : FLUID.getU1();

                // 渲染主代码
                // builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                buildMatrix(matrix, builder, xStart, yStart - yOffset, 0.0f, u0, v0, color);
                buildMatrix(matrix, builder, xStart, yStart, 0.0f, u0, v1, color);
                buildMatrix(matrix, builder, xStart + xOffset, yStart, 0.0f, u1, v1, color);
                buildMatrix(matrix, builder, xStart + xOffset, yStart - yOffset, 0.0f, u1, v0, color);
                // tessellator.end();
            }
        }

        BufferUploader.drawWithShader(builder.buildOrThrow());

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.disableBlend();
    }
}
