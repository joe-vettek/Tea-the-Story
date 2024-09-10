package com.teamtea.teastory.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.CampfireRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;

public class StoneCampfireRenderer  implements BlockEntityRenderer<CampfireBlockEntity> {
    private static final float SIZE = 0.375F;
    private final ItemRenderer itemRenderer;

    public StoneCampfireRenderer(BlockEntityRendererProvider.Context pContext) {
        this.itemRenderer = pContext.getItemRenderer();
    }

    public void render(CampfireBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        Direction direction = pBlockEntity.getBlockState().getValue(CampfireBlock.FACING);
        NonNullList<ItemStack> nonnulllist = pBlockEntity.getItems();
        int i = (int)pBlockEntity.getBlockPos().asLong();

        for (int j = 0; j < nonnulllist.size(); j++) {
            ItemStack itemstack = nonnulllist.get(j);
            if (itemstack != ItemStack.EMPTY) {
                pPoseStack.pushPose();
                BakedModel bakedmodel = itemRenderer.getModel(itemstack, pBlockEntity.getLevel(), Minecraft.getInstance().player, i + j);
                pPoseStack.translate(0.5F, bakedmodel.isGui3d()? 0.18921875F: 0.10921875F, 0.5F);
                Direction direction1 = Direction.from2DDataValue((j + direction.get2DDataValue()) % 4);
                float f = -direction1.toYRot();
                pPoseStack.mulPose(Axis.YP.rotationDegrees(f));
                pPoseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                pPoseStack.translate(-0.3125F, -0.3125F, 0.0F);
                pPoseStack.scale(0.375F, 0.375F, 0.375F);
                this.itemRenderer.renderStatic(itemstack, ItemDisplayContext.FIXED, pPackedLight, pPackedOverlay, pPoseStack, pBufferSource, pBlockEntity.getLevel(), i + j);
                pPoseStack.popPose();
            }
        }
    }
}
