package com.teamtea.teastory.helper;



import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.block.Block;

public final class VoxelShapeHelper
{
    public static VoxelShape createVoxelShape(double beginX, double beginY, double beginZ, double length, double height, double width)
    {
        return Block.box(beginX, beginY, beginZ, beginX + length, beginY + height, beginZ + width);
    }

}
