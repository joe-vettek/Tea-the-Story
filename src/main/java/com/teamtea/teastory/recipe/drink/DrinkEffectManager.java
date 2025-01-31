package com.teamtea.teastory.recipe.drink;


import com.teamtea.teastory.registry.EffectRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import com.teamtea.teastory.registry.FluidRegister;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public final class DrinkEffectManager {
    private final static Map<Fluid, BiConsumer<LivingEntity, Integer>> DRINK_EFFECTS = new HashMap<>();

    public static void init() {
        // registerDrinkEffects();
    }

    // private static void registerDrinkEffects() {
    //     registerEffects(FluidRegister.SUGARY_WATER_STILL.get(), createSimpleDrinkEffect(MobEffects.MOVEMENT_SPEED, 2, 0));
    //
    //     registerEffects(FluidRegister.WEAK_GREEN_TEA_STILL.get(), createSimpleDrinkEffect(EffectRegister.getMobEffect(EffectRegister.AGILITY), 2, 0));
    //     registerEffects(FluidRegister.GREEN_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.AGILITY), 2, 1), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 2, 0)));
    //     registerEffects(FluidRegister.STRONG_GREEN_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.AGILITY), 2, 2), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 4, 0)));
    //
    //     registerEffects(FluidRegister.WEAK_BLACK_TEA_STILL.get(), createSimpleDrinkEffect(MobEffects.HEALTH_BOOST, 4, 0));
    //     registerEffects(FluidRegister.BLACK_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.HEALTH_BOOST, 4, 1), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 4, 0)));
    //     registerEffects(FluidRegister.STRONG_BLACK_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.HEALTH_BOOST, 4, 2), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 8, 0)));
    //
    //     registerEffects(FluidRegister.WEAK_WHITE_TEA_STILL.get(), createSimpleDrinkEffect(MobEffects.DIG_SPEED, 2, 0));
    //     registerEffects(FluidRegister.WHITE_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.DIG_SPEED, 2, 1), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 2, 0)));
    //     registerEffects(FluidRegister.STRONG_WHITE_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.DIG_SPEED, 2, 2), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 4, 0)));
    // }

    @Deprecated
    public static void registerEffects(Fluid fluid, BiConsumer<LivingEntity, Integer> doEffects) {
        DRINK_EFFECTS.put(fluid, doEffects);
    }

    @Deprecated
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

    public static BiConsumer<LivingEntity, Integer> getEffects(Registry<DrinkEffect> drinkEffects, FluidStack fluid) {
        List<DrinkEffectAttribute> drinkEffectAttributes = new ArrayList<>();
        for (Map.Entry<ResourceKey<DrinkEffect>, DrinkEffect> entry : drinkEffects.entrySet()) {
            FluidStack fluidStack = entry.getValue().fluidStack();
            if (FluidStack.isSameFluidSameComponents(fluidStack, fluid)
                    && fluidStack.getAmount()>0
                    && fluidStack.getAmount() <= fluid.getAmount()) {
                int mul = fluid.getAmount() / fluidStack.getAmount();
                if (mul > 1) {
                    for (DrinkEffectAttribute drinkEffectAttribute : entry.getValue().drinkEffectAttribute()) {
                        drinkEffectAttributes.add(new DrinkEffectAttribute(drinkEffectAttribute.getPotion(), drinkEffectAttribute.getDuration() * mul, drinkEffectAttribute.getLevel()));
                    }
                } else drinkEffectAttributes.addAll(entry.getValue().drinkEffectAttribute());
            }
        }
        if (!drinkEffectAttributes.isEmpty()) {
            return createDrinkEffects(drinkEffectAttributes.toArray(new DrinkEffectAttribute[0]));
        }
        return null;
    }
}
