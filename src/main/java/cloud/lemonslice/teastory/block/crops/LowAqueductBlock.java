package cloud.lemonslice.teastory.block.crops;


import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class LowAqueductBlock extends AqueductBlock {

    public LowAqueductBlock(Properties properties) {
        super(properties);
    }

    protected static VoxelShape[] SHAPES;

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        if (state.getValue(BLOCKED)) {
            return FULL_SHAPE;
        }
        int bottom = state.getValue(BOTTOM) ? 0 : 16;
        int north = state.getValue(NORTH) ? 0 : 8;
        int south = state.getValue(SOUTH) ? 0 : 4;
        int west = state.getValue(WEST) ? 0 : 2;
        int east = state.getValue(EAST) ? 0 : 1;
        return SHAPES[bottom + north + south + west + east];
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        // super.tick(state, level, pos, randomSource);
        boolean flag = false;
        int nearDistance = getNearDistance(level, pos);
        int currentDistance = state.getValue(DISTANCE);
        if (state.getValue(BLOCKED)) {
            return;
        }
        var lost = new Random(pos.asLong() + level.getSeed()).nextInt(1) + 5;
        if (nearDistance + 1 < currentDistance) {
            state = state.setValue(WATERLOGGED, true).setValue(DISTANCE, Math.min(nearDistance + lost, 31));
            // avoid water out
            level.scheduleTick(pos, this, Fluids.WATER.getTickDelay(level));
            updateConnected(level, pos, state);
            flag = true;

        } else if (nearDistance + 1 != currentDistance) {
            state = state.setValue(WATERLOGGED, false).setValue(DISTANCE, 32);
            updateConnected(level, pos, state);
            flag = true;
        }
        if (flag) {
            level.setBlockAndUpdate(pos, state);
            updateWater(level, pos, state);
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        // 排水和给水计划刻时间别设一样
        if (!(facingState.getBlock() instanceof AqueductBlock) && !(facingState.getBlock() instanceof PaddyFieldBlock)) {
            level.scheduleTick(currentPos, this, Fluids.WATER.getTickDelay(level) / 2);
        }
        if (stateIn.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, this, Fluids.WATER.getTickDelay(level));
        }
        if (facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL) {
            stateIn = stateIn.setValue(FACING_TO_PROPERTY_MAP.get(facing), this.canConnect(facingState));
        } else if (facing == Direction.DOWN) {
            stateIn = stateIn.setValue(BOTTOM, this.isAqueduct(facingState));
        }
        return stateIn;
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getFlowing(8, false) : super.getFluidState(state);
    }

    static {
        VoxelShape POST_0 = VoxelShapeHelper.createVoxelShape(0.0D, 4.0D, 0.0D, 4.0D, 11.0D, 4.0D);
        VoxelShape POST_1 = VoxelShapeHelper.createVoxelShape(12.0D, 4.0D, 0.0D, 4.0D, 11.0D, 4.0D);
        VoxelShape POST_2 = VoxelShapeHelper.createVoxelShape(0.0D, 4.0D, 12.0D, 4.0D, 11.0D, 4.0D);
        VoxelShape POST_3 = VoxelShapeHelper.createVoxelShape(12.0D, 4.0D, 12.0D, 4.0D, 11.0D, 4.0D);
        VoxelShape BOTTOM = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
        VoxelShape BOTTOM_OPTIONAL = VoxelShapeHelper.createVoxelShape(4.0D, 0.0D, 4.0D, 8.0D, 4.0D, 8.0D);
        VoxelShape DEFAULT = Shapes.or(POST_0, POST_1, POST_2, POST_3, Shapes.join(BOTTOM, BOTTOM_OPTIONAL, BooleanOp.ONLY_FIRST));
        VoxelShape WALL_NORTH = VoxelShapeHelper.createVoxelShape(4.0D, 4.0D, 0.0D, 8.0D, 11.0D, 4.0D);
        VoxelShape WALL_SOUTH = VoxelShapeHelper.createVoxelShape(4.0D, 4.0D, 12.0D, 8.0D, 11.0D, 4.0D);
        VoxelShape WALL_WEST = VoxelShapeHelper.createVoxelShape(0.0D, 4.0D, 4.0D, 4.0D, 11.0D, 8.0D);
        VoxelShape WALL_EAST = VoxelShapeHelper.createVoxelShape(12.0D, 4.0D, 4.0D, 4.0D, 11.0D, 8.0D);
        SHAPES = new VoxelShape[]{DEFAULT, Shapes.or(DEFAULT, WALL_EAST).optimize(), Shapes.or(DEFAULT, WALL_WEST).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_EAST).optimize(),
                Shapes.or(DEFAULT, WALL_SOUTH).optimize(), Shapes.or(DEFAULT, WALL_EAST, WALL_SOUTH).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_SOUTH).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_EAST, WALL_SOUTH).optimize(),
                Shapes.or(DEFAULT, WALL_NORTH).optimize(), Shapes.or(DEFAULT, WALL_EAST, WALL_NORTH).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_NORTH).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_EAST, WALL_NORTH).optimize(),
                Shapes.or(DEFAULT, WALL_SOUTH, WALL_NORTH).optimize(), Shapes.or(DEFAULT, WALL_EAST, WALL_SOUTH, WALL_NORTH).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_SOUTH, WALL_NORTH).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_EAST, WALL_SOUTH, WALL_NORTH).optimize(),
                Shapes.or(DEFAULT, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_EAST, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_WEST, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_EAST, BOTTOM_OPTIONAL).optimize(),
                Shapes.or(DEFAULT, WALL_SOUTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_EAST, WALL_SOUTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_SOUTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_EAST, WALL_SOUTH, BOTTOM_OPTIONAL).optimize(),
                Shapes.or(DEFAULT, WALL_NORTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_EAST, WALL_NORTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_NORTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_EAST, WALL_NORTH, BOTTOM_OPTIONAL).optimize(),
                Shapes.or(DEFAULT, WALL_SOUTH, WALL_NORTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_EAST, WALL_SOUTH, WALL_NORTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_SOUTH, WALL_NORTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_EAST, WALL_SOUTH, WALL_NORTH, BOTTOM_OPTIONAL).optimize()};
    }
}
