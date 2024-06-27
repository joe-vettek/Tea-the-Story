package xueluoanping.teastory;


import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
// import xueluoanping.fluiddrawerslegacy.handler.ControllerFluidCapabilityHandler;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TeaStory.MOD_ID)
public class TeaStory {
    public static final String MOD_ID = "teastory";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger(TeaStory.MOD_ID);

    public static void logger(String x) {
        // if (!FMLEnvironment.production||General.bool.get())
        {
//            LOGGER.debug(x);
            LOGGER.info(x);
        }
    }

    public static void logger(Object... x) {

        // if (!FMLEnvironment.production||General.bool.get())
        {
            StringBuilder output = new StringBuilder();

            for (Object i : x) {
                if (i == null) output.append(", ").append("null");
                else if (i.getClass().isArray()) {
                    output.append(", [");
                    for (Object c : (int[]) i) {
                        output.append(c).append(",");
                    }
                    output.append("]");
                } else if (i instanceof List) {
                    output.append(", [");
                    for (Object c : (List) i) {
                        output.append(c);
                    }
                    output.append("]");
                } else
                    output.append(", ").append(i);
            }
            LOGGER.info(output.substring(1));
        }

    }


    public TeaStory() {

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);


        ModContents.ModBlocks.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModContents.ModItems.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModContents.DRBlockEntities.register(FMLJavaModLoadingContext.get().getModEventBus());


        AllItems.ModItems.register(FMLJavaModLoadingContext.get().getModEventBus());
        // ModContents.DRMenuType.register(FMLJavaModLoadingContext.get().getModEventBus());

        // ModContents.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherData);
    }


    public static ResourceLocation rl(String id) {
        return new ResourceLocation(MOD_ID, id);
    }

    public void gatherData(final GatherDataEvent event) {
        // start.dataGen(event);
    }
}
