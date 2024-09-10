package com.teamtea.teastory.block.craft;

import com.teamtea.teastory.blockentity.StoneMillTileEntity;
import com.teamtea.teastory.helper.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import com.teamtea.teastory.BlockEntityRegistry;
import com.teamtea.teastory.block.NormalHorizontalBlock;


import java.util.Optional;


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
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    @Override
    public void onRemove(BlockState blockState, Level worldIn, BlockPos pos, BlockState pNewState, boolean isMoving) {
        dropItems(worldIn, pos);
        super.onRemove(blockState, worldIn, pos, pNewState, isMoving);
    }


    private void dropItems(Level worldIn, BlockPos pos) {
        var te = worldIn.getBlockEntity(pos);
        if (te != null) {
            Optional.ofNullable(worldIn.getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.UP)).ifPresent(inv ->
            {
                for (int i = inv.getSlots() - 1; i >= 0; --i) {
                    if (inv.getStackInSlot(i) != ItemStack.EMPTY) {
                        Block.popResource(worldIn, pos, inv.getStackInSlot(i));
                        ((IItemHandlerModifiable) inv).setStackInSlot(i, ItemStack.EMPTY);
                    }
                }
            });
            Optional.ofNullable(worldIn.getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.DOWN)).ifPresent(inv ->
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
    public InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        if (!worldIn.isClientSide()) {
            var handIn = player.getUsedItemHand();
            var te = worldIn.getBlockEntity(pos);
            if (FluidUtil.getFluidHandler(player.getItemInHand(handIn).copy()).isPresent()) {
                return Optional.ofNullable(player.getItemInHand(handIn).getCapability(Capabilities.FluidHandler.ITEM))
                        .map(fluidTank ->
                        {
                            FluidUtil.interactWithFluidHandler(player, handIn, fluidTank);
                            return InteractionResult.SUCCESS;
                        }).orElse(InteractionResult.FAIL);
            }
            if (te instanceof StoneMillTileEntity) {
                if (!player.isShiftKeyDown()) {
                    if (!player.getItemInHand(handIn).isEmpty()) {
                        return Optional.ofNullable(worldIn.getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.UP)).map(container ->
                        {
                            player.setItemInHand(handIn, container.insertItem(0, player.getItemInHand(handIn), false));
                            return InteractionResult.SUCCESS;
                        }).orElse(InteractionResult.FAIL);
                    } else {
                        Optional.ofNullable(worldIn.getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.DOWN)).ifPresent(container ->
                        {
                            for (int i = 0; i <= 2; i++) {
                                ItemStack itemStack = container.extractItem(i, 64, false);
                                if (!itemStack.isEmpty()) {
                                    Block.popResource(worldIn, pos, itemStack);
                                }
                            }
                        });
                        Optional.ofNullable(worldIn.getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.UP)).ifPresent(container ->
                        {
                            if (((StoneMillTileEntity) te).isCompleted()) {
                                ItemStack itemStack = container.extractItem(0, container.getStackInSlot(0).getCount(), false);
                                Block.popResource(worldIn, pos, itemStack);
                            }
                        });
                        return InteractionResult.SUCCESS;
                    }
                } else {
                    player.openMenu((MenuProvider) te, te.getBlockPos());
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StoneMillTileEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, BlockEntityRegistry.STONE_MILL_TYPE.get(), StoneMillTileEntity::tick);
        // return !worldIn.isClientSide ?
        //         createTickerHelper(blockEntityType, TileEntityTypeRegistry.STONE_MILL_TYPE.get(), StoneMillTileEntity::tick) : null;

    }


}
