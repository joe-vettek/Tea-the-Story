package cloud.lemonslice.teastory.block.drink;

import cloud.lemonslice.silveroak.helper.BlockHelper;
import cloud.lemonslice.teastory.blockentity.BambooTrayTileEntity;
import cloud.lemonslice.teastory.blockentity.DrinkMakerTileEntity;
import cloud.lemonslice.teastory.blockentity.StoneMillTileEntity;
import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.NormalHorizontalBlock;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.ItemHandlerHelper;
import xueluoanping.teastory.block.entity.NormalContainerTileEntity;

import java.util.List;

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
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        if (!worldIn.isClientSide()) {
            if (player.isCreative()) {
                removeBottomHalf(worldIn, pos, state, player);
            }
            onRemove(state, worldIn, pos, state, false);
        }
        super.playerWillDestroy(worldIn, pos, state, player);
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
            return blockstate.isSolidRender(worldIn, pos.below());
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
    public void onRemove(BlockState blockState, Level worldIn, BlockPos pos, BlockState state, boolean isMoving) {
        if (state.getBlock() != this && !(state.getBlock() == this)) {
            if (state.hasBlockEntity()) {
                // ((NormalContainerTileEntity) worldIn.getBlockEntity(pos)).setRemoved();
                dropItems(worldIn, pos);
                worldIn.removeBlockEntity(pos);
            }
        }
        super.onRemove(blockState, worldIn, pos, state, isMoving);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> list = Lists.newArrayList();
        if (state.getValue(LEFT)) list.add(new ItemStack(this));
        return list;
    }


    private void dropItems(Level worldIn, BlockPos pos) {
        BlockEntity te = worldIn.getBlockEntity(pos);
        if (te instanceof DrinkMakerTileEntity) {
            for (int i = 0; i < 11; i++) {
                ItemStack stack = ((DrinkMakerTileEntity) te).decrStackSize(i, Integer.MAX_VALUE);
                if (stack != ItemStack.EMPTY) {
                    Block.popResource(worldIn, pos, stack);
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {

        if (!worldIn.isClientSide()) {
            flag = false;
            if (!state.getValue(LEFT)) {
                pos = pos.relative(BlockHelper.getPreviousHorizontal(state.getValue(FACING)));
            }
            BlockEntity te = worldIn.getBlockEntity(pos);
            FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(player.getItemInHand(handIn), 1)).ifPresent(item ->
                    te.getCapability(ForgeCapabilities.FLUID_HANDLER, hit.getDirection()).ifPresent(fluid ->
                            flag = FluidUtil.interactWithFluidHandler(player, handIn, fluid)));
            if (flag)
                return InteractionResult.SUCCESS;
            if (te instanceof DrinkMakerTileEntity) {
                // ((DrinkMakerTileEntity) te).requestModelDataUpdate();
                NetworkHooks.openScreen((ServerPlayer) player, (MenuProvider) te, te.getBlockPos());
            }
        }
        return InteractionResult.SUCCESS;
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
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state1, boolean b) {
        super.onPlace(state, level, pos, state1, b);
        if (!level.isClientSide() && state.getValue(LEFT)) {
            level.setBlockAndUpdate(pos.relative(state.getValue(FACING).getClockWise()), state.setValue(LEFT, false));
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(LEFT) ? new DrinkMakerTileEntity(pos, state) : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState state, BlockEntityType<T> blockEntityType) {
        // return null;
        return !worldIn.isClientSide && state.getValue(LEFT) ?
                NormalHorizontalBlock.createTickerHelper(blockEntityType, TileEntityTypeRegistry.DRINK_MAKER_TYPE.get(), DrinkMakerTileEntity::tick) : null;
    }
}
