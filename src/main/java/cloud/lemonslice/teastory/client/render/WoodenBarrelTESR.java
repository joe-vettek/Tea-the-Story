package cloud.lemonslice.teastory.client.render;


import cloud.lemonslice.teastory.blockentity.WoodenBarrelTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

public class WoodenBarrelTESR implements BlockEntityRenderer<WoodenBarrelTileEntity> {
    public WoodenBarrelTESR(BlockEntityRendererProvider.Context pContext) {

    }

    @Override
    public void render(WoodenBarrelTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Minecraft mc = Minecraft.getInstance();

        Fluid fluid = tileEntityIn.getRemainFluid();
        if (fluid != Fluids.EMPTY && tileEntityIn.getHeight() > 0.0625F) {
            matrixStackIn.pushPose();

            VertexConsumer buffer = bufferIn.getBuffer(RenderType.translucentMovingBlock());

            TextureAtlasSprite still = mc.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(IClientFluidTypeExtensions.of(fluid).getStillTexture());

            int color = IClientFluidTypeExtensions.of(fluid).getTintColor();
            int r = color >> 16 & 0xFF;
            int g = color >> 8 & 0xFF;
            int b = color & 0xFF;
            int a = color >> 24 & 0xFF;

            float height = tileEntityIn.getHeight();


            int light = LevelRenderer.getLightColor(tileEntityIn.getLevel(), tileEntityIn.getBlockPos());

            buffer.addVertex(matrixStackIn.last().pose(), 0.125F, height, 0.125F).setColor(r, g, b, a).setUv(still.getU0(), still.getV0()).setLight(light).setNormal(1.0F, 0, 0);
            buffer.addVertex(matrixStackIn.last().pose(), 0.125F, height, 0.875F).setColor(r, g, b, a).setUv(still.getU0(), still.getV1()).setLight(light).setNormal(1.0F, 0, 0);
            buffer.addVertex(matrixStackIn.last().pose(), 0.875F, height, 0.875F).setColor(r, g, b, a).setUv(still.getU1(), still.getV1()).setLight(light).setNormal(1.0F, 0, 0);
            buffer.addVertex(matrixStackIn.last().pose(), 0.875F, height, 0.125F).setColor(r, g, b, a).setUv(still.getU1(), still.getV0()).setLight(light).setNormal(1.0F, 0, 0);

            matrixStackIn.popPose();
        }
    }
}
