package com.teamtea.teastory.blockentity;


import com.teamtea.teastory.config.ServerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import com.teamtea.teastory.registry.BlockEntityRegister;
import com.teamtea.teastory.blockentity.base.SyncedBlockEntity;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;


public class WoodenBarrelBlockEntity extends SyncedBlockEntity {
    private final FluidStacksResourceHandler fluidTank;
    private Fluid remainFluid = Fluids.EMPTY;
    private final int capacity;
    private int heightAmount = 0;

    public WoodenBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegister.WOODEN_BARREL_TYPE.get(), pos, state);
        this.capacity = ServerConfig.BlockConfig.woodenBarrelCapacity.getAsInt();
        this.fluidTank = createFluidHandler(this.capacity);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.fluidTank.deserialize(input.childOrEmpty("FluidTank"));
        recordPreviousFluid(new FluidStack(this.fluidTank.getResource(0).getFluid(),this.fluidTank.getAmountAsInt(0)));
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        this.fluidTank.serialize(output.child("FluidTank"));
    }

    private FluidStacksResourceHandler createFluidHandler(int size) {
        return new FluidStacksResourceHandler(1, size) {

            @Override
            protected void onContentsChanged(int index, FluidStack previousContents) {
                super.onContentsChanged(index, previousContents);
                WoodenBarrelBlockEntity.this.recordPreviousFluid(previousContents);
                inventoryChanged();
            }

            @Override
            public boolean isValid(int index, FluidResource resource) {
                return !resource.getFluid().getFluidType().isLighterThanAir() && resource.getFluid().getFluidType().getTemperature() < 500;
            }
        };
    }

    public FluidStacksResourceHandler getFluidTank() {
        return this.fluidTank;
    }

    public Fluid getFluid() {
        return this.fluidTank.getResource(0).getFluid();
    }

    public int getFluidAmount() {
        return getFluidTank().getAmountAsInt(0);
    }


    public void setFluid(Fluid fluid) {
        if (!fluid.isSame(Fluids.EMPTY)) {
            this.fluidTank.set(0, FluidResource.of(fluid), getFluidAmount());
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
