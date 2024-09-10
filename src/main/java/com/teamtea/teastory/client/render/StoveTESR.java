package com.teamtea.teastory.client.render;


import com.teamtea.teastory.blockentity.StoveTileEntity;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;


public class StoveTESR implements BlockEntityRenderer<StoveTileEntity> {
    public StoveTESR(BlockEntityRendererProvider.Context pContext) {
    }

    @Override
    @SuppressWarnings("deprecation")
    public void render(StoveTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Minecraft mc = Minecraft.getInstance();

        NonNullList<ItemStack> list = tileEntityIn.getContents();

        if (list.isEmpty()) {
            return;
        }

        ItemRenderer renderItem = mc.getItemRenderer();

        matrixStackIn.pushPose();
        Lighting.setupFor3DItems();

        matrixStackIn.translate(0.5, 0.13, 0.5);
        int count = 0;

        for (ItemStack stack : list) {
            matrixStackIn.pushPose();
            int seed = count * 4447;

            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            matrixStackIn.translate(((seed % 100) - 50) / 200D, count / 16D, ((seed % 56) - 28) / 112D);
            // matrixStackIn.rotate(new Quaternion(Vector3f.YP, 180 * (seed % 943) / 943F, true));
            matrixStackIn.mulPose(XYZ.deg_to_rad(0,180 * (seed % 943) / 943F,0));

            Lighting.setupForFlatItems();
            renderItem.renderStatic(stack, ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, mc.level, 0);
            Lighting.setupFor3DItems();
            matrixStackIn.popPose();

            count++;
        }

        matrixStackIn.popPose();
    }
}
