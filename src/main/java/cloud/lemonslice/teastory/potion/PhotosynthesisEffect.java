package cloud.lemonslice.teastory.potion;


import net.minecraft.core.BlockPos;
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
    public boolean applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        boolean isDaytime = entityLivingBaseIn.level().getDayTime() < 12000L;
        boolean use = false;
        BlockPos pos = entityLivingBaseIn.getOnPos();
        if ((!entityLivingBaseIn.level().isRainingAt(pos)) && ((isDaytime && (entityLivingBaseIn.level().getLightEmission(pos) >= 13 - 2 * amplifier)) || ((!isDaytime) && (entityLivingBaseIn.level().getBrightness(LightLayer.BLOCK, pos) >= 13 - 2 * amplifier)))) {
            entityLivingBaseIn.heal(1);
            use = true;
        }
        return use;
    }


}
