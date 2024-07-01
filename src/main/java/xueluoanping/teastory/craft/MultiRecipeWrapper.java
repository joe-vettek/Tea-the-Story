package xueluoanping.teastory.craft;


import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.lang.ref.WeakReference;
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
