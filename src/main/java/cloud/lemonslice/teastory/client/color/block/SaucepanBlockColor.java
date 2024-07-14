package cloud.lemonslice.teastory.client.color.block;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public class SaucepanBlockColor implements BlockColor
{
    @Override
    public int getColor(BlockState state, BlockAndTintGetter reader, BlockPos pos, int index) {
        // if (index == 1)
        // {
        //     return Fluids.WATER.getAttributes().getColor();
        // }
        return -1;
    }
}
