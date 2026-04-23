package com.teamtea.teastory.registry;

import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.recipe.drink.DrinkEffect;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.gui.CreativeTabsScreenPage;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import com.teamtea.teastory.item.FluidContainerItem;

import java.util.*;

@EventBusSubscriber
public class ModContent {

    @SubscribeEvent
    public static void onNewRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(TeaStoryRegistries.DRINK_EFFECT, DrinkEffect.CODEC, DrinkEffect.CODEC);
    }


    /**
     * 我们需要的是改变一下{@link CreativeModeTab#getDisplayItems()}的输出。
     * 做法如下，监听{@link ScreenEvent#getScreen()}获取到点击事件和当前的Screen。
     * 我们需要{@link CreativeModeInventoryScreen#getCurrentPage()}，随后获取当前可见Tab是不是我们的Tab,
     * 即检查{@link CreativeTabsScreenPage#getVisibleTabs()}。
     * 拿到状态之后切换输出即可。
     * <p>
     */
    @SubscribeEvent
    public static void creativeModeTabRegister(RegisterEvent event) {
        // TeaStory.logger(event.getRegistryKey(),BuiltInRegistries.BLOCK.entrySet().stream().toList().size());
        // BuiltInRegistries.BLOCK.entrySet().stream().filter(resourceKeyBlockEntry -> resourceKeyBlockEntry.getKey().toString().contains(TeaStory.MODID)).toList()
        if (event.getRegistryKey() == Registries.CREATIVE_MODE_TAB)
            event.register(Registries.CREATIVE_MODE_TAB, helper -> {
                helper.register(TeaStory.rl(TeaStory.MODID),
                        CreativeModeTab.builder().icon(() -> new ItemStack(ItemRegister.TEA_LEAVES.get()))
                                .title(Component.translatable("itemGroup." + TeaStory.MODID + ".core"))
                                .displayItems((params, output) -> {
                                    BlockRegister.ModItems.getEntries().forEach((reg) -> {
                                        output.accept(new ItemStack(reg.get()));
                                    });
                                    ItemRegister.ModItems.getEntries().forEach((reg) -> {
                                        output.accept(new ItemStack(reg.get()));
                                    });
                                    FluidRegister.ITEMS.getEntries().forEach((reg) -> {
                                        output.accept(new ItemStack(reg.get()));
                                    });
                                    BlockEntityRegister.ModItems.getEntries().forEach((reg) -> {
                                        output.accept(new ItemStack(reg.get()));
                                    });
                                    ItemRegister.PORCELAIN_CUP_DRINK.get().fillItemGroup(output);
                                    ItemRegister.BOTTLE_DRINK.get().fillItemGroup(output);
                                    BlockEntityRegister.IRON_KETTLE_ITEM.get().fillItemGroup(output);
                                    BlockEntityRegister.PORCELAIN_TEAPOT.get().fillItemGroup(output);

                                    BlockRegister.CHRYSANTHEMUM_ITEM.get().fillItemGroup(output);
                                    BlockRegister.HYACINTH_ITEM.get().fillItemGroup(output);
                                    BlockRegister.ZINNIA_ITEM.get().fillItemGroup(output);
                                })
                                .build());
            });


    }

    @SubscribeEvent
    public static void onRegisterEntityAttribute(EntityAttributeCreationEvent event) {
        // event.put(EntityTypeRegistry.SCARECROW_TYPE.get(), DefaultAttributes.getSupplier(EntityType.ARMOR_STAND));
        event.put(EntityRegister.SCARECROW_TYPE.get(), LivingEntity.createLivingAttributes().build());
    }

    @SubscribeEvent
    public static void onRegisterEntityAttribute(BlockEntityTypeAddBlocksEvent event) {
       event.modify(BlockEntityType.CAMPFIRE);
    }


    @SubscribeEvent
    public static void onRegisterCapabilitiesEvent(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.FluidHandler.ITEM, (s, a) -> ((FluidContainerItem) s.getItem()).transferToFluidHandler(s),
                ItemRegister.PORCELAIN_CUP_DRINK.value(),
                ItemRegister.PORCELAIN_CUP.value(),
                ItemRegister.BOTTLE.value(),
                ItemRegister.BOTTLE_DRINK.value(),
                BlockEntityRegister.PORCELAIN_TEAPOT.value(),
                BlockEntityRegister.IRON_KETTLE_ITEM.value());

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegister.WOODEN_BARREL_TYPE.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : blockEntity.getFluidTank());


        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegister.IRON_KETTLE_TYPE.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : blockEntity.getFluidTank());
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegister.TEAPOT_TYPE.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : blockEntity.getFluidTank());
    }


}
