package cloud.lemonslice.teastory.potion;


import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.SnowLayerBlock;

public class PhotosynthesisEffect extends MobEffect
{


    public PhotosynthesisEffect(MobEffectCategory neutral, int i) {
        super(neutral,i);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 60 == 0;
    }


    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        boolean isDaytime = entityLivingBaseIn.level().getDayTime() < 12000L;
        BlockPos pos = entityLivingBaseIn.getOnPos();
        if ((!entityLivingBaseIn.level().isRainingAt(pos)) && ((isDaytime && (entityLivingBaseIn.level().getLightEmission(pos) >= 13 - 2 * amplifier)) || ((!isDaytime) && (entityLivingBaseIn.level().getBrightness(LightLayer.BLOCK, pos) >= 13 - 2 * amplifier))))
        {
            entityLivingBaseIn.heal(1);
        }
    }


}
