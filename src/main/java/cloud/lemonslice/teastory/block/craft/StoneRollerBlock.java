package cloud.lemonslice.teastory.block.craft;

import cloud.lemonslice.teastory.blockentity.BambooTrayTileEntity;
import cloud.lemonslice.teastory.blockentity.StoneRollerTileEntity;
import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.NormalHorizontalBlock;
import xueluoanping.teastory.block.entity.NormalContainerTileEntity;

import java.util.List;

public class StoneRollerBlock extends Block implements EntityBlock {
    private static final VoxelShape SHAPE = VoxelShapeHelper.createVoxelShape(0, 0, 0, 16, 12, 16);

    public StoneRollerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }


    @Override
    public void onRemove(BlockState pState, Level worldIn, BlockPos pos, BlockState pNewState, boolean isMoving) {

        if (!(pNewState.is(this))) {
            dropItems(worldIn, pos);
        }
        super.onRemove(pState, worldIn, pos, pNewState, isMoving);
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
            te.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN).ifPresent(inv ->
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
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult hit) {
        if (!worldIn.isClientSide()) {
            var te = worldIn.getBlockEntity(pos);
            if (te instanceof StoneRollerTileEntity) {
                if (!playerIn.isShiftKeyDown()) {
                    if (!playerIn.getItemInHand(handIn).isEmpty()) {
                        return te.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP).map(container ->
                        {
                            playerIn.setItemInHand(handIn, container.insertItem(0, playerIn.getItemInHand(handIn), false));
                            return InteractionResult.SUCCESS;
                        }).orElse(InteractionResult.FAIL);
                    } else {
                        te.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN).ifPresent(container ->
                        {
                            for (int i = 0; i <= 2; i++) {
                                ItemStack itemStack = container.extractItem(i, 64, false);
                                if (!itemStack.isEmpty()) {
                                    Block.popResource(worldIn, pos, itemStack);
                                }
                            }
                        });
                        te.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP).ifPresent(container ->
                        {
                            if (((StoneRollerTileEntity) te).isCompleted()) {
                                ItemStack itemStack = container.extractItem(0, container.getStackInSlot(0).getCount(), false);
                                Block.popResource(worldIn, pos, itemStack);
                            }
                        });
                        return InteractionResult.SUCCESS;
                    }
                } else {
                    NetworkHooks.openScreen((ServerPlayer) playerIn, (MenuProvider) te, te.getBlockPos());
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new StoneRollerTileEntity(p_153215_, p_153216_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState state, BlockEntityType<T> blockEntityType) {
        return NormalHorizontalBlock.createTickerHelper(blockEntityType, TileEntityTypeRegistry.STONE_ROLLER_TYPE.get(), StoneRollerTileEntity::tick);
        // return !worldIn.isClientSide ?
        //         NormalHorizontalBlock.createTickerHelper(blockEntityType, TileEntityTypeRegistry.STONE_ROLLER_TYPE.get(), StoneRollerTileEntity::tick) : null;
    }

}
