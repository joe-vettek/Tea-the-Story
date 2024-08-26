package cloud.lemonslice.teastory.handler;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import xueluoanping.teastory.TeaStory;

import java.io.IOException;

import static cloud.lemonslice.teastory.handler.event.AgricultureEventHandler.*;
import static cloud.lemonslice.teastory.handler.event.BattleEventHandler.onShennongChiAttack;
import static cloud.lemonslice.teastory.handler.event.BlockEventHandler.dropAsh;
import static cloud.lemonslice.teastory.handler.event.DrinkEffectEventHandler.*;


@EventBusSubscriber(modid = TeaStory.MODID)
public final class CommonEventHandler
{
    @SubscribeEvent
    public static void onLivingHurt(LivingIncomingDamageEvent event)
    {
        applyAgilityEffect(event);
        // applyDefenceEffect(event);
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingDamageEvent.Pre event)
    {
        applyLifeDrainEffect(event);
        applyDefenceEffect(event);
    }

    @SubscribeEvent
    public static void onPlayerSleep(CanPlayerSleepEvent event)
    {
        applyExcitementEffect(event);
    }

    @SubscribeEvent
    public static void onUseBoneMeal(BonemealEvent event)
    {
        boneMealUsingLimit(event);
    }

    @SubscribeEvent
    public static void onNeighborChanged(BlockEvent.NeighborNotifyEvent event)
    {
        dropAsh(event);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        plantMelon(event);
        cutMelon(event);
    }

    @SubscribeEvent
    public static void onToolUsing(BlockEvent.BlockToolModificationEvent event)
    {

        onAqueductShovelUsing(event);
    }

    @SubscribeEvent
    public static void onFarmlandTrampled(BlockEvent.FarmlandTrampleEvent event)
    {
        stopTramplingMelonField(event);
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingDamageEvent.Post event)
    {
        onShennongChiAttack(event);
    }
}
