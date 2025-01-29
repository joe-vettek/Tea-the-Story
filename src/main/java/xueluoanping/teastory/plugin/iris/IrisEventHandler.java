package xueluoanping.teastory.plugin.iris;


import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xueluoanping.teastory.plugin.CompatManager;

public class IrisEventHandler {
    public static IrisEventHandler INSTANCE = new IrisEventHandler();


    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent levelTickEvent) {
        if(levelTickEvent.phase== TickEvent.Phase.START) {
            if (CompatManager.irisCompat.get()) {
                TSIrisPlugin.checkReload();
            }
        }
    }


}
