package xueluoanping.teastory.plugin;


import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import net.irisshaders.iris.Iris;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import xueluoanping.teastory.plugin.eclipticseasons.ESCommonEventHandler;
import xueluoanping.teastory.plugin.eclipticseasons.ESDataEventHandler;
import xueluoanping.teastory.plugin.iris.IrisEventHandler;

public class CompatManager {

    private static boolean iris = false;
    private static boolean eclipticseasons = false;

    public static void init(IEventBus loadEventBus) {
        IEventBus gameEventBus = MinecraftForge.EVENT_BUS;
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

    public static ForgeConfigSpec.BooleanValue enable;
    public static ForgeConfigSpec.BooleanValue irisCompat;


    public static void initConfig(ForgeConfigSpec.Builder builder, boolean isServer){
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
