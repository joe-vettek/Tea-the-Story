package cloud.lemonslice.teastory.handler.event;


import cloud.lemonslice.teastory.potion.EffectRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;

public final class DrinkEffectEventHandler
{
    public static void applyAgilityEffect(LivingIncomingDamageEvent event)
    {
        if (!event.getEntity().level().isClientSide())
        {
            MobEffectInstance effect = event.getEntity().getEffect(EffectRegistry.getMobEffect(EffectRegistry.AGILITY));
            if (effect != null)
            {
                if (event.getEntity().getRandom().nextFloat() < ((effect.getAmplifier() + 1) * 0.15F + 0.05F))
                {
                    event.setCanceled(true);
                    // event.setNewDamage(0);
                }
            }
        }
    }

    public static void applyDefenceEffect(LivingDamageEvent.Pre event)
    {
        if (!event.getEntity().level().isClientSide())
        {
            if (event.getEntity().getEffect(EffectRegistry.getMobEffect(EffectRegistry.DEFENCE)) != null)
            {
                if (event instanceof LivingDamageEvent)
                {
                    event.getEntity().playSound(SoundEvents.SHIELD_BREAK, 1.0F, 1.0F);
                }
                event.setNewDamage(0);
                // event.setCanceled(true);
            }
        }
    }

    public static void applyLifeDrainEffect(LivingDamageEvent.Pre event)
    {
        if (!event.getEntity().level().isClientSide())
        {
            MobEffectInstance effect = event.getEntity().getEffect(EffectRegistry.getMobEffect(EffectRegistry.LIFE_DRAIN));
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

    public static void applyExcitementEffect(CanPlayerSleepEvent event)
    {
        if (!event.getEntity().level().isClientSide())
        {
            MobEffectInstance effect = event.getEntity().getEffect(EffectRegistry.getMobEffect(EffectRegistry.EXCITEMENT));
            if (effect != null)
            {
                event.setProblem(Player.BedSleepingProblem.OTHER_PROBLEM);
                event.getEntity().sendSystemMessage(Component.translatable("info.teastory.bed.excited"), true);
            }
        }
    }
}
