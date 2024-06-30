package cloud.lemonslice.teastory.block.craft;


import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum CookStep implements StringRepresentable {
    EMPTY("empty"),
    RAW("raw"),
    WATER("water"),
    COOKED("cooked");
    private final String name;

    private CookStep(String s) {
        this.name = s;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

}
