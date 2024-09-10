package com.teamtea.teastory.client.container;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class NormalContainer <T extends AbstractContainerMenu > extends AbstractContainerMenu {
    private final BlockPos pos;
    private final Level world;

    public NormalContainer(MenuType<T> menuType, int windowId, BlockPos pos, Level world) {
        super(menuType, windowId);
        this.pos = pos;
        this.world = world;
    }


    public void addPlayerInventory(Inventory inv) {
        for (int i = 0; i < 3; ++i) {

            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 51 + i * 18 + 33));
            }
        }

        for (int i = 0; i < 9; ++i) {
            addSlot(new Slot(inv, i, 8 + i * 18, 142));
        }
    }

    // canInteractWith
    @Override
    public boolean stillValid(Player playerIn) {
        return AbstractContainerMenu.stillValid(ContainerLevelAccess.create(this.world, this.pos),
                playerIn,
                this.world.getBlockState(this.pos).getBlock());
    }

    public BlockEntity getTileEntity() {
        return this.world.getBlockEntity(this.pos);
    }
}
