package cloud.lemonslice.teastory.block.craft;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import xueluoanping.teastory.block.NormalHorizontalBlock;

public class StoneCampfireBlock extends NormalHorizontalBlock implements SimpleWaterloggedBlock
{
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // TODO 方块掉落，配方
    public StoneCampfireBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(WATERLOGGED));
    }

}
