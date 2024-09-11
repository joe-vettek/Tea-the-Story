package com.teamtea.teastory.registry;


import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.mobeffect.PhotosynthesisEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public final class EffectRegister {
    public static final MobEffect AGILITY = new MobEffect(MobEffectCategory.BENEFICIAL, 0x828282);
    public static final MobEffect PHOTOSYNTHESIS = new PhotosynthesisEffect(MobEffectCategory.BENEFICIAL, 0x828282);
    public static final MobEffect LIFE_DRAIN = new MobEffect(MobEffectCategory.BENEFICIAL, 0x828282);
    public static final MobEffect DEFENCE = new MobEffect(MobEffectCategory.BENEFICIAL, 0x828282);
    public static final MobEffect EXCITEMENT = new MobEffect(MobEffectCategory.NEUTRAL, 0x828282);

    @SubscribeEvent
    public static void blockRegister(RegisterEvent event) {
        event.register(Registries.MOB_EFFECT, register -> {
            register.register(TeaStory.rl("agility"), AGILITY);
            register.register(TeaStory.rl("photosynthesis"), PHOTOSYNTHESIS);
            register.register(TeaStory.rl("life_drain"), LIFE_DRAIN);
            register.register(TeaStory.rl("defence"), DEFENCE);
            register.register(TeaStory.rl("excitement"), EXCITEMENT);
        });
    }

    public static Holder<MobEffect> getMobEffect(MobEffect mobEffect) {
        return BuiltInRegistries.MOB_EFFECT.getHolder(BuiltInRegistries.MOB_EFFECT.getKey(mobEffect)).get();
    }


}
