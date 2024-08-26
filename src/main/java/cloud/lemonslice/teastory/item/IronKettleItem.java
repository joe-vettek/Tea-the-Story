package cloud.lemonslice.teastory.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;


import javax.annotation.Nullable;
import java.util.List;


public class IronKettleItem extends TeapotItem
{
    public IronKettleItem(Block block, Item.Properties properties, int capacity)
    {
        super(block,properties, capacity, true);
    }


    @Override
    public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        var nbt = stack.getOrCreateTag().getCompound(FLUID_NBT_KEY);
        if (nbt != null)
        {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(nbt);
            if (fluidStack.getFluid() == Fluids.WATER)
            {
                tooltip.add(Component.translatable("info.teastory.tooltip.iron_kettle.to_boil").withStyle(ChatFormatting.GRAY));
            }
        }
        else
        {
            tooltip.add(Component.translatable("info.teastory.tooltip.iron_kettle.to_fill").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
