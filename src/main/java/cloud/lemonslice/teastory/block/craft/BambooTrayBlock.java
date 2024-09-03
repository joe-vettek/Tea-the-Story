package cloud.lemonslice.teastory.block.craft;

import cloud.lemonslice.teastory.blockentity.BambooTrayTileEntity;
import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import xueluoanping.teastory.recipe.bamboo_tray.BambooTraySingleInRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.Nullable;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.NormalHorizontalBlock;

import java.util.Optional;

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
    public boolean canSurvive(BlockState p_60525_, LevelReader pLevel, BlockPos pos) {
        // var low=pLevel.getBlockState(pos.below());
        // return low.getBlock() instanceof StoveBlock
        //         || low.isFaceSturdy(pLevel, pos, Direction.UP);
        return pLevel.getBlockState(pos.below()).isFaceSturdy(pLevel, pos, Direction.UP);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }


    private void dropItems(Level worldIn, BlockPos pos) {
        Optional.ofNullable(worldIn.getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.UP)).ifPresent(inv ->
        {
            for (int i = inv.getSlots() - 1; i >= 0; --i) {
                if (inv.getStackInSlot(i) != ItemStack.EMPTY) {
                    Block.popResource(worldIn, pos, inv.getStackInSlot(i));
                    ((IItemHandlerModifiable) inv).setStackInSlot(i, ItemStack.EMPTY);
                }
            }
        });
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
        if (pNewState.getBlock() != this) {
            dropItems(worldIn, pos);
        }
        super.onRemove(blockState, worldIn, pos, pNewState, isMoving);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        InteractionHand handIn = player.getUsedItemHand();
        var te = level.getBlockEntity(pos);
        if (te instanceof BambooTrayTileEntity) {
            if (level.isClientSide()) {
                ((BambooTrayTileEntity) te).refreshSeed();
                return InteractionResult.SUCCESS;
            }
            if (!player.isShiftKeyDown()) {
                if (((BambooTrayTileEntity) te).isDoubleClick()) {
                    dropItems(level, pos);
                    return InteractionResult.SUCCESS;
                }
                if (!((BambooTrayTileEntity) te).isWorking()) {
                    dropItems(level, pos);
                    te.setChanged();
                }
                if (!player.getItemInHand(handIn).isEmpty()) {
                    Optional.ofNullable(level.getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.UP)).ifPresent(inv ->
                    {
                        BambooTraySingleInRecipe recipe = null;
                        for (var r : level.getRecipeManager().getRecipes()) {
                            if (r.value().getType().equals(((BambooTrayTileEntity) te).getRecipeType()) && ((BambooTraySingleInRecipe) r.value()).getIngredient().test(player.getItemInHand(handIn))) {
                                recipe = (BambooTraySingleInRecipe) r.value();
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
                player.openMenu((MenuProvider) te, te.getBlockPos());
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
        return NormalHorizontalBlock.createTickerHelper(blockEntityType, TileEntityTypeRegistry.BAMBOO_TRAY_TYPE.get(), BambooTrayTileEntity::tick);
    }

}
