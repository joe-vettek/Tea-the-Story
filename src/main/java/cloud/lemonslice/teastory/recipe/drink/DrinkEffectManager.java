package cloud.lemonslice.teastory.recipe.drink;



import cloud.lemonslice.teastory.potion.EffectRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import xueluoanping.teastory.FluidRegistry;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public final class DrinkEffectManager
{
    private final static Map<Fluid, BiConsumer<LivingEntity, Integer>> DRINK_EFFECTS = new HashMap<>();

    public static void init()
    {
        registerDrinkEffects();
    }

    private static void registerDrinkEffects()
    {
        registerEffects(FluidRegistry.SUGARY_WATER_STILL.get(), createSimpleDrinkEffect(MobEffects.MOVEMENT_SPEED, 2, 0));

        registerEffects(FluidRegistry.WEAK_GREEN_TEA_STILL.get(), createSimpleDrinkEffect(EffectRegistry.AGILITY, 2, 0));
        registerEffects(FluidRegistry.GREEN_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(EffectRegistry.AGILITY, 2, 1), new DrinkEffectAttribute(EffectRegistry.EXCITEMENT, 2, 0)));
        registerEffects(FluidRegistry.STRONG_GREEN_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(EffectRegistry.AGILITY, 2, 2), new DrinkEffectAttribute(EffectRegistry.EXCITEMENT, 4, 0)));

        registerEffects(FluidRegistry.WEAK_BLACK_TEA_STILL.get(), createSimpleDrinkEffect(MobEffects.HEALTH_BOOST, 4, 0));
        registerEffects(FluidRegistry.BLACK_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.HEALTH_BOOST, 4, 1), new DrinkEffectAttribute(EffectRegistry.EXCITEMENT, 4, 0)));
        registerEffects(FluidRegistry.STRONG_BLACK_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.HEALTH_BOOST, 4, 2), new DrinkEffectAttribute(EffectRegistry.EXCITEMENT, 8, 0)));

        registerEffects(FluidRegistry.WEAK_WHITE_TEA_STILL.get(), createSimpleDrinkEffect(MobEffects.DIG_SPEED, 2, 0));
        registerEffects(FluidRegistry.WHITE_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.DIG_SPEED, 2, 1), new DrinkEffectAttribute(EffectRegistry.EXCITEMENT, 2, 0)));
        registerEffects(FluidRegistry.STRONG_WHITE_TEA_STILL.get(), createDrinkEffects(new DrinkEffectAttribute(MobEffects.DIG_SPEED, 2, 2), new DrinkEffectAttribute(EffectRegistry.EXCITEMENT, 4, 0)));
    }

    public static void registerEffects(Fluid fluid, BiConsumer<LivingEntity, Integer> doEffects)
    {
        DRINK_EFFECTS.put(fluid, doEffects);
    }

    @Nullable
    public static BiConsumer<LivingEntity, Integer> getEffects(Fluid key)
    {
        return DRINK_EFFECTS.get(key);
    }

    public static BiConsumer<LivingEntity, Integer> createSimpleDrinkEffect(MobEffect potionIn, int durationIn, int level)
    {
        return (livingEntity, amount) -> livingEntity.addEffect(new MobEffectInstance(potionIn, durationIn * amount, level));
    }

    public static BiConsumer<LivingEntity, Integer> createDrinkEffects(DrinkEffectAttribute... attributes)
    {
        return (livingEntity, amount) ->
        {
            for (DrinkEffectAttribute attribute : attributes)
            {
                livingEntity.addEffect(new MobEffectInstance(attribute.getPotion(), amount * attribute.getDuration(), attribute.getLevel()));
            }
        };
    }
}
