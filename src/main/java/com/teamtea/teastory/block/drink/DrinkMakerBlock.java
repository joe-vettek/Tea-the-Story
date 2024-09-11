package com.teamtea.teastory.block.drink;

import com.teamtea.teastory.block.BlockHelper;
import com.teamtea.teastory.blockentity.DrinkMakerBlockEntity;
import com.teamtea.teastory.helper.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.jetbrains.annotations.Nullable;
import com.teamtea.teastory.registry.BlockEntityRegister;
import com.teamtea.teastory.block.NormalHorizontalBlock;

import java.util.Optional;

public class DrinkMakerBlock extends NormalHorizontalBlock implements EntityBlock {
    public static final BooleanProperty LEFT = BooleanProperty.create("left");
    private boolean flag = false;
    private static final VoxelShape NORTH_LEFT = VoxelShapeHelper.createVoxelShape(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 14.0D);
    private static final VoxelShape SOUTH_LEFT = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 1.0D, 15.0D, 4.0D, 14.0D);
    private static final VoxelShape WEST_LEFT = VoxelShapeHelper.createVoxelShape(1.0D, 0.0D, 0.0D, 14.0D, 4.0D, 15.0D);
    private static final VoxelShape EAST_LEFT = VoxelShapeHelper.createVoxelShape(1.0D, 0.0D, 1.0D, 14.0D, 4.0D, 15.0D);

    public DrinkMakerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(LEFT, true));
    }


    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(LEFT));
    }


    @Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0.8F;
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {

        if (state.getValue(LEFT)) {
            switch (state.getValue(FACING)) {
                case NORTH:
                    return NORTH_LEFT;
                case SOUTH:
                    return SOUTH_LEFT;
                case EAST:
                    return EAST_LEFT;
                default:
                    return WEST_LEFT;
            }
        } else {
            switch (state.getValue(FACING)) {
                case NORTH:
                    return SOUTH_LEFT;
                case SOUTH:
                    return NORTH_LEFT;
                case EAST:
                    return WEST_LEFT;
                default:
                    return EAST_LEFT;
            }
        }
    }


    @Override
    public BlockState playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        if (!worldIn.isClientSide()) {
            if (player.isCreative()) {
                removeBottomHalf(worldIn, pos, state, player);
            }
        }

        return super.playerWillDestroy(worldIn, pos, state, player);
    }


    protected static void removeBottomHalf(Level world, BlockPos pos, BlockState state, Player player) {
        if (!state.getValue(LEFT)) {
            Direction facing = state.getValue(FACING);
            BlockPos blockPos = pos.relative(BlockHelper.getPreviousHorizontal(facing));
            BlockState blockstate = world.getBlockState(blockPos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(LEFT)) {
                world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, blockPos, Block.getId(blockstate));
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if (state.getValue(LEFT)) {
            BlockState blockstate = worldIn.getBlockState(pos.below());
            return blockstate.isFaceSturdy(worldIn, pos.below(), Direction.UP);
        } else {
            Direction facing = state.getValue(FACING);
            BlockPos blockPos = pos.relative(BlockHelper.getPreviousHorizontal(facing));
            BlockState blockstate = worldIn.getBlockState(blockPos);
            return blockstate.is(this);
        }
    }


    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(LEFT)) {
            if (facing == BlockHelper.getNextHorizontal(stateIn.getValue(FACING))) {
                return facingState.getBlock() == this && !facingState.getValue(LEFT) ? stateIn : Blocks.AIR.defaultBlockState();
            }
        } else if (facing == BlockHelper.getPreviousHorizontal(stateIn.getValue(FACING))) {
            return facingState.getBlock() == this && facingState.getValue(LEFT) ? stateIn : Blocks.AIR.defaultBlockState();
        }
        return stateIn;
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        super.destroy(level, pos, state);
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            if (state.getValue(LEFT))
                serverLevel.removeBlockEntity(pos);
        }
    }

    @Override
    public void onRemove(BlockState pState, Level worldIn, BlockPos pos, BlockState pNewState, boolean isMoving) {
        if (!(pNewState.is(this))) {
            dropItems(worldIn, pos);
        }
        super.onRemove(pState, worldIn, pos, pNewState, isMoving);
    }


    private void dropItems(Level worldIn, BlockPos pos) {
        BlockEntity te = worldIn.getBlockEntity(pos);
        if (te instanceof DrinkMakerBlockEntity) {
            for (int i = 0; i < 11; i++) {
                ItemStack stack = ((DrinkMakerBlockEntity) te).decrStackSize(i, Integer.MAX_VALUE);
                if (stack != ItemStack.EMPTY) {
                    Block.popResource(worldIn, pos, stack);
                }
            }
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            flag = false;
            if (!pState.getValue(LEFT)) {
                pos = pos.relative(BlockHelper.getPreviousHorizontal(pState.getValue(FACING)));
            }

            BlockPos finalPos = pos;
            FluidUtil.getFluidHandler(pStack.copy()).flatMap(item -> Optional.ofNullable(pLevel.getCapability(Capabilities.FluidHandler.BLOCK, finalPos, pHitResult.getDirection())))
                    .ifPresent(fluid ->
                    flag = FluidUtil.interactWithFluidHandler(pPlayer, pHand, fluid));
            if (flag)
                return ItemInteractionResult.SUCCESS;

        }
        return super.useItemOn(pStack, pState, pLevel, pos, pPlayer, pHand, pHitResult);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player pPlayer, BlockHitResult pHitResult) {
        if (!worldIn.isClientSide()) {
            if (!state.getValue(LEFT)) {
                pos = pos.relative(BlockHelper.getPreviousHorizontal(state.getValue(FACING)));
            }
            BlockEntity te = worldIn.getBlockEntity(pos);
            if (te instanceof DrinkMakerBlockEntity) {
                // ((DrinkMakerTileEntity) te).requestModelDataUpdate();
                pPlayer.openMenu((MenuProvider) te, pos);
            }
        }
        return super.useWithoutItem(state, worldIn, pos, pPlayer, pHitResult);
    }


    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state1, boolean b) {
        if (!level.isClientSide() && state.getValue(LEFT)) {
            level.setBlockAndUpdate(pos.relative(state.getValue(FACING).getClockWise()), state.setValue(LEFT, false));
        }
        super.onPlace(state, level, pos, state1, b);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(LEFT) ? new DrinkMakerBlockEntity(pos, state) : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState state, BlockEntityType<T> blockEntityType) {
        // return null;
        return !worldIn.isClientSide && state.getValue(LEFT) ?
                NormalHorizontalBlock.createTickerHelper(blockEntityType, BlockEntityRegister.DRINK_MAKER_TYPE.get(), DrinkMakerBlockEntity::tick) : null;
    }
}
