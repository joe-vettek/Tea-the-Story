package cloud.lemonslice.teastory.block.craft;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;


public enum BambooTrayMode
{
    IN_RAIN,
    OUTDOORS,
    INDOORS,
    BAKE,
    PROCESS;

    public static BambooTrayMode getMode(Level world, BlockPos pos)
    {
        if (isInRain(world, pos))
        {
            return IN_RAIN;
        }
        if (hasHeat(world, pos))
        {
            return BAKE;
        }
        else if (isOnFrame(world, pos))
        {
            return PROCESS;
        }
        else if (isInSun(world, pos))
        {
            return OUTDOORS;
        }
        else
        {
            return INDOORS;
        }
    }

    public static boolean isInRain(Level world, BlockPos pos)
    {
        return world.isRainingAt(pos.above());
    }

    public static boolean hasHeat(Level world, BlockPos pos)
    {
        return IStoveBlock.isBurning(world, pos.below());
    }

    public static boolean isInSun(Level world, BlockPos pos)
    {
        return world.canSeeSky(pos);
    }

    public static boolean isLevelRaining(Level world)
    {
        return world.isRaining();
    }

    public static boolean isOnFrame(Level world, BlockPos pos)
    {
        // TODO：
        // return world.getBlockState(pos.below()).getBlock() instanceof WoodenFrameBlock;
        return false;
    }

    @Override
    public String toString()
    {
        return super.toString().toLowerCase();
    }

    public Component getTranslationKey()
    {
        return Component.translatable("info.teastory.bamboo_tray.mode." + this);
    }
}
