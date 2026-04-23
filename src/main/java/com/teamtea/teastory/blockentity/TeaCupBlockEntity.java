package com.teamtea.teastory.blockentity;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import com.teamtea.teastory.registry.BlockEntityRegister;
import com.teamtea.teastory.blockentity.base.SyncedBlockEntity;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;

import java.util.List;


public class TeaCupBlockEntity extends SyncedBlockEntity {
    private final List<FluidStacksResourceHandler> fluidTanks = Lists.newArrayList();

    public TeaCupBlockEntity(int capacity, BlockPos pos, BlockState state) {
        super(BlockEntityRegister.WOODEN_TRAY_TYPE.get(), pos, state);
        for (int i = 0; i < 3; i++) {
            fluidTanks.add(createFluidHandler(capacity));
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        for (int i = 0; i < 3; i++) {
            fluidTanks.get(i).deserialize(input.childOrEmpty("FluidTank_" + i));
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        for (int i = 0; i < 3; i++) {
            fluidTanks.get(i).serialize(output.child("FluidTank_" + i));
        }
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

    public FluidStacksResourceHandler getFluidTank(int index) {
        return this.fluidTanks.get(index);
    }

    public Fluid getFluid(int index) {
        return this.fluidTanks.get(index).getResource(0).getFluid();
    }


    public void setFluidTank(int index, FluidStack stack) {
        this.fluidTanks.get(index).set(0, FluidResource.of(stack), stack.getAmount());
    }

}
