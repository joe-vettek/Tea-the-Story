package com.teamtea.teastory.registry;

import com.teamtea.teastory.TeaStory;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageType {

    public static final ResourceKey<DamageType> BOILING = createKey("boiling");

    private static ResourceKey<DamageType> createKey(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, TeaStory.rl(name));
    }

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(BOILING,new DamageType("boiling", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,0.0f));
    }
}
