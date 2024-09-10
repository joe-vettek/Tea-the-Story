package com.teamtea.teastory.block.craft;

import com.teamtea.teastory.helper.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;
import com.teamtea.teastory.ItemRegister;
import com.teamtea.teastory.block.NormalHorizontalBlock;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SaucepanBlock extends NormalHorizontalBlock {
    public static final EnumProperty<CookStep> STEP = EnumProperty.create("step", CookStep.class);
    public static final BooleanProperty LID = BooleanProperty.create("lid");
    private static final VoxelShape PAN_SHAPE;
    private static final VoxelShape LID_SHAPE;

    // TODO 方块掉落
    public SaucepanBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(defaultBlockState().setValue(STEP, CookStep.EMPTY).setValue(LID, true));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(LID) ? LID_SHAPE : PAN_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(STEP, LID));
    }


    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        if (stateIn.getValue(STEP) == CookStep.COOKED) {
            double d0 = pos.getX() + 0.5D;
            double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = pos.getZ() + 0.5D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            worldIn.addParticle(ParticleTypes.CLOUD, false, d0 + d4, d1 + 0.5D, d2 + d4, 0.0D, 0.1D, 0.0D);
        }
    }


    @Override
    public InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        if (!worldIn.isClientSide()) {
            ItemStack held = player.getItemInHand(player.getUsedItemHand());
            if (state.getValue(LID)) {
                worldIn.setBlockAndUpdate(pos, state.setValue(LID, false));
                player.addItem(new ItemStack(ItemRegister.SAUCEPAN_LID.get()));
                return InteractionResult.SUCCESS;
            } else if (state.getValue(STEP) == CookStep.COOKED) {
                worldIn.setBlockAndUpdate(pos, state.setValue(STEP, CookStep.EMPTY));
                player.addItem(new ItemStack(ItemRegister.RICE_BALL.get(), 3));
                return InteractionResult.SUCCESS;
            } else if (held.getItem() == ItemRegister.SAUCEPAN_LID.get()) {
                worldIn.setBlockAndUpdate(pos, state.setValue(LID, true));
                held.shrink(1);
                worldIn.playSound(null, player.getBlockX(), player.getBlockY() + 0.5, player.getBlockZ(), SoundEvents.METAL_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            } else if (state.getValue(STEP) == CookStep.RAW && FluidUtil.getFluidContained(held).isPresent()) {
                FluidStack fluidStack = FluidUtil.getFluidContained(held).get();
                if (fluidStack.getFluid() == Fluids.WATER && fluidStack.getAmount() >= 1000) {
                    if (FluidUtil.interactWithFluidHandler(player, player.getUsedItemHand(), new FluidTank(1000))) {
                        worldIn.setBlockAndUpdate(pos, state.setValue(STEP, CookStep.WATER));
                        return InteractionResult.SUCCESS;
                    }
                    return InteractionResult.FAIL;
                }
                return InteractionResult.FAIL;
            } else if (state.getValue(STEP) == CookStep.EMPTY && held.getItem() == ItemRegister.WASHED_RICE.get() && held.getCount() >= 8) {
                worldIn.setBlockAndUpdate(pos, state.setValue(STEP, CookStep.RAW));
                worldIn.playSound(null, player.getBlockX(), player.getBlockY() + 0.5, player.getBlockZ(), SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!player.isCreative()) {
                    held.shrink(8);
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        // if (state != null && context.getItemInHand().getOrCreateTag().contains("lid")) {
        //     state = state.setValue(LID, context.getItemInHand().getOrCreateTag().getBoolean("lid"));
        // }
        return state;
    }


    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        BlockState stove = worldIn.getBlockState(pos.below());
        if (IStoveBlock.isBurning(stove) && state.getValue(STEP) == CookStep.WATER) {
            int fuelPower = ((IStoveBlock) stove.getBlock()).getFuelPower();
            if (state.getValue(LID)) {
                if (random.nextInt(6 / fuelPower) == 0) {
                    worldIn.setBlockAndUpdate(pos, state.setValue(STEP, CookStep.COOKED));
                }
            } else if (random.nextInt(24 / fuelPower) == 0) {
                worldIn.setBlockAndUpdate(pos, state.setValue(STEP, CookStep.COOKED));
            }
        }
    }


    static {
        VoxelShape outer = VoxelShapeHelper.createVoxelShape(1, 0, 1, 14, 12, 14);
        VoxelShape inner = VoxelShapeHelper.createVoxelShape(2, 1, 2, 12, 11, 12);
        LID_SHAPE = VoxelShapeHelper.createVoxelShape(1, 0, 1, 14, 13, 14);
        PAN_SHAPE = Shapes.join(outer, inner, BooleanOp.NOT_SAME);
    }
}
