package cloud.lemonslice.teastory.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import xueluoanping.teastory.FluidRegistry;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.entity.NormalContainerTileEntity;
import xueluoanping.teastory.block.entity.SyncedBlockEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class TeapotTileEntity extends SyncedBlockEntity {
    private final LazyOptional<FluidTank> fluidTank = LazyOptional.of(this::createFluidHandler);
    private final int capacity;

    public TeapotTileEntity(int capacity, BlockPos pos, BlockState state) {
        super(getTeapotTileEntityType(capacity), pos, state);
        this.capacity = capacity;
    }

    public static BlockEntityType<?> getTeapotTileEntityType(int capacity) {
        if (capacity == 2000) {
            return TileEntityTypeRegistry.IRON_KETTLE_TYPE.get();
        }
        return TileEntityTypeRegistry.TEAPOT_TYPE.get();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (!this.isRemoved()) {
            if (ForgeCapabilities.FLUID_HANDLER.equals(cap)) {
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


    private FluidTank createFluidHandler() {
        return new FluidTank(capacity) {
            @Override
            protected void onContentsChanged() {
                inventoryChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return !stack.getFluid().getFluidType().isLighterThanAir() && stack.getFluid().getFluidType().getTemperature() < 500;
            }
        };
    }

    public FluidTank getFluidTank() {
        return this.fluidTank.orElse(new FluidTank(0));
    }

    public Fluid getFluid() {
        return this.fluidTank.map(f -> f.getFluid().getFluid()).orElse(Fluids.EMPTY);
    }

    public int getFluidAmount() {
        return getFluidTank().getFluidAmount();
    }

    public void setFluidTank(FluidStack stack) {
        this.fluidTank.ifPresent(f -> f.setFluid(stack));
    }

    public void setFluid(Fluid fluid) {
        this.fluidTank.ifPresent(f -> f.setFluid(new FluidStack(fluid, getFluidAmount())));
        inventoryChanged();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TeapotTileEntity teapotTileEntity) {
        if (teapotTileEntity.getFluidTank().getFluid().isEmpty()) return;
        if (!(level.getBlockEntity(pos.below()) instanceof StoveTileEntity stoveTileEntity)) return;
        // TeaStory.logger(stoveTileEntity.isBurning());
        if (!stoveTileEntity.isBurning()) return;

        if (teapotTileEntity.getFluidTank().getFluid().getFluid() == Fluids.WATER) {
            toNextFluid(level, teapotTileEntity.getFluidTank(), FluidRegistry.WARM_WATER_STILL.get());
        } else if (teapotTileEntity.getFluidTank().getFluid().getFluid() == FluidRegistry.WARM_WATER_STILL.get()) {
            toNextFluid(level, teapotTileEntity.getFluidTank(), FluidRegistry.HOT_WATER_60_STILL.get());
        } else if (teapotTileEntity.getFluidTank().getFluid().getFluid() == FluidRegistry.HOT_WATER_60_STILL.get()) {
            toNextFluid(level, teapotTileEntity.getFluidTank(), FluidRegistry.HOT_WATER_80_STILL.get());
        } else if (teapotTileEntity.getFluidTank().getFluid().getFluid() == FluidRegistry.HOT_WATER_80_STILL.get()) {
            toNextFluid(level, teapotTileEntity.getFluidTank(), FluidRegistry.BOILING_WATER_STILL.get());
        }
    }

    private static void toNextFluid(Level level, FluidTank fluidTank, @NotNull FlowingFluid flowingFluid) {
        if (level.getRandom().nextInt(150) == 0) {
            FluidStack fluidStack = fluidTank.drain(fluidTank.getCapacity(), IFluidHandler.FluidAction.EXECUTE);
            if (fluidStack.getAmount() > 0) {
                fluidTank.fill(new FluidStack(flowingFluid, fluidStack.getAmount()), IFluidHandler.FluidAction.EXECUTE);
            }
        }
    }
}
