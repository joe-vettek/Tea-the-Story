package com.teamtea.teastory;


import com.teamtea.teastory.config.NormalConfigs;
import com.teamtea.teastory.plugin.CompatManager;
import com.teamtea.teastory.recipe.drink.DrinkEffectManager;
import com.teamtea.teastory.registry.*;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.teamtea.teastory.data.start;

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

        // 黄茶干叶，由绿茶室内发酵得到 金钟罩 提神
        // 青茶 炒得 需要做一个 竹枝铲 青黄 持续手工翻动茶叶，否则就变成茶叶渣（如果在加热）  提神 光合作用
        // 黑茶 由红茶室内长时间发酵得到，陈化模式，木框架 （水偏棕红） 生命汲取  提神
        // 奶茶 红茶加奶加糖得到 黄色   提神 生命恢复

        // 给煮锅加个提示
        // 竹编加一个jade提示
        // 修复石碾

        // 竹床
        // 六色茶砖，鲜茶块。茶砖必须要放在木框架上，如果空气过于潮湿，那么就会变成发霉的茶砖方块，最后变成苔藓块
        // 木框架主要用于支撑，以及可以做一个漏东西的配方，室内发酵的时候必须要放木框架上。

        // 黄瓜藤，苦瓜藤

        // 茶叶发酵在温度适中，湿润的地方最快，春秋
        // 晒茶适合，不适合多雨的季节和群系，且需要足够的温度，但是不能太热了

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

        BlockEntityRegister.DRBlockEntities.register(modEventBus);
        BlockEntityRegister.ModBlocks.register(modEventBus);
        BlockEntityRegister.ModItems.register(modEventBus);
        BlockEntityRegister.DRMenuType.register(modEventBus);


        ItemRegister.ModItems.register(modEventBus);

        FluidRegister.BLOCKS.register(modEventBus);
        FluidRegister.ITEMS.register(modEventBus);
        FluidRegister.FLUIDS.register(modEventBus);
        FluidRegister.FLUID_TYPES.register(modEventBus);

        RecipeRegister.DRRecipeSerializer.register(modEventBus);
        RecipeRegister.DRRecipeType.register(modEventBus);

        EntityRegister.ENTITY_TYPE_DEFERRED_REGISTER.register(modEventBus);

        LootRegister.LOOT_MODIFIERS.register(modEventBus);

        ModCapabilities.ATTACHMENT_TYPES.register(modEventBus);

        ModBiomeFeatures.FEATURES.register(modEventBus);
        ModBiomeModifiers.BIOME_MODIFIER_SERIALIZERS.register(modEventBus);
        ModPlacementModifiers.PLACEMENT_MODIFIERS.register(modEventBus);

        CompatManager.init(modEventBus);
        // ModContents.DRMenuType.register(modEventBus);

        // ModContents.init();

        // modEventBus.addListener(this::gatherData);

        modContainer.registerConfig(ModConfig.Type.COMMON, NormalConfigs.SERVER_CONFIG);
        modContainer.registerConfig(ModConfig.Type.CLIENT, NormalConfigs.CLIENT_CONFIG);

        if (FMLEnvironment.getDist() == Dist.CLIENT)
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

    }


    public static Identifier rl(String id) {
        return Identifier.fromNamespaceAndPath(MODID, id);
    }

    public static Identifier rl(String namespace, String id) {
        return Identifier.fromNamespaceAndPath(namespace, id);
    }

    public void FMLCommonSetup(final FMLCommonSetupEvent event) {
        // start.dataGen(event);
        DrinkEffectManager.init();
    }

    public void gatherData(final GatherDataEvent event) {
        // start.dataGen(event);
        start.onDataGather(event);
    }
}
