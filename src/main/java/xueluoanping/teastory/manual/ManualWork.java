package xueluoanping.teastory.manual;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.loading.FMLLoader;
import xueluoanping.teastory.registry.ItemRegister;
import xueluoanping.teastory.registry.ManualRegistry;

public class ManualWork {

    public void load(IEventBus loadEventBus) {
        if (FMLLoader.getDist() == Dist.CLIENT) {
            ManualRegistry.MANUALS.register(loadEventBus);
            ManualRegistry.DOCUMENT_PROVIDERS.register(loadEventBus);
            ManualRegistry.PATH_PROVIDERS.register(loadEventBus);
        }
        ManualRegistry.MANUAL_ITEM = ItemRegister.ModItems.register("manual", () -> new ManualItem(new Item.Properties()));
    }

}
