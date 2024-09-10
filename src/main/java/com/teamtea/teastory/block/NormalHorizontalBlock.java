package com.teamtea.teastory.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

public class NormalHorizontalBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<NormalHorizontalBlock> CODEC = simpleCodec(NormalHorizontalBlock::new);

    public NormalHorizontalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends NormalHorizontalBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FACING));
    }

    @javax.annotation.Nullable
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> type1, BlockEntityType<E> type, BlockEntityTicker<? super E> ticker) {
        return type == type1 ? (BlockEntityTicker<A>) ticker : null;
    }


    // playerWillDestroy->onBlockHarvested
}
