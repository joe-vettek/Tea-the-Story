package com.teamtea.teastory.blockentity;


import com.teamtea.teastory.config.ServerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import com.teamtea.teastory.TileEntityTypeRegistry;
import com.teamtea.teastory.block.entity.SyncedBlockEntity;


public class WoodenBarrelTileEntity extends SyncedBlockEntity {
    private final FluidTank fluidTank;
    private Fluid remainFluid = Fluids.EMPTY;
    private final int capacity;
    private int heightAmount = 0;

    public WoodenBarrelTileEntity(BlockPos pos, BlockState state) {
        super(TileEntityTypeRegistry.WOODEN_BARREL_TYPE.get(), pos, state);
        this.capacity = ServerConfig.BlockConfig.woodenBarrelCapacity.getAsInt();
        this.fluidTank = createFluidHandler(this.capacity);
    }


    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(tag, pRegistries);
        this.fluidTank.readFromNBT(pRegistries, tag.getCompound("FluidTank"));
        recordPreviousFluid(this.fluidTank.getFluid());
    }

    // write
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider pRegistries) {
        tag.put("FluidTank", this.fluidTank.writeToNBT(pRegistries, new CompoundTag()));
        super.saveAdditional(tag, pRegistries);
    }


    private FluidTank createFluidHandler(int size) {
        return new FluidTank(size) {
            @Override
            protected void onContentsChanged() {
                WoodenBarrelTileEntity.this.recordPreviousFluid(this.fluid);
                // super.onContentsChanged();
                inventoryChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return !stack.getFluid().getFluidType().isLighterThanAir() && stack.getFluid().getFluidType().getTemperature() < 500;
            }
        };
    }

    public FluidTank getFluidTank() {
        return this.fluidTank;
    }

    public Fluid getFluid() {
        return this.fluidTank.getFluid().getFluid();
    }

    public int getFluidAmount() {
        return getFluidTank().getFluidAmount();
    }

    public void setFluidTank(FluidStack stack) {
        this.fluidTank.setFluid(stack);
    }

    public void setFluid(Fluid fluid) {
        if (!fluid.isSame(Fluids.EMPTY)) {
            this.fluidTank.setFluid(new FluidStack(fluid, getFluidAmount()));
            this.inventoryChanged();
        }
    }

    public void recordPreviousFluid(FluidStack fluid) {
        if (!fluid.isEmpty() && fluid.getAmount() > 0) {
            remainFluid = fluid.getFluid();
        }
    }

    public Fluid getRemainFluid() {
        return remainFluid;
    }


    private void updateHeight() {
        if (this.level.isClientSide()) {
            int viscosity = Math.max(this.remainFluid.getFluidType().getViscosity() / 50, 10);
            if (heightAmount > this.getFluidAmount()) {
                heightAmount -= Math.max(1, (heightAmount - this.getFluidAmount()) / viscosity);
            } else if (heightAmount < this.getFluidAmount()) {
                heightAmount += Math.max(1, (this.getFluidAmount() - heightAmount) / viscosity);
            }
        }
    }

    public float getHeight() {
        updateHeight();
        return 0.0625F + 0.875F * this.heightAmount / capacity;
    }
}
