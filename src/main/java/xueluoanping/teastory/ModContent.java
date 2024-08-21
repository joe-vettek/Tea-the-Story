package xueluoanping.teastory;

import cloud.lemonslice.teastory.block.crops.TrellisBlock;
import cloud.lemonslice.teastory.block.crops.TrellisWithVineBlock;
import cloud.lemonslice.teastory.block.crops.VineInfoManager;
import cloud.lemonslice.teastory.block.crops.VineType;
import cloud.lemonslice.teastory.blockentity.WoodenBarrelTileEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import oshi.util.tuples.Pair;
import xueluoanping.teastory.blockentity.VineEntity;
import xueluoanping.teastory.item.Citem;
import xueluoanping.teastory.resource.ClientModFilePackResources;
import xueluoanping.teastory.resource.ServerModFilePackResources;
import xueluoanping.teastory.variant.Planks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContent {

    @SubscribeEvent
    public static void creativeModeTabRegister(RegisterEvent event) {
        // TeaStory.logger(event.getRegistryKey(),BuiltInRegistries.BLOCK.entrySet().stream().toList().size());
        // BuiltInRegistries.BLOCK.entrySet().stream().filter(resourceKeyBlockEntry -> resourceKeyBlockEntry.getKey().toString().contains(TeaStory.MODID)).toList()
        if (event.getRegistryKey() == Registries.CREATIVE_MODE_TAB)
            event.register(Registries.CREATIVE_MODE_TAB, helper -> {
                helper.register(new ResourceLocation(TeaStory.MODID, TeaStory.MODID),
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
                                    TileEntityTypeRegistry.ModItems.getEntries().forEach((reg) -> {
                                        output.accept(new ItemStack(reg.get()));
                                    });
                                    ItemRegister.PORCELAIN_CUP_DRINK.get().fillItemGroup(output);
                                    ItemRegister.BOTTLE_DRINK.get().fillItemGroup(output);
                                    TileEntityTypeRegistry.IRON_KETTLE_ITEM.get().fillItemGroup(output);
                                    TileEntityTypeRegistry.PORCELAIN_TEAPOT.get().fillItemGroup(output);

                                    BlockRegister.CHRYSANTHEMUM_ITEM.get().fillItemGroup(output);
                                    BlockRegister.HYACINTH_ITEM.get().fillItemGroup(output);
                                    BlockRegister.ZINNIA_ITEM.get().fillItemGroup(output);
                                    Planks.resourceLocationBlockMap.forEach((resourceLocation, blockBlockPair) -> {
                                        output.accept(blockBlockPair.getB());
                                    });
                                })
                                .build());
            });


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
                var blockB = new TrellisBlock(Block.Properties.copy(BlockRegister.OAK_TRELLIS.get()));
                event.register(Registries.BLOCK, TeaStory.rl(name), () -> blockB);
                Planks.resourceLocationBlockMap.put(TeaStory.rl(name), new Pair<>(resourceLocationBlockEntry.getValue(), blockB));

                for (VineType value : VineType.values()) {
                    TrellisWithVineBlock trellisWithVineBlock = new TrellisWithVineBlock(value, Block.Properties.copy(BlockRegister.OAK_TRELLIS.get()).sound(SoundType.CROP).randomTicks());
                    VineInfoManager.registerVineTypeConnections(value, blockB, trellisWithVineBlock);
                    event.register(Registries.BLOCK, TeaStory.rl(name+"_with_"+value.getName()+"_vine"), () -> trellisWithVineBlock);
                }

            }

            // Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation("minecraft","tags/blocks/planks.json"))
        }
        if (event.getRegistryKey() == Registries.ITEM) {
            Planks.resourceLocationBlockMap.forEach((resourceLocation, block) -> {
                event.register(Registries.ITEM, resourceLocation, () -> new Citem(block.getB(), new Item.Properties()));
            });
        }
        if (event.getRegistryKey() == Registries.ENTITY_TYPE) {
           Block[] blocks= BuiltInRegistries.BLOCK.stream()
                   .filter(block -> block instanceof TrellisWithVineBlock)
                   .toArray(Block[]::new);
            TileEntityTypeRegistry.VINE_TYPE= TileEntityTypeRegistry.DRBlockEntities.register("trellis_vine",
                    () -> BlockEntityType.Builder.of(VineEntity::new, blocks).build(null));
        }
        // ServerLifecycleHooks.getCurrentServer().getResourceManager().getResourceStack(new ResourceLocation("tags/blocks/acacia_logs.json"));
    }
    // ServerLifecycleHooks.getCurrentServer().getResourceManager()


    @SubscribeEvent
    public static void onAddPackFindersEvent(AddPackFindersEvent event) {

        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            for (String packID : List.of(TeaStory.MODID + "_asset_generator")) {
                event.addRepositorySource(consumer -> consumer.accept(
                        Pack.readMetaAndCreate(packID, Component.translatable(packID), true,
                                id -> new ClientModFilePackResources(packID, ModList.get().getModFileById(TeaStory.MODID).getFile(), "asset/"), PackType.CLIENT_RESOURCES,
                                Pack.Position.BOTTOM, PackSource.BUILT_IN)));
            }

        }
    }
}
