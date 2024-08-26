package xueluoanping.teastory;


import cloud.lemonslice.teastory.block.crops.VineInfoManager;
import cloud.lemonslice.teastory.config.NormalConfigs;
import cloud.lemonslice.teastory.recipe.drink.DrinkEffectManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
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


    public TeaStory(IEventBus modEventBus, ModContainer modContainer) {

        // Register ourselves for server and other game events we are interested in
        modEventBus.addListener(this::gatherData);
        modEventBus.addListener(this::FMLCommonSetup);


        BlockRegister.ModBlocks.register(modEventBus);
        BlockRegister.ModItems.register(modEventBus);

        TileEntityTypeRegistry.DRBlockEntities.register(modEventBus);
        TileEntityTypeRegistry.ModBlocks.register(modEventBus);
        TileEntityTypeRegistry.ModItems.register(modEventBus);
        TileEntityTypeRegistry.DRMenuType.register(modEventBus);


        ItemRegister.ModItems.register(modEventBus);

        FluidRegistry.BLOCKS.register(modEventBus);
        FluidRegistry.ITEMS.register(modEventBus);
        FluidRegistry.FLUIDS.register(modEventBus);
        FluidRegistry.FLUID_TYPES.register(modEventBus);

        RecipeRegister.DRRecipeSerializer.register(modEventBus);
        RecipeRegister.DRRecipeType.register(modEventBus);

        EntityTypeRegistry.ENTITY_TYPE_DEFERRED_REGISTER.register(modEventBus);

        LootRegister.LOOT_MODIFIERS.register(modEventBus);

        ModCapabilities.ATTACHMENT_TYPES.register(modEventBus);
        // ModContents.DRMenuType.register(modEventBus);

        // ModContents.init();

        // modEventBus.addListener(this::gatherData);

        modContainer.registerConfig(ModConfig.Type.COMMON, NormalConfigs.SERVER_CONFIG);
        modContainer.registerConfig(ModConfig.Type.CLIENT, NormalConfigs.CLIENT_CONFIG);
    }


    public static ResourceLocation rl(String id) {
        return ResourceLocation.fromNamespaceAndPath(MODID, id);
    }

    public static ResourceLocation rl(String namespace, String id) {
        return ResourceLocation.fromNamespaceAndPath(namespace, id);
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
