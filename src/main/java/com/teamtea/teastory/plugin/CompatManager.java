package com.teamtea.teastory.plugin;

import com.teamtea.teastory.plugin.iris.IrisEventHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.ModConfigSpec;

public class CompatManager {

    private static boolean iris = false;

    public static void init(IEventBus modEventBus) {
        iris = Platform.isModLoaded("iris");
        if (Platform.isPhysicalClient()) {
            if (iris) {
                modEventBus.register(IrisEventHandler.INSTANCE);
            }
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
