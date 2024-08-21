package xueluoanping.teastory.handler;

import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import xueluoanping.teastory.ItemRegister;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventsHandlers {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRegisterForWood(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() == ItemRegister.RICE_BALL.get()) {
            event.getToolTip().add(Component.translatable("info.teastory.tooltip.rice"));
        }
    }

}
