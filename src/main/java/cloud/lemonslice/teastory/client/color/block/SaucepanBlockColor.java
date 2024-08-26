package cloud.lemonslice.teastory.client.color.block;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

public class SaucepanBlockColor implements BlockColor {
    @Override
    public int getColor(BlockState state, BlockAndTintGetter reader, BlockPos pos, int index) {
        if (index == 1) {
            return IClientFluidTypeExtensions.of(Fluids.WATER).getTintColor();
        }
        return -1;
        // return IClientFluidTypeExtensions.of(Fluids.WATER).getTintColor();
    }
}
