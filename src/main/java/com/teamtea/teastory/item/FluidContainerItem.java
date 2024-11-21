package com.teamtea.teastory.item;

import com.teamtea.teastory.tag.TeaTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import org.jetbrains.annotations.NotNull;
import com.teamtea.teastory.registry.ModCapabilities;

import javax.annotation.Nonnull;

public interface FluidContainerItem {

    int getCapacity();

    default Item getRemainingItem(){
        return (Item) this;
    }

    default ItemStack getHolderItem(ItemStack stack){
        return stack;
    };

    default boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        return stack.getFluid().is(TeaTags.Fluids.DRINK);
    }

    default IFluidHandlerItem transferToFluidHandler(@NotNull ItemStack stack) {
        return new FluidHandlerItemStack(ModCapabilities.SIMPLE_FLUID, stack, getCapacity()) {

            @Override
            public int fill(FluidStack resource, FluidAction doFill) {
                return super.fill(resource, doFill);
            }

            @Override
            public @NotNull ItemStack getContainer() {
                return getFluid().isEmpty() ?
                        new ItemStack(getRemainingItem()) :
                        getHolderItem(this.container);
            }

            @Override
            public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
               return FluidContainerItem.this.isFluidValid(tank, stack);
            }
        };
    }
}
