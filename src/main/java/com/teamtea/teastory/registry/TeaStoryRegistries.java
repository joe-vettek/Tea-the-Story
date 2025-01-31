package com.teamtea.teastory.registry;

import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.recipe.drink.DrinkEffect;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class TeaStoryRegistries {
    public static final ResourceKey<Registry<DrinkEffect>> DRINK_EFFECT = ResourceKey.createRegistryKey(TeaStory.rl("drink_effect"));
}
