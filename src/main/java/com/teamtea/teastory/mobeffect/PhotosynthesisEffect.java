package com.teamtea.teastory.mobeffect;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;

public class PhotosynthesisEffect extends MobEffect {


    public PhotosynthesisEffect(MobEffectCategory neutral, int i) {
        super(neutral, i);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 60 == 0;
    }


    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity entityLivingBaseIn, int amplifier) {
        boolean isDaytime = serverLevel.getDefaultClockTime() < 12000L;
        boolean use = false;
        BlockPos pos = entityLivingBaseIn.getOnPos();
        if ((!serverLevel.isRainingAt(pos)) && ((isDaytime && (serverLevel.getLightEmission(pos) >= 13 - 2 * amplifier)) || ((!isDaytime) && (serverLevel.getBrightness(LightLayer.BLOCK, pos) >= 13 - 2 * amplifier)))) {
            entityLivingBaseIn.heal(1);
            use = true;
        }
        return use;
    }
}
