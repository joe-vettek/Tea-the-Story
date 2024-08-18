package cloud.lemonslice.teastory.block.craft;


import cloud.lemonslice.teastory.blockentity.BambooTrayTileEntity;
import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import cloud.lemonslice.teastory.recipe.bamboo_tray.BambooTraySingleInRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.NormalHorizontalBlock;
import xueluoanping.teastory.block.entity.NormalContainerTileEntity;

import java.util.Random;

public class CatapultBoardBlockWithTray extends NormalHorizontalBlock implements EntityBlock {
    private static final VoxelShape SHAPE = VoxelShapeHelper.createVoxelShape(0, 0, 0, 16, 5, 16);
    private static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;

    public CatapultBoardBlockWithTray(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(ENABLED, false));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return state.isFaceSturdy(worldIn, pos.below(), Direction.UP);
    }


    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.canSurvive(worldIn, currentPos) ?
                Blocks.AIR.defaultBlockState() :
                super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(ENABLED));
    }


    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        worldIn.setBlock(pos, state.setValue(ENABLED, false), Block.UPDATE_CLIENTS);
    }

    private static void dropItems(Level worldIn, BlockPos pos) {
        var te = worldIn.getBlockEntity(pos);
        if (te != null) {
            te.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP).ifPresent(inv ->
            {
                for (int i = inv.getSlots() - 1; i >= 0; --i) {
                    if (inv.getStackInSlot(i) != ItemStack.EMPTY) {
                        Block.popResource(worldIn, pos, inv.getStackInSlot(i));
                        ((IItemHandlerModifiable) inv).setStackInSlot(i, ItemStack.EMPTY);
                    }
                }
            });
        }
    }

    @Override
    public void onRemove(BlockState blockState, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (blockState.hasBlockEntity() && !(newState.getBlock() == this)) {

            dropItems(worldIn, pos);
            worldIn.removeBlockEntity(pos);
        }
        super.onRemove(blockState, worldIn, pos, newState, isMoving);
    }


    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        var te = worldIn.getBlockEntity(pos);
        if (te instanceof BambooTrayTileEntity) {
            if (worldIn.isClientSide()) {
                ((BambooTrayTileEntity) te).refreshSeed();
                return InteractionResult.SUCCESS;
            }
            if (!player.isShiftKeyDown()) {
                if (((BambooTrayTileEntity) te).isDoubleClick()) {
                    dropItems(worldIn, pos);
                    return InteractionResult.SUCCESS;
                }
                if (!((BambooTrayTileEntity) te).isWorking()) {
                    dropItems(worldIn, pos);
                    te.setChanged();
                }
                if (!player.getItemInHand(handIn).isEmpty()) {
                    te.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP).ifPresent(inv ->
                    {
                        BambooTraySingleInRecipe recipe = null;
                        for (var r : worldIn.getRecipeManager().getRecipes()) {
                            if (r.getType().equals(((BambooTrayTileEntity) te).getRecipeType()) && ((BambooTraySingleInRecipe) r).getIngredient().test(player.getItemInHand(handIn))) {
                                recipe = (BambooTraySingleInRecipe) r;
                                break;
                            }
                        }
                        if (recipe != null && !recipe.getRecipeOutput().isEmpty()) {
                            player.setItemInHand(handIn, inv.insertItem(0, player.getItemInHand(handIn), false));
                            te.setChanged();
                        } else ((BambooTrayTileEntity) te).singleClickStart();
                    });
                    return InteractionResult.SUCCESS;
                } else {
                    if (((BambooTrayTileEntity) te).isWorking()) {
                        ((BambooTrayTileEntity) te).singleClickStart();
                    }
                }
            } else {
                NetworkHooks.openScreen((ServerPlayer) player, (MenuProvider) te, te.getBlockPos());
            }
        }
        return InteractionResult.FAIL;
    }


    public static void shoot(Level worldIn, BlockPos pos) {
        Direction d = worldIn.getBlockState(pos).getValue(FACING).getOpposite();
        var te = worldIn.getBlockEntity(pos);
        if (te != null) {
            te.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP).ifPresent(inv ->
            {
                for (int i = inv.getSlots() - 1; i >= 0; --i) {
                    if (inv.getStackInSlot(i) != ItemStack.EMPTY) {
                        Block.popResource(worldIn, pos.relative(d), inv.getStackInSlot(i));
                        ((IItemHandlerModifiable) inv).setStackInSlot(i, ItemStack.EMPTY);
                    }
                }
            });
        }
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return TileEntityTypeRegistry.BAMBOO_TRAY_TYPE.get().create(pPos, pState);
    }
}
