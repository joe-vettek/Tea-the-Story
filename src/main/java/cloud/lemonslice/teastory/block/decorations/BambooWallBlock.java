package cloud.lemonslice.teastory.block.decorations;


import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BambooWallBlock extends BambooLatticeBlock
{
    protected static final VoxelShape NORTH_SHAPE = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 5.0D);
    protected static final VoxelShape SOUTH_SHAPE = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 11.0D, 16.0D, 16.0D, 5.0D);
    protected static final VoxelShape WEST_SHAPE = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 5.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_SHAPE = VoxelShapeHelper.createVoxelShape(11.0D, 0.0D, 0.0D, 5.0D, 16.0D, 16.0D);

    public BambooWallBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.hasProperty(WATERLOGGED) && state.getValue(WATERLOGGED) ? 0 : 60;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.hasProperty(WATERLOGGED) && state.getValue(WATERLOGGED) ? 0 : 60;
    }



    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context)
    {
        VoxelShape shape = Shapes.empty();
        if (state.getValue(NORTH))
        {
            shape = Shapes.or(shape, NORTH_SHAPE);
        }
        if (state.getValue(SOUTH))
        {
            shape = Shapes.or(shape, SOUTH_SHAPE);
        }
        if (state.getValue(WEST))
        {
            shape = Shapes.or(shape, WEST_SHAPE);
        }
        if (state.getValue(EAST))
        {
            shape = Shapes.or(shape, EAST_SHAPE);
        }
        return shape;
    }
}
