package com.teamtea.teastory.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;

/**
 * Simple BlockEntity with networking boilerplate. (Farmer's Delight)
 */
public class SyncedBlockEntity extends BlockEntity {
    public SyncedBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
//
//	@Override
//	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
//		load(pkt.getTag());
//	}

    protected void inventoryChanged() {
        super.setChanged();
        if (level != null)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }


    public class SyncedItemStackHandler extends ItemStackHandler {
        public SyncedItemStackHandler() {
            this(1);
        }
        public SyncedItemStackHandler(int i) {
            super(i);
        }

        @Override
        protected void onContentsChanged(int slot) {
            // super.onContentsChanged(slot);
            inventoryChanged();
        }

    }

    public class SyncedFluidTank extends FluidTank {

        public SyncedFluidTank(int capacity) {
            super(capacity);
        }

        @Override
        protected void onContentsChanged() {
            // super.onContentsChanged();
            inventoryChanged();
        }

    }
}
