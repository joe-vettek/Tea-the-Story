package xueluoanping.teastory.plugin;


import cloud.lemonslice.teastory.item.AqueductShovelItem;
import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import li.cil.manual.client.forge.MarkdownManualForge;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import xueluoanping.teastory.manual.ManualItem;
import xueluoanping.teastory.plugin.eclipticseasons.ESCommonEventHandler;
import xueluoanping.teastory.plugin.eclipticseasons.ESDataEventHandler;
import xueluoanping.teastory.registry.ItemRegister;
import xueluoanping.teastory.registry.ManualRegistry;

public class CompatManager {

    private static boolean eclipticseasons = false;
    private static boolean markdown_manual = false;

    public static void init(IEventBus loadEventBus) {
        IEventBus gameEventBus = MinecraftForge.EVENT_BUS;
        eclipticseasons = Platform.isModLoaded(EclipticSeasonsApi.MODID);
        markdown_manual = Platform.isModLoaded("markdown_manual");

        if (eclipticseasons) {
            loadEventBus.register(ESDataEventHandler.INSTANCE);
            gameEventBus.register(ESCommonEventHandler.INSTANCE);
        }
        if (markdown_manual) {
            ManualRegistry.MANUALS.register(loadEventBus);
            ManualRegistry.DOCUMENT_PROVIDERS.register(loadEventBus);
            ManualRegistry.PATH_PROVIDERS.register(loadEventBus);

            ManualRegistry.MANUAL_ITEM = ItemRegister.ModItems.register("manual", () -> new ManualItem( new Item.Properties()));
        }
    }

    public static ForgeConfigSpec.BooleanValue enableSeason;

    public static void initConfig(ForgeConfigSpec.Builder builder, boolean isServer) {
        if (isServer) {
            builder.push("Compat");
            enableSeason = builder.comment("Enable solar term season compat.")
                    .define("EnableSeason", true);
            builder.pop();
        } else {
            builder.push("Compat");
            builder.pop();
        }
    }
}
