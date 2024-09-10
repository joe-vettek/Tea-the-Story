package com.teamtea.teastory.craft;


import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;


import java.util.List;

public class MultiRecipeWrapper extends RecipeWrapper {

    public final List<ItemStackHandler> slots;

    public MultiRecipeWrapper(ItemStackHandler... handlers) {
        super(new ItemStackHandler(0));
        slots= List.of(handlers);
    }

    @Override
    public ItemStack getItem(int slot) {
        for (int i = 0; i < slots.size(); i++) {
            var theSlot = slots.get(i);
            if (theSlot.getSlots() > slot)
                return theSlot.getStackInSlot(slot);
            slot -= theSlot.getSlots();
        }
        throw new ArrayIndexOutOfBoundsException(slot);
    }
}
