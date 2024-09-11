package com.teamtea.teastory.client.color.block;

import com.teamtea.teastory.blockentity.TeaCupBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

public class TeaCupBlockColor implements BlockColor {
    @Override
    public int getColor(BlockState state, BlockAndTintGetter reader, BlockPos pos, int tintindex) {
        if (pos != null) {
            if (Minecraft.getInstance().level != null) {

                var world = Minecraft.getInstance().level;
                var te = world.getBlockEntity(pos);
                if (te instanceof TeaCupBlockEntity) {
                    int drink = 0;
                    int t = tintindex;
                    int color;
                    do {
                        color = IClientFluidTypeExtensions.of(((TeaCupBlockEntity) te).getFluid(drink)).getTintColor();
                        drink++;
                        if (color != 0) {
                            t--;
                        }
                    }
                    while (t != 0 && drink < 3);
                    return color;
                }
            }
        }
        return -1;
    }
}
