package cloud.lemonslice.teastory.block.crops;


import cloud.lemonslice.teastory.block.HorizontalConnectedBlock;
import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PaddyFieldBlock extends HorizontalConnectedBlock implements SimpleWaterloggedBlock {
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<Water> WATER = EnumProperty.create("water", Water.class);
    private static final VoxelShape[] SHAPES;

    public PaddyFieldBlock() {
        super(Block.Properties.of().mapColor(MapColor.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS).noOcclusion());
        this.registerDefaultState(this.defaultBlockState().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false).setValue(WATER, Water.SKIP));
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (state.getValue(WATERLOGGED)) {
            return Shapes.block();
        } else {
            return getPaddyFieldShape(state);
        }
    }



    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return getPaddyFieldShape(state);
    }


    private VoxelShape getPaddyFieldShape(BlockState state) {
        int north = state.getValue(NORTH) ? 0 : 8;
        int south = state.getValue(SOUTH) ? 0 : 4;
        int west = state.getValue(WEST) ? 0 : 2;
        int east = state.getValue(EAST) ? 0 : 1;
        return SHAPES[north + south + west + east];
    }

    // receiveFluid
    @Override
    public boolean placeLiquid(LevelAccessor accessor, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }


    // pickupFluid
    @Override
    public ItemStack pickupBlock(LevelAccessor accessor, BlockPos pos, BlockState state) {
        return new ItemStack(Items.WATER_BUCKET);
    }

    // canPlaceLiquid
    @Override
    public boolean canPlaceLiquid(BlockGetter blockGetter, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }


    public boolean canConnect(BlockState state) {
        return state.getBlock() instanceof PaddyFieldBlock || state.getBlock() instanceof AqueductConnectorBlock;
    }


    public BlockState getStateForPlacement(Level world, BlockPos pos) {
        BlockState state = this.defaultBlockState();

        for (Direction facing : Direction.Plane.HORIZONTAL) {
            BlockPos facingPos = pos.relative(facing);
            BlockState facingState = world.getBlockState(facingPos);
            if (this.canConnect(facingState)) {
                state = state.setValue(FACING_TO_PROPERTY_MAP.get(facing), true);
            }
        }
        return state;
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return getStateForPlacement(world, pos)
                .setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER);
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        updateWater(worldIn, pos);
    }

    public void updateWater(ServerLevel world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == this) {
            boolean hasWater = state.getValue(WATERLOGGED);
            Water trend = state.getValue(WATER);

            if (trend != Water.SKIP) {
                if (trend == Water.POUR) {
                    if (!hasWater) {
                        world.setBlock(pos, state.setValue(WATERLOGGED, true).setValue(WATER, Water.SKIP), 3);
                        setToUpdate(world, pos.north(), trend);
                        setToUpdate(world, pos.south(), trend);
                        setToUpdate(world, pos.west(), trend);
                        setToUpdate(world, pos.east(), trend);
                    }
                } else {
                    if (hasWater) {
                        world.setBlock(pos, state.setValue(WATERLOGGED, false).setValue(WATER, Water.SKIP), 3);
                        setToUpdate(world, pos.north(), trend);
                        setToUpdate(world, pos.south(), trend);
                        setToUpdate(world, pos.west(), trend);
                        setToUpdate(world, pos.east(), trend);
                    }
                }
            }
        }
    }

    public void setToUpdate(ServerLevel world, BlockPos pos, Water water) {
        if (water == Water.SKIP) return;
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == this) {
            if (water == Water.POUR && !state.getValue(WATERLOGGED)) {
                world.setBlock(pos, state.setValue(WATER, water), 3);
                world.scheduleTick(pos, this, Fluids.WATER.getTickDelay(world) + 1);
            } else if (water == Water.DRAIN && state.getValue(WATERLOGGED)) {
                world.setBlock(pos, state.setValue(WATER, water), 3);
                world.scheduleTick(pos, this, Fluids.WATER.getTickDelay(world));
            }
        }
    }


    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL) {
            return stateIn.setValue(FACING_TO_PROPERTY_MAP.get(facing), this.canConnect(facingState));
        } else return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }


    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        this.playerWillDestroy(level, pos, state, player);
        return level.setBlock(pos, Blocks.AIR.defaultBlockState(), level.isClientSide() ? Block.UPDATE_ALL_IMMEDIATE : Block.UPDATE_ALL);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(WATERLOGGED, WATER));
    }

    @Override
    public boolean isFertile(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        return Lists.newArrayList(new ItemStack(Blocks.DIRT));
    }

    public enum Water implements StringRepresentable {
        SKIP("skip"),
        DRAIN("darin"),
        POUR("pour");
        private final String name;

        private Water(String s) {
            this.name = s;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }

    static {
        VoxelShape BOTTOM = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);
        VoxelShape WALL_NORTH = VoxelShapeHelper.createVoxelShape(0.0D, 6.0D, 0.0D, 16.0D, 10.0D, 3.0D);
        VoxelShape WALL_SOUTH = VoxelShapeHelper.createVoxelShape(0.0D, 6.0D, 13.0D, 16.0D, 10.0D, 3.0D);
        VoxelShape WALL_WEST = VoxelShapeHelper.createVoxelShape(0.0D, 6.0D, 0.0D, 3.0D, 10.0D, 16.0D);
        VoxelShape WALL_EAST = VoxelShapeHelper.createVoxelShape(13.0D, 6.0D, 0.0D, 3.0D, 10.0D, 16.0D);
        SHAPES = new VoxelShape[]{BOTTOM, Shapes.or(BOTTOM, WALL_EAST).optimize(), Shapes.or(BOTTOM, WALL_WEST).optimize(), Shapes.or(BOTTOM, WALL_WEST, WALL_EAST).optimize(),
                Shapes.or(BOTTOM, WALL_SOUTH).optimize(), Shapes.or(BOTTOM, WALL_EAST, WALL_SOUTH).optimize(), Shapes.or(BOTTOM, WALL_WEST, WALL_SOUTH).optimize(), Shapes.or(BOTTOM, WALL_WEST, WALL_EAST, WALL_SOUTH).optimize(),
                Shapes.or(BOTTOM, WALL_NORTH).optimize(), Shapes.or(BOTTOM, WALL_EAST, WALL_NORTH).optimize(), Shapes.or(BOTTOM, WALL_WEST, WALL_NORTH).optimize(), Shapes.or(BOTTOM, WALL_WEST, WALL_EAST, WALL_NORTH).optimize(),
                Shapes.or(BOTTOM, WALL_SOUTH, WALL_NORTH).optimize(), Shapes.or(BOTTOM, WALL_EAST, WALL_SOUTH, WALL_NORTH).optimize(), Shapes.or(BOTTOM, WALL_WEST, WALL_SOUTH, WALL_NORTH).optimize(), Shapes.or(BOTTOM, WALL_WEST, WALL_EAST, WALL_SOUTH, WALL_NORTH).optimize()};
    }
}
