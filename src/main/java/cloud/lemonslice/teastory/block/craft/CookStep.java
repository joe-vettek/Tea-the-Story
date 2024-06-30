package cloud.lemonslice.teastory.block.craft;

import net.minecraft.util.IStringSerializable;

public enum CookStep implements IStringSerializable
{
    EMPTY,
    RAW,
    WATER,
    COOKED;

    @Override
    public String getString()
    {
        return toString().toLowerCase();
    }
}
