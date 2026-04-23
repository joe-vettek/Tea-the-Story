package com.teamtea.teastory.block.craft;


import com.teamtea.teastory.blockentity.WoodenBarrelBlockEntity;
import com.teamtea.teastory.helper.VoxelShapeHelper;
import com.teamtea.teastory.tag.TeaTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.jetbrains.annotations.Nullable;
import com.teamtea.teastory.registry.ItemRegister;
import com.teamtea.teastory.registry.BlockEntityRegister;

import java.util.Optional;

public class WoodenBarrelBlock extends Block implements EntityBlock {
    private static final VoxelShape SHAPE;

    public WoodenBarrelBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }


    @Override
    public InteractionResult useItemOn(ItemStack pStack, BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!worldIn.isClientSide()) {
            if (FluidUtil.getFluidHandler(pStack.copy()).isPresent()) {
                return Optional.ofNullable(worldIn.getCapability(Capabilities.FluidHandler.BLOCK, pos, hit.getDirection())).map(fluidTank ->
                {
                    FluidUtil.interactWithFluidHandler(player, handIn, fluidTank);
                    return InteractionResult.SUCCESS;
                }).orElse(InteractionResult.FAIL);
            }
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        var te = level.getBlockEntity(pos);
        if (te instanceof WoodenBarrelBlockEntity) {
            int i = ((WoodenBarrelBlockEntity) te).getFluidAmount();
            float f = pos.getY() + 0.0625F + 0.875F * i / 2000;
            if (!level.isClientSide()) {
                if (entity.isOnFire()) {
                    if (((WoodenBarrelBlockEntity) te).getFluid().is(FluidTags.WATER) && i > 250 && entity.getBlockY() <= f) {
                        entity.extinguishFire();
                    }
                } else if (entity instanceof ItemEntity && ((WoodenBarrelBlockEntity) te).getFluid() == Fluids.WATER) {
                    ItemStack item = ((ItemEntity) entity).getItem();
                    if (item.is(TeaTags.Items.CROPS_RICE)) {
                        ((ItemEntity) entity).setItem(new ItemStack(ItemRegister.WASHED_RICE.get(), item.getCount()));
                    }
                }
            }
        }
        super.entityInside(state, level, pos, entity, effectApplier, isPrecise);
    }

    static {
        VoxelShape outer = VoxelShapeHelper.createVoxelShape(1, 0, 1, 14, 16, 14);
        VoxelShape inner = VoxelShapeHelper.createVoxelShape(2, 1, 2, 12, 15, 12);
        SHAPE = Shapes.join(outer, inner, BooleanOp.NOT_SAME);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return BlockEntityRegister.WOODEN_BARREL_TYPE.get().create(blockPos, blockState);
    }
}
