package com.teamtea.teastory.block.craft;

import com.teamtea.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class FilterScreenBlock extends Block {
    private static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;

    private static final VoxelShape[] SHAPE = new VoxelShape[]{
            VoxelShapeHelper.createVoxelShape(6.5, 0, 0, 3, 16, 16),
            VoxelShapeHelper.createVoxelShape(0, 6.5, 0, 16, 3, 16),
            VoxelShapeHelper.createVoxelShape(0, 0, 6.5, 16, 16, 3)};

    public FilterScreenBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(defaultBlockState().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE[pState.getValue(AXIS).ordinal()];
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pContext instanceof EntityCollisionContext entitycollisioncontext) {
            Entity entity = entitycollisioncontext.getEntity();
            if (entity instanceof ItemEntity) {
                return Shapes.empty();
            }
        }
        return super.getCollisionShape(pState, pLevel, pPos, pContext);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isClientSide() && entityIn instanceof ItemEntity) {
            List<Item> list = getPassableItem(worldIn, pos);
            if (list.contains(((ItemEntity) entityIn).getItem().getItem())) {
                return;
            }
            var motion = entityIn.getDeltaMovement();
            if (motion.x != 0 || motion.z != 0) {
                entityIn.setDeltaMovement(0, 0, 0);
            }
        }
    }


    public static List<Item> getPassableItem(Level worldIn, BlockPos pos) {
        BlockPos blockPos = pos.above();
        while (worldIn.getBlockState(blockPos).getBlock() instanceof FilterScreenBlock) {
            blockPos = blockPos.above();
        }
        if (worldIn.getBlockEntity(blockPos) != null) {
            return Optional.ofNullable(worldIn.getCapability(Capabilities.ItemHandler.BLOCK, blockPos, null)).map(h ->
            {
                List<Item> list = Lists.newArrayList();
                for (int i = 0; i < h.getSlots(); i++) {
                    list.add(h.getStackInSlot(i).getItem());
                }
                return new ArrayList<>(new HashSet<>(list));
            }).orElse(Lists.newArrayList());
        }
        return Lists.newArrayList();
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player pPlayer, BlockHitResult pHitResult) {
        if (state.getValue(AXIS).equals(Direction.Axis.X)) {
            level.setBlockAndUpdate(pos, state.setValue(AXIS, Direction.Axis.Z));
        } else {
            level.setBlockAndUpdate(pos, state.setValue(AXIS, Direction.Axis.X));
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    public @org.jetbrains.annotations.Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getPlayer() != null) {
            return defaultBlockState().setValue(AXIS, context.getPlayer().getDirection().getAxis());
        }
        return defaultBlockState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(AXIS));
    }


}
