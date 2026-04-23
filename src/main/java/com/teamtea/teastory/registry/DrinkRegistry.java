package com.teamtea.teastory.registry;

import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.recipe.drink.DrinkEffect;
import com.teamtea.teastory.recipe.drink.DrinkEffectAttribute;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public class DrinkRegistry {

    private static ResourceKey<DrinkEffect> createKey(String name) {
        return ResourceKey.create(TeaStoryRegistries.DRINK_EFFECT, TeaStory.rl(name));
    }

    private static ResourceKey<DrinkEffect> createKey(Identifier name) {
        return ResourceKey.create(TeaStoryRegistries.DRINK_EFFECT, name.withSuffix("_patch"));
    }

    public static DrinkEffectAttribute createSimpleDrinkEffect(MobEffect potionIn, int durationIn, int level) {
        return new DrinkEffectAttribute(EffectRegister.getMobEffect(potionIn), durationIn, level);
    }

    public static List<DrinkEffectAttribute> createDrinkEffects(DrinkEffectAttribute... attributes) {
        return List.of(attributes);
    }

    public static void registerEffects(BootstrapContext<DrinkEffect> context, Fluid fluid, DrinkEffectAttribute... doEffects) {
        Identifier key = BuiltInRegistries.FLUID.getKey(fluid);
        context.register(createKey(key), new DrinkEffect(new FluidStack(fluid, 250), List.of(doEffects)));
    }

    public static void registerEffects(BootstrapContext<DrinkEffect> context, Fluid fluid, List<DrinkEffectAttribute> doEffects) {
        Identifier key = BuiltInRegistries.FLUID.getKey(fluid);
        context.register(createKey(key), new DrinkEffect(new FluidStack(fluid, 250), doEffects));
    }

    public static void bootstrap(BootstrapContext<DrinkEffect> context) {
        registerEffects(context, FluidRegister.SUGARY_WATER_STILL.get(), createSimpleDrinkEffect(MobEffects.SPEED.value(), 2, 0));

        registerEffects(context, FluidRegister.WEAK_GREEN_TEA_STILL.get(), createSimpleDrinkEffect(EffectRegister.AGILITY, 2, 0));
        registerEffects(context, FluidRegister.GREEN_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.AGILITY), 2, 1), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 2, 0)));
        registerEffects(context, FluidRegister.STRONG_GREEN_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.AGILITY), 2, 2), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 4, 0)));

        registerEffects(context, FluidRegister.WEAK_BLACK_TEA_STILL.get(), createSimpleDrinkEffect(MobEffects.HEALTH_BOOST.value(), 4, 0));
        registerEffects(context, FluidRegister.BLACK_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.HEALTH_BOOST, 4, 1), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 4, 0)));
        registerEffects(context, FluidRegister.STRONG_BLACK_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.HEALTH_BOOST, 4, 2), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 8, 0)));

        registerEffects(context, FluidRegister.WEAK_WHITE_TEA_STILL.get(), createSimpleDrinkEffect(MobEffects.SPEED.value(), 2, 0));
        registerEffects(context, FluidRegister.WHITE_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.SPEED, 2, 1), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 2, 0)));
        registerEffects(context, FluidRegister.STRONG_WHITE_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.SPEED, 2, 2), new DrinkEffectAttribute(EffectRegister.getMobEffect(EffectRegister.EXCITEMENT), 4, 0)));
    }


}
