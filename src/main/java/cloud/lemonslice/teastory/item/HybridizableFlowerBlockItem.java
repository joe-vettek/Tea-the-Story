package cloud.lemonslice.teastory.item;


import cloud.lemonslice.teastory.block.crops.*;
import cloud.lemonslice.teastory.block.crops.flower.FlowerColor;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nullable;
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