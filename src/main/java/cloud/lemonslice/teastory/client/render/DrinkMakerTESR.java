package cloud.lemonslice.teastory.client.render;


import cloud.lemonslice.teastory.blockentity.DrinkMakerTileEntity;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;


import java.util.List;

public class DrinkMakerTESR implements BlockEntityRenderer<DrinkMakerTileEntity> {

    public DrinkMakerTESR(BlockEntityRendererProvider.Context pContext) {

    }

    @Override
    @SuppressWarnings("deprecation")
    public void render(DrinkMakerTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Minecraft mc = Minecraft.getInstance();

        List<ItemStack> list = tileEntityIn.getContent();

        ItemRenderer renderItem = mc.getItemRenderer();

        matrixStackIn.pushPose();
        Lighting.setupFor3DItems();
        for (int i = 0; i < 5; i++) {
            ItemStack itemStack = list.get(i);
            if (itemStack.isEmpty()) {
                continue;
            }

            matrixStackIn.pushPose();

            switch (tileEntityIn.getFacing()) {
                case NORTH:
                    matrixStackIn.translate(0.35 * (i - 2) + 1, 0.35, 0.5);
                    break;
                case SOUTH:
                    matrixStackIn.translate(0.35 * (2 - i), 0.35, 0.5);
                    break;
                case WEST:
                    matrixStackIn.translate(0.5, 0.35, 0.35 * (2 - i));
                    break;
                default:
                    matrixStackIn.translate(0.5, 0.35, 0.35 * (i - 2) + 1);
            }
            // matrixStackIn.rotate(new Quaternion(Vector3f.YP, 45, true));
            matrixStackIn.mulPose(XYZ.deg_to_rad(0,45,0));

            matrixStackIn.scale(0.5F, 0.5F, 0.5F);

            Lighting.setupForFlatItems();
            renderItem.renderStatic(itemStack, ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, mc.level, 0);
            Lighting.setupFor3DItems();
            matrixStackIn.popPose();
        }
        matrixStackIn.popPose();
    }
}
