package com.teamtea.teastory.handler;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import com.teamtea.teastory.registry.ItemRegister;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
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
