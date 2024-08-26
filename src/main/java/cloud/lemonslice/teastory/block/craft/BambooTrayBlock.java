package cloud.lemonslice.teastory.block.craft;

import cloud.lemonslice.teastory.blockentity.BambooTrayTileEntity;
import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import cloud.lemonslice.teastory.recipe.bamboo_tray.BambooTraySingleInRecipe;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
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
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.NormalHorizontalBlock;
import xueluoanping.teastory.block.entity.NormalContainerTileEntity;

import java.util.List;

public class BambooTrayBlock extends Block implements EntityBlock {
    protected static final VoxelShape SHAPE;

    public BambooTrayBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }


    @Override
    public boolean canSurvive(BlockState p_60525_, LevelReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.below()).isSolidRender(worldIn, pos);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }


    private void dropItems(Level worldIn, BlockPos pos) {
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
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 60;
    }


    @Override
    public void onRemove(BlockState blockState, Level worldIn, BlockPos pos, BlockState pNewState, boolean isMoving) {
        if ( pNewState.getBlock() != this)
        {
            dropItems(worldIn, pos);
        }
        super.onRemove(blockState, worldIn, pos, pNewState, isMoving);
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
                        for (Recipe<?> r : worldIn.getRecipeManager().getRecipes()) {
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
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }


    static {
        VoxelShape side_north = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 2.0D);
        VoxelShape side_south = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 14.0D, 16.0D, 3.0D, 2.0D);
        VoxelShape side_west = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 2.0D, 3.0D, 16.0D);
        VoxelShape side_east = VoxelShapeHelper.createVoxelShape(14.0D, 0.0D, 0.0D, 2.0D, 3.0D, 16.0D);
        VoxelShape bottom = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
        SHAPE = Shapes.or(side_north, side_south, side_east, side_west, bottom);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new BambooTrayTileEntity(p_153215_, p_153216_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState state, BlockEntityType<T> blockEntityType) {
        // return !worldIn.isClientSide ?
           return      NormalHorizontalBlock.createTickerHelper(blockEntityType, TileEntityTypeRegistry.BAMBOO_TRAY_TYPE.get(), BambooTrayTileEntity::tick) ;
    }

}
