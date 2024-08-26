package cloud.lemonslice.teastory.block.craft;

import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WoodenFrameBlock extends Block implements SimpleWaterloggedBlock {
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE = box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);

    public WoodenFrameBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : 20;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : 5;
    }


    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        VoxelShape shape = SHAPE;
        if (worldIn.getBlockState(pos.north()).getBlock() == this) {
            shape = Shapes.or(shape, VoxelShapeHelper.createVoxelShape(5.0D, 0.0D, 0.0D, 6.0D, 16.0D, 5.0D));
        }
        if (worldIn.getBlockState(pos.south()).getBlock() == this) {
            shape = Shapes.or(shape, VoxelShapeHelper.createVoxelShape(5.0D, 0.0D, 11.0D, 6.0D, 16.0D, 5.0D));
        }
        if (worldIn.getBlockState(pos.west()).getBlock() == this) {
            shape = Shapes.or(shape, VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 5.0D, 5.0D, 16.0D, 6.0D));
        }
        if (worldIn.getBlockState(pos.east()).getBlock() == this) {
            shape = Shapes.or(shape, VoxelShapeHelper.createVoxelShape(11.0D, 0.0D, 5.0D, 5.0D, 16.0D, 6.0D));
        }
        return shape;
    }


    // getAmbientOcclusionLightValue
    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 0.5F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter pLevel, BlockPos pPos) {

        return !state.getValue(WATERLOGGED);
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return true;
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }


    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(WATERLOGGED));
    }

}
