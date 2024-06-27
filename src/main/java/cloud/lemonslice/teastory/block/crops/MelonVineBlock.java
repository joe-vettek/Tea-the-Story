package cloud.lemonslice.teastory.block.crops;


import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MelonVineBlock extends BushBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    private static final VoxelShape SHAPE = VoxelShapeHelper.createVoxelShape(0, 0, 0, 16, 5, 16);
    private static final VoxelShape SHAPE_MELON = VoxelShapeHelper.createVoxelShape(0, 0, 0, 16, 13, 16);
    private final Block melon;

    public MelonVineBlock(Properties copy, Block melon) {
        super(copy);
        this.registerDefaultState(defaultBlockState().setValue(AGE, 0));
        this.melon = melon;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(AGE) == 7 ? SHAPE_MELON : SHAPE;
    }


    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (player.getItemInHand(handIn).getItem() == Items.SHEARS && state.getValue(AGE) == 7) {
            player.getItemInHand(handIn).hurtAndBreak(1, player, e -> e.broadcastBreakEvent(handIn));
            worldIn.setBlockAndUpdate(pos, this.defaultBlockState());
            if (!worldIn.isClientSide()) {
                Block.popResource(worldIn, pos, new ItemStack(melon));
                worldIn.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return InteractionResult.SUCCESS;
        } else return InteractionResult.PASS;
    }


    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.is(BlockTags.DIRT) || state.getBlock() instanceof FarmBlock || canSupportRigidBlock(worldIn, pos);
    }


    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (!worldIn.isAreaLoaded(pos, 1)) {
            return;
        }
        if (!isValidBonemealTarget(worldIn, pos, state, false)) {
            return;
        }
        if (hasNearMelon(worldIn, pos, this)) {
            return;
        }
        if (worldIn.getRawBrightness(pos,0) >= 9) {
            float f = getGrowthChance(this, worldIn, pos);
            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                int i = state.getValue(AGE);
                if (i + 1 < 7) {
                    worldIn.setBlock(pos, state.setValue(AGE, i + 1), Block.UPDATE_CLIENTS);

                    BlockPos blockPos = pos.relative(Direction.Plane.HORIZONTAL.getRandomDirection(random));
                    BlockState blockState = worldIn.getBlockState(blockPos);
                    if (blockState.isAir() && this.mayPlaceOn(worldIn.getBlockState(blockPos.below()), worldIn, blockPos.below())) {
                        worldIn.setBlockAndUpdate(blockPos, this.defaultBlockState());
                    }
                } else {
                    worldIn.setBlock(pos, state.setValue(AGE, 7),  Block.UPDATE_CLIENTS);
                }
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }
        }
    }

    public static boolean hasNearMelon(ServerLevel worldIn, BlockPos pos, Block melon) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState state = worldIn.getBlockState(pos.relative(direction));
            if (state.is(melon) && state.getValue(AGE) == 7) {
                return true;
            }
        }
        return false;
    }


    // canUseBonemeal
    @Override
    public boolean isValidBonemealTarget(LevelReader p_256559_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
        return true;
    }

    // canGrow
    @Override
    public boolean isBonemealSuccess(Level worldIn, RandomSource p_220879_, BlockPos pos, BlockState state) {
        return state.getValue(AGE) < 7 && worldIn.getBlockState(pos.below()).getBlock() instanceof FarmBlock;
    }

    // grow
    @Override
    public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        int i = Math.min(7, state.getValue(AGE) + Mth.nextInt(worldIn.getRandom(), 2, 5));
        worldIn.setBlock(pos, state.setValue(AGE, i), Block.UPDATE_CLIENTS);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(AGE));
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return PlantType.CROP;
    }


    protected static float getGrowthChance(Block blockIn, ServerLevel worldIn, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockpos = pos.below();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState blockstate = worldIn.getBlockState(blockpos.offset(i, 0, j));
                if (blockstate.canSustainPlant(worldIn, blockpos.offset(i, 0, j), Direction.UP, (IPlantable) blockIn)) {
                    f1 = 1.0F;
                    if (blockstate.isFertile(worldIn, pos.offset(i, 0, j))) {
                        f1 = 3.0F;
                    }
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
        return f;
    }


    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return state.getValue(AGE) == 7 ? Lists.newArrayList(new ItemStack(Blocks.MELON)) : Collections.emptyList();
    }


    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(Items.MELON_SEEDS);
    }

}
