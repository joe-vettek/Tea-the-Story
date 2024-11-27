package com.teamtea.teastory.plugin;

import com.teamtea.teastory.plugin.iris.IrisEventHandler;
import net.neoforged.bus.api.IEventBus;

public class PluginManager {

    private static boolean iris = false;

    public static void init(IEventBus modEventBus) {
        iris = Platform.isModLoaded("iris");
        if (Platform.isPhysicalClient()) {
            if (iris)
                modEventBus.register(IrisEventHandler.INSTANCE);
        }
    }
}
