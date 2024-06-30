package cloud.lemonslice.teastory.container;


import cloud.lemonslice.teastory.blockentity.StoneMillTileEntity;
import cloud.lemonslice.teastory.blockentity.StoneRollerTileEntity;
import cloud.lemonslice.teastory.blockentity.StoveTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.client.container.NormalContainer;

public class StoveContainer extends NormalContainer {
    private final StoveTileEntity tileEntity;

    public StoveContainer(int windowId, Inventory playerInv, FriendlyByteBuf data) {
        this(windowId, playerInv, data.readBlockPos(), playerInv.player.getCommandSenderWorld());
    }

    public StoveContainer(int windowId, Inventory inv, BlockPos pos, Level world) {
        super(TileEntityTypeRegistry.STOVE_CONTAINER.get(), windowId, pos, world);
        this.tileEntity = (StoveTileEntity) getTileEntity();
        tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP).ifPresent(h ->
        {
            addSlot(new SlotItemHandler(h, 0, 80, 33));
        });
        tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN).ifPresent(h ->
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
