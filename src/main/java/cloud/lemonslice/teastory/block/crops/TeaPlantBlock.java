package cloud.lemonslice.teastory.block.crops;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;
import xueluoanping.teastory.ItemRegister;
import xueluoanping.teastory.BlockRegister;

import java.util.List;

public class TeaPlantBlock extends BushBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 11);
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};


    public TeaPlantBlock(Properties copy) {
        super(copy);
        this.registerDefaultState(defaultBlockState().setValue(AGE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(AGE)];
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 11;
    }

    protected int getAge(BlockState state) {
        return state.getValue(this.getAgeProperty());
    }

    public BlockState withAge(int age) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), age);
    }

    public boolean isMaxAge(BlockState state) {
        return state.getValue(this.getAgeProperty()) >= this.getMaxAge();
    }


    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        super.tick(state, worldIn, pos, rand);
        if (!worldIn.isAreaLoaded(pos, 1) || !worldIn.dimensionType().hasSkyLight())
            return;
        if (worldIn.getRawBrightness(pos, 0) >= 9) {
            int i = this.getAge(state);

            if (i < this.getMaxAge() && i != 8) {
                float f = getGrowthChance(this, worldIn, pos);
                if (net.neoforged.neoforge.common.CommonHooks.canCropGrow(worldIn, pos, state, rand.nextInt((int) (25.0F / f) + 1) == 0)) {
                    worldIn.setBlock(pos, state.setValue(AGE, i + 1), Block.UPDATE_CLIENTS);
                    net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(worldIn, pos, state);
                }
            }
        }
    }


    protected int getBonemealAgeIncrease(ServerLevel worldIn) {
        return 2;
    }

    protected static float getGrowthChance(Block blockIn, ServerLevel worldIn, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockpos = pos.below();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState blockstate = worldIn.getBlockState(blockpos.offset(i, 0, j));
                if (blockstate.canSustainPlant(worldIn, blockpos.offset(i, 0, j), Direction.UP, (net.minecraftforge.common.IPlantable) blockIn)) {
                    f1 = 1.0F;
                    if (blockstate.isFertile(worldIn, blockpos.offset(i, 0, j))) {
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
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return PlantType.CROP;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!worldIn.isClientSide()) {
            if (player.getItemInHand(handIn).is(Tags.Items.SHEARS))
                switch (this.getAge(state)) {
                    case 8:
                        worldIn.setBlockAndUpdate(pos, this.defaultBlockState().setValue(AGE, worldIn.getRandom().nextInt(3) + 4));
                        worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(ItemRegister.TEA_LEAVES.get(), worldIn.getRandom().nextInt(5) + 1)));
                        player.getItemInHand(handIn).setDamageValue(player.getItemInHand(handIn).getDamageValue()+1);
                        return InteractionResult.SUCCESS;
                    case 11:
                        worldIn.setBlockAndUpdate(pos, this.defaultBlockState().setValue(AGE, worldIn.getRandom().nextInt(3) + 4));
                        worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(BlockRegister.TEA_SEEDS.get(), worldIn.getRandom().nextInt(5) + 1)));
                        worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(ItemRegister.TEA_LEAVES.get(), 1)));
                        player.getItemInHand(handIn).setDamageValue(player.getItemInHand(handIn).getDamageValue()+1);
                        return InteractionResult.SUCCESS;
                }
            return InteractionResult.FAIL;
        } else {
            return InteractionResult.SUCCESS;
        }
    }


    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.getBlock() instanceof FarmBlock;
    }

    // @Override
    // public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
    //     return worldIn.getBlockState(pos.below()).getBlock() instanceof FarmBlock;
    // }

    // onEntityCollision
    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof Ravager && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, entityIn)) {
            worldIn.destroyBlock(pos, true);
        }
        if (entityIn instanceof LivingEntity) {
            entityIn.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
        }

        super.entityInside(state, worldIn, pos, entityIn);
    }

    // getSeedsItem
    // @Override
    protected ItemLike getBaseSeedId() {
        return BlockRegister.TEA_SEEDS.get();
    }

    // getCloneItemStack
    @Override
    public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(this.getBaseSeedId());
    }

    // canUseBonemeal
    @Override
    public boolean isValidBonemealTarget(LevelReader p_256559_, BlockPos p_50898_, BlockState p_50899_) {
        return true;
    }

    // canGrow
    @Override
    public boolean isBonemealSuccess(Level level, RandomSource p_220879_, BlockPos p_220880_, BlockState state) {
        return !this.isMaxAge(state) && state.getValue(this.getAgeProperty()) != 8;
    }

    // grow
    @Override
    public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        if (!worldIn.dimensionType().natural()) {
            return;
        }
        int i = this.getAge(state);
        if (i == 6) {
            worldIn.setBlock(pos, this.withAge(9), Block.UPDATE_CLIENTS);
            return;
        } else {
            i += this.getBonemealAgeIncrease(worldIn);
        }
        i = Math.min(i, this.getMaxAge());

        worldIn.setBlock(pos, this.withAge(i), Block.UPDATE_CLIENTS);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(AGE));
    }
}
