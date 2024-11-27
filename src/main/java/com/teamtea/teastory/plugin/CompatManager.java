package com.teamtea.teastory.plugin;

import com.teamtea.teastory.plugin.ecliptic_seasons.ESDataEventHandler;
import com.teamtea.teastory.plugin.iris.IrisEventHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;

public class CompatManager {

    private static boolean iris = false;

    public static void init(IEventBus loadEventBus) {
        IEventBus gameEventBus = NeoForge.EVENT_BUS;
        if (Platform.isPhysicalClient()) {
            iris = Platform.isModLoaded("iris");
            if (iris) {
                gameEventBus.register(IrisEventHandler.INSTANCE);
            }
        }
        loadEventBus.register(ESDataEventHandler.INSTANCE);
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
