package xueluoanping.teastory.registry;

import cloud.lemonslice.teastory.potion.EffectRegistry;
import cloud.lemonslice.teastory.recipe.drink.DrinkEffectAttribute;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.recipe.DrinkEffect;

import java.util.List;

public class DrinkRegistry {
    public static final ResourceKey<Registry<DrinkEffect>> DRINK_EFFECT = ResourceKey.createRegistryKey(TeaStory.rl("drink_effect"));

    private static ResourceKey<DrinkEffect> createKey(String name) {
        return ResourceKey.create(DRINK_EFFECT, TeaStory.rl(name));
    }

    private static ResourceKey<DrinkEffect> createKey(ResourceLocation name) {
        return ResourceKey.create(DRINK_EFFECT, name);
    }

    public static DrinkEffectAttribute createSimpleDrinkEffect(MobEffect potionIn, int durationIn, int level) {
        return new DrinkEffectAttribute(potionIn, durationIn, level);
    }

    public static List<DrinkEffectAttribute> createDrinkEffects(DrinkEffectAttribute... attributes) {
        return List.of(attributes);
    }

    public static void registerEffects(BootstapContext<DrinkEffect> context, Fluid fluid, DrinkEffectAttribute... doEffects) {
        ResourceLocation key = BuiltInRegistries.FLUID.getKey(fluid);
        context.register(createKey(key), new DrinkEffect(new FluidStack(fluid, 250), List.of(doEffects)));
    }

    public static void registerEffects(BootstapContext<DrinkEffect> context, Fluid fluid, List<DrinkEffectAttribute> doEffects) {
        ResourceLocation key = BuiltInRegistries.FLUID.getKey(fluid);
        context.register(createKey(key), new DrinkEffect(new FluidStack(fluid, 250), doEffects));
    }

    public static void bootstrap(BootstapContext<DrinkEffect> context) {
        registerEffects(context, FluidRegistry.SUGARY_WATER_STILL.get(), createSimpleDrinkEffect(MobEffects.MOVEMENT_SPEED, 2, 0));

        registerEffects(context, FluidRegistry.WEAK_GREEN_TEA_STILL.get(), createSimpleDrinkEffect(EffectRegistry.AGILITY, 2, 0));
        registerEffects(context, FluidRegistry.GREEN_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(EffectRegistry.AGILITY, 2, 1), new DrinkEffectAttribute(EffectRegistry.EXCITEMENT, 2, 0)));
        registerEffects(context, FluidRegistry.STRONG_GREEN_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(EffectRegistry.AGILITY, 2, 2), new DrinkEffectAttribute(EffectRegistry.EXCITEMENT, 4, 0)));

        registerEffects(context, FluidRegistry.WEAK_BLACK_TEA_STILL.get(), createSimpleDrinkEffect(MobEffects.HEALTH_BOOST, 4, 0));
        registerEffects(context, FluidRegistry.BLACK_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.HEALTH_BOOST, 4, 1), new DrinkEffectAttribute(EffectRegistry.EXCITEMENT, 4, 0)));
        registerEffects(context, FluidRegistry.STRONG_BLACK_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.HEALTH_BOOST, 4, 2), new DrinkEffectAttribute(EffectRegistry.EXCITEMENT, 8, 0)));

        registerEffects(context, FluidRegistry.WEAK_WHITE_TEA_STILL.get(), createSimpleDrinkEffect(MobEffects.DIG_SPEED, 2, 0));
        registerEffects(context, FluidRegistry.WHITE_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.DIG_SPEED, 2, 1), new DrinkEffectAttribute(EffectRegistry.EXCITEMENT, 2, 0)));
        registerEffects(context, FluidRegistry.STRONG_WHITE_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.DIG_SPEED, 2, 2), new DrinkEffectAttribute(EffectRegistry.EXCITEMENT, 4, 0)));
    }


}
