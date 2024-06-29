package xueluoanping.teastory.client;


// import com.jaquadro.minecraft.storagedrawers.client.renderer.TileEntityDrawersRenderer;

import cloud.lemonslice.teastory.client.color.block.BirchLeavesColor;
import cloud.lemonslice.teastory.client.color.block.GrassBlockColor;
import cloud.lemonslice.teastory.client.color.season.BiomeColorsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import xueluoanping.teastory.ModContents;
import xueluoanping.teastory.TeaStory;

import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {


    // does the Glass Lantern render in the given layer (RenderType) - used as Predicate<RenderType> lambda for setRenderLayer
    public static boolean isGlassLanternValidLayer(RenderType layerToCheck) {
        return layerToCheck == RenderType.cutoutMipped() || layerToCheck == RenderType.translucent();
    }


    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent event) {
        TeaStory.logger("Register Client");
        event.enqueueWork(() -> {
            // ItemBlockRenderTypes.setRenderLayer(ModContents.fluiddrawer.get(), ClientSetup::isGlassLanternValidLayer);
            // MenuScreens.register(ModContents.containerType.get(), Screen.Slot1::new);
            //
            // ItemBlockRenderTypes.setRenderLayer(ModContents.ricePlant.get(),RenderType.cutout());
            // ItemBlockRenderTypes.setRenderLayer(ModContents.RiceSeedlingBlock.get(),RenderType.cutout());
            // fix json file instead
            BiomeColors.GRASS_COLOR_RESOLVER = BiomeColorsHandler.GRASS_COLOR;
            BiomeColors.FOLIAGE_COLOR_RESOLVER = BiomeColorsHandler.FOLIAGE_COLOR;
        });
    }

    //    注意static是单次，比如启动类，没有比如右击事件
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // FluidDrawersLegacyMod.logger("Register Renderer");
        // ModContents.DRBlockEntities.getEntries().forEach((reg) -> {
        //     event.registerBlockEntityRenderer((BlockEntityType<BlockEntityFluidDrawer>)reg.get(),
        //             TESRFluidDrawer::new);
        // });
    }


    @SubscribeEvent
    public static void onModelBaked(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> modelRegistry = event.getModels();

        // ModContents.DREntityBlockItems.getEntries().forEach((reg) -> {
        //     ModelResourceLocation location = new ModelResourceLocation(reg.getId(), "inventory");
        //     BakedModel existingModel = modelRegistry.get(location);
        //     if (existingModel == null) {
        //         throw new RuntimeException("Did not find in registry");
        //     } else if (existingModel instanceof BakedModelFluidDrawer) {
        //         throw new RuntimeException("Tried to replace twice");
        //     } else {
        //         BakedModelFluidDrawer model = new BakedModelFluidDrawer(existingModel);
        //         modelRegistry.put(location, model);
        //     }
        // });
    }

    @SubscribeEvent
    public static void onRegisterColorHandlersEvent_Block(RegisterColorHandlersEvent.Block event) {
        // Register programmable custom block color providers for LeavesPropertiesJson

        BlockState birchLeaves = Blocks.BIRCH_LEAVES.defaultBlockState();
        BlockColors blockColors = event.getBlockColors();

        // Minecraft.getInstance().getBlockColors().register(HYBRIDIZABLE_FLOWER_COLOR, BlockRegistry.CHRYSANTHEMUM, BlockRegistry.HYACINTH, BlockRegistry.ZINNIA);
        // FluidRegistry.BLOCKS.getEntries().forEach(e -> Minecraft.getInstance().getBlockColors().register(FLUID_COLOR, e.get()));
        // Minecraft.getInstance().getBlockColors().register(TEA_CUP_COLOR, BlockRegistry.WOODEN_TRAY);
        // Minecraft.getInstance().getBlockColors().register(SAUCEPAN_COLOR, BlockRegistry.SAUCEPAN);

        event.register(new GrassBlockColor(), ModContents.GRASS_BLOCK_WITH_HOLE.get(), ModContents.WATERMELON_VINE.get());
        event.register(new BirchLeavesColor(), Blocks.BIRCH_LEAVES);
    }
}
