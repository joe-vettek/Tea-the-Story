package com.teamtea.teastory.client;

import com.teamtea.teastory.block.crops.HybridizableFlowerBlock;
import com.teamtea.teastory.*;
import com.teamtea.teastory.block.crops.TrellisWithVineBlock;
import com.teamtea.teastory.block.crops.WildCropBlock;
import com.teamtea.teastory.client.color.block.GrassBlockColor;
import com.teamtea.teastory.client.color.block.HybridizableFlowerBlockColor;
import com.teamtea.teastory.client.color.block.SaucepanBlockColor;
import com.teamtea.teastory.client.color.block.TeaCupBlockColor;
import com.teamtea.teastory.client.color.item.*;
import com.teamtea.teastory.client.render.*;
import com.teamtea.teastory.registry.*;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelIdentifier;
import net.minecraft.client.resources.model.MultiPartBakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import com.teamtea.teastory.fluid.TeaFluidType;
import com.teamtea.teastory.resource.PathResourcesSupplier;
import com.teamtea.teastory.variant.Planks;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.*;

@EventBusSubscriber( value = Dist.CLIENT)
public class ClientSetup {


    @SubscribeEvent
    public static void onRegisterClientExtensionsEvent(RegisterClientExtensionsEvent event) {

        FluidRegister.FLUIDS.getEntries().forEach(holder -> {

            if (holder.get().getFluidType() instanceof TeaFluidType teaFluidType
                    && holder.get() instanceof BaseFlowingFluid.Source baseFlowingFluid)
                event.registerFluidType(TeaFluidType.getIClientFluidTypeExtensions(teaFluidType), baseFlowingFluid.getFluidType());
        });

    }

    //    注意static是单次，比如启动类，没有比如右击事件
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegister.SEAT_TYPE.get(), pContext -> new EntityRenderer<Entity>(pContext) {
            @Override
            public Identifier getTextureLocation(Entity pEntity) {
                return null;
            }
        });
        event.registerEntityRenderer(EntityRegister.SCARECROW_TYPE.get(), pContext -> new EntityRenderer<Entity>(pContext) {
            @Override
            public Identifier getTextureLocation(Entity pEntity) {
                return null;
            }
        });
        event.registerBlockEntityRenderer(BlockEntityRegister.WOODEN_BARREL_TYPE.get(), WoodenBarrelTESR::new);

        event.registerBlockEntityRenderer(BlockEntityRegister.stone_campfire_TYPE.get(), StoneCampfireRenderer::new);
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
        Map<ModelIdentifier, BakedModel> modelRegistry = event.getModels();

        for (ModelIdentifier grapesRe : WarpBakeModel.grapesRes) {
            WarpBakeModel.grapes.add(modelRegistry.get(grapesRe));
        }

        // MultiPartBakedModel OAK_TRELLIS_MODEL = (MultiPartBakedModel) event.getModels().get(BlockModelShaper.stateToModelLocation(BlockRegister.OAK_TRELLIS.get().defaultBlockState()));
        ModelIdentifier OAK_TRELLIS_ITEM_LOCATION = new ModelIdentifier(BlockRegister.OAK_TRELLIS.getId(), "inventory");
        BakedModel OAK_TRELLIS_ITEM_MODEL = event.getModels().get(OAK_TRELLIS_ITEM_LOCATION);

        // TeaStory.logger(OAK_TRELLIS_MODEL);
        TeaStory.logger("Minecraft loading all the models status with " + modelRegistry.entrySet().size());
        TeaStory.logger("Minecraft loading all models with size " + new HashSet<>(modelRegistry.values()).size());

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
        event.register(new TeaCupBlockColor(), BlockEntityRegister.WOODEN_TRAY.get());
        event.register(new SaucepanBlockColor(), BlockRegister.saucepan.get());


    }

    @SubscribeEvent
    public static void onRegisterColorHandlersEvent_Item(RegisterColorHandlersEvent.Item event) {
        var buckColors = new BucketItemColors();
        FluidRegister.ITEMS.getEntries().forEach(itemRegistryObject -> event.register(buckColors, itemRegistryObject.get()));
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
