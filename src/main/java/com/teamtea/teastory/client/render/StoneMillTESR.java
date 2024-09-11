package com.teamtea.teastory.client.render;


import com.teamtea.teastory.blockentity.StoneMillBlockEntity;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import com.teamtea.teastory.registry.ItemRegister;
import com.teamtea.teastory.block.NormalHorizontalBlock;

public class StoneMillTESR implements BlockEntityRenderer<StoneMillBlockEntity> {
    public StoneMillTESR(BlockEntityRendererProvider.Context pContext) {

    }

    @Override
    public void render(StoneMillBlockEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Minecraft mc = Minecraft.getInstance();

        ItemRenderer renderItem = mc.getItemRenderer();

        matrixStackIn.pushPose();

        matrixStackIn.translate(0.5, 0.5, 0.5);
        float angel = tileEntityIn.getAngel();
        if (tileEntityIn.isWorking()) {
            angel += partialTicks * 3.0F;
        }

        // matrixStackIn.rotate(new Quaternion(Vector3f.YP, angel, true));
        matrixStackIn.mulPose(XYZ.deg_to_rad(0,angel,0));

        Lighting.setupForFlatItems();
        renderItem.renderStatic(new ItemStack(ItemRegister.STONE_MILL_TOP.get()), ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, mc.level, 0);
        Lighting.setupFor3DItems();

        matrixStackIn.popPose();

        Fluid fluid = tileEntityIn.getOutputFluid();
        if (fluid != Fluids.EMPTY) {
            matrixStackIn.pushPose();

            VertexConsumer buffer = bufferIn.getBuffer(RenderType.translucentMovingBlock());
            TextureAtlasSprite flowing = mc.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(IClientFluidTypeExtensions.of(fluid).getFlowingTexture());

            int color = IClientFluidTypeExtensions.of(fluid).getTintColor();
            int r = color >> 16 & 0xFF;
            int g = color >> 8 & 0xFF;
            int b = color & 0xFF;
            int a = color >> 24 & 0xFF;

            Direction facing = tileEntityIn.getBlockState().getValue(NormalHorizontalBlock.FACING);
            int light = LevelRenderer.getLightColor(tileEntityIn.getLevel(), tileEntityIn.getBlockPos());

            switch (facing) {
                case EAST: {
                    float min_u_0 = flowing.getU(1);
                    float max_u_0 = flowing.getU(15);
                    float min_v_0 = flowing.getV(2);
                    float max_v_0 = flowing.getV(16);

                 buffer.addVertex(matrixStackIn.last().pose(), 0.0625F, 0.065F, 0.9375F).setColor(r, g, b, a).setUv(min_u_0, min_v_0).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.9375F, 0.065F, 0.9375F).setColor(r, g, b, a).setUv(min_u_0, max_v_0).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.9375F, 0.065F, 0.0625F).setColor(r, g, b, a).setUv(max_u_0, max_v_0).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.0625F, 0.065F, 0.0625F).setColor(r, g, b, a).setUv(max_u_0, min_v_0).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_1 = getPosition(0.4375F, flowing.getU0(), flowing.getU1());
                    float max_u_1 = getPosition(0.5625F, flowing.getU0(), flowing.getU1());
                    float min_v_1 = getPosition(0.0000F, flowing.getV0(), flowing.getV1());
                    float max_v_1 = getPosition(0.25F, flowing.getV0(), flowing.getV1());

                 buffer.addVertex(matrixStackIn.last().pose(), 0.9375F, 0.065F, 0.5625F).setColor(r, g, b, a).setUv(min_u_1, min_v_1).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 1.1875F, 0.065F, 0.5625F).setColor(r, g, b, a).setUv(min_u_1, max_v_1).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 1.1875F, 0.065F, 0.4375F).setColor(r, g, b, a).setUv(max_u_1, max_v_1).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.9375F, 0.065F, 0.4375F).setColor(r, g, b, a).setUv(max_u_1, min_v_1).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_2 = getPosition(0.4375F, flowing.getU0(), flowing.getU1());
                    float max_u_2 = getPosition(0.5625F, flowing.getU0(), flowing.getU1());
                    float min_v_2 = getPosition(0.25F, flowing.getV0(), flowing.getV1());
                    float max_v_2 = getPosition(1F, flowing.getV0(), flowing.getV1());

                 buffer.addVertex(matrixStackIn.last().pose(), 1.1875F, 0.065F, 0.5625F).setColor(r, g, b, a).setUv(min_u_2, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 1.1875F, -0.7435F, 0.5625F).setColor(r, g, b, a).setUv(min_u_2, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 1.1875F, -0.7435F, 0.4375F).setColor(r, g, b, a).setUv(max_u_2, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 1.1875F, 0.065F, 0.4375F).setColor(r, g, b, a).setUv(max_u_2, min_v_2).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_3 = getPosition(0.5625F, flowing.getU0(), flowing.getU1());
                    float max_u_3 = getPosition(0.6875F, flowing.getU0(), flowing.getU1());

                 buffer.addVertex(matrixStackIn.last().pose(), 1.1875F, 0.065F, 0.4375F).setColor(r, g, b, a).setUv(min_u_3, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 1.1875F, -0.7435F, 0.4375F).setColor(r, g, b, a).setUv(min_u_3, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 1.0625F, -0.7435F, 0.4375F).setColor(r, g, b, a).setUv(max_u_3, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 1.0625F, 0.065F, 0.4375F).setColor(r, g, b, a).setUv(max_u_3, min_v_2).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_4 = getPosition(0.3125F, flowing.getU0(), flowing.getU1());
                    float max_u_4 = getPosition(0.4375F, flowing.getU0(), flowing.getU1());

                 buffer.addVertex(matrixStackIn.last().pose(), 1.0625F, 0.065F, 0.5625F).setColor(r, g, b, a).setUv(min_u_4, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 1.0625F, -0.7435F, 0.5625F).setColor(r, g, b, a).setUv(min_u_4, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 1.1875F, -0.7435F, 0.5625F).setColor(r, g, b, a).setUv(max_u_4, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 1.1875F, 0.065F, 0.5625F).setColor(r, g, b, a).setUv(max_u_4, min_v_2).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_5 = getPosition(0.1875F, flowing.getU0(), flowing.getU1());
                    float max_u_5 = getPosition(0.3125F, flowing.getU0(), flowing.getU1());

                 buffer.addVertex(matrixStackIn.last().pose(), 1.0625F, 0.065F, 0.4375F).setColor(r, g, b, a).setUv(min_u_5, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 1.0625F, -0.7435F, 0.4375F).setColor(r, g, b, a).setUv(min_u_5, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 1.0625F, -0.7435F, 0.5625F).setColor(r, g, b, a).setUv(max_u_5, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 1.0625F, 0.065F, 0.5625F).setColor(r, g, b, a).setUv(max_u_5, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                    break;
                }
                case WEST: {
                    float min_u_0 = getPosition(0.0625F, flowing.getU0(), flowing.getU1());
                    float max_u_0 = getPosition(0.9375F, flowing.getU0(), flowing.getU1());
                    float min_v_0 = getPosition(0.125F, flowing.getV0(), flowing.getV1());
                    float max_v_0 = getPosition(1F, flowing.getV0(), flowing.getV1());

                 buffer.addVertex(matrixStackIn.last().pose(), 0.9375F, 0.065F, 0.0625F).setColor(r, g, b, a).setUv(min_u_0, min_v_0).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.0625F, 0.065F, 0.0625F).setColor(r, g, b, a).setUv(min_u_0, max_v_0).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.0625F, 0.065F, 0.9375F).setColor(r, g, b, a).setUv(max_u_0, max_v_0).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.9375F, 0.065F, 0.9375F).setColor(r, g, b, a).setUv(max_u_0, min_v_0).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_1 = getPosition(0.4375F, flowing.getU0(), flowing.getU1());
                    float max_u_1 = getPosition(0.5625F, flowing.getU0(), flowing.getU1());
                    float min_v_1 = getPosition(0.0000F, flowing.getV0(), flowing.getV1());
                    float max_v_1 = getPosition(0.25F, flowing.getV0(), flowing.getV1());

                 buffer.addVertex(matrixStackIn.last().pose(), 0.0625F, 0.065F, 0.4375F).setColor(r, g, b, a).setUv(min_u_1, min_v_1).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), -0.1875F, 0.065F, 0.4375F).setColor(r, g, b, a).setUv(min_u_1, max_v_1).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), -0.1875F, 0.065F, 0.5625F).setColor(r, g, b, a).setUv(max_u_1, max_v_1).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.0625F, 0.065F, 0.5625F).setColor(r, g, b, a).setUv(max_u_1, min_v_1).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_2 = getPosition(0.4375F, flowing.getU0(), flowing.getU1());
                    float max_u_2 = getPosition(0.5625F, flowing.getU0(), flowing.getU1());
                    float min_v_2 = getPosition(0.25F, flowing.getV0(), flowing.getV1());
                    float max_v_2 = getPosition(1F, flowing.getV0(), flowing.getV1());

                 buffer.addVertex(matrixStackIn.last().pose(), -0.1875F, 0.065F, 0.4375F).setColor(r, g, b, a).setUv(min_u_2, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), -0.1875F, -0.7435F, 0.4375F).setColor(r, g, b, a).setUv(min_u_2, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), -0.1875F, -0.7435F, 0.5625F).setColor(r, g, b, a).setUv(max_u_2, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), -0.1875F, 0.065F, 0.5625F).setColor(r, g, b, a).setUv(max_u_2, min_v_2).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_3 = getPosition(0.3125F, flowing.getU0(), flowing.getU1());
                    float max_u_3 = getPosition(0.4375F, flowing.getU0(), flowing.getU1());

                 buffer.addVertex(matrixStackIn.last().pose(), -0.1875F, 0.065F, 0.5625F).setColor(r, g, b, a).setUv(min_u_3, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), -0.1875F, -0.7435F, 0.5625F).setColor(r, g, b, a).setUv(min_u_3, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), -0.0625F, -0.7435F, 0.5625F).setColor(r, g, b, a).setUv(max_u_3, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), -0.0625F, 0.065F, 0.5625F).setColor(r, g, b, a).setUv(max_u_3, min_v_2).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_4 = getPosition(0.5625F, flowing.getU0(), flowing.getU1());
                    float max_u_4 = getPosition(0.6875F, flowing.getU0(), flowing.getU1());

                 buffer.addVertex(matrixStackIn.last().pose(), -0.0625F, 0.065F, 0.4375F).setColor(r, g, b, a).setUv(min_u_4, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), -0.0625F, -0.7435F, 0.4375F).setColor(r, g, b, a).setUv(min_u_4, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), -0.1875F, -0.7435F, 0.4375F).setColor(r, g, b, a).setUv(max_u_4, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), -0.1875F, 0.065F, 0.4375F).setColor(r, g, b, a).setUv(max_u_4, min_v_2).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_5 = getPosition(0.1875F, flowing.getU0(), flowing.getU1());
                    float max_u_5 = getPosition(0.3125F, flowing.getU0(), flowing.getU1());

                 buffer.addVertex(matrixStackIn.last().pose(), -0.0625F, 0.065F, 0.5625F).setColor(r, g, b, a).setUv(min_u_5, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), -0.0625F, -0.7435F, 0.5625F).setColor(r, g, b, a).setUv(min_u_5, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), -0.0625F, -0.7435F, 0.4375F).setColor(r, g, b, a).setUv(max_u_5, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), -0.0625F, 0.065F, 0.4375F).setColor(r, g, b, a).setUv(max_u_5, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                    break;
                }
                case NORTH: {
                    float min_u_0 = getPosition(0.0625F, flowing.getU0(), flowing.getU1());
                    float max_u_0 = getPosition(0.9375F, flowing.getU0(), flowing.getU1());
                    float min_v_0 = getPosition(0.125F, flowing.getV0(), flowing.getV1());
                    float max_v_0 = getPosition(1F, flowing.getV0(), flowing.getV1());

                 buffer.addVertex(matrixStackIn.last().pose(), 0.9375F, 0.065F, 0.9375F).setColor(r, g, b, a).setUv(min_u_0, min_v_0).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.9375F, 0.065F, 0.0625F).setColor(r, g, b, a).setUv(min_u_0, max_v_0).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.0625F, 0.065F, 0.0625F).setColor(r, g, b, a).setUv(max_u_0, max_v_0).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.0625F, 0.065F, 0.9375F).setColor(r, g, b, a).setUv(max_u_0, min_v_0).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_1 = getPosition(0.4375F, flowing.getU0(), flowing.getU1());
                    float max_u_1 = getPosition(0.5625F, flowing.getU0(), flowing.getU1());
                    float min_v_1 = getPosition(0.0000F, flowing.getV0(), flowing.getV1());
                    float max_v_1 = getPosition(0.25F, flowing.getV0(), flowing.getV1());

                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, 0.065F, 0.0625F).setColor(r, g, b, a).setUv(min_u_1, min_v_1).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, 0.065F, -0.1875F).setColor(r, g, b, a).setUv(min_u_1, max_v_1).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, 0.065F, -0.1875F).setColor(r, g, b, a).setUv(max_u_1, max_v_1).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, 0.065F, 0.0625F).setColor(r, g, b, a).setUv(max_u_1, min_v_1).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_2 = getPosition(0.4375F, flowing.getU0(), flowing.getU1());
                    float max_u_2 = getPosition(0.5625F, flowing.getU0(), flowing.getU1());
                    float min_v_2 = getPosition(0.25F, flowing.getV0(), flowing.getV1());
                    float max_v_2 = getPosition(1F, flowing.getV0(), flowing.getV1());

                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, 0.065F, -0.1875F).setColor(r, g, b, a).setUv(min_u_2, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, -0.7435F, -0.1875F).setColor(r, g, b, a).setUv(min_u_2, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, -0.7435F, -0.1875F).setColor(r, g, b, a).setUv(max_u_2, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, 0.065F, -0.1875F).setColor(r, g, b, a).setUv(max_u_2, min_v_2).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_3 = getPosition(0.5625F, flowing.getU0(), flowing.getU1());
                    float max_u_3 = getPosition(0.6875F, flowing.getU0(), flowing.getU1());

                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, 0.065F, -0.1875F).setColor(r, g, b, a).setUv(min_u_3, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, -0.7435F, -0.1875F).setColor(r, g, b, a).setUv(min_u_3, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, -0.7435F, -0.0625F).setColor(r, g, b, a).setUv(max_u_3, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, 0.065F, -0.0625F).setColor(r, g, b, a).setUv(max_u_3, min_v_2).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_4 = getPosition(0.3125F, flowing.getU0(), flowing.getU1());
                    float max_u_4 = getPosition(0.4375F, flowing.getU0(), flowing.getU1());

                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, 0.065F, -0.0625F).setColor(r, g, b, a).setUv(min_u_4, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, -0.7435F, -0.0625F).setColor(r, g, b, a).setUv(min_u_4, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, -0.7435F, -0.1875F).setColor(r, g, b, a).setUv(max_u_4, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, 0.065F, -0.1875F).setColor(r, g, b, a).setUv(max_u_4, min_v_2).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_5 = getPosition(0.1875F, flowing.getU0(), flowing.getU1());
                    float max_u_5 = getPosition(0.3125F, flowing.getU0(), flowing.getU1());

                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, 0.065F, -0.0625F).setColor(r, g, b, a).setUv(min_u_5, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, -0.7435F, -0.0625F).setColor(r, g, b, a).setUv(min_u_5, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, -0.7435F, -0.0625F).setColor(r, g, b, a).setUv(max_u_5, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, 0.065F, -0.0625F).setColor(r, g, b, a).setUv(max_u_5, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                    break;
                }
                case SOUTH: {
                    float min_u_0 = getPosition(0.0625F, flowing.getU0(), flowing.getU1());
                    float max_u_0 = getPosition(0.9375F, flowing.getU0(), flowing.getU1());
                    float min_v_0 = getPosition(0.125F, flowing.getV0(), flowing.getV1());
                    float max_v_0 = getPosition(1F, flowing.getV0(), flowing.getV1());

                 buffer.addVertex(matrixStackIn.last().pose(), 0.0625F, 0.065F, 0.0625F).setColor(r, g, b, a).setUv(min_u_0, min_v_0).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.0625F, 0.065F, 0.9375F).setColor(r, g, b, a).setUv(min_u_0, max_v_0).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.9375F, 0.065F, 0.9375F).setColor(r, g, b, a).setUv(max_u_0, max_v_0).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.9375F, 0.065F, 0.0625F).setColor(r, g, b, a).setUv(max_u_0, min_v_0).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_1 = getPosition(0.4375F, flowing.getU0(), flowing.getU1());
                    float max_u_1 = getPosition(0.5625F, flowing.getU0(), flowing.getU1());
                    float min_v_1 = getPosition(0.0000F, flowing.getV0(), flowing.getV1());
                    float max_v_1 = getPosition(0.25F, flowing.getV0(), flowing.getV1());

                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, 0.065F, 0.9375F).setColor(r, g, b, a).setUv(min_u_1, min_v_1).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, 0.065F, 1.1875F).setColor(r, g, b, a).setUv(min_u_1, max_v_1).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, 0.065F, 1.1875F).setColor(r, g, b, a).setUv(max_u_1, max_v_1).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, 0.065F, 0.9375F).setColor(r, g, b, a).setUv(max_u_1, min_v_1).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_2 = getPosition(0.4375F, flowing.getU0(), flowing.getU1());
                    float max_u_2 = getPosition(0.5625F, flowing.getU0(), flowing.getU1());
                    float min_v_2 = getPosition(0.25F, flowing.getV0(), flowing.getV1());
                    float max_v_2 = getPosition(1F, flowing.getV0(), flowing.getV1());

                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, 0.065F, 1.1875F).setColor(r, g, b, a).setUv(min_u_2, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, -0.7435F, 1.1875F).setColor(r, g, b, a).setUv(min_u_2, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, -0.7435F, 1.1875F).setColor(r, g, b, a).setUv(max_u_2, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, 0.065F, 1.1875F).setColor(r, g, b, a).setUv(max_u_2, min_v_2).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_3 = getPosition(0.5625F, flowing.getU0(), flowing.getU1());
                    float max_u_3 = getPosition(0.6875F, flowing.getU0(), flowing.getU1());

                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, 0.065F, 1.1875F).setColor(r, g, b, a).setUv(min_u_3, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, -0.7435F, 1.1875F).setColor(r, g, b, a).setUv(min_u_3, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, -0.7435F, 1.0625F).setColor(r, g, b, a).setUv(max_u_3, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, 0.065F, 1.0625F).setColor(r, g, b, a).setUv(max_u_3, min_v_2).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_4 = getPosition(0.3125F, flowing.getU0(), flowing.getU1());
                    float max_u_4 = getPosition(0.4375F, flowing.getU0(), flowing.getU1());

                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, 0.065F, 1.0625F).setColor(r, g, b, a).setUv(min_u_4, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, -0.7435F, 1.0625F).setColor(r, g, b, a).setUv(min_u_4, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, -0.7435F, 1.1875F).setColor(r, g, b, a).setUv(max_u_4, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, 0.065F, 1.1875F).setColor(r, g, b, a).setUv(max_u_4, min_v_2).setLight(light).setNormal(1.0F, 0, 0);

                    float min_u_5 = getPosition(0.1875F, flowing.getU0(), flowing.getU1());
                    float max_u_5 = getPosition(0.3125F, flowing.getU0(), flowing.getU1());

                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, 0.065F, 1.0625F).setColor(r, g, b, a).setUv(min_u_5, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.5625F, -0.7435F, 1.0625F).setColor(r, g, b, a).setUv(min_u_5, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, -0.7435F, 1.0625F).setColor(r, g, b, a).setUv(max_u_5, max_v_2).setLight(light).setNormal(1.0F, 0, 0);
                 buffer.addVertex(matrixStackIn.last().pose(), 0.4375F, 0.065F, 1.0625F).setColor(r, g, b, a).setUv(max_u_5, min_v_2).setLight(light).setNormal(1.0F, 0, 0);
                }
            }

            matrixStackIn.popPose();
        }
    }

    public static float getPosition(float add, float min, float max) {
        return add * (max - min) + min;
    }
}
