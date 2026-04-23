package com.teamtea.teastory.client.color.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class SaucepanBlockColor implements BlockTintSource {


    @Override
    public int color(BlockState state) {
        return Minecraft.getInstance().getModelManager().getFluidStateModelSet().get(Fluids.WATER.defaultFluidState()).fluidTintSource().color(Fluids.WATER.defaultFluidState());
    }
}
