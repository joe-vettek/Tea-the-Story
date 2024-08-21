package cloud.lemonslice.teastory.block.crops;

import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

import java.util.List;

public class TrellisWithVineBlock extends TrellisBlock
{
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final IntegerProperty DISTANCE = IntegerProperty.create("distance", 0, 7);
    private static final VoxelShape[] SHAPES;
    private final VineType type;

    public TrellisWithVineBlock( VineType type, Properties properties)
    {
        super(properties);
        this.type = type;
        this.registerDefaultState(defaultBlockState().setValue(AGE, 0).setValue(DISTANCE, 0).setValue(POST, false).setValue(UP, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(getEmptyTrellis());
    }



    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random)
    {
        // Grow vertically. 垂直方向生长。
        if (hasPost(state))
        {
            int i = state.getValue(AGE);
            float f = 8.0F; //TODO Connected setValue humidity.
            if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0))
            {
                if (i < 3)
                {
                    worldIn.setBlock(pos, state.setValue(AGE, i + 1), 2);
                }
                else
                {
                    BlockState up = worldIn.getBlockState(pos.above());
                    if (up.getBlock() instanceof TrellisBlock && !(up.getBlock() instanceof TrellisWithVineBlock))
                    {
                        worldIn.setBlock(pos.above(), VineInfoManager.getVineTrellis(type, (TrellisBlock) up.getBlock()).getRelevantState(up).setValue(DISTANCE, state.getValue(DISTANCE)), 2);
                    }
                }
                ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                return;
            }
        }
        // Grow horizontally and bear fruit. 水平方向蔓延和结果。
        if (hasHorizontalBar(state))
        {
            int i = state.getValue(AGE);
            float f = 5.0F; //TODO Connected setValue humidity.
            if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0))
            {
                if (!hasPost(state))
                {
                    if (random.nextBoolean()) // Leaves grow up.
                    {
                        if (worldIn.getBlockState(pos.below()).getBlock() != type.getFruit())
                            worldIn.setBlock(pos, state.setValue(AGE, (i + 1) % 4), 2);
                    }
                    else // Bear fruit.
                    {
                        if (worldIn.getBlockState(pos.below()).isAir() && !hasNearFruit(worldIn, pos.below(), type.getFruit()))
                        {
                            worldIn.setBlockAndUpdate(pos, state.setValue(AGE, (i + 1) % 4));
                            worldIn.setBlockAndUpdate(pos.below(), type.getFruit().defaultBlockState());
                            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                            return;
                        }
                    }
                }
                BlockPos blockPos = pos;
                switch (random.nextInt(4))
                {
                    case 0:
                        blockPos = blockPos.north();
                        break;
                    case 1:
                        blockPos = blockPos.south();
                        break;
                    case 2:
                        blockPos = blockPos.east();
                        break;
                    default:
                        blockPos = blockPos.west();
                }
                BlockState next = worldIn.getBlockState(blockPos);
                if (next.getBlock() instanceof TrellisBlock && !(next.getBlock() instanceof TrellisWithVineBlock) && state.getValue(DISTANCE) < 7)
                {
                    worldIn.setBlock(blockPos, VineInfoManager.getVineTrellis(type, (TrellisBlock) next.getBlock()).getRelevantState(next).setValue(DISTANCE, state.getValue(DISTANCE) + 1), 2);
                }
                ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }
        }
    }

    public static boolean hasNearFruit(Level worldIn, BlockPos pos, Block fruit)
    {
        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            BlockState state = worldIn.getBlockState(pos.relative(direction));
            if (state.is(fruit))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context)
    {
        int bar = hasHorizontalBar(state) ? 4 : 0;
        int post = hasPost(state) ? 2 : 0;
        int up = state.getValue(UP) ? 1 : 0;
        return SHAPES[bar + post + up];
    }

    public int getNearDistance(LevelAccessor world, BlockPos pos)
    {
        int distance = 7;
        distance = Math.min(distance, getDistance(world.getBlockState(pos.north())));
        distance = Math.min(distance, getDistance(world.getBlockState(pos.south())));
        distance = Math.min(distance, getDistance(world.getBlockState(pos.east())));
        distance = Math.min(distance, getDistance(world.getBlockState(pos.west())));
        return distance;
    }

    public int getDistance(BlockState state)
    {
        if (state.getBlock() instanceof TrellisWithVineBlock && ((TrellisWithVineBlock) state.getBlock()).type == type)
        {
            return state.getValue(DISTANCE);
        }
        return 7;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        // Update the connecting state of trellis. 更新棚架方块的连接状态。
        if (facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL)
        {
            stateIn = stateIn.setValue(FACING_TO_PROPERTY_MAP.get(facing), this.canConnect(facingState, facingState.isFaceSturdy(worldIn, facingPos, facing.getOpposite())));
        }
        else if (facing == Direction.DOWN)
        {
            BlockPos posDown = currentPos.relative(facing);
            BlockState state = worldIn.getBlockState(posDown);
            stateIn = stateIn.setValue(POST, state.is(BlockTags.WOODEN_FENCES) || state.isFaceSturdy(worldIn, posDown, Direction.UP));
        }
        else if (facing == Direction.UP)
        {
            BlockPos posUp = currentPos.relative(facing);
            BlockState state = worldIn.getBlockState(posUp);
            stateIn = stateIn.setValue(UP, state.is(BlockTags.WOODEN_FENCES) || state.isFaceSturdy(worldIn, posUp, Direction.DOWN));
        }

        // To judge whether vines can be stay here. 判断缠绕藤（棚架）作物在此是否合理。
        // Distance should be setValuein 7. 攀爬距离应该小于等于7。
        boolean valid = false;
        if (hasHorizontalBar(stateIn))
        {
            int distance = getNearDistance( worldIn, currentPos);
            if (distance < 7)
            {
                stateIn = stateIn.setValue(DISTANCE, distance + 1);
                valid = true;
            }
        }
        if (hasPost(stateIn))
        {
            BlockState down = worldIn.getBlockState(currentPos.below());
            if (down.is(BlockTags.DIRT))
            {
                stateIn = stateIn.setValue(DISTANCE, 0);
                valid = true;
            }
            else if (down.getBlock() instanceof TrellisWithVineBlock)
            {
                if (down.getValue(AGE) == 3 && ((TrellisWithVineBlock) down.getBlock()).type == type)
                {
                    stateIn = stateIn.setValue(DISTANCE, down.getValue(DISTANCE));
                    valid = true;
                }
            }
        }
        if (!valid)
        {
            stateIn = VineInfoManager.getEmptyTrellis((TrellisWithVineBlock) stateIn.getBlock()).getRelevantState(stateIn);
        }
        return stateIn;
    }



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(AGE, DISTANCE));
    }

    public Block getEmptyTrellis()
    {
        return VineInfoManager.getEmptyTrellis(this);
    }

    @Override
    public BlockState getRelevantState(BlockState old)
    {
        BlockState newState = super.getRelevantState(old);
        return newState.setValue(AGE, 0);
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder)
    {
        return Lists.newArrayList(new ItemStack(getEmptyTrellis()));
    }

    public VineType getType()
    {
        return type;
    }

    static
    {
        VoxelShape TOP_SHAPE = VoxelShapeHelper.createVoxelShape(0.0D, 4.0D, 0.0D, 16.0D, 9.0D, 16.0D);
        VoxelShape POST_SHAPE = VoxelShapeHelper.createVoxelShape(6.0D, 0.0D, 6.0D, 4.0D, 12.0D, 4.0D);
        VoxelShape POST_UP_SHAPE = VoxelShapeHelper.createVoxelShape(6.0D, 7.0D, 6.0D, 4.0D, 9.0D, 4.0D);
        SHAPES = new VoxelShape[]{TOP_SHAPE, POST_UP_SHAPE, POST_SHAPE, Shapes.or(POST_UP_SHAPE, POST_SHAPE),
                TOP_SHAPE, Shapes.or(TOP_SHAPE, POST_UP_SHAPE), Shapes.or(TOP_SHAPE, POST_SHAPE), Shapes.or(TOP_SHAPE, POST_UP_SHAPE, POST_SHAPE)};
    }
}
