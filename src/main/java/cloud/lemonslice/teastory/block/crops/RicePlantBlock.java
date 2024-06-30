package cloud.lemonslice.teastory.block.crops;


import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import xueluoanping.teastory.AllItems;
import xueluoanping.teastory.ItemBlocks;


import java.util.List;


public class RicePlantBlock extends CropBlock
{
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public RicePlantBlock(Properties copy)
    {
        super(copy);
        this.registerDefaultState(defaultBlockState().setValue(AGE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(AGE)];
    }

    protected boolean isValidGround(BlockState state, LevelReader worldIn, BlockPos pos)
    {
        return state.getBlock() == ItemBlocks.paddyField.get();
    }

    protected boolean canPlantSeedlings(BlockState state, LevelReader worldIn, BlockPos pos)
    {
        return isValidGround(state, worldIn, pos) && state.getValue(BlockStateProperties.WATERLOGGED);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        if (state.getValue(AGE) <= 0)
            return canPlantSeedlings(worldIn.getBlockState(blockpos), worldIn, blockpos);
        return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        super.tick(state, worldIn, pos, rand);
        if (!worldIn.isAreaLoaded(pos, 1)) return;
        if (worldIn.getRawBrightness(pos,0) >= 9)
        {
            int i = this.getAge(state);
            if (i < this.getMaxAge())
            {
                BlockState paddy = worldIn.getBlockState(pos.below());
                if (!(paddy.getBlock() instanceof PaddyFieldBlock))
                {
                    return;
                }
                if (i <= 2 && !paddy.getValue(BlockStateProperties.WATERLOGGED))
                {
                    return;
                }
                else if (i >= 6 && paddy.getValue(BlockStateProperties.WATERLOGGED))
                {
                    return;
                }
                float f = getGrowthSpeed(this, worldIn, pos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int) (50.0F / f) + 1) == 0))
                {
                    worldIn.setBlock(pos, this.getStateForAge(i + 1), Block.UPDATE_CLIENTS);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);

                    if (!isNearbyTidy(i + 1, worldIn, pos))
                    {
                        growTogether(i + 1, worldIn, pos.north());
                        growTogether(i + 1, worldIn, pos.south());
                        growTogether(i + 1, worldIn, pos.east());
                        growTogether(i + 1, worldIn, pos.west());
                    }
                }
            }
        }
    }


    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        super.randomTick(state, worldIn, pos, random);
        tick(state, worldIn, pos, random);
    }


    private boolean isNearbyTidy(int age, ServerLevel worldIn, BlockPos pos)
    {
        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            BlockState state = worldIn.getBlockState(pos.relative(direction));
            if (state.getBlock() == this)
            {
                if (age - state.getValue(AGE) > 2)
                {
                    return false;
                }
            }
        }
        return true;
    }

    private void growTogether(int ageToGrow, ServerLevel worldIn, BlockPos pos)
    {
        BlockState state = worldIn.getBlockState(pos);
        if (state.getBlock() == this)
        {
            if (state.getValue(AGE) < ageToGrow - 2)
            {
                worldIn.setBlockAndUpdate(pos, state.setValue(AGE, state.getValue(AGE) + 1));
                growTogether(ageToGrow, worldIn, pos.north());
                growTogether(ageToGrow, worldIn, pos.south());
                growTogether(ageToGrow, worldIn, pos.east());
                growTogether(ageToGrow, worldIn, pos.west());
            }
        }
    }
    @Override
    protected ItemLike getBaseSeedId() {
        return ItemBlocks.riceGrains.get();
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
        int age = state.getValue(AGE);
        if (age > 0)
        {
            return new ItemStack(ItemBlocks.riceGrains.get());
        }
        else return new ItemStack(ItemBlocks.riceSeedlings.get());
    }


    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> list = Lists.newArrayList();
        if (getAge(state) < 3)
        {
            list.add(new ItemStack(ItemBlocks.riceSeedlings.get()));
        }
        else if (getAge(state) < 7)
        {
            list.add(new ItemStack(AllItems.dryStraw.get()));
        }
        else
        {
            list.add(new ItemStack(AllItems.dryStraw.get()));
            list.add(new ItemStack(ItemBlocks.riceGrains.get(), builder.getLevel().getRandom().nextInt(4) + 1));
        }
        return list;
    }

}
