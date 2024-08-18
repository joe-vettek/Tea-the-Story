package cloud.lemonslice.teastory.block.decorations;

import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xueluoanping.teastory.block.NormalHorizontalBlock;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ScarecrowBlock extends NormalHorizontalBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final VoxelShape LOWER_SHAPE;
    public static final VoxelShape UPPER_SHAPE;

    public ScarecrowBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.UPPER));
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? 60 : 0;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? 60 : 0;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(HALF));
    }


    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? LOWER_SHAPE : UPPER_SHAPE;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        DoubleBlockHalf doubleblockhalf = stateIn.getValue(HALF);
        if (facing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
            return facingState.is(this) && facingState.getValue(HALF) != doubleblockhalf ? stateIn.setValue(FACING, facingState.getValue(FACING)) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleblockhalf == DoubleBlockHalf.LOWER
                    && facing == Direction.DOWN
                    && !stateIn.canSurvive(worldIn, currentPos) ?
                    Blocks.AIR.defaultBlockState() :
                    super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }


    protected static void removeBottomHalf(Level world, BlockPos pos, BlockState state, Player player) {
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        if (doubleblockhalf == DoubleBlockHalf.UPPER) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
                world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            }
        }
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        if (!worldIn.isClientSide()) {
            if (player.isCreative()) {
                removeBottomHalf(worldIn, pos, state, player);
            }
        }
        super.playerWillDestroy(worldIn, pos, state, player);
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        if (blockpos.getY() < 255 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context)) {
            return super.getStateForPlacement(context).setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }


    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), Block.UPDATE_ALL);
    }


    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = worldIn.getBlockState(blockpos);
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(worldIn, blockpos, Direction.UP) : blockstate.is(this);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? Lists.newArrayList(new ItemStack(this)) : Collections.emptyList();
    }


    static {
        LOWER_SHAPE = VoxelShapeHelper.createVoxelShape(5, 0, 5, 6, 16, 6);
        UPPER_SHAPE = VoxelShapeHelper.createVoxelShape(2, 0, 2, 12, 12, 12);
    }
}
