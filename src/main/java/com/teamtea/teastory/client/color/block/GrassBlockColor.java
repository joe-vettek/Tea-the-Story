package com.teamtea.teastory.client.color.block;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;



public class GrassBlockColor implements BlockColor
{

    public int getColor(BlockState state, BlockAndTintGetter reader, BlockPos pos, int index) {
        if (reader != null & pos != null)
        {
            // BiomeColors.GRASS_COLOR_RESOLVER
            return reader.getBlockTint(pos, BiomeColors.GRASS_COLOR_RESOLVER);
        }
        return -1;

    }
}
