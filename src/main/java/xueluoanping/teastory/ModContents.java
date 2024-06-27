package xueluoanping.teastory;


import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.teastory.block.crops.*;

import java.util.List;


// import static xueluoanping.fluiddrawerslegacy.FluidDrawersLegacyMod.CREATIVE_TAB;

// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContents {
    public static final DeferredRegister<Item> ModItems = DeferredRegister.create(ForgeRegistries.ITEMS, TeaStory.MOD_ID);
    public static final DeferredRegister<Block> ModBlocks = DeferredRegister.create(ForgeRegistries.BLOCKS, TeaStory.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> DRBlockEntities = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TeaStory.MOD_ID);


    private static CreativeModeTab MAIN;

    @SubscribeEvent
    public static void creativeModeTabRegister(RegisterEvent event) {
        event.register(Registries.CREATIVE_MODE_TAB, helper -> {
            helper.register(new ResourceLocation(TeaStory.MOD_ID, TeaStory.MOD_ID),
                    CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.getEntries().stream().findFirst().get().get()))
                            .title(Component.translatable("itemGroup." + TeaStory.MOD_ID))
                            .displayItems((params, output) -> {
                                ModItems.getEntries().forEach((reg) -> {
                                    output.accept(new ItemStack(reg.get()));
                                });
                                AllItems.ModItems.getEntries().forEach((reg) -> {
                                    output.accept(new ItemStack(reg.get()));
                                });
                            })
                            .build());
        });
    }

    public static RegistryObject<Block> cobblestoneAqueduct = ModBlocks.register("cobblestone_aqueduct", () -> new AqueductBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)
            .sound(SoundType.STONE).strength(1.5F)
            .noOcclusion()));
    public static RegistryObject<Block> mossyCobblestoneAqueduct = ModBlocks.register("mossy_cobblestone_aqueduct", () -> new AqueductConnectorBlock(BlockBehaviour.Properties.copy(cobblestoneAqueduct.get())));
    public static RegistryObject<Block> paddyField = ModBlocks.register("paddy_field", PaddyFieldBlock::new);



    public static RegistryObject<Block> RiceSeedlingBlock = ModBlocks.register("rice_seedling", () -> new RiceSeedlingBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static RegistryObject<Block> ricePlant = ModBlocks.register("rice_plant", () -> new RicePlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));

    public static RegistryObject<Item> riceGrains = ModItems.register("rice_grains", () -> new BlockItem(RiceSeedlingBlock.get(), new Item.Properties()));
    public static RegistryObject<Item> riceSeedlings = ModItems.register("rice_seedlings", () -> new BlockItem(ricePlant.get(), new Item.Properties()));
    static {


        var items = List.of(cobblestoneAqueduct, mossyCobblestoneAqueduct, RiceSeedlingBlock);
        // for (RegistryObject<Block> item : items) {
        //     DREntityBlockItems.register(item.getId().getPath(), () -> new BlockItem(item.get(), new Item.Properties()));
        // }
        ModItems.register("cobblestone_aqueduct", () -> new BlockItem(cobblestoneAqueduct.get(), new Item.Properties()));
        ModItems.register("mossy_cobblestone_aqueduct", () -> new BlockItem(mossyCobblestoneAqueduct.get(), new Item.Properties()));
        ModItems.register("paddy_field", () -> new BlockItem(paddyField.get(), new Item.Properties()));
    }

    // RegistryObject<Item> itemBlock = DREntityBlockItems.register(path, () -> new ItemFluidDrawer(fluiddrawer.get(), new Item.Properties()));
    // RegistryObject<BlockEntityType<BlockEntityFluidDrawer>> tankTileEntityType = DRBlockEntities.register(path,
    //         () -> BlockEntityType.Builder.of((pos, state) -> new BlockEntityFluidDrawer(count, pos, state), fluiddrawer.get()).build(null));


    private static boolean predFalse(BlockState p_235436_0_, BlockGetter p_235436_1_, BlockPos p_235436_2_) {
        return false;
    }

}

