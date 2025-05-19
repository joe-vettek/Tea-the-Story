package xueluoanping.teastory.plugin;


import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.plugin.eclipticseasons.ESCommonEventHandler;
import xueluoanping.teastory.plugin.eclipticseasons.ESDataEventHandler;

import java.lang.reflect.Method;

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
            // ManualWork xx=new ManualWork();
            // xx.load(loadEventBus);
            try {
                Class<?> manualWorkClass = Class.forName("xueluoanping.teastory.manual.ManualWork");
                Object manualWorkInstance = manualWorkClass.getDeclaredConstructor().newInstance();
                Method loadMethod = manualWorkClass.getMethod("load", IEventBus.class);
                loadMethod.invoke(manualWorkInstance, loadEventBus);
            } catch (Exception e) {
                TeaStory.LOGGER.error(e);
            }
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
