package cloud.lemonslice.teastory.recipe.drink;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.fluids.FluidStack;
import xueluoanping.teastory.recipe.DrinkEffect;

public class DrinkEffectAttribute {
    private final MobEffect potion;
    private final int duration;
    private final int level;

    public DrinkEffectAttribute(MobEffect potionIn, int durationIn, int level) {
        this.potion = potionIn;
        this.duration = durationIn;
        this.level = level;
    }

    public static final Codec<DrinkEffectAttribute> DIRECT_CODEC = RecordCodecBuilder.create(builder -> builder.group(
                    BuiltInRegistries.MOB_EFFECT.byNameCodec().fieldOf("potion").forGetter(DrinkEffectAttribute::getPotion),
                    Codec.INT.fieldOf("duration").forGetter(DrinkEffectAttribute::getDuration),
                    Codec.INT.fieldOf("level").forGetter(DrinkEffectAttribute::getDuration))
            .apply(builder, DrinkEffectAttribute::new));


    public MobEffect getPotion() {
        return potion;
    }

    public int getDuration() {
        return duration;
    }

    public int getLevel() {
        return level;
    }
}
