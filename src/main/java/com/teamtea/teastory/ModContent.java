package com.teamtea.teastory;

import com.teamtea.teastory.block.crops.TrellisBlock;
import com.teamtea.teastory.block.crops.TrellisWithVineBlock;
import com.teamtea.teastory.block.crops.VineInfoManager;
import com.teamtea.teastory.block.crops.VineType;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import com.teamtea.teastory.blockentity.VineEntity;
import com.teamtea.teastory.item.Citem;
import com.teamtea.teastory.item.FluidContainerItem;
import com.teamtea.teastory.resource.ServerPathResourcesSupplier;
import com.teamtea.teastory.variant.Planks;

import java.util.*;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModContent {

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
                                    FluidRegistry.ITEMS.getEntries().forEach((reg) -> {
                                        output.accept(new ItemStack(reg.get()));
                                    });
                                    BlockEntityRegistry.ModItems.getEntries().forEach((reg) -> {
                                        output.accept(new ItemStack(reg.get()));
                                    });
                                    ItemRegister.PORCELAIN_CUP_DRINK.get().fillItemGroup(output);
                                    ItemRegister.BOTTLE_DRINK.get().fillItemGroup(output);
                                    BlockEntityRegistry.IRON_KETTLE_ITEM.get().fillItemGroup(output);
                                    BlockEntityRegistry.PORCELAIN_TEAPOT.get().fillItemGroup(output);

                                    BlockRegister.CHRYSANTHEMUM_ITEM.get().fillItemGroup(output);
                                    BlockRegister.HYACINTH_ITEM.get().fillItemGroup(output);
                                    BlockRegister.ZINNIA_ITEM.get().fillItemGroup(output);
                                    Planks.TrellisBlockMap.forEach((resourceLocation, blockBlockPair) -> {
                                        output.accept(blockBlockPair.trellisBlock());
                                    });
                                })
                                .build());
            });


    }

    @SubscribeEvent
    public static void onRegisterEntityAttribute(EntityAttributeCreationEvent event) {
        // event.put(EntityTypeRegistry.SCARECROW_TYPE.get(), DefaultAttributes.getSupplier(EntityType.ARMOR_STAND));
        event.put(EntityTypeRegistry.SCARECROW_TYPE.get(), LivingEntity.createLivingAttributes().build());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRegisterForWood(RegisterEvent event) {
        // if(true)return;
        if (event.getRegistryKey() == Registries.BLOCK) {
            Map<ResourceLocation, Block> resourceLocationBlockMap = new HashMap<>();
            for (var block : BuiltInRegistries.BLOCK.entrySet()) {
                if (block.getKey().location().getPath().endsWith("_planks")) {
                    resourceLocationBlockMap.put(block.getKey().location(), block.getValue());
                }
            }

            for (Map.Entry<ResourceLocation, Block> resourceLocationBlockEntry : resourceLocationBlockMap.entrySet()) {
                String name = resourceLocationBlockEntry.getKey().toString().replace(":", ".").replace("_planks", "_trellis");
                var blockB = new TrellisBlock(Block.Properties.ofFullCopy(BlockRegister.OAK_TRELLIS.get()));
                event.register(Registries.BLOCK, TeaStory.rl(name), () -> blockB);

                ArrayList<TrellisWithVineBlock> blocks = new ArrayList<>(3);
                for (VineType value : VineType.values()) {
                    TrellisWithVineBlock trellisWithVineBlock = new TrellisWithVineBlock(value, Block.Properties.ofFullCopy(BlockRegister.OAK_TRELLIS.get()).sound(SoundType.CROP).randomTicks());
                    VineInfoManager.registerVineTypeConnections(value, blockB, trellisWithVineBlock);
                    event.register(Registries.BLOCK, TeaStory.rl(name + "_with_" + value.getName() + "_vine"), () -> trellisWithVineBlock);
                    blocks.add(trellisWithVineBlock);
                }

                Planks.TrellisBlockMap.put(TeaStory.rl(name), new Planks.PlankHolders(resourceLocationBlockEntry.getValue(), blockB, blocks));

            }

            // Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation("minecraft","tags/blocks/planks.json"))
        }
        if (event.getRegistryKey() == Registries.ITEM) {
            Planks.TrellisBlockMap.forEach((resourceLocation, block) -> {
                event.register(Registries.ITEM, resourceLocation, () -> new Citem(block.trellisBlock(), new Item.Properties()));
            });
        }
        if (event.getRegistryKey() == Registries.ENTITY_TYPE) {
            Block[] blocks = BuiltInRegistries.BLOCK.stream()
                    .filter(block -> block instanceof TrellisWithVineBlock)
                    .toArray(Block[]::new);
            BlockEntityRegistry.VINE_TYPE = BlockEntityRegistry.DRBlockEntities.register("trellis_vine",
                    () -> BlockEntityType.Builder.of(VineEntity::new, blocks).build(null));
        }
        // ServerLifecycleHooks.getCurrentServer().getResourceManager().getResourceStack(new ResourceLocation("tags/blocks/acacia_logs.json"));
    }
    // ServerLifecycleHooks.getCurrentServer().getResourceManager()


    // SimpleFluidContent
    @SubscribeEvent
    public static void onRegisterCapabilitiesEvent(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.FluidHandler.ITEM, (s, a) -> ((FluidContainerItem) s.getItem()).transferToFluidHandler(s),
                ItemRegister.PORCELAIN_CUP_DRINK.value(),
                ItemRegister.PORCELAIN_CUP.value(),
                ItemRegister.BOTTLE.value(),
                ItemRegister.BOTTLE_DRINK.value(),
                BlockEntityRegistry.PORCELAIN_TEAPOT.value(),
                BlockEntityRegistry.IRON_KETTLE_ITEM.value());

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegistry.WOODEN_BARREL_TYPE.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : blockEntity.getFluidTank());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.BAMBOO_TRAY_TYPE.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : blockEntity.getContainerInventory());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.DRINK_MAKER_TYPE.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : (context == Direction.DOWN ? blockEntity.getResiduesInventory() : blockEntity.getIngredientsInventory()));
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegistry.DRINK_MAKER_TYPE.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : blockEntity.getFluidHandler());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.STONE_MILL_TYPE.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : (context == Direction.DOWN ? blockEntity.getOutputInventory() : blockEntity.getInputInventory()));
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegistry.STONE_MILL_TYPE.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : blockEntity.getFluidTank());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.STONE_ROLLER_TYPE.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : (context == Direction.DOWN ? blockEntity.getOutputInventory() : blockEntity.getInputInventory()));

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.STOVE_TYPE.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : (context == Direction.DOWN ? blockEntity.getAshInventory() : blockEntity.getFuelInventory()));

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegistry.IRON_KETTLE_TYPE.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : blockEntity.getFluidTank());
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegistry.TEAPOT_TYPE.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : blockEntity.getFluidTank());
    }

    @SubscribeEvent
    public static void onAddPackFindersEvent(AddPackFindersEvent event) {

        if (event.getPackType() == PackType.SERVER_DATA) {

            for (String packID : List.of(TeaStory.MODID + "_generator")) {
                var packLocationInfo = new PackLocationInfo(
                        packID, Component.translatable(packID), PackSource.BUILT_IN, Optional.of(Planks.knowPack(packID)
                ));
                event.addRepositorySource(consumer -> consumer.accept(
                        Pack.readMetaAndCreate(packLocationInfo,
                                new ServerPathResourcesSupplier("data/"), PackType.SERVER_DATA,
                                Planks.FEATURE_SELECTION_CONFIG)));
            }

        }
    }
}
