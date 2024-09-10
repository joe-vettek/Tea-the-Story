package com.teamtea.teastory.client;

import com.teamtea.teastory.block.crops.HybridizableFlowerBlock;
import com.teamtea.teastory.*;
import com.teamtea.teastory.block.crops.TrellisWithVineBlock;
import com.teamtea.teastory.client.color.block.GrassBlockColor;
import com.teamtea.teastory.client.color.block.HybridizableFlowerBlockColor;
import com.teamtea.teastory.client.color.block.SaucepanBlockColor;
import com.teamtea.teastory.client.color.block.TeaCupBlockColor;
import com.teamtea.teastory.client.color.item.*;
import com.teamtea.teastory.client.gui.BambooTrayGui;
import com.teamtea.teastory.client.gui.DrinkMakerGui;
import com.teamtea.teastory.client.gui.StoneMillGui;
import com.teamtea.teastory.client.gui.StoneRollerGui;
import com.teamtea.teastory.client.gui.StoveGui;
import com.teamtea.teastory.client.render.*;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.blockentity.CampfireRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.MultiPartBakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import com.teamtea.teastory.fluid.TeaFluidType;
import com.teamtea.teastory.resource.PathResourcesSupplier;
import com.teamtea.teastory.variant.Planks;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {


    // does the Glass Lantern render in the given layer (RenderType) - used as Predicate<RenderType> lambda for setRenderLayer
    public static boolean isGlassLanternValidLayer(RenderType layerToCheck) {
        return layerToCheck == RenderType.cutoutMipped() || layerToCheck == RenderType.translucent();
    }

    @SubscribeEvent
    public static void onRegisterClientExtensionsEvent(RegisterClientExtensionsEvent event) {

        FluidRegistry.FLUIDS.getEntries().forEach(holder -> {

            if (holder.get().getFluidType() instanceof TeaFluidType teaFluidType
                    && holder.get() instanceof BaseFlowingFluid.Source baseFlowingFluid)
                event.registerFluidType(TeaFluidType.getIClientFluidTypeExtensions(teaFluidType), baseFlowingFluid.getFluidType());
        });

    }

    @SubscribeEvent
    public static void onRegisterMenuScreensEvent(RegisterMenuScreensEvent event) {
        event.register(BlockEntityRegistry.BAMBOO_TRAY_CONTAINER.get(), BambooTrayGui::new);
        event.register(BlockEntityRegistry.DRINK_MAKER_CONTAINER.get(), DrinkMakerGui::new);
        event.register(BlockEntityRegistry.STONE_MILL_CONTAINER.get(), StoneMillGui::new);
        event.register(BlockEntityRegistry.STONE_ROLLER_CONTAINER.get(), StoneRollerGui::new);
        event.register(BlockEntityRegistry.STOVE_CONTAINER.get(), StoveGui::new);
    }

    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent event) {
        TeaStory.logger("Register Client");
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.stone_campfire.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.DRY_HAYSTACK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.WET_HAYSTACK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.GRASS_BLOCK_WITH_HOLE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.BAMBOO_GLASS_DOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockEntityRegistry.DRINK_MAKER.get(), RenderType.cutout());

            ItemBlockRenderTypes.setRenderLayer(BlockRegister.WILD_GRAPE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.GRAPE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.CUCUMBER.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.BITTER_GOURD.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.CHILI_PLANT.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.CHINESE_CABBAGE_PLANT.get(), RenderType.cutout());

            BlockRegister.ModBlocks.getEntries().forEach(blockHolder -> {
                if (blockHolder.get() instanceof HybridizableFlowerBlock) {
                    ItemBlockRenderTypes.setRenderLayer(blockHolder.get(), RenderType.cutout());
                }
            });

            for (Block block : BuiltInRegistries.BLOCK) {
                if (block instanceof TrellisWithVineBlock) {
                    ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());
                }
            }
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
        event.registerEntityRenderer(EntityTypeRegistry.SCARECROW_TYPE.get(), pContext -> new EntityRenderer<Entity>(pContext) {
            @Override
            public ResourceLocation getTextureLocation(Entity pEntity) {
                return null;
            }
        });
        event.registerBlockEntityRenderer(BlockEntityRegistry.BAMBOO_TRAY_TYPE.get(), BambooTrayTESR::new);
        event.registerBlockEntityRenderer(BlockEntityRegistry.DRINK_MAKER_TYPE.get(), DrinkMakerTESR::new);
        event.registerBlockEntityRenderer(BlockEntityRegistry.STONE_MILL_TYPE.get(), StoneMillTESR::new);
        event.registerBlockEntityRenderer(BlockEntityRegistry.STONE_ROLLER_TYPE.get(), StoneRollerTESR::new);
        event.registerBlockEntityRenderer(BlockEntityRegistry.STOVE_TYPE.get(), StoveTESR::new);
        event.registerBlockEntityRenderer(BlockEntityRegistry.WOODEN_BARREL_TYPE.get(), WoodenBarrelTESR::new);

        event.registerBlockEntityRenderer(BlockEntityRegistry.stone_campfire_TYPE.get(), StoneCampfireRenderer::new);
    }

    @SubscribeEvent
    public static void onModelBaked(ModelEvent.RegisterAdditional event) {
        event.register(WarpBakeModel.grape_leaves_on_beam);
        event.register(WarpBakeModel.grape_on_post_0);
        event.register(WarpBakeModel.grape_on_post_1);
        event.register(WarpBakeModel.grape_on_post_2);
        event.register(WarpBakeModel.grape_on_post_3);
    }

    @SubscribeEvent
    public static void onModelBaked(ModelEvent.ModifyBakingResult event) {
        Map<ModelResourceLocation, BakedModel> modelRegistry = event.getModels();

        for (ModelResourceLocation grapesRe : WarpBakeModel.grapesRes) {
            WarpBakeModel.grapes.add(modelRegistry.get(grapesRe));
        }

        MultiPartBakedModel OAK_TRELLIS_MODEL = (MultiPartBakedModel) event.getModels().get(BlockModelShaper.stateToModelLocation(BlockRegister.OAK_TRELLIS.get().defaultBlockState()));
        ModelResourceLocation OAK_TRELLIS_ITEM_LOCATION = new ModelResourceLocation(BlockRegister.OAK_TRELLIS.getId(), "inventory");
        BakedModel OAK_TRELLIS_ITEM_MODEL = event.getModels().get(OAK_TRELLIS_ITEM_LOCATION);

        // TeaStory.logger(OAK_TRELLIS_MODEL);
        TeaStory.logger("Minecraft loading all the models with " + modelRegistry.entrySet().size());
        var state = BlockRegister.OAK_TRELLIS.get().defaultBlockState();


        Planks.TrellisBlockMap.forEach((res, plankHolders) -> {
            // modelRegistry.get(((HashMap.Node) (Planks.resourceLocationBlockMap).entrySet().toArray()[0]).getKey())
            // modelRegistry.put(new ModelResourceLocation((res),""),cc);
            ArrayList<BlockState> blockStates = new ArrayList<>(plankHolders.trellisBlock().getStateDefinition().getPossibleStates());
            plankHolders.trellisWithVineBlocks()
                    .forEach(trellisWithVineBlock ->
                            blockStates.addAll(trellisWithVineBlock.getStateDefinition().getPossibleStates()));
            for (BlockState possibleState : blockStates) {
                final BlockState[] newstate = {state};
                state.getProperties().forEach(
                        k -> {
                            newstate[0] = newstate[0].setValue((Property) k, possibleState.getValue(k));
                        }
                );
                var toRes = BlockModelShaper.stateToModelLocation(possibleState);
                TextureAtlasSprite itemc = modelRegistry.get(BlockModelShaper.stateToModelLocation(plankHolders.plank().defaultBlockState())).getParticleIcon();
                var reModel = modelRegistry.get(BlockModelShaper.stateToModelLocation(newstate[0]));
                modelRegistry.put(toRes, new WarpBakeModel(reModel, itemc));
            }
            // var list = List.of("bar", "center", "post", "post_up", "support");
            // for (String s : list) {
            //     var partModelRes = TeaStory.rl("block/%s_%s".formatted(res.getPath(), s));
            //     var reModel = modelRegistry.get(partModelRes);
            //     TextureAtlasSprite particleIcon = modelRegistry.get(BlockModelShaper.stateToModelLocation(block.getA().defaultBlockState())).getParticleIcon();
            //     modelRegistry.put(partModelRes, new WarpBakeModel(reModel, particleIcon));
            // }

            {
                var location_A = new ModelResourceLocation(BuiltInRegistries.ITEM.getKey(plankHolders.plank().asItem()), "inventory");

                var location_3 = new ModelResourceLocation(BuiltInRegistries.ITEM.getKey(plankHolders.trellisBlock().asItem()), "inventory");

                modelRegistry.put(location_3, new WarpBakeModel(OAK_TRELLIS_ITEM_MODEL, modelRegistry.get(location_A).getParticleIcon()));
            }
        });

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
        // BlockRegister.ModBlocks.getEntries().forEach(blockHolder -> {
        //     if (blockHolder.get() instanceof TrellisWithVineBlock) {
        //         event.register(grassColor, blockHolder.get());
        //     }
        // });
        for (Block block : BuiltInRegistries.BLOCK) {
            if (block instanceof TrellisWithVineBlock) {
                event.register(grassColor, block);
            }
        }
        var hybridizableFlowerBlockColor = new HybridizableFlowerBlockColor();
        BlockRegister.ModBlocks.getEntries().forEach(blockHolder -> {
            if (blockHolder.get() instanceof HybridizableFlowerBlock) {
                event.register(hybridizableFlowerBlockColor, blockHolder.get());
            }
        });
        event.register(new TeaCupBlockColor(), BlockEntityRegistry.WOODEN_TRAY.get());
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

    @SubscribeEvent
    public static void onAddPackFindersEvent(AddPackFindersEvent event) {

        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            for (String packID : List.of(TeaStory.MODID + "_asset_generator")) {
                var packLocationInfo = new PackLocationInfo(
                        packID, Component.translatable(packID), PackSource.BUILT_IN, Optional.of(Planks.knowPack(packID)
                ));
                event.addRepositorySource(consumer -> consumer.accept(
                        Pack.readMetaAndCreate(packLocationInfo,
                                new PathResourcesSupplier(ModList.get().getModFileById(TeaStory.MODID).getFile(), Path.of("asset/")), PackType.CLIENT_RESOURCES,
                                Planks.FEATURE_SELECTION_CONFIG)));
            }

        }
    }


}
