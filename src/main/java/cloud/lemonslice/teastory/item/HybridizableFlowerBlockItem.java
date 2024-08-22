package cloud.lemonslice.teastory.item;


import cloud.lemonslice.teastory.block.crops.TrellisBlock;
import cloud.lemonslice.teastory.block.crops.TrellisWithVineBlock;
import cloud.lemonslice.teastory.block.crops.VineInfoManager;
import cloud.lemonslice.teastory.block.crops.VineType;
import cloud.lemonslice.teastory.block.crops.flower.FlowerColor;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;


public class HybridizableFlowerBlockItem extends BlockItem {
    public HybridizableFlowerBlockItem(Block blockIn, Item.Properties pProperties) {
        super(blockIn, pProperties);
    }

    public void fillItemGroup(CreativeModeTab.Output group) {
        for (var f : FlowerColor.values()) {
            ItemStack itemStack = new ItemStack(this);
            itemStack.getOrCreateTag().putString("color", f.getString());
            group.accept(itemStack);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @org.jetbrains.annotations.Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        if (pStack.getOrCreateTag().contains("color")) {
            pTooltip.add(Component.translatable(FlowerColor.getFlowerColor(pStack.getOrCreateTag().getString("color")).getTranslation()).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        }
         if (pStack.getTag().contains("BlockStateTag")
                && pStack.getTag().getCompound("BlockStateTag").contains("color")) {
             pTooltip.add(Component.translatable(FlowerColor.getFlowerColor(pStack.getOrCreateTag().getCompound("BlockStateTag").getString("color")).getTranslation()).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        }
    }


}