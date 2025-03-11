package xueluoanping.teastory.manual;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import xueluoanping.teastory.registry.ItemRegister;
import xueluoanping.teastory.registry.ManualRegistry;

public class ManualWork {

    public void load(IEventBus loadEventBus) {
        ManualRegistry.MANUALS.register(loadEventBus);
        ManualRegistry.DOCUMENT_PROVIDERS.register(loadEventBus);
        ManualRegistry.PATH_PROVIDERS.register(loadEventBus);

        ManualRegistry.MANUAL_ITEM = ItemRegister.ModItems.register("manual", () -> new ManualItem(new Item.Properties()));
    }

}
