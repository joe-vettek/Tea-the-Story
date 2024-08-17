package cloud.lemonslice.teastory.blockentity;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.entity.SyncedBlockEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class TeaCupTileEntity extends SyncedBlockEntity
{
    private final List<LazyOptional<FluidTank>> fluidTanks = Lists.newArrayList();

    public TeaCupTileEntity(int capacity, BlockPos pos, BlockState state)
    {
        super(TileEntityTypeRegistry.WOODEN_TRAY_TYPE.get(),pos,state);
        for (int i = 0; i < 3; i++)
        {
            fluidTanks.add(LazyOptional.of(() -> createFluidHandler(capacity)));
        }
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        return super.getCapability(cap, side);
    }
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        fluidTanks.get(0).ifPresent(f -> f.readFromNBT(tag.getCompound("FluidTank_0")));
        fluidTanks.get(1).ifPresent(f -> f.readFromNBT(tag.getCompound("FluidTank_1")));
        fluidTanks.get(2).ifPresent(f -> f.readFromNBT(tag.getCompound("FluidTank_2")));
    }

    // write
    @Override
    protected void saveAdditional(CompoundTag tag) {
        fluidTanks.get(0).ifPresent(f -> tag.put("FluidTank_0", f.writeToNBT(new CompoundTag())));
        fluidTanks.get(1).ifPresent(f -> tag.put("FluidTank_1", f.writeToNBT(new CompoundTag())));
        fluidTanks.get(2).ifPresent(f -> tag.put("FluidTank_2", f.writeToNBT(new CompoundTag())));
        super.saveAdditional(tag);
    }

    private FluidTank createFluidHandler(int capacity)
    {
        return new FluidTank(capacity)
        {
            @Override
            protected void onContentsChanged()
            {
                // TeaCupTileEntity.this.refresh();
                // TeaCupTileEntity.this.markDirty();
                // super.onContentsChanged();
                TeaCupTileEntity.this.inventoryChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack)
            {
                return !stack.getFluid().getFluidType().isLighterThanAir() && stack.getFluid().getFluidType().getTemperature() < 500;
            }
        };
    }

    public FluidTank getFluidTank(int index)
    {
        return this.fluidTanks.get(index).orElse(new FluidTank(0));
    }

    public Fluid getFluid(int index)
    {
        return this.fluidTanks.get(index).map(f -> f.getFluid().getFluid()).orElse(Fluids.EMPTY);
    }

    public int getFluidAmount(int index)
    {
        return getFluidTank(index).getFluidAmount();
    }

    public void setFluidTank(int index, FluidStack stack)
    {
        this.fluidTanks.get(index).ifPresent(f -> f.setFluid(stack));
    }

    public void setFluid(int index, Fluid fluid)
    {
        this.fluidTanks.get(index).ifPresent(f -> f.setFluid(new FluidStack(fluid, getFluidAmount(index))));
        // this.refresh();
        inventoryChanged();
    }


}
