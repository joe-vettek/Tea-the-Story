package cloud.lemonslice.teastory.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.jetbrains.annotations.NotNull;


import java.util.List;


public class IronKettleItem extends TeapotItem {
    public IronKettleItem(Block block, Item.Properties properties, int capacity) {
        super(block, properties, capacity, true);
    }


    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flagIn) {
        FluidUtil.getFluidHandler(stack).ifPresent(fluidHandlerItem ->
        {
            FluidStack fluidStack = fluidHandlerItem.getFluidInTank(0);
            if (!fluidStack.isEmpty()) {
                if (fluidStack.getFluid() == Fluids.WATER) {
                    tooltip.add(Component.translatable("info.teastory.tooltip.iron_kettle.to_boil").withStyle(ChatFormatting.GRAY));
                }
            } else {
                tooltip.add(Component.translatable("info.teastory.tooltip.iron_kettle.to_fill").withStyle(ChatFormatting.GRAY));
            }
        });

        super.appendHoverText(stack, tooltipContext, tooltip, flagIn);
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return true;
    }
}
