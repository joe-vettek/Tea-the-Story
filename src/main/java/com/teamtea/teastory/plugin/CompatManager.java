package com.teamtea.teastory.plugin;

import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import com.teamtea.teastory.plugin.eclipticseasons.ESCommonEventHandler;
import com.teamtea.teastory.plugin.eclipticseasons.ESDataEventHandler;
import com.teamtea.teastory.plugin.iris.IrisEventHandler;
import net.irisshaders.iris.Iris;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;

public class CompatManager {

    private static boolean iris = false;
    private static boolean eclipticseasons = false;

    public static void init(IEventBus loadEventBus) {
        IEventBus gameEventBus = NeoForge.EVENT_BUS;
        if (Platform.isPhysicalClient()) {
            iris = Platform.isModLoaded(Iris.MODID);
            if (iris) {
                gameEventBus.register(IrisEventHandler.INSTANCE);
            }
        }
        eclipticseasons =Platform.isModLoaded(EclipticSeasonsApi.MODID);
        if(eclipticseasons)
        {
            loadEventBus.register(ESDataEventHandler.INSTANCE);
            gameEventBus.register(ESCommonEventHandler.INSTANCE);
        }
    }

    public static ModConfigSpec.BooleanValue enable;
    public static ModConfigSpec.BooleanValue irisCompat;


    public static void initConfig(ModConfigSpec.Builder builder,boolean isServer){
        if(isServer){
            builder.push("Compat");
            enable = builder.comment("Enable solar term season compat.")
                    .define("EnableSeason", true);
            builder.pop();
        }else {
            builder.push("Compat");
            irisCompat = builder.comment("Automatically compatible with shader pack foliage swaying effects.")
                    .define("Iris", true);
            builder.pop();
        }
    }
}
