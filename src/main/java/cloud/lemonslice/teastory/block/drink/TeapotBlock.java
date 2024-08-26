package cloud.lemonslice.teastory.block.drink;


import cloud.lemonslice.teastory.block.craft.IStoveBlock;
import cloud.lemonslice.teastory.blockentity.TeapotTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;
import xueluoanping.teastory.FluidRegistry;
import xueluoanping.teastory.ModCapabilities;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.NormalHorizontalBlock;
import xueluoanping.teastory.client.SoundEventsRegistry;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

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
        if (tileentity instanceof TeapotTileEntity && ((TeapotTileEntity) tileentity).getFluid() == FluidRegistry.BOILING_WATER_STILL.get()) {
            worldIn.addParticle(ParticleTypes.CLOUD, false, d0 + d4, d1 + 0.5D, d2 + d4, 0.0D, 0.1D, 0.0D);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        var tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof TeapotTileEntity) {
            if (IStoveBlock.isBurning(worldIn, pos.below()) && ((TeapotTileEntity) tileentity).getFluid() == Fluids.WATER) {
                ((TeapotTileEntity) tileentity).setFluid(FluidRegistry.BOILING_WATER_STILL.get());
            }
        }
    }


    @Override
    public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
        if (!worldIn.isClientSide()) {
            var te = worldIn.getBlockEntity(pos);
            if (te instanceof TeapotTileEntity) {
                worldIn.destroyBlock(pos, false);
                Fluid fluid = ((TeapotTileEntity) te).getFluid();
                if (fluid instanceof FlowingFluid)
                    worldIn.setBlockAndUpdate(pos, ((FlowingFluid) fluid).getFlowing().defaultFluidState().createLegacyBlock());
                worldIn.playSound(null, pos, SoundEventsRegistry.CUP_BROKEN, SoundSource.BLOCKS, 0.5F, 0.9F);
            }
        }

        super.fallOn(worldIn, state, pos, entityIn, fallDistance);
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level level, BlockPos pos, Player player, BlockHitResult pHitResult) {
        ItemHandlerHelper.giveItemToPlayer(player, getDrop(level, pos));
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        return InteractionResult.SUCCESS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        FluidUtil.getFluidHandler(player.getItemInHand(handIn).copy()).flatMap(item -> Optional.ofNullable(level.getCapability(Capabilities.FluidHandler.BLOCK, pos, hit.getDirection()))).ifPresent(fluid ->
                FluidUtil.interactWithFluidHandler(player, handIn, fluid));
        return ItemInteractionResult.SUCCESS;
    }


    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pos, BlockState pNewState, boolean isMoving) {
        if (!pNewState.is(this))
            popResource(pLevel, pos, getDrop(pLevel, pos));
        super.onRemove(pState, pLevel, pos, pNewState, isMoving);
    }


    public ItemStack getDrop(Level worldIn, BlockPos pos) {
        var tileEntity = worldIn.getBlockEntity(pos);
        if (tileEntity instanceof TeapotTileEntity) {
            FluidStack fluidStack = ((TeapotTileEntity) tileEntity).getFluidTank().getFluidInTank(0);
            ItemStack itemStack = new ItemStack(this);
            if (fluidStack.isEmpty()) {
                return itemStack;
            }
            itemStack.set(ModCapabilities.SIMPLE_FLUID, SimpleFluidContent.copyOf(fluidStack));
            return itemStack;
        } else return ItemStack.EMPTY;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        var tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof TeapotTileEntity) {
            FluidUtil.getFluidHandler(stack).ifPresent(f -> ((TeapotTileEntity) tileentity).setFluidTank(f.getFluidInTank(0)));
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return TileEntityTypeRegistry.TEAPOT_TYPE.get().create(blockPos, blockState);
    }
}
