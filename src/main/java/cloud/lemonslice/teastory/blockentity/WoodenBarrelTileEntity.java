package cloud.lemonslice.teastory.blockentity;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.entity.NormalContainerTileEntity;
import xueluoanping.teastory.block.entity.SyncedBlockEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class WoodenBarrelTileEntity extends SyncedBlockEntity
{
    private final LazyOptional<FluidTank> fluidTank = LazyOptional.of(this::createFluidHandler);
    private Fluid remainFluid = Fluids.EMPTY;
    private final int capacity;
    private int heightAmount = 0;

    public WoodenBarrelTileEntity(BlockPos pos, BlockState state)
    {
        super(TileEntityTypeRegistry.WOODEN_BARREL_TYPE.get(),pos,state);
        this.capacity = 4000;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (!this.isRemoved())
        {
            if (ForgeCapabilities.FLUID_HANDLER.equals(cap))
            {
                return fluidTank.cast();
            }
        }
        return super.getCapability(cap, side);
    }


    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.fluidTank.ifPresent(f ->
        {
            f.readFromNBT(tag.getCompound("FluidTank"));
            recordPreviousFluid(f.getFluid());
        });
    }

    // write
    @Override
    protected void saveAdditional(CompoundTag tag) {
        fluidTank.ifPresent(f -> tag.put("FluidTank", f.writeToNBT(new CompoundTag())));
        super.saveAdditional(tag);
    }


    private FluidTank createFluidHandler()
    {
        return new FluidTank(capacity)
        {
            @Override
            protected void onContentsChanged()
            {
                WoodenBarrelTileEntity.this.recordPreviousFluid(this.fluid);
                // super.onContentsChanged();
                this.onContentsChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack)
            {
                return !stack.getFluid().getFluidType().isLighterThanAir() && stack.getFluid().getFluidType().getTemperature() < 500;
            }
        };
    }

    public FluidTank getFluidTank()
    {
        return this.fluidTank.orElse(new FluidTank(0));
    }

    public Fluid getFluid()
    {
        return this.fluidTank.map(f -> f.getFluid().getFluid()).orElse(Fluids.EMPTY);
    }

    public int getFluidAmount()
    {
        return getFluidTank().getFluidAmount();
    }

    public void setFluidTank(FluidStack stack)
    {
        this.fluidTank.ifPresent(f -> f.setFluid(stack));
    }

    public void setFluid(Fluid fluid)
    {
        this.fluidTank.ifPresent(f -> f.setFluid(new FluidStack(fluid, getFluidAmount())));
        this.inventoryChanged();
    }

    public void recordPreviousFluid(FluidStack fluid)
    {
        if (!fluid.isEmpty() && fluid.getAmount() > 0)
        {
            remainFluid = fluid.getFluid();
        }
    }

    public Fluid getRemainFluid()
    {
        return remainFluid;
    }



    private void updateHeight()
    {
        if (this.level.isClientSide())
        {
            int viscosity = Math.max(this.remainFluid.getFluidType().getViscosity() / 50, 10);
            if (heightAmount > this.getFluidAmount())
            {
                heightAmount -= Math.max(1, (heightAmount - this.getFluidAmount()) / viscosity);
            }
            else if (heightAmount < this.getFluidAmount())
            {
                heightAmount += Math.max(1, (this.getFluidAmount() - heightAmount) / viscosity);
            }
        }
    }

    public float getHeight()
    {
        updateHeight();
        return 0.0625F + 0.875F * this.heightAmount / capacity;
    }
}
