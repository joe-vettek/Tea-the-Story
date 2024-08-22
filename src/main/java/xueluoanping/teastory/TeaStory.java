package xueluoanping.teastory;


import cloud.lemonslice.teastory.block.crops.VineInfoManager;
import cloud.lemonslice.teastory.config.NormalConfigs;
import cloud.lemonslice.teastory.recipe.drink.DrinkEffectManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xueluoanping.teastory.data.start;

import java.util.List;
// import xueluoanping.fluiddrawerslegacy.handler.ControllerFluidCapabilityHandler;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TeaStory.MODID)
public class TeaStory {
    public static final String MODID = "teastory";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger(TeaStory.MODID);
    public static final String NETWORK_VERSION = "1.0";

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


        BlockRegister.ModBlocks.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegister.ModItems.register(FMLJavaModLoadingContext.get().getModEventBus());

        TileEntityTypeRegistry.DRBlockEntities.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileEntityTypeRegistry.ModBlocks.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileEntityTypeRegistry.ModItems.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileEntityTypeRegistry.DRMenuType.register(FMLJavaModLoadingContext.get().getModEventBus());


        ItemRegister.ModItems.register(FMLJavaModLoadingContext.get().getModEventBus());

        FluidRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FluidRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FluidRegistry.FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FluidRegistry.FLUID_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

        RecipeRegister.DRRecipeSerializer.register(FMLJavaModLoadingContext.get().getModEventBus());
        RecipeRegister.DRRecipeType.register(FMLJavaModLoadingContext.get().getModEventBus());

        EntityTypeRegistry.ENTITY_TYPE_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());

        LootRegister.LOOT_MODIFIERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        // ModContents.DRMenuType.register(FMLJavaModLoadingContext.get().getModEventBus());

        // ModContents.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherData);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::FMLCommonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, NormalConfigs.SERVER_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, NormalConfigs.CLIENT_CONFIG);
    }


    public static ResourceLocation rl(String id) {
        return new ResourceLocation(MODID, id);
    }

    public static ResourceLocation rl(String namespace, String id) {
        return new ResourceLocation(namespace, id);
    }

    public void FMLCommonSetup(final FMLCommonSetupEvent event) {
        // start.dataGen(event);
        DrinkEffectManager.init();
        VineInfoManager.initTrellisBlocks();
    }

    public void gatherData(final GatherDataEvent event) {
        // start.dataGen(event);
        start.onDataGather(event);
    }
}
