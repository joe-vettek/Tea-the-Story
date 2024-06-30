package cloud.lemonslice.teastory.block.craft;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;


public interface IStoveBlock
{
    static boolean isBurning(Level world, BlockPos pos)
    {
        return isBurning(world.getBlockState(pos));
    }

    static boolean isBurning(BlockState state)
    {
        if (state.getBlock() instanceof IStoveBlock)
        {
            return state.getValue(BlockStateProperties.LIT);
        }
        return false;
    }

    int getFuelPower();
}
