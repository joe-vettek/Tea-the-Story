package com.teamtea.teastory.client.render;

import com.teamtea.teastory.block.craft.CatapultBoardBlockWithTray;
import com.teamtea.teastory.blockentity.BambooTrayBlockEntity;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;


public class BambooTrayTESR implements BlockEntityRenderer<BambooTrayBlockEntity> {

    public BambooTrayTESR(BlockEntityRendererProvider.Context pContext) {
    }

    @Override
    @SuppressWarnings("deprecation")
    public void render(BambooTrayBlockEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Minecraft mc = Minecraft.getInstance();

        ItemStack itemStack = tileEntityIn.getInput();

        if (itemStack.isEmpty()) {
            return;
        }

        var renderItem = mc.getItemRenderer();

        matrixStackIn.pushPose();
        Lighting.setupFor3DItems();
        double h = 0.125;
        if (tileEntityIn.getBlockState().getBlock() instanceof CatapultBoardBlockWithTray) {
            h += 0.125;
        }
        matrixStackIn.translate(0.5, h, 0.5);

        int seed = tileEntityIn.getRandomSeed();

        matrixStackIn.scale(0.5F, 0.5F, 0.5F);
        matrixStackIn.translate(((seed % 100) - 50) / 200D, 0, ((seed % 56) - 28) / 112D);
        // matrixStackIn.rotate(new Quaternion(Vector3f.YP, 360 * (seed % 943) / 943F, true));
        matrixStackIn.mulPose(XYZ.deg_to_rad(0,360 * (seed % 943) / 943F,0));

        // matrixStackIn.rotate(new Quaternion(Vector3f.XP, 90, true));
        matrixStackIn.mulPose(XYZ.deg_to_rad(90,0,0));

        Lighting.setupForFlatItems();
        renderItem.renderStatic(itemStack, ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, Minecraft.getInstance().level, 0);
        Lighting.setupFor3DItems();
        matrixStackIn.popPose();
    }


}
