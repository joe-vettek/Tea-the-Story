package cloud.lemonslice.teastory.block.craft;

import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import javax.annotation.Nullable;
import java.util.List;

public class FilterScreenBlock extends Block
{
    private static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;

    private static final VoxelShape[] SHAPE = new VoxelShape[]{
            VoxelShapeHelper.createVoxelShape(6.5, 0, 0, 3, 16, 16),
            VoxelShapeHelper.createVoxelShape(0, 6.5, 0, 16, 3, 16),
            VoxelShapeHelper.createVoxelShape(0, 0, 6.5, 16, 16, 3)};

    public FilterScreenBlock(BlockBehaviour.Properties pProperties)
    {
        super(pProperties);
        this.registerDefaultState(defaultBlockState().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE[pState.getValue(AXIS).ordinal()];
    }



    @Override
    @SuppressWarnings("deprecation")
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn)
    {
        if (!worldIn.isClientSide() && entityIn instanceof ItemEntity)
        {
            List<Item> list = getPassableItem(worldIn, pos);
            if (list.contains(((ItemEntity) entityIn).getItem().getItem()))
            {
                return;
            }
            var motion = entityIn.getDeltaMovement();
            if (motion.x != 0 || motion.z != 0)
            {
                entityIn.setDeltaMovement(0, 0, 0);
            }
        }
    }

    public static List<Item> getPassableItem(Level worldIn, BlockPos pos)
    {
        BlockPos blockPos = pos.above();
        while (worldIn.getBlockState(blockPos).getBlock() instanceof FilterScreenBlock)
        {
            blockPos = blockPos.above();
        }
        if (worldIn.getBlockEntity(blockPos) != null)
        {
            return worldIn.getBlockEntity(blockPos).getCapability(ForgeCapabilities.ITEM_HANDLER).map(h ->
            {
                List<Item> list = Lists.newArrayList();
                for (int i = 0; i < h.getSlots(); i++)
                {
                    list.add(h.getStackInSlot(i).getItem());
                }
                return list;
            }).orElse(Lists.newArrayList());
        }
        return Lists.newArrayList();
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if (player.getItemInHand(handIn).isEmpty())
        {
            if (state.getValue(AXIS).equals(Direction.Axis.X))
            {
                worldIn.setBlockAndUpdate(pos, state.setValue(AXIS, Direction.Axis.Z));
            }
            else
            {
                worldIn.setBlockAndUpdate(pos, state.setValue(AXIS, Direction.Axis.X));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public @org.jetbrains.annotations.Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getPlayer() != null)
        {
            return defaultBlockState().setValue(AXIS, context.getPlayer().getDirection().getAxis());
        }
        return defaultBlockState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(AXIS));
    }


}
