package com.teamtea.teastory.client.color.item;


import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

public class BucketItemColors implements ItemColor
{
    @Override
    public int getColor(ItemStack itemStack, int tintIndex)
    {
        if (itemStack.getItem() instanceof BucketItem bucketItem && tintIndex == 1)
        {
            return IClientFluidTypeExtensions.of(bucketItem.content).getTintColor();
        }
        return -1;
    }
}
