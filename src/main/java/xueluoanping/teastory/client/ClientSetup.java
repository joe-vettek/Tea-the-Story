package xueluoanping.teastory.client;

import cloud.lemonslice.teastory.block.crops.HybridizableFlowerBlock;
import cloud.lemonslice.teastory.block.crops.TrellisWithVineBlock;
import cloud.lemonslice.teastory.client.color.block.GrassBlockColor;
import cloud.lemonslice.teastory.client.color.block.HybridizableFlowerBlockColor;
import cloud.lemonslice.teastory.client.color.block.SaucepanBlockColor;
import cloud.lemonslice.teastory.client.color.block.TeaCupBlockColor;
import cloud.lemonslice.teastory.client.color.item.*;
import cloud.lemonslice.teastory.client.gui.BambooTrayGui;
import cloud.lemonslice.teastory.client.gui.DrinkMakerGui;
import cloud.lemonslice.teastory.client.gui.StoneMillGui;
import cloud.lemonslice.teastory.client.gui.StoneRollerGui;
import cloud.lemonslice.teastory.client.gui.StoveGui;
import cloud.lemonslice.teastory.client.render.BambooTrayTESR;
import cloud.lemonslice.teastory.client.render.DrinkMakerTESR;
import cloud.lemonslice.teastory.client.render.StoneMillTESR;
import cloud.lemonslice.teastory.client.render.StoneRollerTESR;
import cloud.lemonslice.teastory.client.render.StoveTESR;
import cloud.lemonslice.teastory.client.render.WoodenBarrelTESR;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.MultiPartBakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import xueluoanping.teastory.*;
import xueluoanping.teastory.variant.Planks;

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
            MenuScreens.register(TileEntityTypeRegistry.BAMBOO_TRAY_CONTAINER.get(), BambooTrayGui::new);
            MenuScreens.register(TileEntityTypeRegistry.DRINK_MAKER_CONTAINER.get(), DrinkMakerGui::new);
            MenuScreens.register(TileEntityTypeRegistry.STONE_MILL_CONTAINER.get(), StoneMillGui::new);
            MenuScreens.register(TileEntityTypeRegistry.STONE_ROLLER_CONTAINER.get(), StoneRollerGui::new);
            MenuScreens.register(TileEntityTypeRegistry.STOVE_CONTAINER.get(), StoveGui::new);

            ItemBlockRenderTypes.setRenderLayer(BlockRegister.stone_campfire.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.DRY_HAYSTACK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.WET_HAYSTACK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.GRASS_BLOCK_WITH_HOLE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.BAMBOO_GLASS_DOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(TileEntityTypeRegistry.DRINK_MAKER.get(), RenderType.cutout());

            ItemBlockRenderTypes.setRenderLayer(BlockRegister.WILD_GRAPE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.GRAPE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.CUCUMBER.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.BITTER_GOURD.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.CHILI_PLANT.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.CHINESE_CABBAGE_PLANT.get(), RenderType.cutout());

            BlockRegister.ModBlocks.getEntries().forEach(blockHolder -> {
                if (blockHolder.get() instanceof TrellisWithVineBlock || blockHolder.get() instanceof HybridizableFlowerBlock) {
                    ItemBlockRenderTypes.setRenderLayer(blockHolder.get(), RenderType.cutout());
                }
            });
        });
    }

    //    注意static是单次，比如启动类，没有比如右击事件
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeRegistry.SEAT_TYPE.get(), pContext -> new EntityRenderer<Entity>(pContext) {
            @Override
            public ResourceLocation getTextureLocation(Entity pEntity) {
                return null;
            }
        });
        event.registerBlockEntityRenderer(TileEntityTypeRegistry.BAMBOO_TRAY_TYPE.get(), BambooTrayTESR::new);
        event.registerBlockEntityRenderer(TileEntityTypeRegistry.DRINK_MAKER_TYPE.get(), DrinkMakerTESR::new);
        event.registerBlockEntityRenderer(TileEntityTypeRegistry.STONE_MILL_TYPE.get(), StoneMillTESR::new);
        event.registerBlockEntityRenderer(TileEntityTypeRegistry.STONE_ROLLER_TYPE.get(), StoneRollerTESR::new);
        event.registerBlockEntityRenderer(TileEntityTypeRegistry.STOVE_TYPE.get(), StoveTESR::new);
        event.registerBlockEntityRenderer(TileEntityTypeRegistry.WOODEN_BARREL_TYPE.get(), WoodenBarrelTESR::new);


    }


    @SubscribeEvent
    public static void onModelBaked(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> modelRegistry = event.getModels();
        MultiPartBakedModel OAK_TRELLIS_MODEL = (MultiPartBakedModel) event.getModels().get(BlockModelShaper.stateToModelLocation(BlockRegister.OAK_TRELLIS.get().defaultBlockState()));
        ModelResourceLocation OAK_TRELLIS_ITEM_LOCATION = new ModelResourceLocation(BlockRegister.OAK_TRELLIS.getId(), "inventory");
        BakedModel OAK_TRELLIS_ITEM_MODEL = event.getModels().get(OAK_TRELLIS_ITEM_LOCATION);

        TeaStory.logger(OAK_TRELLIS_MODEL);

        var state = BlockRegister.OAK_TRELLIS.get().defaultBlockState();
        Planks.resourceLocationBlockMap.forEach((res, block) -> {
            // modelRegistry.get(((HashMap.Node) (Planks.resourceLocationBlockMap).entrySet().toArray()[0]).getKey())
            // modelRegistry.put(new ModelResourceLocation((res),""),cc);
            for (BlockState possibleState : block.getB().getStateDefinition().getPossibleStates()) {
                final BlockState[] newstate = {state};
                state.getProperties().forEach(
                        k -> {
                            newstate[0] = newstate[0].setValue((Property) k, possibleState.getValue(k));
                        }
                );
                var toRes = BlockModelShaper.stateToModelLocation(possibleState);
                TextureAtlasSprite itemc = modelRegistry.get(BlockModelShaper.stateToModelLocation(block.getA().defaultBlockState())).getParticleIcon();
                var reModel = modelRegistry.get(BlockModelShaper.stateToModelLocation(newstate[0]));
                modelRegistry.put(toRes, new WarpBakeModel(reModel, itemc));
            }
            {
                var location_A = new ModelResourceLocation(BuiltInRegistries.ITEM.getKey(block.getA().asItem()), "inventory");

                var location_3 = new ModelResourceLocation(BuiltInRegistries.ITEM.getKey(block.getB().asItem()), "inventory");

                modelRegistry.put(location_3, new WarpBakeModel(OAK_TRELLIS_ITEM_MODEL,  modelRegistry.get(location_A).getParticleIcon()));
            }
        });

        // BakedModel existingModel = modelRegistry.get(location);
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
        var grassColor = new GrassBlockColor();
        event.register(grassColor, BlockRegister.GRASS_BLOCK_WITH_HOLE.get(), BlockRegister.WATERMELON_VINE.get());
        BlockRegister.ModBlocks.getEntries().forEach(blockHolder -> {
            if (blockHolder.get() instanceof TrellisWithVineBlock) {
                event.register(grassColor, blockHolder.get());
            }
        });
        var hybridizableFlowerBlockColor = new HybridizableFlowerBlockColor();
        BlockRegister.ModBlocks.getEntries().forEach(blockHolder -> {
            if (blockHolder.get() instanceof HybridizableFlowerBlock) {
                event.register(hybridizableFlowerBlockColor, blockHolder.get());
            }
        });
        event.register(new TeaCupBlockColor(), TileEntityTypeRegistry.WOODEN_TRAY.get());
        event.register(new SaucepanBlockColor(), BlockRegister.saucepan.get());


    }

    @SubscribeEvent
    public static void onRegisterColorHandlersEvent_Item(RegisterColorHandlersEvent.Item event) {
        var buckColors = new BucketItemColors();
        FluidRegistry.ITEMS.getEntries().forEach(itemRegistryObject -> event.register(buckColors, itemRegistryObject.get()));
        event.register(new CupItemColors(), ItemRegister.PORCELAIN_CUP_DRINK.get());
        event.register(new BottleItemColors(), ItemRegister.BOTTLE_DRINK.get());
        event.register(new GrassBlockItemColors(), BlockRegister.GRASS_BLOCK_WITH_HOLE.get().asItem());
        var hybridizableFlowerItemColor = new HybridizableFlowerItemColor();
        BlockRegister.ModBlocks.getEntries().forEach(blockHolder -> {
            if (blockHolder.get() instanceof HybridizableFlowerBlock) {
                event.register(hybridizableFlowerItemColor, blockHolder.get());
            }
        });
    }
}
