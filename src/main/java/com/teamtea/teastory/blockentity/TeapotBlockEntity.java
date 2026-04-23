package com.teamtea.teastory.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import org.jetbrains.annotations.NotNull;
import com.teamtea.teastory.registry.FluidRegister;
import com.teamtea.teastory.registry.BlockEntityRegister;
import com.teamtea.teastory.blockentity.base.SyncedBlockEntity;


public class TeapotBlockEntity extends SyncedBlockEntity {
    private final FluidStacksResourceHandler fluidTank;
    private final int capacity;

    public TeapotBlockEntity(int capacity, BlockPos pos, BlockState state) {
        super(getTeapotTileEntityType(capacity), pos, state);
        this.capacity = capacity;
        this.fluidTank = createFluidHandler(capacity);
    }

    public static BlockEntityType<?> getTeapotTileEntityType(int capacity) {
        if (capacity == 2000) {
            return BlockEntityRegister.IRON_KETTLE_TYPE.get();
        }
        return BlockEntityRegister.TEAPOT_TYPE.get();
    }


    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.fluidTank.deserialize(input.childOrEmpty("FluidTank"));
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        this.fluidTank.serialize(output.child("FluidTank"));
    }


    private FluidStacksResourceHandler createFluidHandler(int capacity) {
        return new FluidStacksResourceHandler(1, capacity) {

            @Override
            protected void onContentsChanged(int index, FluidStack previousContents) {
                super.onContentsChanged(index, previousContents);
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

    public void setFluidTank(FluidStack stack) {
        this.fluidTank.set(0, FluidResource.of(stack), stack.getAmount());
    }

    public void setFluid(Fluid fluid) {
        if (fluid != Fluids.EMPTY) {
            this.fluidTank.set(0, FluidResource.of(fluid), getFluidAmount());
            inventoryChanged();
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TeapotBlockEntity teapotTileEntity) {
        if (teapotTileEntity.getFluidTank().getAmountAsInt(0) == 0) return;
        if (!(level.getBlockState(pos.below()) instanceof BlockState state1
                && state1.getBlock() instanceof CampfireBlock)) return;
        // TeaStory.logger(stoveTileEntity.isBurning());
        if (!state1.getValue(CampfireBlock.LIT)) return;

        if (teapotTileEntity.getFluidTank().getResource(0).getFluid() == Fluids.WATER) {
            toNextFluid(level, teapotTileEntity.getFluidTank(), FluidRegister.WARM_WATER_STILL.get());
        } else if (teapotTileEntity.getFluidTank().getResource(0).getFluid() == FluidRegister.WARM_WATER_STILL.get()) {
            toNextFluid(level, teapotTileEntity.getFluidTank(), FluidRegister.HOT_WATER_60_STILL.get());
        } else if (teapotTileEntity.getFluidTank().getResource(0).getFluid() == FluidRegister.HOT_WATER_60_STILL.get()) {
            toNextFluid(level, teapotTileEntity.getFluidTank(), FluidRegister.HOT_WATER_80_STILL.get());
        } else if (teapotTileEntity.getFluidTank().getResource(0).getFluid() == FluidRegister.HOT_WATER_80_STILL.get()) {
            toNextFluid(level, teapotTileEntity.getFluidTank(), FluidRegister.BOILING_WATER_STILL.get());
        }
    }

    private static void toNextFluid(Level level, FluidStacksResourceHandler fluidTank, @NotNull FlowingFluid flowingFluid) {
        if (level.getRandom().nextInt(150) == 0) {
            fluidTank.extract(0,FluidResource.of(flowingFluid.getSource()),fluidTank.getAmountAsInt(0),new Tran());
            FluidStack fluidStack = fluidTank.drain(fluidTank.getCapacity(), IFluidHandler.FluidAction.EXECUTE);
            if (fluidStack.getAmount() > 0) {
                fluidTank.fill(new FluidStack(flowingFluid, fluidStack.getAmount()), IFluidHandler.FluidAction.EXECUTE);
            }
        }
    }
}
