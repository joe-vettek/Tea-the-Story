package cloud.lemonslice.teastory.client.color.block;



import cloud.lemonslice.teastory.block.crops.HybridizableFlowerBlock;
import cloud.lemonslice.teastory.block.crops.flower.FlowerColor;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class HybridizableFlowerBlockColor implements BlockColor
{
    @Override
    public int getColor(BlockState state, @Nullable BlockAndTintGetter reader, @Nullable BlockPos pos, int index)
    {
        if (index == 1)
        {
            return FlowerColor.getFlowerColor(state.getValue(HybridizableFlowerBlock.FLOWER_COLOR).getString()).getColorValue();
        }
        return -1;
    }
}
