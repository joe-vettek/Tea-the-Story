package com.teamtea.teastory.plugin.iris;

import com.teamtea.teastory.plugin.CompatManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class IrisEventHandler {
    public static IrisEventHandler INSTANCE = new IrisEventHandler();


    @SubscribeEvent
    public void onTick(ClientTickEvent.Pre levelTickEvent) {
        if (CompatManager.irisCompat.getAsBoolean()) {
            TSIrisPlugin.checkReload();
        }
    }


}
