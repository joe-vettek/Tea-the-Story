package cloud.lemonslice.teastory.block.crops;


import cloud.lemonslice.teastory.block.HorizontalConnectedBlock;
import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xueluoanping.teastory.variant.Planks;

import java.util.List;

public class TrellisBlock extends HorizontalConnectedBlock {
    public static final BooleanProperty POST = BooleanProperty.create("post");
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty HORIZONTAL = BooleanProperty.create("horizontal");
    private static final VoxelShape[] SHAPES;

    public TrellisBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(POST, false).setValue(UP, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false));
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 20;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }


    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return getProperState(world, pos);
    }

    public boolean hasHorizontalBar(BlockState state) {
        return state.getValue(NORTH) || state.getValue(SOUTH) || state.getValue(WEST) || state.getValue(EAST);
    }

    public boolean hasPost(BlockState state) {
        return state.getValue(POST);
    }


    // EmptyBlockGetter.INSTANCE
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int north = state.getValue(NORTH) ? 32 : 0;
        int south = state.getValue(SOUTH) ? 16 : 0;
        int east = state.getValue(WEST) ? 8 : 0;
        int west = state.getValue(EAST) ? 4 : 0;
        int up = state.getValue(UP) ? 2 : 0;
        int post = hasPost(state) ? 1 : 0;
        return SHAPES[north + south + east + west + post + up];
    }

    @SuppressWarnings("deprecation")
    public BlockState getProperState(Level world, BlockPos pos) {
        BlockState state = this.defaultBlockState();
        BlockState belowState = world.getBlockState(pos.below());
        if (belowState.getBlock() instanceof TrellisBlock || belowState.is(BlockTags.WOODEN_FENCES) || belowState.isFaceSturdy(world, pos.below(), Direction.UP)) {
            state = state.setValue(POST, true);
        }
        BlockState up = world.getBlockState(pos.above());
        if (up.getBlock() instanceof TrellisBlock || up.is(BlockTags.WOODEN_FENCES) || up.isFaceSturdy(world, pos.above(), Direction.DOWN)) {
            state = state.setValue(UP, true);
        }

        for (Direction facing : Direction.Plane.HORIZONTAL) {
            BlockPos facingPos = pos.relative(facing);
            BlockState facingState = world.getBlockState(facingPos);
            if (this.canConnect(facingState, facingState.isFaceSturdy(world, facingPos, facing.getOpposite()))) {
                state = state.setValue(FACING_TO_PROPERTY_MAP.get(facing), true);
            }
        }
        return state;
    }

    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0F;
    }

    // cannotAttach
    public boolean canConnect(BlockState state, boolean isSolidSide) {
        Block block = state.getBlock();
        return !isExceptionForConnection(state) && isSolidSide || block instanceof TrellisBlock;
    }


    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        // Update the connecting state of trellis. 更新棚架方块的连接状态。
        if (facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL) {
            stateIn = stateIn.setValue(FACING_TO_PROPERTY_MAP.get(facing), this.canConnect(facingState, facingState.isFaceSturdy(worldIn, facingPos, facing.getOpposite())));
        } else if (facing == Direction.DOWN) {
            BlockPos posDown = currentPos.relative(facing);
            BlockState state = worldIn.getBlockState(posDown);
            stateIn = stateIn.setValue(POST, state.getBlock() instanceof TrellisBlock || state.is(BlockTags.WOODEN_FENCES) || state.isFaceSturdy(worldIn, posDown, Direction.UP));
        } else if (facing == Direction.UP) {
            BlockPos posUp = currentPos.relative(facing);
            BlockState state = worldIn.getBlockState(posUp);
            stateIn = stateIn.setValue(UP, state.getBlock() instanceof TrellisBlock || state.is(BlockTags.WOODEN_FENCES) || state.isFaceSturdy(worldIn, posUp, Direction.DOWN));
        }
        return stateIn;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(POST, UP));
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        return Lists.newArrayList(new ItemStack(this));
    }


    public BlockState getRelevantState(BlockState old) {
        BlockState newState = this.defaultBlockState();
        return newState.setValue(NORTH, old.getValue(NORTH)).setValue(SOUTH, old.getValue(SOUTH)).setValue(WEST, old.getValue(WEST)).setValue(EAST, old.getValue(EAST)).setValue(POST, old.getValue(POST)).setValue(UP, old.getValue(UP));
    }


    @Override
    public MutableComponent getName() {
        var ss = Planks.resourceLocationBlockMap.get(BuiltInRegistries.BLOCK.getKey(this));
        if (ss == null) {
            return super.getName();
        }
        return Component.translatable(ss.getA().getDescriptionId()).append(Component.translatable("misc.block.teastory.trellis_suffix"));
    }

    static {
        VoxelShape CENTER = VoxelShapeHelper.createVoxelShape(7.0D, 7.0D, 7.0D, 2.0D, 3.0D, 2.0D);
        VoxelShape TOP_NORTH = VoxelShapeHelper.createVoxelShape(7.0D, 7.0D, 0.0D, 2.0D, 3.0D, 14.0D);
        VoxelShape TOP_SOUTH = VoxelShapeHelper.createVoxelShape(7.0D, 7.0D, 2.0D, 2.0D, 3.0D, 14.0D);
        VoxelShape TOP_EAST = VoxelShapeHelper.createVoxelShape(0.0D, 7.0D, 7.0D, 14.0D, 3.0D, 2.0D);
        VoxelShape TOP_WEST = VoxelShapeHelper.createVoxelShape(2.0D, 7.0D, 7.0D, 14.0D, 3.0D, 2.0D);
        VoxelShape POST_SHAPE = VoxelShapeHelper.createVoxelShape(6.0D, 0.0D, 6.0D, 4.0D, 12.0D, 4.0D);
        VoxelShape POST_UP_SHAPE = VoxelShapeHelper.createVoxelShape(6.0D, 7.0D, 6.0D, 4.0D, 9.0D, 4.0D);
        SHAPES = new VoxelShape[]{CENTER, POST_SHAPE, POST_UP_SHAPE, Shapes.or(POST_UP_SHAPE, POST_SHAPE),
                TOP_WEST, Shapes.or(TOP_WEST, POST_SHAPE), Shapes.or(TOP_WEST, POST_UP_SHAPE), Shapes.or(TOP_WEST, POST_UP_SHAPE, POST_SHAPE),
                TOP_EAST, Shapes.or(TOP_EAST, POST_SHAPE), Shapes.or(TOP_EAST, POST_UP_SHAPE), Shapes.or(TOP_EAST, POST_UP_SHAPE, POST_SHAPE),
                Shapes.or(TOP_EAST, TOP_WEST), Shapes.or(TOP_EAST, TOP_WEST, POST_SHAPE), Shapes.or(TOP_EAST, TOP_WEST, POST_UP_SHAPE), Shapes.or(TOP_EAST, TOP_WEST, POST_UP_SHAPE, POST_SHAPE),
                TOP_SOUTH, Shapes.or(TOP_SOUTH, POST_SHAPE), Shapes.or(TOP_SOUTH, POST_UP_SHAPE), Shapes.or(TOP_SOUTH, POST_UP_SHAPE, POST_SHAPE),
                Shapes.or(TOP_SOUTH, TOP_WEST), Shapes.or(TOP_SOUTH, TOP_WEST, POST_SHAPE), Shapes.or(TOP_SOUTH, TOP_WEST, POST_UP_SHAPE), Shapes.or(TOP_SOUTH, TOP_WEST, POST_UP_SHAPE, POST_SHAPE),
                Shapes.or(TOP_SOUTH, TOP_EAST), Shapes.or(TOP_SOUTH, TOP_EAST, POST_SHAPE), Shapes.or(TOP_SOUTH, TOP_EAST, POST_UP_SHAPE), Shapes.or(TOP_SOUTH, TOP_EAST, POST_UP_SHAPE, POST_SHAPE),
                Shapes.or(TOP_SOUTH, TOP_EAST, TOP_WEST), Shapes.or(TOP_SOUTH, TOP_EAST, TOP_WEST, POST_SHAPE), Shapes.or(TOP_SOUTH, TOP_EAST, TOP_WEST, POST_UP_SHAPE), Shapes.or(TOP_SOUTH, TOP_EAST, TOP_WEST, POST_UP_SHAPE, POST_SHAPE),
                TOP_NORTH, Shapes.or(TOP_NORTH, POST_SHAPE), Shapes.or(TOP_NORTH, POST_UP_SHAPE), Shapes.or(TOP_NORTH, POST_UP_SHAPE, POST_SHAPE),
                Shapes.or(TOP_NORTH, TOP_WEST), Shapes.or(TOP_NORTH, TOP_WEST, POST_SHAPE), Shapes.or(TOP_NORTH, TOP_WEST, POST_UP_SHAPE), Shapes.or(TOP_NORTH, TOP_WEST, POST_UP_SHAPE, POST_SHAPE),
                Shapes.or(TOP_NORTH, TOP_EAST), Shapes.or(TOP_NORTH, TOP_EAST, POST_SHAPE), Shapes.or(TOP_NORTH, TOP_EAST, POST_UP_SHAPE), Shapes.or(TOP_NORTH, TOP_EAST, POST_UP_SHAPE, POST_SHAPE),
                Shapes.or(TOP_NORTH, TOP_EAST, TOP_WEST), Shapes.or(TOP_NORTH, TOP_EAST, TOP_WEST, POST_SHAPE), Shapes.or(TOP_NORTH, TOP_EAST, TOP_WEST, POST_UP_SHAPE), Shapes.or(TOP_NORTH, TOP_EAST, TOP_WEST, POST_UP_SHAPE, POST_SHAPE),
                Shapes.or(TOP_NORTH, TOP_SOUTH), Shapes.or(TOP_NORTH, TOP_SOUTH, POST_SHAPE), Shapes.or(TOP_NORTH, TOP_SOUTH, POST_UP_SHAPE), Shapes.or(TOP_NORTH, TOP_SOUTH, POST_UP_SHAPE, POST_SHAPE),
                Shapes.or(TOP_NORTH, TOP_SOUTH, TOP_WEST), Shapes.or(TOP_NORTH, TOP_SOUTH, TOP_WEST, POST_SHAPE), Shapes.or(TOP_NORTH, TOP_SOUTH, TOP_WEST, POST_UP_SHAPE), Shapes.or(TOP_NORTH, TOP_SOUTH, TOP_WEST, POST_UP_SHAPE, POST_SHAPE),
                Shapes.or(TOP_NORTH, TOP_SOUTH, TOP_EAST), Shapes.or(TOP_NORTH, TOP_SOUTH, TOP_EAST, POST_SHAPE), Shapes.or(TOP_NORTH, TOP_SOUTH, TOP_EAST, POST_UP_SHAPE), Shapes.or(TOP_NORTH, TOP_SOUTH, TOP_EAST, POST_UP_SHAPE, POST_SHAPE),
                Shapes.or(TOP_NORTH, TOP_SOUTH, TOP_EAST, TOP_WEST), Shapes.or(TOP_NORTH, TOP_SOUTH, TOP_EAST, TOP_WEST, POST_SHAPE), Shapes.or(TOP_NORTH, TOP_SOUTH, TOP_EAST, TOP_WEST, POST_UP_SHAPE), Shapes.or(TOP_NORTH, TOP_SOUTH, TOP_EAST, TOP_WEST, POST_UP_SHAPE, POST_SHAPE),
        };
    }
}
