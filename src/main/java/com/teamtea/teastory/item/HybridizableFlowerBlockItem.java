package com.teamtea.teastory.item;


import com.teamtea.teastory.block.crops.*;
import com.teamtea.teastory.block.crops.flower.FlowerColor;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.Block;

import java.util.List;


public class HybridizableFlowerBlockItem extends BlockItem {
    public HybridizableFlowerBlockItem(Block blockIn, Item.Properties pProperties) {
        super(blockIn, pProperties);
    }

    public void fillItemGroup(CreativeModeTab.Output group) {
        var state = getBlock().defaultBlockState();
        for (var f : FlowerColor.values()) {
            ItemStack itemStack = new ItemStack(this);
            // itemStack.getOrCreateTag().putString("color", f.getString());
            itemStack.update(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY,
                    blockItemStateProperties ->
                            blockItemStateProperties
                                    .with(HybridizableFlowerBlock.FLOWER_COLOR, state.setValue(HybridizableFlowerBlock.FLOWER_COLOR, f)));
            group.accept(itemStack);
        }
    }


    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pContext, pTooltip, pFlag);
        if (pStack.has(DataComponents.BLOCK_STATE)) {
            pTooltip.add(Component.translatable(pStack.get(DataComponents.BLOCK_STATE).get(HybridizableFlowerBlock.FLOWER_COLOR).getTranslation()).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        }
    }


}