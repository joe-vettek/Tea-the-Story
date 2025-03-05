package com.teamtea.teastory.plugin;

import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import com.teamtea.teastory.plugin.eclipticseasons.ESCommonEventHandler;
import com.teamtea.teastory.plugin.eclipticseasons.ESDataEventHandler;
import com.teamtea.teastory.plugin.guideme.GuideMeMode;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;

public class CompatManager {

    private static boolean eclipticseasons = false;
    private static boolean guideme = false;

    public static void init(IEventBus loadEventBus) {
        IEventBus gameEventBus = NeoForge.EVENT_BUS;
        if (Platform.isPhysicalClient()) {

        }
        eclipticseasons =Platform.isModLoaded(EclipticSeasonsApi.MODID);
        if(eclipticseasons)
        {
            loadEventBus.register(ESDataEventHandler.INSTANCE);
            gameEventBus.register(ESCommonEventHandler.INSTANCE);
        }

        guideme =Platform.isModLoaded("guideme");
        if(guideme)
        {
            GuideMeMode.init();
        }
    }

    public static ModConfigSpec.BooleanValue enable;


    public static void initConfig(ModConfigSpec.Builder builder,boolean isServer){
        if(isServer){
            builder.push("Compat");
            enable = builder.comment("Enable solar term season compat.")
                    .define("EnableSeason", true);
            builder.pop();
        }else {
            builder.push("Compat");

            builder.pop();
        }
    }
}
