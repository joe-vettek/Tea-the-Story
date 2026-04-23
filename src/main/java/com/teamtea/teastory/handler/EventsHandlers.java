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
// import net.neoforged.neoforge.event.village.WandererTradesEvent;

@EventBusSubscriber
public class EventsHandlers {
    @SubscribeEvent
    public static void onItemTooltipEvent(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() == ItemRegister.RICE_BALL.get()) {
            // event.getToolTip().add(Component.translatable("info.teastory.tooltip.rice"));
        }
    }


}
