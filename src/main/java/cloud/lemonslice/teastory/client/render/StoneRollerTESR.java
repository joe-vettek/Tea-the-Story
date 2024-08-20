package cloud.lemonslice.teastory.client.render;


import cloud.lemonslice.teastory.blockentity.StoneRollerTileEntity;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xueluoanping.teastory.ItemRegister;
import xueluoanping.teastory.TeaStory;


public class StoneRollerTESR implements BlockEntityRenderer<StoneRollerTileEntity> {
    public StoneRollerTESR(BlockEntityRendererProvider.Context pContext) {
    }

    @Override
    public void render(StoneRollerTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Minecraft mc = Minecraft.getInstance();

        ItemRenderer renderItem = mc.getItemRenderer();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5, 0.5, 0.5);
        float woodenFrameAngel = tileEntityIn.getWoodenFrameAngel();

        if (tileEntityIn.isWorking()) {
            woodenFrameAngel += partialTicks * 3.0F;
        }
        float stoneAngel = tileEntityIn.getStoneAngel();
        if (tileEntityIn.isWorking()) {
            stoneAngel += partialTicks * 4.0F;
        }

        matrixStackIn.pushPose();
        // matrixStackIn.rotate(new Quaternion(Vector3f.YP, woodenFrameAngel, true));
        matrixStackIn.mulPose(XYZ.deg_to_rad(0,woodenFrameAngel,0));

        // matrixStackIn.rotate(new Quaternion(Vector3f.ZP, stoneAngel, true));
        matrixStackIn.mulPose(XYZ.deg_to_rad(0,0,stoneAngel));

        // Lighting.setupForFlatItems();
        int seed=(int)(tileEntityIn.getBlockState().getBlock().getSeed(tileEntityIn.getBlockState(), tileEntityIn.getBlockPos()));
        renderItem.renderStatic(new ItemStack(ItemRegister.STONE_ROLLER_TOP.get()), ItemDisplayContext.FIXED,  combinedLightIn, combinedOverlayIn,matrixStackIn, bufferIn, tileEntityIn.getLevel(),seed );
        // Lighting.setupFor3DItems();
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        // matrixStackIn.rotate(new Quaternion(Vector3f.YP, woodenFrameAngel, true));
        matrixStackIn.mulPose(XYZ.deg_to_rad(0,woodenFrameAngel,0));

        Lighting.setupForFlatItems();
        renderItem.renderStatic(new ItemStack(ItemRegister.STONE_ROLLER_WOODEN_FRAME.get()), ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, mc.level, 0);
        Lighting.setupFor3DItems();
        matrixStackIn.popPose();

        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        ItemStack item = tileEntityIn.getStackInSlot(0).copy();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                matrixStackIn.pushPose();
                matrixStackIn.translate(0.3 + 0.4 * i, 0.32, 0.3 + 0.4 * j);
                matrixStackIn.scale(0.5F, 0.5F, 0.5F);
                // matrixStackIn.rotate(new Quaternion(Vector3f.YP, 127 * (i + 133 * j) % 360, true));
                matrixStackIn.mulPose(XYZ.deg_to_rad(0,127 * (i + 133 * j) % 360,0));
                // matrixStackIn.rotate(new Quaternion(Vector3f.XP, 90, true));
                matrixStackIn.mulPose(XYZ.deg_to_rad(90,0,0));

                Lighting.setupForFlatItems();
                renderItem.renderStatic(item, ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, mc.level, 0);
                Lighting.setupFor3DItems();

                matrixStackIn.popPose();
            }
        }
        matrixStackIn.popPose();
    }
}
