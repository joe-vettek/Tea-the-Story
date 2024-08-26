package cloud.lemonslice.teastory.block.decorations;


import cloud.lemonslice.teastory.block.HorizontalConnectedBlock;
import cloud.lemonslice.teastory.helper.VoxelShapeHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BambooLatticeBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty NORTH = HorizontalConnectedBlock.NORTH;
    public static final BooleanProperty EAST = HorizontalConnectedBlock.EAST;
    public static final BooleanProperty SOUTH = HorizontalConnectedBlock.SOUTH;
    public static final BooleanProperty WEST = HorizontalConnectedBlock.WEST;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape NORTH_SHAPE = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
    protected static final VoxelShape SOUTH_SHAPE = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 4.0D);
    protected static final VoxelShape WEST_SHAPE = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_SHAPE = VoxelShapeHelper.createVoxelShape(12.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D);


    public BambooLatticeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter pLevel, BlockPos pPos) {
        return !state.getValue(WATERLOGGED);
    }


    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.hasProperty(WATERLOGGED) && state.getValue(WATERLOGGED) ? 0 : 60;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.hasProperty(WATERLOGGED) && state.getValue(WATERLOGGED) ? 0 : 60;
    }


    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0F;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        if (useContext.getItemInHand().getItem() == this.asItem()) {
            if (useContext.getClickedFace() != Direction.UP) {
                return true;
            } else if (useContext.replacingClickedOnBlock()) {
                return false;
            } else {
                return true;
            }
        } else return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        VoxelShape shape = Shapes.empty();
        if (state.getValue(NORTH)) {
            shape = Shapes.or(shape, NORTH_SHAPE);
        }
        if (state.getValue(SOUTH)) {
            shape = Shapes.or(shape, SOUTH_SHAPE);
        }
        if (state.getValue(WEST)) {
            shape = Shapes.or(shape, WEST_SHAPE);
        }
        if (state.getValue(EAST)) {
            shape = Shapes.or(shape, EAST_SHAPE);
        }
        return shape;
    }


    protected BlockState getStateWithFacing(Player player, BlockState state) {
        switch (player.getDirection()) {
            case NORTH:
                state = state.setValue(NORTH, true);
                break;
            case SOUTH:
                state = state.setValue(SOUTH, true);
                break;
            case WEST:
                state = state.setValue(WEST, true);
                break;
            default:
                state = state.setValue(EAST, true);
        }
        return state;
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if (state.getBlock() != this) {
            state = this.defaultBlockState();
        }
        if (context.getPlayer() != null) {
            state = getStateWithFacing(context.getPlayer(), state);
        }
        return state.setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rot) {
        switch (rot) {
            case CLOCKWISE_180:
                return state.setValue(NORTH, state.getValue(SOUTH)).setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH)).setValue(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return state.setValue(NORTH, state.getValue(EAST)).setValue(EAST, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(WEST)).setValue(WEST, state.getValue(NORTH));
            case CLOCKWISE_90:
                return state.setValue(NORTH, state.getValue(WEST)).setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST)).setValue(WEST, state.getValue(SOUTH));
            default:
                return state;
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        switch (mirrorIn) {
            case LEFT_RIGHT:
                return state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
            case FRONT_BACK:
                return state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
            default:
                return super.mirror(state, mirrorIn);
        }
    }


}
