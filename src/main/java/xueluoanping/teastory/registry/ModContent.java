package xueluoanping.teastory.registry;

import cloud.lemonslice.teastory.block.crops.TrellisBlock;
import cloud.lemonslice.teastory.block.crops.TrellisWithVineBlock;
import cloud.lemonslice.teastory.block.crops.VineInfoManager;
import cloud.lemonslice.teastory.block.crops.VineType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.blockentity.VineBlockEntity;
import xueluoanping.teastory.item.Citem;
import xueluoanping.teastory.resource.ServerModFilePackResources;
import xueluoanping.teastory.variant.Planks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContent {

    @SubscribeEvent
    public static void creativeModeTabRegister(RegisterEvent event) {
        // TeaStory.logger(event.getRegistryKey(),BuiltInRegistries.BLOCK.entrySet().stream().toList().size());
        // BuiltInRegistries.BLOCK.entrySet().stream().filter(resourceKeyBlockEntry -> resourceKeyBlockEntry.getKey().toString().contains(TeaStory.MODID)).toList()

    }

    @SubscribeEvent
    public static void onRegisterEntityAttribute(EntityAttributeCreationEvent event) {
        // event.put(EntityTypeRegistry.SCARECROW_TYPE.get(), DefaultAttributes.getSupplier(EntityType.ARMOR_STAND));
        event.put(EntityTypeRegistry.SCARECROW_TYPE.get(), LivingEntity.createLivingAttributes().build());
    }

    // refer to BlockSetInternalImpl of Moonlight
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRegisterForEnd(RegisterEvent event) {
        // if(true)return;
        if (event.getRegistryKey().equals(ForgeRegistries.ATTRIBUTES.getRegistryKey())) {
            Map<ResourceLocation, Block> resourceLocationBlockMap = new HashMap<>();
            for (var block : BuiltInRegistries.BLOCK.entrySet()) {
                // TeaStory.logger(block.getKey().location(),block.getKey().location().getPath().endsWith("_planks"));
                if (block.getKey().location().getPath().endsWith("_planks")) {
                    resourceLocationBlockMap.put(block.getKey().location(), block.getValue());
                }
            }

            ForgeRegistry<Block> blockForgeRegistry = (ForgeRegistry<Block>) ForgeRegistries.BLOCKS;
            boolean frozen = blockForgeRegistry.isLocked();
            blockForgeRegistry.unfreeze();
            for (Map.Entry<ResourceLocation, Block> resourceLocationBlockEntry : resourceLocationBlockMap.entrySet()) {
                String name = resourceLocationBlockEntry.getKey().toString().replace(":", ".").replace("_planks", "_trellis");
                var blockB = new TrellisBlock(Block.Properties.copy(BlockRegister.OAK_TRELLIS.get()));
                // event.register(Registries.BLOCK, TeaStory.rl(name), () -> blockB);

                blockForgeRegistry.register(TeaStory.rl(name), blockB);

                ArrayList<TrellisWithVineBlock> blocks = new ArrayList<>(3);
                for (VineType value : VineType.values()) {
                    TrellisWithVineBlock trellisWithVineBlock = new TrellisWithVineBlock(value, Block.Properties.copy(BlockRegister.OAK_TRELLIS.get()).sound(SoundType.CROP).randomTicks());
                    VineInfoManager.registerVineTypeConnections(value, blockB, trellisWithVineBlock);
                    // event.register(Registries.BLOCK, TeaStory.rl(name + "_with_" + value.getName() + "_vine"), () -> trellisWithVineBlock);
                    blockForgeRegistry.register(TeaStory.rl(name + "_with_" + value.getName() + "_vine"), trellisWithVineBlock);
                    blocks.add(trellisWithVineBlock);
                }
                Planks.TrellisBlockMap.put(TeaStory.rl(name), new Planks.PlankHolders(resourceLocationBlockEntry.getValue(), blockB, blocks));
            }
            if (frozen) blockForgeRegistry.freeze();
        }
        if (event.getRegistryKey().equals(ForgeRegistries.ENTITY_TYPES.getRegistryKey())) {

            ForgeRegistry<Item> itemForgeRegistry = (ForgeRegistry<Item>) ForgeRegistries.ITEMS;
            boolean frozen = itemForgeRegistry.isLocked();
            itemForgeRegistry.unfreeze();
            Planks.TrellisBlockMap.forEach((resourceLocation, block) -> {
                itemForgeRegistry.register(resourceLocation, new Citem(block.trellisBlock(), new Item.Properties()));
            });
            if (frozen) itemForgeRegistry.freeze();

            Block[] blocks = BuiltInRegistries.BLOCK.stream()
                    .filter(block -> block instanceof TrellisWithVineBlock)
                    .toArray(Block[]::new);
            BlockEntityRegister.VINE_TYPE = BlockEntityRegister.DRBlockEntities.register("trellis_vine",
                    () -> BlockEntityType.Builder.of(VineBlockEntity::new, blocks).build(null));
        }
        // if (event.getRegistryKey() == Registries.BLOCK) {
        //
        //
        //     // Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation("minecraft","tags/blocks/planks.json"))
        // }
        // if (event.getRegistryKey() == Registries.ITEM) {
        //     Planks.TrellisBlockMap.forEach((resourceLocation, block) -> {
        //         event.register(Registries.ITEM, resourceLocation, () -> new Citem(block.trellisBlock(), new Item.Properties()));
        //     });
        // }
        // if (event.getRegistryKey() == Registries.ENTITY_TYPE) {
        //     Block[] blocks = BuiltInRegistries.BLOCK.stream()
        //             .filter(block -> block instanceof TrellisWithVineBlock)
        //             .toArray(Block[]::new);
        //     BlockEntityRegister.VINE_TYPE = BlockEntityRegister.DRBlockEntities.register("trellis_vine",
        //             () -> BlockEntityType.Builder.of(VineBlockEntity::new, blocks).build(null));
        // }

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
                                    Planks.TrellisBlockMap.forEach((resourceLocation, blockBlockPair) -> {
                                        output.accept(blockBlockPair.trellisBlock());
                                    });
                                })
                                .build());
            });

        // ServerLifecycleHooks.getCurrentServer().getResourceManager().getResourceStack(new ResourceLocation("tags/blocks/acacia_logs.json"));
    }
    // ServerLifecycleHooks.getCurrentServer().getResourceManager()


    @SubscribeEvent
    public static void onAddPackFindersEvent(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            for (String packID : List.of(TeaStory.MODID + "_generator")) {
                event.addRepositorySource(consumer -> consumer.accept(
                        Pack.readMetaAndCreate(packID, Component.translatable(packID), true,
                                id -> new ServerModFilePackResources(packID, "data/"), PackType.SERVER_DATA,
                                Pack.Position.BOTTOM, PackSource.BUILT_IN)));
            }

        }
    }
}
