package cloud.lemonslice.teastory.block.decorations;


import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StoneChairBlock extends ChairBlock
{
    private final static VoxelShape NORTH_SHAPE;
    private final static VoxelShape EAST_SHAPE;
    private final static VoxelShape WEST_SHAPE;
    private final static VoxelShape SOUTH_SHAPE;

    public StoneChairBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
    }



    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context)
    {
        switch (state.getValue(FACING))
        {
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

    static
    {
        NORTH_SHAPE = VoxelShapeHelper.createVoxelShape(0, 0, 5, 16, 8, 9);
        SOUTH_SHAPE = VoxelShapeHelper.createVoxelShape(0, 0, 2, 16, 8, 9);
        WEST_SHAPE = VoxelShapeHelper.createVoxelShape(5, 0, 0, 9, 8, 16);
        EAST_SHAPE = VoxelShapeHelper.createVoxelShape(2, 0, 0, 9, 8, 16);
    }
}
