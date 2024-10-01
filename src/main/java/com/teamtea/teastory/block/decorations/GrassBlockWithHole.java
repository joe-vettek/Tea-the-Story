package com.teamtea.teastory.block.decorations;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class GrassBlockWithHole extends Block {
    public static final IntegerProperty MOISTURE = BlockStateProperties.MOISTURE;

    public GrassBlockWithHole(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(MOISTURE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(MOISTURE));
    }

    @Override
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
        if (pState.getValue(MOISTURE) < 7) {
            if (pLevel.isRainingAt(pPos.above())) {
                if (pRandom.nextInt(15) == 0) {
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(MOISTURE, pState.getValue(MOISTURE) + 1));
                }
            }
        } else {
            if (!pLevel.isRainingAt(pPos.above())) {
                if (pRandom.nextInt(15) == 0) {
                    if(pLevel.isEmptyBlock(pPos.above())) {
                        pLevel.setBlockAndUpdate(pPos, Blocks.GRASS_BLOCK.defaultBlockState());
                        pLevel.setBlockAndUpdate(pPos.above(), Blocks.BAMBOO_SAPLING.defaultBlockState());
                    }
                }
            }
        }
    }
}
