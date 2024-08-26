package cloud.lemonslice.teastory.block.crops;


import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.TriState;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MelonVineBlock extends BushBlock implements BonemealableBlock {
    // public static final MapCodec<MelonVineBlock> CODEC = simpleCodec(MelonVineBlock::new);
    public static final MapCodec<MelonVineBlock> CODEC = RecordCodecBuilder.mapCodec(
            vineBlockInstance -> vineBlockInstance.group(BlockSetType.CODEC.fieldOf("melon").forGetter((melonVineBlock -> melonVineBlock.melon)), propertiesCodec())
                    .apply(vineBlockInstance, MelonVineBlock::new)
    );

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
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(AGE) == 7 ? SHAPE_MELON : SHAPE;
    }


    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult pHitResult) {
        if (player.getItemInHand(handIn).is(Tags.Items.TOOLS_SHEAR) && state.getValue(AGE) == 7) {
            player.getItemInHand(handIn).hurtAndBreak(1, player, LivingEntity.getSlotForHand(handIn));
            level.setBlockAndUpdate(pos, this.defaultBlockState());
            if (!level.isClientSide()) {
                Block.popResource(level, pos, new ItemStack(melon));
                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(pStack, state, level, pos, player, handIn, pHitResult);
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
        if (!isValidBonemealTarget(worldIn, pos, state)) {
            return;
        }
        if (hasNearMelon(worldIn, pos, this)) {
            return;
        }
        if (worldIn.getRawBrightness(pos, 0) >= 9) {
            float f = getGrowthChance(state, worldIn, pos);
            if (net.neoforged.neoforge.common.CommonHooks.canCropGrow(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                int i = state.getValue(AGE);
                if (i + 1 < 7) {
                    worldIn.setBlock(pos, state.setValue(AGE, i + 1), Block.UPDATE_CLIENTS);

                    BlockPos blockPos = pos.relative(Direction.Plane.HORIZONTAL.getRandomDirection(random));
                    BlockState blockState = worldIn.getBlockState(blockPos);
                    if (blockState.isAir() && this.mayPlaceOn(worldIn.getBlockState(blockPos.below()), worldIn, blockPos.below())) {
                        worldIn.setBlockAndUpdate(blockPos, this.defaultBlockState());
                    }
                } else {
                    worldIn.setBlock(pos, state.setValue(AGE, 7), Block.UPDATE_CLIENTS);
                }

                net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(worldIn, pos, state);
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
    public boolean isValidBonemealTarget(LevelReader p_256559_, BlockPos p_50898_, BlockState p_50899_) {
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


    protected static float getGrowthChance(BlockState blockIn, ServerLevel worldIn, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockpos = pos.below();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState blockstate = worldIn.getBlockState(blockpos.offset(i, 0, j));
                if (blockstate.canSustainPlant(worldIn, blockpos.offset(i, 0, j), Direction.UP, blockIn)!= TriState.FALSE) {
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
        boolean flag = blockIn.getBlock() == worldIn.getBlockState(blockpos3).getBlock() || blockIn.getBlock() == worldIn.getBlockState(blockpos4).getBlock();
        boolean flag1 = blockIn.getBlock() == worldIn.getBlockState(blockpos1).getBlock() || blockIn.getBlock() == worldIn.getBlockState(blockpos2).getBlock();
        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = blockIn.getBlock() == worldIn.getBlockState(blockpos3.north()).getBlock() || blockIn.getBlock() == worldIn.getBlockState(blockpos4.north()).getBlock() || blockIn.getBlock() == worldIn.getBlockState(blockpos4.south()).getBlock() || blockIn.getBlock() == worldIn.getBlockState(blockpos3.south()).getBlock();
            if (flag2) {
                f /= 2.0F;
            }
        }
        return f;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return new ItemStack(Items.MELON_SEEDS);
    }

}
