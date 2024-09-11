package com.teamtea.teastory.handler.event;


import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import com.teamtea.teastory.registry.ItemRegister;

public final class BattleEventHandler
{
    public static void onShennongChiAttack(LivingDamageEvent.Post event)
    {
        if (event.getSource().getEntity() instanceof Player && ((Player) event.getSource().getEntity()).getMainHandItem().getItem() == ItemRegister.IRON_SICKLE.get())
        {
            event.getEntity().addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
        }
    }
}
