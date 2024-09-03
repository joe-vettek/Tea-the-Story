package cloud.lemonslice.teastory.handler;

import cloud.lemonslice.teastory.config.ServerConfig;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xueluoanping.teastory.TeaStory;

import java.io.IOException;

import static cloud.lemonslice.teastory.handler.event.AgricultureEventHandler.*;
import static cloud.lemonslice.teastory.handler.event.BattleEventHandler.onShennongChiAttack;
import static cloud.lemonslice.teastory.handler.event.BlockEventHandler.dropAsh;
import static cloud.lemonslice.teastory.handler.event.DrinkEffectEventHandler.*;


@Mod.EventBusSubscriber(modid = TeaStory.MODID)
public final class CommonEventHandler {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        applyAgilityEffect(event);
        applyDefenceEffect(event);
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        applyLifeDrainEffect(event);
        applyDefenceEffect(event);
    }

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
        applyExcitementEffect(event);
    }

    @SubscribeEvent
    public static void onUseBoneMeal(BonemealEvent event) {
        boneMealUsingLimit(event);
    }

    @SubscribeEvent
    public static void onNeighborChanged(BlockEvent.NeighborNotifyEvent event) {
        dropAsh(event);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (ServerConfig.Agriculture.betterMelon.get())
            plantMelon(event);
        cutMelon(event);
    }

    @SubscribeEvent
    public static void onToolUsing(BlockEvent.BlockToolModificationEvent event) {

        onAqueductShovelUsing(event);
    }

    @SubscribeEvent
    public static void onFarmlandTrampled(BlockEvent.FarmlandTrampleEvent event) {
        stopTramplingMelonField(event);
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        try {
            onShennongChiAttack(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
