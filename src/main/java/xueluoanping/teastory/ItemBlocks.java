package xueluoanping.teastory;


import cloud.lemonslice.teastory.block.crops.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;


// import static xueluoanping.fluiddrawerslegacy.FluidDrawersLegacyMod.CREATIVE_TAB;

// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)

public class ItemBlocks {
    public static final DeferredRegister<Item> ModItems = DeferredRegister.create(ForgeRegistries.ITEMS, TeaStory.MODID);
    public static final DeferredRegister<Block> ModBlocks = DeferredRegister.create(ForgeRegistries.BLOCKS, TeaStory.MODID);


    public static RegistryObject<Block> cobblestoneAqueduct = ModBlocks.register("cobblestone_aqueduct", () -> new AqueductBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)
            .sound(SoundType.STONE).strength(1.5F)
            .noOcclusion()));
    public static RegistryObject<Block> mossyCobblestoneAqueduct = ModBlocks.register("mossy_cobblestone_aqueduct", () -> new AqueductConnectorBlock(BlockBehaviour.Properties.copy(cobblestoneAqueduct.get())));
    public static RegistryObject<Block> paddyField = ModBlocks.register("paddy_field", PaddyFieldBlock::new);


    public static RegistryObject<Block> RiceSeedlingBlock = ModBlocks.register("rice_seedling", () -> new RiceSeedlingBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static RegistryObject<Block> ricePlant = ModBlocks.register("rice_plant", () -> new RicePlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));

    public static RegistryObject<Item> riceGrains = ModItems.register("rice_grains", () -> new BlockItem(RiceSeedlingBlock.get(), new Item.Properties()));
    public static RegistryObject<Item> riceSeedlings = ModItems.register("rice_seedlings", () -> new BlockItem(ricePlant.get(), new Item.Properties()));


    public static RegistryObject<Block> tea_plant = ModBlocks.register("tea_plant", () -> new TeaPlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static RegistryObject<Block> wild_tea_plant = ModBlocks.register("wild_tea_plant", () -> new WildTeaPlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));

    // public static RegistryObject<Item> tea_plant_item = ModItems.register("tea_plant", () -> new BlockItem(tea_plant.get(), new Item.Properties()));
    public static RegistryObject<Item> wild_tea_plant_item = ModItems.register("wild_tea_plant", () -> new BlockItem(wild_tea_plant.get(), new Item.Properties()));
    public static RegistryObject<Item> TEA_SEEDS = ModItems.register("tea_seeds", () -> new BlockItem(tea_plant.get(), new Item.Properties()));

    public static RegistryObject<Block> WATERMELON_VINE = ModBlocks.register("watermelon_vine", () -> new MelonVineBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY), Blocks.MELON));


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
    public static RegistryObject<Block> GRASS_BLOCK_WITH_HOLE = ModBlocks.register("grass_block_with_hole", () -> new GrassBlock(Block.Properties.copy(Blocks.GRASS).strength(0.6F)));

    public static RegistryObject<Item> GRASS_BLOCK_WITH_HOLE_ITEM = ModItems.register("grass_block_with_hole", () -> new BlockItem(GRASS_BLOCK_WITH_HOLE.get(), new Item.Properties()));


    private static boolean predFalse(BlockState p_235436_0_, BlockGetter p_235436_1_, BlockPos p_235436_2_) {
        return false;
    }

}

