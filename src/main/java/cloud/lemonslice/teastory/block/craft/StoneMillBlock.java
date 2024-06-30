package cloud.lemonslice.teastory.block.craft;

import cloud.lemonslice.teastory.blockentity.StoneMillTileEntity;
import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.IItemHandlerModifiable;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.NormalHorizontalBlock;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.List;


public class StoneMillBlock extends NormalHorizontalBlock implements EntityBlock {
    private static final VoxelShape SHAPE = VoxelShapeHelper.createVoxelShape(0, 0, 0, 16, 9, 16);

    public StoneMillBlock(Properties properties) {
        super(properties);
    }

    // public StoneMillBlock()
    // {
    //
    //     // super(AbstractBlock.Properties.create(Material.ROCK).notSolid().hardnessAndResistance(1.5F).sound(SoundType.STONE), "stone_mill");
    // }


    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, BlockGetter world) {
        return STONE_MILL.create();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onReplaced(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.hasTileEntity() && !(newState.getBlock() == this)) {
            ((NormalContainerTileEntity) worldIn.getTileEntity(pos)).prepareForRemove();
            dropItems(worldIn, pos);
            worldIn.removeTileEntity(pos);
        }
    }

    private void dropItems(Level worldIn, BlockPos pos) {
        var te = worldIn.getBlockEntity(pos);
        if (te != null) {
            te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(inv ->
            {
                for (int i = inv.getSlots() - 1; i >= 0; --i) {
                    if (inv.getStackInSlot(i) != ItemStack.EMPTY) {
                        Block.spawnAsEntity(worldIn, pos, inv.getStackInSlot(i));
                        ((IItemHandlerModifiable) inv).setStackInSlot(i, ItemStack.EMPTY);
                    }
                }
            });
            te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).ifPresent(inv ->
            {
                for (int i = inv.getSlots() - 1; i >= 0; --i) {
                    if (inv.getStackInSlot(i) != ItemStack.EMPTY) {
                        Block.spawnAsEntity(worldIn, pos, inv.getStackInSlot(i));
                        ((IItemHandlerModifiable) inv).setStackInSlot(i, ItemStack.EMPTY);
                    }
                }
            });
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return Lists.newArrayList(new ItemStack(this));
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, Level worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(playerIn.getHeldItem(handIn), 1)).isPresent()) {
                return te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, hit.getFace()).map(fluidTank ->
                {
                    FluidUtil.interactWithFluidHandler(playerIn, handIn, fluidTank);
                    return ActionResultType.SUCCESS;
                }).orElse(ActionResultType.FAIL);
            }
            if (te instanceof StoneMillTileEntity) {
                if (!playerIn.isSneaking()) {
                    if (!playerIn.getHeldItem(handIn).isEmpty()) {
                        return te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).map(container ->
                        {
                            playerIn.setHeldItem(handIn, container.insertItem(0, playerIn.getHeldItem(handIn), false));
                            return ActionResultType.SUCCESS;
                        }).orElse(ActionResultType.FAIL);
                    } else {
                        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).ifPresent(container ->
                        {
                            for (int i = 0; i <= 2; i++) {
                                ItemStack itemStack = container.extractItem(i, 64, false);
                                if (!itemStack.isEmpty()) {
                                    Block.spawnAsEntity(worldIn, pos, itemStack);
                                }
                            }
                        });
                        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(container ->
                        {
                            if (((StoneMillTileEntity) te).isCompleted()) {
                                ItemStack itemStack = container.extractItem(0, container.getStackInSlot(0).getCount(), false);
                                Block.spawnAsEntity(worldIn, pos, itemStack);
                            }
                        });
                        return ActionResultType.SUCCESS;
                    }
                } else {
                    NetworkHooks.openGui((ServerPlayerEntity) playerIn, (INamedContainerProvider) te, te.getPos());
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StoneMillTileEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState state, BlockEntityType<T> blockEntityType) {
        return !worldIn.isClientSide ?
                createTickerHelper(blockEntityType, TileEntityTypeRegistry.STONE_MILL_TYPE.get(), StoneMillTileEntity::tick) : null;

    }


}
