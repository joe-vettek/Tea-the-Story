package com.teamtea.teastory.recipe.drink;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

public class DrinkEffectAttribute {
    private final Holder<MobEffect> potion;
    private final int duration;
    private final int level;

    public DrinkEffectAttribute(Holder<MobEffect> potionIn, int durationIn, int level) {
        this.potion = potionIn;
        this.duration = durationIn;
        this.level = level;
    }

    public static final Codec<DrinkEffectAttribute> DIRECT_CODEC = RecordCodecBuilder.create(builder -> builder.group(
                    BuiltInRegistries.MOB_EFFECT.holderByNameCodec().fieldOf("potion").forGetter(DrinkEffectAttribute::getPotion),
                    Codec.INT.fieldOf("duration").forGetter(DrinkEffectAttribute::getDuration),
                    Codec.INT.fieldOf("level").forGetter(DrinkEffectAttribute::getDuration))
            .apply(builder, DrinkEffectAttribute::new));

    public Holder<MobEffect> getPotion() {
        return potion;
    }

    public int getDuration() {
        return duration;
    }

    public int getLevel() {
        return level;
    }
}
