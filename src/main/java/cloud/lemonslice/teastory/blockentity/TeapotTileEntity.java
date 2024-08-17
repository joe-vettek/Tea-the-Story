package cloud.lemonslice.teastory.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.entity.NormalContainerTileEntity;
import xueluoanping.teastory.block.entity.SyncedBlockEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class TeapotTileEntity extends SyncedBlockEntity
{
    private final LazyOptional<FluidTank> fluidTank = LazyOptional.of(this::createFluidHandler);
    private final int capacity;

    public TeapotTileEntity(int capacity, BlockPos pos, BlockState state)
    {
        super(getTeapotTileEntityType(capacity), pos,  state);
        this.capacity = capacity;
    }

    public static BlockEntityType<?> getTeapotTileEntityType(int capacity)
    {
        if (capacity == 2000)
        {
            return TileEntityTypeRegistry.IRON_KETTLE_TYPE.get();
        }
        return  TileEntityTypeRegistry.TEAPOT_TYPE.get();
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
        this.fluidTank.ifPresent(f -> f.readFromNBT(tag.getCompound("FluidTank")));
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
                inventoryChanged();
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
       inventoryChanged();
    }


}
