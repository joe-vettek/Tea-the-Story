package cloud.lemonslice.teastory.handler.event;


import cloud.lemonslice.teastory.potion.EffectRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;

public final class DrinkEffectEventHandler
{
    public static void applyAgilityEffect(LivingHurtEvent event)
    {
        if (!event.getEntity().level().isClientSide())
        {
            MobEffectInstance effect = event.getEntity().getEffect(EffectRegistry.AGILITY);
            if (effect != null)
            {
                if (event.getEntity().getRandom().nextFloat() < ((effect.getAmplifier() + 1) * 0.15F + 0.05F))
                {
                    event.setCanceled(true);
                }
            }
        }
    }

    public static void applyDefenceEffect(LivingEvent event)
    {
        if (!event.getEntity().level().isClientSide())
        {
            if (event.getEntity().getEffect(EffectRegistry.DEFENCE) != null)
            {
                if (event instanceof LivingHurtEvent)
                {
                    event.getEntity().playSound(SoundEvents.SHIELD_BREAK, 1.0F, 1.0F);
                }
                event.setCanceled(true);
            }
        }
    }

    public static void applyLifeDrainEffect(LivingAttackEvent event)
    {
        if (!event.getEntity().level().isClientSide())
        {
            MobEffectInstance effect = event.getEntity().getEffect(EffectRegistry.LIFE_DRAIN);
            if (effect != null)
            {
                int level = effect.getAmplifier() + 1;
                float r = event.getEntity().getRandom().nextFloat();
                float health = event.getEntity().getHealth();
                if (health < event.getEntity().getMaxHealth() && r <= level * 0.2F)
                {
                    event.getEntity().heal(4.0F - 6.0F / (level + 1.0F));
                }
            }
        }
    }

    public static void applyExcitementEffect(PlayerSleepInBedEvent event)
    {
        if (!event.getEntity().level().isClientSide())
        {
            MobEffectInstance effect = event.getEntity().getEffect(EffectRegistry.EXCITEMENT);
            if (effect != null)
            {
                event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
                ((ServerPlayer) event.getEntity()).sendSystemMessage(Component.translatable("info.teastory.bed.excited"), true);
            }
        }
    }
}
