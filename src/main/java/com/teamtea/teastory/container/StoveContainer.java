package com.teamtea.teastory.container;


import com.teamtea.teastory.blockentity.StoveBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.SlotItemHandler;
import com.teamtea.teastory.registry.BlockEntityRegister;
import com.teamtea.teastory.client.container.NormalContainer;

import java.util.Optional;

public class StoveContainer extends NormalContainer {
    private final StoveBlockEntity tileEntity;

    public StoveContainer(int windowId, Inventory playerInv, FriendlyByteBuf data) {
        this(windowId, playerInv, data.readBlockPos(), playerInv.player.getCommandSenderWorld());
    }

    public StoveContainer(int windowId, Inventory inv, BlockPos pos, Level world) {
        super(BlockEntityRegister.STOVE_CONTAINER.get(), windowId, pos, world);
        this.tileEntity = (StoveBlockEntity) getTileEntity();
        Optional.ofNullable(world.getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.UP)).ifPresent(h ->
        {
            addSlot(new SlotItemHandler(h, 0, 80, 33));
        });
        Optional.ofNullable(world.getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.DOWN)).ifPresent(h ->
        {
            addSlot(new SlotItemHandler(h, 0, 80, 61) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
        });
        for (int i = 0; i < 3; ++i) {

            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 51 + i * 18 + 33));
            }
        }

        for (int i = 0; i < 9; ++i) {
            addSlot(new Slot(inv, i, 8 + i * 18, 142));
        }
    }


    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot slot = slots.get(index);

        if (slot == null || !slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack newStack = slot.getItem(), oldStack = newStack.copy();

        boolean isMerged;

        // 0~1: Fuel; 1~2: Ash 2~29: Player Backpack; 29~38: Hot Bar.

        if (index < 2) {
            isMerged = moveItemStackTo(newStack, 29, 38, true)
                    || moveItemStackTo(newStack, 2, 29, false);
        } else if (index < 29) {
            isMerged = moveItemStackTo(newStack, 0, 1, false)
                    || moveItemStackTo(newStack, 29, 38, true);
        } else {
            isMerged = moveItemStackTo(newStack, 0, 1, false)
                    || moveItemStackTo(newStack, 2, 29, false);
        }

        if (!isMerged) {
            return ItemStack.EMPTY;
        }

        if (newStack.getCount() == 0) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        slot.onTake(playerIn, newStack);

        return oldStack;
    }

}
