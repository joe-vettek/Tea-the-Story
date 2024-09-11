package com.teamtea.teastory.recipe.drink;


import com.teamtea.teastory.registry.EffectRegister;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import com.teamtea.teastory.registry.FluidRegister;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public final class DrinkEffectManager {
    private final static Map<Fluid, BiConsumer<LivingEntity, Integer>> DRINK_EFFECTS = new HashMap<>();

    public static void init() {
        registerDrinkEffects();
    }

    private static void registerDrinkEffects() {
        registerEffects(FluidRegister.SUGARY_WATER_STILL.get(), createSimpleDrinkEffect(MobEffects.MOVEMENT_SPEED, 2, 0));

        registerEffects(FluidRegister.WEAK_GREEN_TEA_STILL.get(), createSimpleDrinkEffect(EffectRegister.getMobEffect(EffectRegister.AGILITY), 2, 0));
        registerEffects(FluidRegister.GREEN_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.AGILITY), 2, 1), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 2, 0)));
        registerEffects(FluidRegister.STRONG_GREEN_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.AGILITY), 2, 2), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 4, 0)));

        registerEffects(FluidRegister.WEAK_BLACK_TEA_STILL.get(), createSimpleDrinkEffect(MobEffects.HEALTH_BOOST, 4, 0));
        registerEffects(FluidRegister.BLACK_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.HEALTH_BOOST, 4, 1), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 4, 0)));
        registerEffects(FluidRegister.STRONG_BLACK_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.HEALTH_BOOST, 4, 2), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 8, 0)));

        registerEffects(FluidRegister.WEAK_WHITE_TEA_STILL.get(), createSimpleDrinkEffect(MobEffects.DIG_SPEED, 2, 0));
        registerEffects(FluidRegister.WHITE_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.DIG_SPEED, 2, 1), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 2, 0)));
        registerEffects(FluidRegister.STRONG_WHITE_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.DIG_SPEED, 2, 2), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 4, 0)));
    }

    public static void registerEffects(Fluid fluid, BiConsumer<LivingEntity, Integer> doEffects) {
        DRINK_EFFECTS.put(fluid, doEffects);
    }

    @Nullable
    public static BiConsumer<LivingEntity, Integer> getEffects(Fluid key) {
        return DRINK_EFFECTS.get(key);
    }

    public static BiConsumer<LivingEntity, Integer> createSimpleDrinkEffect(Holder<MobEffect> potionIn, int durationIn, int level) {
        return (livingEntity, amount) -> livingEntity.addEffect(new MobEffectInstance(potionIn, durationIn * amount, level));
    }

    public static BiConsumer<LivingEntity, Integer> createDrinkEffects(DrinkEffectAttribute... attributes) {
        return (livingEntity, amount) ->
        {
            for (DrinkEffectAttribute attribute : attributes) {
                livingEntity.addEffect(new MobEffectInstance(attribute.getPotion(), amount * attribute.getDuration(), attribute.getLevel()));
            }
        };
    }
}
