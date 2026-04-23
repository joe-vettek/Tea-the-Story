package com.teamtea.teastory.client.color.item;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GrassColor;
import org.jspecify.annotations.Nullable;


public class GrassBlockItemColors implements ItemTintSource
{
    @Override
    public int getColor(ItemStack itemStack, int tintIndex)
    {
        return GrassColor.get(0.5D, 1.0D);
    }

    @Override
    public int calculate(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
        return GrassColor.get(0.5D, 1.0D);
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return null;
    }
}
