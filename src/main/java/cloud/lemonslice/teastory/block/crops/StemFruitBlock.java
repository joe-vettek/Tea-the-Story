package cloud.lemonslice.teastory.block.crops;


import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;


import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StemFruitBlock extends Block implements BonemealableBlock {
    public final VineType type;
    public static final IntegerProperty AGE_0_4 = IntegerProperty.create("age", 0, 4);
    private static final VoxelShape SHAPE = VoxelShapeHelper.createVoxelShape(3.5, 6.0, 3.5, 9.0, 10.0, 9.0);

    public StemFruitBlock( VineType type,Properties properties) {
        super(properties);
        this.type = type;
        this.registerDefaultState(this.defaultBlockState().setValue(AGE_0_4, 0));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }


    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.above();
        BlockState up = worldIn.getBlockState(blockpos);
        if (state.getBlock() == this && up.getBlock() instanceof TrellisWithVineBlock && ((TrellisWithVineBlock) up.getBlock()).getType() == type)
            return true;
        else return false;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction pDirection, BlockState pNeighborState, LevelAccessor worldIn, BlockPos currentPos, BlockPos pNeighborPos) {
        if (canSurvive(stateIn, worldIn, currentPos)) {
            return stateIn;
        } else return Blocks.AIR.defaultBlockState();
    }


    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        super.tick(state, worldIn, pos, rand);
        if (!worldIn.isAreaLoaded(pos, 1)) return;
        if (worldIn.getRawBrightness(pos, 0) >= 9) {
            int i = state.getValue(AGE_0_4);
            if (i < 4) {
                float f = getGrowthChance(this, worldIn, pos, type);
                if (net.neoforged.neoforge.common.CommonHooks.canCropGrow(worldIn, pos, state, rand.nextInt((int) (25.0F / f) + 1) == 0)) {
                    worldIn.setBlock(pos, state.setValue(AGE_0_4, i + 1), 2);
                    net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(worldIn, pos, state);
                }
            }
        }
    }

    protected static float getGrowthChance(Block blockIn, ServerLevel worldIn, BlockPos pos, VineType type) {
        float f = 1.0F;
        BlockPos blockpos = pos.above();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState blockstate = worldIn.getBlockState(blockpos.offset(i, 0, j));
                if (blockstate.getBlock() instanceof TrellisWithVineBlock && ((TrellisWithVineBlock) blockstate.getBlock()).getType() == type) {
                    f1 = 1.0F;
                }

                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos blockpos1 = pos.north();
        BlockPos blockpos2 = pos.south();
        BlockPos blockpos3 = pos.west();
        BlockPos blockpos4 = pos.east();
        boolean flag = blockIn == worldIn.getBlockState(blockpos3).getBlock() || blockIn == worldIn.getBlockState(blockpos4).getBlock();
        boolean flag1 = blockIn == worldIn.getBlockState(blockpos1).getBlock() || blockIn == worldIn.getBlockState(blockpos2).getBlock();
        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = blockIn == worldIn.getBlockState(blockpos3.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.south()).getBlock() || blockIn == worldIn.getBlockState(blockpos3.south()).getBlock();
            if (flag2) {
                f /= 2.0F;
            }
        }

        return f / 2.0F;
    }


    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState state) {
        return state.getValue(AGE_0_4) < 4;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, RandomSource pRandom, BlockPos pos, BlockState state) {
        int i = state.getValue(AGE_0_4) + 2;
        if (i > 4) {
            i = 4;
        }

        worldIn.setBlock(pos, state.setValue(AGE_0_4, i), 2);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(AGE_0_4));
    }

}
