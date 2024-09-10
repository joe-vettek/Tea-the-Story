package com.teamtea.teastory.container;

import com.teamtea.teastory.blockentity.DrinkMakerTileEntity;
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
import com.teamtea.teastory.TileEntityTypeRegistry;
import com.teamtea.teastory.client.container.NormalContainer;

import java.util.Optional;

public class DrinkMakerContainer extends NormalContainer {
    private final DrinkMakerTileEntity tileEntity;

    public DrinkMakerContainer(int windowId, Inventory playerInv, FriendlyByteBuf data) {
        this(windowId, playerInv, data.readBlockPos(), playerInv.player.getCommandSenderWorld());
    }

    public DrinkMakerContainer(int windowId, Inventory inv, BlockPos pos, Level world) {
        super(TileEntityTypeRegistry.DRINK_MAKER_CONTAINER.get(), windowId, pos, world);
        this.tileEntity = (DrinkMakerTileEntity) super.getTileEntity();
        Optional.ofNullable(world.getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.UP)).ifPresent(h ->
        {
            addSlot(new SlotItemHandler(h, 0, 21, 24));
            addSlot(new SlotItemHandler(h, 1, 39, 24));
            addSlot(new SlotItemHandler(h, 2, 57, 24));
            addSlot(new SlotItemHandler(h, 3, 75, 24));
        });
        Optional.ofNullable(world.getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.DOWN)).ifPresent(h ->
        {
            addSlot(new SlotItemHandler(h, 0, 21, 51));
            addSlot(new SlotItemHandler(h, 1, 39, 51));
            addSlot(new SlotItemHandler(h, 2, 57, 51));
            addSlot(new SlotItemHandler(h, 3, 75, 51));
        });
        addPlayerInventory(inv);
        Optional.ofNullable(tileEntity.getContainerInventory()).ifPresent(h -> addSlot(new SlotItemHandler(h, 0, 102, 13)));
        Optional.ofNullable(tileEntity.getInputInventory()).ifPresent(h -> addSlot(new SlotItemHandler(h, 0, 152, 15)));
        Optional.ofNullable(tileEntity.getOutputInventory()).ifPresent(h -> addSlot(new SlotItemHandler(h, 0, 152, 59)));
    }


    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot slot = slots.get(index);

        if (slot == null || !slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack newStack = slot.getItem(), oldStack = newStack.copy();

        boolean isMerged;

        // 0~4: Input; 4~8: Residue; 8~35: Player Backpack; 35~44: Hot Bar; 44~45: Container; 45~46: Cup In; 46~47: Cup Out.

        if (index < 4) {
            isMerged = moveItemStackTo(newStack, 35, 44, true)
                    || moveItemStackTo(newStack, 8, 35, false);
        } else if (index < 8) {
            isMerged = moveItemStackTo(newStack, 35, 44, true)
                    || moveItemStackTo(newStack, 8, 35, false);
        } else if (index < 35) {
            isMerged = moveItemStackTo(newStack, 0, 4, false)
                    || moveItemStackTo(newStack, 35, 44, true);
        } else if (index < 44) {
            isMerged = moveItemStackTo(newStack, 0, 4, false)
                    || moveItemStackTo(newStack, 8, 35, false);
        } else {
            isMerged = moveItemStackTo(newStack, 35, 44, true)
                    || moveItemStackTo(newStack, 8, 35, false);
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

    public DrinkMakerTileEntity getTileEntity() {
        return this.tileEntity;
    }
}
