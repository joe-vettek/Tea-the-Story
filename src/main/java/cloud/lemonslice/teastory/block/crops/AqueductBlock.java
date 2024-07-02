package cloud.lemonslice.teastory.block.crops;


import cloud.lemonslice.teastory.block.HorizontalConnectedBlock;
import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;


import java.util.List;

public class AqueductBlock extends HorizontalConnectedBlock implements SimpleWaterloggedBlock {
    public static final IntegerProperty DISTANCE = IntegerProperty.create("distance", 0, 32);
    public static final BooleanProperty BLOCKED = BooleanProperty.create("blocked");
    public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static VoxelShape[] SHAPES;
    protected static final VoxelShape FULL_SHAPE = VoxelShapeHelper.createVoxelShape(0, 0, 0, 16, 15, 16);

    public AqueductBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(DISTANCE, 32)
                .setValue(BLOCKED, false)
                .setValue(BOTTOM, false)
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(WATERLOGGED, false));
    }


    // onBlockActivated
    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (state.getValue(BLOCKED)) {
            worldIn.setBlockAndUpdate(pos, state.setValue(BLOCKED, false));
            worldIn.scheduleTick(pos, this, Fluids.WATER.getTickDelay(worldIn));
            if (!worldIn.isClientSide()) {
                popResource(worldIn, pos, new ItemStack(Blocks.GRAVEL));
            }
            return InteractionResult.SUCCESS;
        } else if (player.getItemInHand(handIn).getItem() == Blocks.GRAVEL.asItem() && !state.getValue(BLOCKED)) {
            worldIn.setBlockAndUpdate(pos, state.setValue(BLOCKED, true).setValue(WATERLOGGED, false).setValue(DISTANCE, 32));
            if (worldIn instanceof ServerLevel) {
                updateWater((ServerLevel) worldIn, pos.north(), state);
                updateWater((ServerLevel) worldIn, pos.south(), state);
                updateWater((ServerLevel) worldIn, pos.east(), state);
                updateWater((ServerLevel) worldIn, pos.west(), state);
            }
            player.getItemInHand(handIn).shrink(1);
            return InteractionResult.SUCCESS;
        } else {
            return fillAqueduct(worldIn, pos, player, handIn);
        }
    }

    public InteractionResult fillAqueduct(Level worldIn, BlockPos pos, Player player, InteractionHand handIn) {
        if (player.getItemInHand(handIn).is(Tags.Items.COBBLESTONE)) {
            worldIn.setBlockAndUpdate(pos, Blocks.COBBLESTONE.defaultBlockState());
            return InteractionResult.SUCCESS;
        } else return InteractionResult.PASS;
    }


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


    public boolean canConnect(BlockState state) {
        return isAqueduct(state)
                || state.getFluidState().getType() == Fluids.WATER
                && !(state.getBlock() instanceof PaddyFieldBlock);
    }

    public static boolean isAqueduct(BlockState state) {
        return state.getBlock() instanceof AqueductBlock;
    }

    public int getNearDistance(ServerLevel world, BlockPos pos) {
        int distance = 31;
        distance = Math.min(distance, getDistance(world.getBlockState(pos.north()), false));
        distance = Math.min(distance, getDistance(world.getBlockState(pos.south()), false));
        distance = Math.min(distance, getDistance(world.getBlockState(pos.east()), false));
        distance = Math.min(distance, getDistance(world.getBlockState(pos.west()), false));
        distance = Math.min(distance, getDistance(world.getBlockState(pos.above()), true));
        return distance;
    }

    // removedByPlayer
    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        this.playerWillDestroy(level, pos, state, player);
        return level.setBlock(pos, Blocks.AIR.defaultBlockState(), level.isClientSide() ? Block.UPDATE_ALL_IMMEDIATE : Block.UPDATE_ALL);
    }

    public int getDistance(BlockState state, boolean isUp) {
        if (!canConnect(state) || state.getBlock() instanceof AqueductBlock && state.getValue(BLOCKED)) {
            return 32;
        }
        if (!isUp) {
            if (state.getBlock() instanceof AqueductBlock) {
                return state.getValue(DISTANCE);
            } else {
                Fluid fluid = state.getFluidState().getType();
                if (fluid == Fluids.WATER && !(state.getBlock() instanceof PaddyFieldBlock)) {
                    return -1;
                }
            }
        } else {
            Fluid fluid = state.getFluidState().getType();
            if (fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER) {
                return -1;
            }
        }
        return 32;
    }

    public BlockState getStateForPlacement(Level world, BlockPos pos) {
        BlockState state = this.defaultBlockState();
        FluidState up = world.getBlockState(pos.above()).getFluidState();
        if (up.getType() == Fluids.WATER || up.getType() == Fluids.FLOWING_WATER) {
            state = state.setValue(DISTANCE, 0).setValue(WATERLOGGED, true);
        }

        for (Direction facing : Direction.Plane.HORIZONTAL) {
            BlockPos facingPos = pos.relative(facing);
            BlockState facingState = world.getBlockState(facingPos);
            if (this.canConnect(facingState)) {
                state = state.setValue(FACING_TO_PROPERTY_MAP.get(facing), true);
            }
        }
        BlockPos facingPos = pos.relative(Direction.DOWN);
        BlockState facingState = world.getBlockState(facingPos);
        if (this.isAqueduct(facingState)) {
            state = state.setValue(BOTTOM, true);
        }
        return state;
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return getStateForPlacement(world, pos);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        super.tick(state, level, pos, randomSource);
        boolean flag = false;
        int nearDistance = getNearDistance(level, pos);
        int currentDistance = state.getValue(DISTANCE);
        if (state.getValue(BLOCKED)) {
            return;
        }
        if (nearDistance + 1 < currentDistance) {
            state = state.setValue(WATERLOGGED, true).setValue(DISTANCE, nearDistance + 1);
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
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

    public void updateConnected(ServerLevel worldIn, BlockPos pos, BlockState state) {
        if (state.getValue(NORTH)) updateWater(worldIn, pos.north(), state);
        if (state.getValue(SOUTH)) updateWater(worldIn, pos.south(), state);
        if (state.getValue(EAST)) updateWater(worldIn, pos.east(), state);
        if (state.getValue(WEST)) updateWater(worldIn, pos.west(), state);
        if (state.getValue(BOTTOM)) updateWater(worldIn, pos.below(), state);
    }

    public void updateWater(ServerLevel worldIn, BlockPos pos, BlockState origin) {
        BlockState state = worldIn.getBlockState(pos);
        if (state.getBlock() instanceof PaddyFieldBlock) {
            worldIn.setBlockAndUpdate(pos, state.setValue(PaddyFieldBlock.WATER, origin.getValue(WATERLOGGED) ? PaddyFieldBlock.Water.POUR : PaddyFieldBlock.Water.DRAIN));
            ((PaddyFieldBlock) state.getBlock()).updateWater(worldIn, pos);
        } else if (state.getBlock() instanceof AqueductBlock) {
            worldIn.scheduleTick(pos, state.getBlock(), Fluids.WATER.getTickDelay(worldIn));
        }
    }


    // onBlockPlacedBy
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state1, boolean b) {
        super.onPlace(state, level, pos, state1, b);
        level.scheduleTick(pos, this, Fluids.WATER.getTickDelay(level));
    }


    // updatePostPlacement
    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!(facingState.getBlock() instanceof AqueductBlock) && !(facingState.getBlock() instanceof PaddyFieldBlock)) {
            worldIn.scheduleTick(currentPos, this, Fluids.WATER.getTickDelay(worldIn) / 2);
        }
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        if (facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL) {
            stateIn = stateIn.setValue(FACING_TO_PROPERTY_MAP.get(facing), this.canConnect(facingState));
        } else if (facing == Direction.DOWN) {
            stateIn = stateIn.setValue(BOTTOM, this.isAqueduct(facingState));
        }
        return stateIn;
    }

    // receiveFluid
    @Override
    public boolean placeLiquid(LevelAccessor accessor, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }


    // pickupFluid
    @Override
    public ItemStack pickupBlock(LevelAccessor accessor, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    // canPlaceLiquid
    @Override
    public boolean canPlaceLiquid(BlockGetter blockGetter, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    // fillStateContainer
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(DISTANCE, BLOCKED, BOTTOM, WATERLOGGED));
    }

    @Override
    public List<ItemStack> getDrops(BlockState p_287732_, LootParams.Builder p_287596_) {
        return Lists.newArrayList(new ItemStack(Blocks.COBBLESTONE));
    }

    static {
        VoxelShape POST_0 = VoxelShapeHelper.createVoxelShape(0.0D, 4.0D, 0.0D, 4.0D, 12.0D, 4.0D);
        VoxelShape POST_1 = VoxelShapeHelper.createVoxelShape(12.0D, 4.0D, 0.0D, 4.0D, 12.0D, 4.0D);
        VoxelShape POST_2 = VoxelShapeHelper.createVoxelShape(0.0D, 4.0D, 12.0D, 4.0D, 12.0D, 4.0D);
        VoxelShape POST_3 = VoxelShapeHelper.createVoxelShape(12.0D, 4.0D, 12.0D, 4.0D, 12.0D, 4.0D);
        VoxelShape BOTTOM = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
        VoxelShape BOTTOM_OPTIONAL = VoxelShapeHelper.createVoxelShape(4.0D, 0.0D, 4.0D, 8.0D, 4.0D, 8.0D);
        VoxelShape DEFAULT = Shapes.or(POST_0, POST_1, POST_2, POST_3, Shapes.join(BOTTOM, BOTTOM_OPTIONAL, BooleanOp.ONLY_FIRST));
        VoxelShape WALL_NORTH = VoxelShapeHelper.createVoxelShape(4.0D, 4.0D, 0.0D, 8.0D, 12.0D, 4.0D);
        VoxelShape WALL_SOUTH = VoxelShapeHelper.createVoxelShape(4.0D, 4.0D, 12.0D, 8.0D, 12.0D, 4.0D);
        VoxelShape WALL_WEST = VoxelShapeHelper.createVoxelShape(0.0D, 4.0D, 4.0D, 4.0D, 12.0D, 8.0D);
        VoxelShape WALL_EAST = VoxelShapeHelper.createVoxelShape(12.0D, 4.0D, 4.0D, 4.0D, 12.0D, 8.0D);
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
