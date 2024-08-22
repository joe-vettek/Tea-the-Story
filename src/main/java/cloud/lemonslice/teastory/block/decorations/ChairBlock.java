package cloud.lemonslice.teastory.block.decorations;

import cloud.lemonslice.teastory.entity.SeatEntity;
import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xueluoanping.teastory.block.NormalHorizontalBlock;

import java.util.List;

public class ChairBlock extends NormalHorizontalBlock {
    private final static VoxelShape NORTH_SHAPE;
    private final static VoxelShape EAST_SHAPE;
    private final static VoxelShape WEST_SHAPE;
    private final static VoxelShape SOUTH_SHAPE;

    public ChairBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }



    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case NORTH:
                return NORTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return SOUTH_SHAPE;
        }
    }



    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        double x = 0, z = 0;
        switch (state.getValue(FACING)) {
            case NORTH:
                z = 0.25;
                break;
            case SOUTH:
                z = -0.25;
                break;
            case EAST:
                x = -0.25;
                break;
            default:
                x = 0.25;
        }
        return SeatEntity.createSeat(worldIn, pos, player, 0.25+0.05, x, z);
    }

    
    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0F;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }


    static {
        VoxelShape north_seat = VoxelShapeHelper.createVoxelShape(1D, 0D, 2D, 14D, 8D, 14D);
        VoxelShape north_back = VoxelShapeHelper.createVoxelShape(1D, 8D, 15D, 14D, 13D, 1D);
        VoxelShape south_seat = VoxelShapeHelper.createVoxelShape(1D, 0D, 0D, 14D, 8D, 14D);
        VoxelShape south_back = VoxelShapeHelper.createVoxelShape(1D, 8D, 0D, 14D, 13D, 1D);
        VoxelShape east_seat = VoxelShapeHelper.createVoxelShape(0D, 0D, 1D, 14D, 8D, 14D);
        VoxelShape east_back = VoxelShapeHelper.createVoxelShape(0D, 8D, 1D, 1D, 13D, 14D);
        VoxelShape west_seat = VoxelShapeHelper.createVoxelShape(2D, 0D, 1D, 14D, 8D, 14D);
        VoxelShape west_back = VoxelShapeHelper.createVoxelShape(15D, 8D, 1D, 1D, 13D, 14D);
        NORTH_SHAPE = Shapes.or(north_seat, north_back);
        EAST_SHAPE = Shapes.or(east_seat, east_back);
        WEST_SHAPE = Shapes.or(west_seat, west_back);
        SOUTH_SHAPE = Shapes.or(south_seat, south_back);
    }

}
