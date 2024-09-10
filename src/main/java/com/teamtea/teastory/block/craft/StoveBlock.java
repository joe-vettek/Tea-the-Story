package com.teamtea.teastory.block.craft;


import com.teamtea.teastory.blockentity.StoveTileEntity;
import com.teamtea.teastory.helper.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.Nullable;
import com.teamtea.teastory.BlockEntityRegistry;
import com.teamtea.teastory.block.NormalHorizontalBlock;

import java.util.Optional;


public class StoveBlock extends NormalHorizontalBlock implements IStoveBlock, EntityBlock {
    protected int efficiency;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    private static final VoxelShape SHAPE;

    public StoveBlock(Properties properties, int efficiency) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(LIT, false));
        this.efficiency = efficiency;
    }

    @Override
    public int getFuelPower() {
        return efficiency;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(LIT));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        if (IStoveBlock.isBurning(stateIn)) {
            double d0 = pos.getX() + 0.5D;
            double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = pos.getZ() + 0.5D;

            BlockEntity te = worldIn.getBlockEntity(pos);

            Optional.ofNullable(worldIn.getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.DOWN)).ifPresent(inv ->
            {
                int ash = inv.getStackInSlot(0).getCount();
                if (ash < 32) {
                    for (int i = 0; i < ash / 4 + 1; i++) {
                        double d4 = rand.nextDouble() * 0.6D - 0.3D;
                        worldIn.addParticle(ParticleTypes.SMOKE, false, d0 + d4, d1 + 1.0D, d2 + d4, 0.0D, 0.1D, 0.0D);
                    }
                } else {
                    for (int i = 0; i < ash / 5; i++) {
                        double d4 = rand.nextDouble() * 0.6D - 0.3D;
                        worldIn.addParticle(ParticleTypes.LARGE_SMOKE, false, d0 + d4, d1 + 1.0D, d2 + d4, 0.0D, 0.1D, 0.0D);
                    }
                }
                double d4 = rand.nextDouble() * 0.6D - 0.3D;
                worldIn.addParticle(ParticleTypes.FLAME, false, d0 + d4, d1, d2 + d4, 0.0D, 0.06D, 0.0D);
            });
        }
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        BlockEntity te = worldIn.getBlockEntity(pos);
        InteractionHand handIn = player.getUsedItemHand();
        Item held = player.getItemInHand(handIn).getItem();
        if (held == BlockEntityRegistry.BAMBOO_TRAY_ITEM.get()
                || held == BlockEntityRegistry.IRON_KETTLE_ITEM.get()
        ) {
            return InteractionResult.PASS;
        }
        if (te instanceof StoveTileEntity) {
            if (player.isShiftKeyDown()) {
                if (!worldIn.isClientSide()) {
                    // ((StoveTileEntity) te).refresh();
                    player.openMenu((MenuProvider) te, te.getBlockPos());
                }
                return InteractionResult.SUCCESS;
            } else {
                if (held.equals(Items.FLINT_AND_STEEL)) {
                    ((StoveTileEntity) te).setToLit();
                    player.getItemInHand(handIn).hurtAndBreak(1, player, LivingEntity.getSlotForHand(handIn));
                    worldIn.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, worldIn.getRandom().nextFloat() * 0.4F + 0.8F);
                    return InteractionResult.SUCCESS;
                } else if (held.equals(Items.FIRE_CHARGE)) {
                    ((StoveTileEntity) te).setToLit();
                    player.getItemInHand(handIn).shrink(1);
                    return InteractionResult.SUCCESS;
                } else if (player.getItemInHand(handIn).getBurnTime(RecipeType.SMELTING) > 0) {
                    Optional.ofNullable(worldIn.getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.UP)).ifPresent(fuel ->
                    {
                        player.setItemInHand(handIn, fuel.insertItem(0, player.getItemInHand(handIn), false));
                        te.setChanged();
                    });
                    return InteractionResult.SUCCESS;
                } else if (((StoveTileEntity) te).isDoubleClick()) {
                    dropFuel(worldIn, pos);
                    return InteractionResult.SUCCESS;
                } else {
                    dropAsh(worldIn, pos);
                    if (!worldIn.isClientSide())
                        ((StoveTileEntity) te).singleClickStart();
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.FAIL;
    }


    private void dropAsh(Level worldIn, BlockPos pos) {
        BlockEntity te = worldIn.getBlockEntity(pos);
        if (te != null) {
            Optional.ofNullable(worldIn.getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.DOWN)).ifPresent(ash ->
            {
                for (int i = ash.getSlots() - 1; i >= 0; --i) {
                    if (ash.getStackInSlot(i) != ItemStack.EMPTY) {
                        Block.popResource(worldIn, pos, ash.getStackInSlot(i));
                        ((IItemHandlerModifiable) ash).setStackInSlot(i, ItemStack.EMPTY);
                    }
                }
            });
        }
    }

    private void dropFuel(Level worldIn, BlockPos pos) {
        BlockEntity te = worldIn.getBlockEntity(pos);
        if (te != null) {
            Optional.ofNullable(worldIn.getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.UP)).ifPresent(fuel ->
            {
                for (int i = fuel.getSlots() - 1; i >= 0; --i) {
                    if (fuel.getStackInSlot(i) != ItemStack.EMPTY) {
                        Block.popResource(worldIn, pos, fuel.getStackInSlot(i));
                        ((IItemHandlerModifiable) fuel).setStackInSlot(i, ItemStack.EMPTY);
                    }
                }
            });
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pos, BlockState pNewState, boolean isMoving) {
        if (!pNewState.is(this)) {
            dropFuel(pLevel, pos);
            dropAsh(pLevel, pos);
        }
        super.onRemove(pState, pLevel, pos, pNewState, isMoving);
    }


    public static void setState(boolean active, Level worldIn, BlockPos pos) {
        BlockState iblockstate = worldIn.getBlockState(pos);
        BlockEntity tileentity = worldIn.getBlockEntity(pos);

        if (active) {
            worldIn.setBlockAndUpdate(pos, iblockstate.setValue(FACING, iblockstate.getValue(FACING)).setValue(LIT, true));
        } else {
            worldIn.setBlockAndUpdate(pos, iblockstate.setValue(FACING, iblockstate.getValue(FACING)).setValue(LIT, false));
        }

        if (tileentity != null) {
            tileentity.invalidateCapabilities();
            worldIn.setBlockEntity(tileentity);
        }
    }


    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity livingEntity) {

            float level = livingEntity.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(worldIn.registryAccess().holder(Enchantments.FROST_WALKER).get());
            if (!entityIn.isOnFire() && IStoveBlock.isBurning(worldIn, pos) && level > 0) {
                if (worldIn.getRandom().nextBoolean())
                    entityIn.hurt(entityIn.damageSources().inFire(), 1.0F);
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60));
            }
        }
        super.entityInside(state, worldIn, pos, entityIn);
    }

    static {
        VoxelShape top = VoxelShapeHelper.createVoxelShape(0.0D, 14.0D, 0.0D, 16.0D, 2.0D, 16.0D);
        VoxelShape body = VoxelShapeHelper.createVoxelShape(1.0D, 0.0D, 1.0D, 14.0D, 16.0D, 14.0D);
        SHAPE = Shapes.or(top, body);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new StoveTileEntity(p_153215_, p_153216_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState state, BlockEntityType<T> blockEntityType) {
        return !worldIn.isClientSide ?
                NormalHorizontalBlock.createTickerHelper(blockEntityType, BlockEntityRegistry.STOVE_TYPE.get(), StoveTileEntity::tick) : null;
    }
}
