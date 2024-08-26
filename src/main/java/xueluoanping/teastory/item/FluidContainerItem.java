package xueluoanping.teastory.item;

import cloud.lemonslice.teastory.tag.NormalTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import org.jetbrains.annotations.NotNull;
import xueluoanping.teastory.ModCapabilities;

import javax.annotation.Nonnull;

public interface FluidContainerItem {

    int getCapacity();

    Item getCraftingRemainingItem();

    default IFluidHandlerItem transferToFluidHandler(@NotNull ItemStack stack) {
        return new FluidHandlerItemStack(ModCapabilities.SIMPLE_FLUID, stack, getCapacity()) {

            @Override
            public @NotNull ItemStack getContainer() {
                return getFluid().isEmpty() ? new ItemStack(getCraftingRemainingItem()) : this.container;
            }

            @Override
            public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
                return stack.getFluid().is(NormalTags.Fluids.DRINK);
                // return true;
            }
        };
    }
}
