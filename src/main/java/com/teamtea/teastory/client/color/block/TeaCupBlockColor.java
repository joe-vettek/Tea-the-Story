package com.teamtea.teastory.client.color.block;

import com.teamtea.teastory.blockentity.TeaCupBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

public record TeaCupBlockColor(int tintindex) implements BlockTintSource {


    @Override
    public int color(BlockState state) {
        return -1;
    }

    @Override
    public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
        if (pos != null) {
            if (Minecraft.getInstance().level != null) {

                var world = Minecraft.getInstance().level;
                var te = world.getBlockEntity(pos);
                if (te instanceof TeaCupBlockEntity) {
                    int drink = 0;
                    int t = tintindex;
                    int color;
                    do {
                        Fluid fluid = ((TeaCupBlockEntity) te).getFluid(drink);
                        color = Minecraft.getInstance().getModelManager().getFluidStateModelSet().get(fluid.defaultFluidState()).fluidTintSource().color(fluid.defaultFluidState());
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
