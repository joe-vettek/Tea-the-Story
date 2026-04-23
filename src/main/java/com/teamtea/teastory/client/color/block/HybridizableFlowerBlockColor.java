package com.teamtea.teastory.client.color.block;


import com.teamtea.teastory.block.crops.HybridizableFlowerBlock;
import com.teamtea.teastory.block.crops.flower.FlowerColor;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.world.level.block.state.BlockState;

public class HybridizableFlowerBlockColor implements BlockTintSource {


    @Override
    public int color(BlockState state) {
        return FlowerColor.getFlowerColor(state.getValue(HybridizableFlowerBlock.FLOWER_COLOR).getString()).getColorValue();
    }
}
