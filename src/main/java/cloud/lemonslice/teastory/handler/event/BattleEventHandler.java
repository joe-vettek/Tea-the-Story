package cloud.lemonslice.teastory.handler.event;


import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import xueluoanping.teastory.ItemRegister;

import java.io.IOException;

public final class BattleEventHandler
{
    public static void onShennongChiAttack(LivingHurtEvent event) throws IOException
    {
        if (event.getSource().getEntity() instanceof Player && ((Player) event.getSource().getEntity()).getMainHandItem().getItem() == ItemRegister.IRON_SICKLE.get())
        {
            event.getEntity().addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
        }
    }
}
