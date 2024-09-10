package com.teamtea.teastory.block.drink;


import com.teamtea.teastory.blockentity.TeaCupTileEntity;
import com.teamtea.teastory.helper.VoxelShapeHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;
import com.teamtea.teastory.ItemRegister;
import com.teamtea.teastory.ModCapabilities;
import com.teamtea.teastory.TileEntityTypeRegistry;


public class WoodenTrayBlock extends Block implements EntityBlock {
    public static final IntegerProperty DRINK = IntegerProperty.create("drink", 0, 3);
    public static final IntegerProperty CUP = IntegerProperty.create("cup", 0, 3);

    private static final VoxelShape TRAY = VoxelShapeHelper.createVoxelShape(0, 0, 0, 16, 2, 16);
    private static final VoxelShape WITH_CUP = VoxelShapeHelper.createVoxelShape(0, 0, 0, 16, 6, 16);

    public WoodenTrayBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(CUP) > 0) {
            return WITH_CUP;
        } else {
            return TRAY;
        }
    }


    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(CUP, DRINK));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        var te = pLevel.getBlockEntity(pPos);
        if (te instanceof TeaCupTileEntity) {
            {
                int index = pState.getValue(CUP);
                if (!setCup((TeaCupTileEntity) te, index, pStack, pLevel, pPos, pState)) {
                    if (pStack.getItem() == TileEntityTypeRegistry.PORCELAIN_TEAPOT.get()) {
                        FluidUtil.getFluidHandler(pStack.copy()).ifPresent(item ->
                        {
                            for (int i = 0; i < 3; i++) {
                                FluidTank tank = ((TeaCupTileEntity) te).getFluidTank(i);
                                if (tank.isEmpty()) {
                                    if (FluidUtil.interactWithFluidHandler(pPlayer, pHand, tank)) {
                                        if (pState.getValue(DRINK) + 1 <= 3) {
                                            pLevel.setBlockAndUpdate(pPos, pState.setValue(DRINK, pState.getValue(DRINK) + 1));
                                        }
                                        pLevel.playSound(null, pPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 0.5F, 0.9F);
                                    }
                                    break;
                                }
                            }
                        });
                    }
                }
            }
        }
        return super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        var te = pLevel.getBlockEntity(pPos);
        if (te instanceof TeaCupTileEntity) {
            int index = pState.getValue(CUP) - 1;
            if (index > -1) {
                ItemStack itemStack = getCup((TeaCupTileEntity) te, index);
                ItemHandlerHelper.giveItemToPlayer(pPlayer, itemStack);
                if (itemStack.getItem() == ItemRegister.PORCELAIN_CUP.get()) {
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(CUP, pState.getValue(CUP) - 1));
                } else {
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(CUP, pState.getValue(CUP) - 1).setValue(DRINK, pState.getValue(DRINK) - 1));
                }
            }
        }

        return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHitResult);
    }


    public boolean setCup(TeaCupTileEntity te, int index, ItemStack itemStack, Level world, BlockPos pos, BlockState state) {
        if (index >= 3) {
            return false;
        }
        if (itemStack.getItem() == ItemRegister.PORCELAIN_CUP_DRINK.get() || itemStack.getItem() == ItemRegister.PORCELAIN_CUP.get()) {
            FluidStack stack = FluidUtil.getFluidContained(itemStack).orElse(FluidStack.EMPTY);
            if (!stack.isEmpty()) {
                if (state.getValue(DRINK) + 1 <= 3) {
                    world.setBlockAndUpdate(pos, state.setValue(CUP, state.getValue(CUP) + 1).setValue(DRINK, state.getValue(DRINK) + 1));
                    world.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 0.5F, 0.9F);
                }
            } else {
                world.setBlockAndUpdate(pos, state.setValue(CUP, state.getValue(CUP) + 1));
                world.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 0.5F, 0.9F);
            }
            te.setFluidTank(index, stack);
            itemStack.shrink(1);
            return true;
        } else return false;
    }

    public ItemStack getCup(TeaCupTileEntity te, int index) {
        if (index > 3) {
            return ItemStack.EMPTY;
        }
        FluidStack fluidStack = te.getFluidTank(index).getFluidInTank(0);
        if (fluidStack.isEmpty()) {
            return new ItemStack(ItemRegister.PORCELAIN_CUP.get());
        } else {
            ItemStack itemStack = new ItemStack(ItemRegister.PORCELAIN_CUP_DRINK.get());
            itemStack.set(ModCapabilities.SIMPLE_FLUID, SimpleFluidContent.copyOf(fluidStack));
            return itemStack;
        }
    }


    // onReplaced
    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level pLevel, BlockPos pos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pNewState.is(this)) {
            var tileEntity = pLevel.getBlockEntity(pos);
            if (tileEntity instanceof TeaCupTileEntity) {
                for (int i = 0; i < 3; i++) {
                    if (!pLevel.isClientSide() && state.getValue(CUP) > i) {
                        Block.popResource(pLevel, pos, getCup((TeaCupTileEntity) tileEntity, i));
                    }
                }
            }
        }
        super.onRemove(state, pLevel, pos, pNewState, pMovedByPiston);
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return TileEntityTypeRegistry.WOODEN_TRAY_TYPE.get().create(blockPos, blockState);
    }
}
