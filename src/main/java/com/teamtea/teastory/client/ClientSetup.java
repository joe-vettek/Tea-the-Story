package com.teamtea.teastory.client;

import com.teamtea.teastory.block.crops.HybridizableFlowerBlock;
import com.teamtea.teastory.client.color.block.HybridizableFlowerBlockColor;
import com.teamtea.teastory.client.color.block.SaucepanBlockColor;
import com.teamtea.teastory.client.color.block.TeaCupBlockColor;
import com.teamtea.teastory.client.color.item.*;
import com.teamtea.teastory.client.render.*;
import com.teamtea.teastory.registry.*;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelIdentifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.model.standalone.SimpleUnbakedStandaloneModel;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import com.teamtea.teastory.fluid.TeaFluidType;

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
    public static void onRegisterColorHandlersEvent_Block(RegisterColorHandlersEvent.BlockTintSources event) {

        var grassColor = BlockTintSources.grassBlock();
        event.register(List.of(grassColor), BlockRegister.GRASS_BLOCK_WITH_HOLE.get(), BlockRegister.WATERMELON_VINE.get());

        var hybridizableFlowerBlockColor = new HybridizableFlowerBlockColor();
        BlockRegister.ModBlocks.getEntries().forEach(blockHolder -> {
            if (blockHolder.get() instanceof HybridizableFlowerBlock) {
                event.register(List.of(hybridizableFlowerBlockColor), blockHolder.get());
            }
        });
        event.register(List.of(new TeaCupBlockColor()), BlockEntityRegister.WOODEN_TRAY.get());
        event.register(List.of(new SaucepanBlockColor()), BlockRegister.saucepan.get());


    }

    @SubscribeEvent
    public static void onRegisterColorHandlersEvent_Item(RegisterColorHandlersEvent.ItemTintSources event) {
        var buckColors = new BucketItemColors();
        FluidRegister.ITEMS.getEntries().forEach(itemRegistryObject -> event.register(buckColors, itemRegistryObject.get()));
        event.register(new CupItemColors(), ItemRegister.PORCELAIN_CUP_DRINK.get());
        event.register(new BottleItemColors(), ItemRegister.BOTTLE_DRINK.get());
        event.register(List.of(new GrassColorSource(0.5f,0.5f)), BlockRegister.GRASS_BLOCK_WITH_HOLE.get().asItem());
        var hybridizableFlowerItemColor = new HybridizableFlowerItemColor();
        BlockRegister.ModBlocks.getEntries().forEach(blockHolder -> {
            if (blockHolder.get() instanceof HybridizableFlowerBlock) {
                event.register(hybridizableFlowerItemColor, blockHolder.get());
            }
        });
    }




}
