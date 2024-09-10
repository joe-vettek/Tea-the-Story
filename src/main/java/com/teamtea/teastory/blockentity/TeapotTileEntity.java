package com.teamtea.teastory.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import com.teamtea.teastory.FluidRegistry;
import com.teamtea.teastory.TileEntityTypeRegistry;
import com.teamtea.teastory.block.entity.SyncedBlockEntity;


public class TeapotTileEntity extends SyncedBlockEntity {
    private final FluidTank fluidTank ;
    private final int capacity;

    public TeapotTileEntity(int capacity, BlockPos pos, BlockState state) {
        super(getTeapotTileEntityType(capacity), pos, state);
        this.capacity = capacity;
        this.fluidTank= createFluidHandler(capacity);
    }

    public static BlockEntityType<?> getTeapotTileEntityType(int capacity) {
        if (capacity == 2000) {
            return TileEntityTypeRegistry.IRON_KETTLE_TYPE.get();
        }
        return TileEntityTypeRegistry.TEAPOT_TYPE.get();
    }


    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(tag, pRegistries);
        this.fluidTank.readFromNBT(pRegistries, tag.getCompound("FluidTank"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider pRegistries) {
        tag.put("FluidTank", this.fluidTank.writeToNBT(pRegistries, new CompoundTag()));
        super.saveAdditional(tag, pRegistries);
    }


    private FluidTank createFluidHandler(int size) {
        return new FluidTank(size) {
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
        if (fluid != Fluids.EMPTY) {
            this.fluidTank.setFluid(new FluidStack(fluid, getFluidAmount()));
            inventoryChanged();
        }
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
