package cloud.lemonslice.teastory.container;


import cloud.lemonslice.teastory.blockentity.StoneMillTileEntity;
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


public class StoneMillContainer extends NormalContainer {
    private final StoneMillTileEntity tileEntity;


    public StoneMillContainer(int windowId, Inventory playerInv, FriendlyByteBuf data) {
        this(windowId, playerInv, data.readBlockPos(), playerInv.player.getCommandSenderWorld());
    }

    public StoneMillContainer(int windowId, Inventory inv, BlockPos pos, Level world) {
        super(TileEntityTypeRegistry.STONE_MILL_CONTAINER.get(), windowId, pos, world);
        this.tileEntity = (StoneMillTileEntity) getTileEntity();
        tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP).ifPresent(h ->
        {
            addSlot(new SlotItemHandler(h, 0, 73, 38));
        });
        tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN).ifPresent(h ->
        {
            addSlot(new SlotItemHandler(h, 0, 123, 20));
            addSlot(new SlotItemHandler(h, 1, 123, 38));
            addSlot(new SlotItemHandler(h, 2, 123, 56));
        });
        addPlayerInventory(inv);
    }


    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot slot = slots.get(index);

        if (slot == null || !slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack newStack = slot.getItem(), oldStack = newStack.copy();

        boolean isMerged;

        // 0~1: Input; 1~4: Output; 4~31: Player Backpack; 31~40: Hot Bar.

        if (index == 0) {
            // mergeItemStack
            isMerged = moveItemStackTo(newStack, 31, 40, true)
                    || moveItemStackTo(newStack, 4, 31, false);
        } else if (index < 4) {
            isMerged = moveItemStackTo(newStack, 31, 40, true)
                    || moveItemStackTo(newStack, 4, 31, false);
        } else if (index < 31) {
            isMerged = moveItemStackTo(newStack, 0, 1, false)
                    || moveItemStackTo(newStack, 31, 40, true);
        } else {
            isMerged = moveItemStackTo(newStack, 0, 1, false)
                    || moveItemStackTo(newStack, 4, 31, false);
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
