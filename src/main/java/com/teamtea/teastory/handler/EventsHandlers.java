package com.teamtea.teastory.handler;

import com.teamtea.teastory.registry.BlockRegister;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import com.teamtea.teastory.entity.ai.AvoidCommonEntityGoal;
import com.teamtea.teastory.tag.TeaTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import com.teamtea.teastory.registry.ItemRegister;
import com.teamtea.teastory.entity.ScarecrowEntity;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class EventsHandlers {
    @SubscribeEvent
    public static void onItemTooltipEvent(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() == ItemRegister.RICE_BALL.get()) {
            // event.getToolTip().add(Component.translatable("info.teastory.tooltip.rice"));
        }
    }

    @SubscribeEvent
    public static void onWandererTradesEvent(WandererTradesEvent event) {
        event.getGenericTrades().add((pTrader, pRandom) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 4),
                BlockRegister.WILD_BITTER_GOURD_ITEM.get().getDefaultInstance(),3,1,pRandom.nextFloat()*9.43f));
    }


    @SubscribeEvent
    public static void onMobSpawnEvent(EntityJoinLevelEvent event) {
        if (
                event.getLevel() instanceof ServerLevel
                        && !event.loadedFromDisk()
                        && event.getEntity().getType().is(TeaTags.Entities.BIRDS)
                        && event.getEntity() instanceof PathfinderMob pathfinderMob) {

            // TeaStory.logger(event.getLevel(), pathfinderMob.goalSelector.getAvailableGoals().size());
            pathfinderMob.goalSelector.addGoal(0, new AvoidCommonEntityGoal<>(
                    pathfinderMob, ScarecrowEntity.class, Entity::isAlive, 8F, 1.0D, 1.2D,Entity::isAlive
            ));
            // event.getToolTip().add(Component.translatable("info.teastory.tooltip.rice"));
        }
    }


}
