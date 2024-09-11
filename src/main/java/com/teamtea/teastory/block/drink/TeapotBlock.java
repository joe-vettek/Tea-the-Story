package com.teamtea.teastory.block.drink;


import com.teamtea.teastory.blockentity.TeapotBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;
import com.teamtea.teastory.registry.FluidRegister;
import com.teamtea.teastory.registry.ModCapabilities;
import com.teamtea.teastory.registry.BlockEntityRegister;
import com.teamtea.teastory.block.NormalHorizontalBlock;
import com.teamtea.teastory.client.SoundEventsRegistry;

import java.util.Optional;

public class TeapotBlock extends NormalHorizontalBlock implements EntityBlock {
    private static final VoxelShape SHAPE = Block.box(5F, 0F, 5F, 11F, 8F, 11F);

    public TeapotBlock(Properties properties) {
        super(properties);
    }


    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    // @Override
    // protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    //     super.createBlockStateDefinition(builder.add(HORIZONTAL_FACING));
    // }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        double d0 = pos.getX() + 0.5D;
        double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
        double d2 = pos.getZ() + 0.5D;
        double d4 = rand.nextDouble() * 0.6D - 0.3D;
        var tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof TeapotBlockEntity && ((TeapotBlockEntity) tileentity).getFluid() == FluidRegister.BOILING_WATER_STILL.get()) {
            worldIn.addParticle(ParticleTypes.CLOUD, false, d0 + d4, d1 + 0.5D, d2 + d4, 0.0D, 0.1D, 0.0D);
        }
    }


    @Override
    public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
        if (!worldIn.isClientSide()) {
            var te = worldIn.getBlockEntity(pos);
            if (te instanceof TeapotBlockEntity) {
                worldIn.destroyBlock(pos, false);
                Fluid fluid = ((TeapotBlockEntity) te).getFluid();
                if (fluid instanceof FlowingFluid)
                    worldIn.setBlockAndUpdate(pos, ((FlowingFluid) fluid).getFlowing().defaultFluidState().createLegacyBlock());
                worldIn.playSound(null, pos, SoundEventsRegistry.CUP_BROKEN, SoundSource.BLOCKS, 0.5F, 0.9F);
            }
        }

        super.fallOn(worldIn, state, pos, entityIn, fallDistance);
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level level, BlockPos pos, Player player, BlockHitResult pHitResult) {
        ItemHandlerHelper.giveItemToPlayer(player, getSelf(level, pos));
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        return InteractionResult.SUCCESS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        FluidUtil.getFluidHandler(player.getItemInHand(handIn).copy())
                .flatMap(item ->
                        Optional.ofNullable(level.getCapability(Capabilities.FluidHandler.BLOCK, pos, hit.getDirection()))).ifPresent(fluid ->
                        FluidUtil.interactWithFluidHandler(player, handIn, fluid));
        return ItemInteractionResult.SUCCESS;
    }


    public ItemStack getSelf(BlockGetter worldIn, BlockPos pos) {
        var tileEntity = worldIn.getBlockEntity(pos);
        if (tileEntity instanceof TeapotBlockEntity) {
            FluidStack fluidStack = ((TeapotBlockEntity) tileEntity).getFluidTank().getFluidInTank(0);
            ItemStack itemStack = new ItemStack(this);
            if (fluidStack.isEmpty()) {
                return itemStack;
            }
            itemStack.set(ModCapabilities.SIMPLE_FLUID, SimpleFluidContent.copyOf(fluidStack));
            return itemStack;
        } else return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return getSelf(level, pos);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if (pDirection == Direction.DOWN && pNeighborState.isAir()) {
            if (pLevel instanceof ServerLevel serverLevel) {
                popResource(serverLevel, pPos, getSelf(serverLevel, pPos));
            }
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        var tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof TeapotBlockEntity) {
            FluidUtil.getFluidHandler(stack).ifPresent(f -> ((TeapotBlockEntity) tileentity).setFluidTank(f.getFluidInTank(0)));
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return BlockEntityRegister.TEAPOT_TYPE.get().create(blockPos, blockState);
    }
}
