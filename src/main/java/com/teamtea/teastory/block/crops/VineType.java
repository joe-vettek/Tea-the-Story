package com.teamtea.teastory.block.crops;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import com.teamtea.teastory.BlockRegister;

public enum VineType
{
    GRAPE,
    CUCUMBER,
    BITTER_GOURD;

    public Block getFruit()
    {
        switch (this.ordinal())
        {
            case 0:
                return BlockRegister.GRAPE.get();
            case 1:
                return BlockRegister.CUCUMBER.get();
            case 2:
                return BlockRegister.BITTER_GOURD.get();
            default:
                return Blocks.AIR;
        }
    }

    public String getName()
    {
        return toString().toLowerCase();
    }
}
