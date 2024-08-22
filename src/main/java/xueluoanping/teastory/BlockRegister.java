package xueluoanping.teastory;


import cloud.lemonslice.teastory.block.BowlBlock;
import cloud.lemonslice.teastory.block.craft.*;
import cloud.lemonslice.teastory.block.crops.*;
import cloud.lemonslice.teastory.block.decorations.*;
import cloud.lemonslice.teastory.item.HybridizableFlowerBlockItem;
import cloud.lemonslice.teastory.item.VineSeedsItem;
import cloud.lemonslice.teastory.item.food.NormalFoods;
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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;


// import static xueluoanping.fluiddrawerslegacy.FluidDrawersLegacyMod.CREATIVE_TAB;

// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)

public class BlockRegister {
    public static final DeferredRegister<Item> ModItems = DeferredRegister.create(ForgeRegistries.ITEMS, TeaStory.MODID);
    public static final DeferredRegister<Block> ModBlocks = DeferredRegister.create(ForgeRegistries.BLOCKS, TeaStory.MODID);


    public static RegistryObject<Block> cobblestoneAqueduct = ModBlocks.register("cobblestone_aqueduct", () -> new AqueductBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)
            .sound(SoundType.STONE).strength(1.5F)
            .noOcclusion()));
    public static RegistryObject<Block> dirtAqueduct = ModBlocks.register("dirt_aqueduct", () -> new LowAqueductBlock(BlockBehaviour.Properties.copy(Blocks.DIRT_PATH)
            .sound(SoundType.GRASS).strength(0.8F)
            .noOcclusion()));

    public static RegistryObject<Block> mossyCobblestoneAqueduct = ModBlocks.register("mossy_cobblestone_aqueduct", () -> new AqueductConnectorBlock(BlockBehaviour.Properties.copy(cobblestoneAqueduct.get())));
    public static RegistryObject<Block> paddyField = ModBlocks.register("paddy_field", PaddyFieldBlock::new);


    public static RegistryObject<Block> RiceSeedlingBlock = ModBlocks.register("rice_seedling", () -> new RiceSeedlingBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static RegistryObject<Block> ricePlant = ModBlocks.register("rice_plant", () -> new RicePlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));

    public static RegistryObject<Item> RICE_GRAINS = ModItems.register("rice_grains", () -> new BlockItem(RiceSeedlingBlock.get(), new Item.Properties()));
    public static RegistryObject<Item> riceSeedlings = ModItems.register("rice_seedlings", () -> new BlockItem(ricePlant.get(), new Item.Properties()));

    public static RegistryObject<Block> tea_plant = ModBlocks.register("tea_plant", () -> new TeaPlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static RegistryObject<Block> wild_tea_plant = ModBlocks.register("wild_tea_plant", () -> new WildTeaPlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));

    // public static RegistryObject<Item> tea_plant_item = ModItems.register("tea_plant", () -> new BlockItem(tea_plant.get(), new Item.Properties()));
    public static RegistryObject<Item> wild_tea_plant_item = ModItems.register("wild_tea_plant", () -> new BlockItem(wild_tea_plant.get(), new Item.Properties()));
    public static RegistryObject<Item> TEA_SEEDS = ModItems.register("tea_seeds", () -> new BlockItem(tea_plant.get(), new Item.Properties()));

    public static RegistryObject<Block> WATERMELON_VINE = ModBlocks.register("watermelon_vine", () -> new MelonVineBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY), Blocks.MELON));

    public static RegistryObject<Block> WILD_GRAPE = ModBlocks.register("wild_grape", () -> new WildGrapeBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static RegistryObject<Item> WILD_GRAPE_ITEM = ModItems.register("wild_grape", () -> new BlockItem(WILD_GRAPE.get(), new Item.Properties()));

    public static RegistryObject<Block> CHILI_PLANT = ModBlocks.register("chili_plant", () -> new ChiliBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static RegistryObject<Item> CHILI = ModItems.register("chili", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> CHILI_SEEDS = ModItems.register("chili_seeds", () -> new BlockItem(CHILI_PLANT.get(), new Item.Properties()));

    public static RegistryObject<Block> CHINESE_CABBAGE_PLANT = ModBlocks.register("chinese_cabbage_plant", () -> new ChineseCabbageBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static RegistryObject<Item> CHINESE_CABBAGE = ModItems.register("chinese_cabbage", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> CHINESE_CABBAGE_SEEDS = ModItems.register("chinese_cabbage_seeds", () -> new BlockItem(CHINESE_CABBAGE_PLANT.get(), new Item.Properties()));

    public static RegistryObject<TrellisBlock> OAK_TRELLIS = ModBlocks.register("oak_trellis", () -> new TrellisBlock(Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.WOOD)));
    public static RegistryObject<Item> OAK_TRELLIS_ITEM = ModItems.register("oak_trellis", () -> new Item(new Item.Properties()));
    // public static RegistryObject<TrellisBlock> BIRCH_TRELLIS = ModBlocks.register("birch_trellis", () -> new TrellisBlock(Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.WOOD)));
    // public static RegistryObject<Item> BIRCH_TRELLIS_ITEM = ModItems.register("birch_trellis", () -> new BlockItem(BIRCH_TRELLIS.get(), new Item.Properties()));
    // public static RegistryObject<TrellisBlock> JUNGLE_TRELLIS = ModBlocks.register("jungle_trellis", () -> new TrellisBlock(Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.WOOD)));
    // public static RegistryObject<Item> JUNGLE_TRELLIS_ITEM = ModItems.register("jungle_trellis", () -> new BlockItem(JUNGLE_TRELLIS.get(), new Item.Properties()));
    // public static RegistryObject<TrellisBlock> SPRUCE_TRELLIS = ModBlocks.register("spruce_trellis", () -> new TrellisBlock(Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.WOOD)));
    // public static RegistryObject<Item> SPRUCE_TRELLIS_ITEM = ModItems.register("spruce_trellis", () -> new BlockItem(SPRUCE_TRELLIS.get(), new Item.Properties()));
    // public static RegistryObject<TrellisBlock> DARK_OAK_TRELLIS = ModBlocks.register("dark_oak_trellis", () -> new TrellisBlock(Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.WOOD)));
    // public static RegistryObject<Item> DARK_OAK_TRELLIS_ITEM = ModItems.register("dark_oak_trellis", () -> new BlockItem(DARK_OAK_TRELLIS.get(), new Item.Properties()));
    // public static RegistryObject<TrellisBlock> ACACIA_TRELLIS = ModBlocks.register("acacia_trellis", () -> new TrellisBlock(Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.WOOD)));
    // public static RegistryObject<Item> ACACIA_TRELLIS_ITEM = ModItems.register("acacia_trellis", () -> new BlockItem(ACACIA_TRELLIS.get(), new Item.Properties()));

    // public static RegistryObject<TrellisWithVineBlock> OAK_TRELLIS_GRAPE = ModBlocks.register("oak_trellis_grape", () -> new TrellisWithVineBlock(VineType.GRAPE, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    // public static RegistryObject<TrellisWithVineBlock> BIRCH_TRELLIS_GRAPE = ModBlocks.register("birch_trellis_grape", () -> new TrellisWithVineBlock(VineType.GRAPE, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    // public static RegistryObject<TrellisWithVineBlock> JUNGLE_TRELLIS_GRAPE = ModBlocks.register("jungle_trellis_grape", () -> new TrellisWithVineBlock(VineType.GRAPE, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    // public static RegistryObject<TrellisWithVineBlock> SPRUCE_TRELLIS_GRAPE = ModBlocks.register("spruce_trellis_grape", () -> new TrellisWithVineBlock(VineType.GRAPE, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    // public static RegistryObject<TrellisWithVineBlock> DARK_OAK_TRELLIS_GRAPE = ModBlocks.register("dark_oak_trellis_grape", () -> new TrellisWithVineBlock(VineType.GRAPE, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    // public static RegistryObject<TrellisWithVineBlock> ACACIA_TRELLIS_GRAPE = ModBlocks.register("acacia_trellis_grape", () -> new TrellisWithVineBlock(VineType.GRAPE, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    //
    // public static RegistryObject<TrellisWithVineBlock> OAK_TRELLIS_CUCUMBER = ModBlocks.register("oak_trellis_cucumber", () -> new TrellisWithVineBlock(VineType.CUCUMBER, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    // public static RegistryObject<TrellisWithVineBlock> BIRCH_TRELLIS_CUCUMBER = ModBlocks.register("birch_trellis_cucumber", () -> new TrellisWithVineBlock(VineType.CUCUMBER, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    // public static RegistryObject<TrellisWithVineBlock> JUNGLE_TRELLIS_CUCUMBER = ModBlocks.register("jungle_trellis_cucumber", () -> new TrellisWithVineBlock(VineType.CUCUMBER, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    // public static RegistryObject<TrellisWithVineBlock> SPRUCE_TRELLIS_CUCUMBER = ModBlocks.register("spruce_trellis_cucumber", () -> new TrellisWithVineBlock(VineType.CUCUMBER, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    // public static RegistryObject<TrellisWithVineBlock> DARK_OAK_TRELLIS_CUCUMBER = ModBlocks.register("dark_oak_trellis_cucumber", () -> new TrellisWithVineBlock(VineType.CUCUMBER, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    // public static RegistryObject<TrellisWithVineBlock> ACACIA_TRELLIS_CUCUMBER = ModBlocks.register("acacia_trellis_cucumber", () -> new TrellisWithVineBlock(VineType.CUCUMBER, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    //
    // public static RegistryObject<TrellisWithVineBlock> OAK_TRELLIS_BITTER_GOURD = ModBlocks.register("oak_trellis_bitter_gourd", () -> new TrellisWithVineBlock(VineType.BITTER_GOURD, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    // public static RegistryObject<TrellisWithVineBlock> BIRCH_TRELLIS_BITTER_GOURD = ModBlocks.register("birch_trellis_bitter_gourd", () -> new TrellisWithVineBlock(VineType.BITTER_GOURD, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    // public static RegistryObject<TrellisWithVineBlock> JUNGLE_TRELLIS_BITTER_GOURD = ModBlocks.register("jungle_trellis_bitter_gourd", () -> new TrellisWithVineBlock(VineType.BITTER_GOURD, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    // public static RegistryObject<TrellisWithVineBlock> SPRUCE_TRELLIS_BITTER_GOURD = ModBlocks.register("spruce_trellis_bitter_gourd", () -> new TrellisWithVineBlock(VineType.BITTER_GOURD, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    // public static RegistryObject<TrellisWithVineBlock> DARK_OAK_TRELLIS_BITTER_GOURD = ModBlocks.register("dark_oak_trellis_bitter_gourd", () -> new TrellisWithVineBlock(VineType.BITTER_GOURD, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));
    // public static RegistryObject<TrellisWithVineBlock> ACACIA_TRELLIS_BITTER_GOURD = ModBlocks.register("acacia_trellis_bitter_gourd", () -> new TrellisWithVineBlock(VineType.BITTER_GOURD, Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.CROP).randomTicks()));

    public static RegistryObject<Block> GRAPE = ModBlocks.register("grape_plant", () -> new StemFruitBlock(VineType.GRAPE, Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).instabreak().randomTicks().noCollission().pushReaction(PushReaction.DESTROY).sound(SoundType.CROP)));
    public static RegistryObject<Block> CUCUMBER = ModBlocks.register("cucumber_plant", () -> new StemFruitBlock(VineType.CUCUMBER, Block.Properties.of().mapColor(MapColor.GRASS).instabreak().randomTicks().noCollission().pushReaction(PushReaction.DESTROY).sound(SoundType.CROP)));
    public static RegistryObject<Block> BITTER_GOURD = ModBlocks.register("bitter_gourd_plant", () -> new StemFruitBlock(VineType.BITTER_GOURD, Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).instabreak().randomTicks().noCollission().pushReaction(PushReaction.DESTROY).sound(SoundType.CROP)));

    public static RegistryObject<Item> GRAPES = ModItems.register("grapes", () -> new VineSeedsItem(VineType.GRAPE, new Item.Properties().food(NormalFoods.GRAPE)));
    public static RegistryObject<Item> CUCUMBERS = ModItems.register("cucumber", () -> new VineSeedsItem(VineType.CUCUMBER, new Item.Properties().food(NormalFoods.CUCUMBER)));
    public static RegistryObject<Item> BITTER_GOURDS = ModItems.register("bitter_gourd", () -> new VineSeedsItem(VineType.BITTER_GOURD, new Item.Properties().food(NormalFoods.BITTER_GOURD)));


    static {

        var items = List.of(cobblestoneAqueduct, mossyCobblestoneAqueduct, RiceSeedlingBlock);
        // for (RegistryObject<Block> item : items) {
        //     DREntityBlockItems.register(item.getId().getPath(), () -> new BlockItem(item.get(), new Item.Properties()));
        // }
        ModItems.register("cobblestone_aqueduct", () -> new BlockItem(cobblestoneAqueduct.get(), new Item.Properties()));
        ModItems.register("dirt_aqueduct", () -> new BlockItem(dirtAqueduct.get(), new Item.Properties()));
        ModItems.register("mossy_cobblestone_aqueduct", () -> new BlockItem(mossyCobblestoneAqueduct.get(), new Item.Properties()));

        ModItems.register("paddy_field", () -> new BlockItem(paddyField.get(), new Item.Properties()));
    }

    // RegistryObject<Item> itemBlock = DREntityBlockItems.register(path, () -> new ItemFluidDrawer(fluiddrawer.get(), new Item.Properties()));
    // RegistryObject<BlockEntityType<BlockEntityFluidDrawer>> tankTileEntityType = DRBlockEntities.register(path,
    //         () -> BlockEntityType.Builder.of((pos, state) -> new BlockEntityFluidDrawer(count, pos, state), fluiddrawer.get()).build(null));
    public static RegistryObject<Block> GRASS_BLOCK_WITH_HOLE = ModBlocks.register("grass_block_with_hole", () -> new GrassBlock(Block.Properties.copy(Blocks.GRASS_BLOCK).strength(0.6F).randomTicks()));
    public static RegistryObject<Item> GRASS_BLOCK_WITH_HOLE_ITEM = ModItems.register("grass_block_with_hole", () -> new BlockItem(GRASS_BLOCK_WITH_HOLE.get(), new Item.Properties()));
    // public static RegistryObject<Block> WOODEN_BOWL = ModBlocks.register("wooden_bowl", () -> new BowlBlock(Block.Properties.copy(Blocks.GRASS_BLOCK).strength(0.4F).noOcclusion()));
    public static RegistryObject<Item> WOODEN_BOWL_ITEM = ModItems.register("wooden_bowl", () -> new Item( new Item.Properties()));


    private static boolean predFalse(BlockState p_235436_0_, BlockGetter p_235436_1_, BlockPos p_235436_2_) {
        return false;
    }

    public static RegistryObject<Block> WOODEN_FRAME = ModBlocks.register("wooden_frame", () -> new WoodenFrameBlock(Block.Properties.copy(Blocks.OAK_WOOD).strength(0.5F).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static RegistryObject<Item> WOODEN_FRAME_ITEM = ModItems.register("wooden_frame", () -> new BlockItem(WOODEN_FRAME.get(), new Item.Properties()));

    public static RegistryObject<Block> stone_campfire = ModBlocks.register("stone_campfire", () -> new StoneCampfireBlock(Block.Properties.copy(Blocks.STONE).strength(3.5F).noOcclusion()));
    public static RegistryObject<Item> stone_campfire_ITEM = ModItems.register("stone_campfire", () -> new BlockItem(stone_campfire.get(), new Item.Properties()));

    public static RegistryObject<Block> saucepan = ModBlocks.register("saucepan", () -> new SaucepanBlock(Block.Properties.copy(Blocks.IRON_BLOCK).strength(3.5F).randomTicks()));
    public static RegistryObject<Item> saucepan_ITEM = ModItems.register("saucepan", () -> new BlockItem(saucepan.get(), new Item.Properties()));

    public static RegistryObject<Block> filter_screen = ModBlocks.register("filter_screen", () -> new FilterScreenBlock(Block.Properties.copy(Blocks.OAK_WOOD).strength(0.2F).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static RegistryObject<Item> filter_screen_ITEM = ModItems.register("filter_screen", () -> new BlockItem(filter_screen.get(), new Item.Properties()));

    public static RegistryObject<Block> STONE_CATAPULT_BOARD = ModBlocks.register("stone_catapult_board", () -> new CatapultBoardBlock(0.25F, Block.Properties.copy(Blocks.STONE).strength(3.5F).noOcclusion()));
    public static RegistryObject<Item> STONE_CATAPULT_BOARD_ITEM = ModItems.register("stone_catapult_board", () -> new BlockItem(STONE_CATAPULT_BOARD.get(), new Item.Properties()));
    public static RegistryObject<Block> BAMBOO_CATAPULT_BOARD = ModBlocks.register("bamboo_catapult_board", () -> new CatapultBoardBlock(0.5F, Block.Properties.copy(Blocks.BAMBOO).offsetType(BlockBehaviour.OffsetType.NONE).noOcclusion()));
    public static RegistryObject<Item> BAMBOO_CATAPULT_BOARD_ITEM = ModItems.register("bamboo_catapult_board", () -> new BlockItem(BAMBOO_CATAPULT_BOARD.get(), new Item.Properties()));
    public static RegistryObject<Block> IRON_CATAPULT_BOARD = ModBlocks.register("iron_catapult_board", () -> new CatapultBoardBlock(0.75F, Block.Properties.copy(Blocks.IRON_BLOCK).strength(3.5F).noOcclusion()));
    public static RegistryObject<Item> IRON_CATAPULT_BOARD_ITEM = ModItems.register("iron_catapult_board", () -> new BlockItem(IRON_CATAPULT_BOARD.get(), new Item.Properties()));


    public static RegistryObject<Block> SCARECROW = ModBlocks.register("scarecrow", () -> new ScarecrowBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).randomTicks().strength(0.5F).sound(SoundType.GRASS).pushReaction(PushReaction.IGNORE)));
    public static RegistryObject<Item> SCARECROW_ITEM = ModItems.register("scarecrow", () -> new BlockItem(SCARECROW.get(), new Item.Properties()));
    public static RegistryObject<Block> DRY_HAYSTACK = ModBlocks.register("dry_haystack", () -> new HaystackBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).randomTicks().strength(0.5F).sound(SoundType.GRASS).pushReaction(PushReaction.IGNORE)));
    public static RegistryObject<Item> DRY_HAYSTACK_ITEM = ModItems.register("dry_haystack", () -> new BlockItem(DRY_HAYSTACK.get(), new Item.Properties()));
    public static RegistryObject<Block> WET_HAYSTACK = ModBlocks.register("wet_haystack", () -> new HaystackBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).randomTicks().strength(0.5F).sound(SoundType.GRASS).pushReaction(PushReaction.IGNORE)));
    public static RegistryObject<Item> WET_HAYSTACK_ITEM = ModItems.register("wet_haystack", () -> new BlockItem(WET_HAYSTACK.get(), new Item.Properties()));

    public static RegistryObject<Block> WOODEN_TABLE = ModBlocks.register("wooden_table", () -> new TableBlock(Block.Properties.copy(Blocks.OAK_WOOD).strength(0.6F).noOcclusion()));
    public static RegistryObject<Item> WOODEN_TABLE_ITEM = ModItems.register("wooden_table", () -> new BlockItem(WOODEN_TABLE.get(), new Item.Properties()));
    public static RegistryObject<Block> WOODEN_CHAIR = ModBlocks.register("wooden_chair", () -> new ChairBlock(Block.Properties.copy(Blocks.OAK_WOOD).strength(0.6F).noOcclusion()));
    public static RegistryObject<Item> WOODEN_CHAIR_ITEM = ModItems.register("wooden_chair", () -> new BlockItem(WOODEN_CHAIR.get(), new Item.Properties()));

    public static RegistryObject<Block> STONE_TABLE = ModBlocks.register("stone_table", () -> new TableBlock(Block.Properties.copy(Blocks.STONE).strength(1.5F).noOcclusion()));
    public static RegistryObject<Item> STONE_TABLE_ITEM = ModItems.register("stone_table", () -> new BlockItem(STONE_TABLE.get(), new Item.Properties()));
    public static RegistryObject<Block> STONE_CHAIR = ModBlocks.register("stone_chair", () -> new StoneChairBlock(Block.Properties.copy(Blocks.STONE).strength(1.5F).noOcclusion()));
    public static RegistryObject<Item> STONE_CHAIR_ITEM = ModItems.register("stone_chair", () -> new BlockItem(STONE_CHAIR.get(), new Item.Properties()));

    public static RegistryObject<Block> BAMBOO_TABLE = ModBlocks.register("bamboo_table", () -> new TableBlock(Block.Properties.copy(Blocks.BAMBOO_PLANKS).strength(0.5F).noOcclusion()));
    public static RegistryObject<Item> BAMBOO_TABLE_ITEM = ModItems.register("bamboo_table", () -> new BlockItem(BAMBOO_TABLE.get(), new Item.Properties()));
    public static RegistryObject<Block> BAMBOO_CHAIR = ModBlocks.register("bamboo_chair", () -> new ChairBlock(Block.Properties.copy(Blocks.BAMBOO_PLANKS).strength(0.5F).noOcclusion()));
    public static RegistryObject<Item> BAMBOO_CHAIR_ITEM = ModItems.register("bamboo_chair", () -> new BlockItem(BAMBOO_CHAIR.get(), new Item.Properties()));

    public static RegistryObject<Block> BAMBOO_LANTERN = ModBlocks.register("bamboo_lantern", () -> new BambooLanternBlock(Block.Properties.copy(Blocks.BAMBOO_PLANKS).strength(0.5F).lightLevel(state -> 15).noOcclusion()));
    public static RegistryObject<Item> BAMBOO_LANTERN_ITEM = ModItems.register("bamboo_lantern", () -> new BlockItem(BAMBOO_LANTERN.get(), new Item.Properties()));

    public static RegistryObject<Block> BAMBOO_DOOR = ModBlocks.register("bamboo_door", () -> new BambooDoorBlock(Block.Properties.copy(Blocks.BAMBOO_PLANKS).strength(0.5F).noOcclusion()));
    public static RegistryObject<Item> BAMBOO_DOOR_ITEM = ModItems.register("bamboo_door", () -> new BlockItem(BAMBOO_DOOR.get(), new Item.Properties()));
    public static RegistryObject<Block> BAMBOO_GLASS_DOOR = ModBlocks.register("bamboo_glass_door", () -> new BambooDoorBlock(Block.Properties.copy(Blocks.BAMBOO_PLANKS).strength(0.5F).noOcclusion()));
    public static RegistryObject<Item> BAMBOO_GLASS_DOOR_ITEM = ModItems.register("bamboo_glass_door", () -> new BlockItem(BAMBOO_GLASS_DOOR.get(), new Item.Properties()));

    public static RegistryObject<Block> BAMBOO_LATTICE = ModBlocks.register("bamboo_lattice", () -> new BambooLatticeBlock(Block.Properties.copy(Blocks.BAMBOO_PLANKS).strength(0.6F).noOcclusion()));
    public static RegistryObject<Item> BAMBOO_LATTICE_ITEM = ModItems.register("bamboo_lattice", () -> new BlockItem(BAMBOO_LATTICE.get(), new Item.Properties()));

    public static RegistryObject<Block> FRESH_BAMBOO_WALL = ModBlocks.register("fresh_bamboo_wall", () -> new BambooWallBlock(Block.Properties.copy(Blocks.BAMBOO_PLANKS).strength(0.6F).noOcclusion()));
    public static RegistryObject<Item> FRESH_BAMBOO_WALL_ITEM = ModItems.register("fresh_bamboo_wall", () -> new BlockItem(FRESH_BAMBOO_WALL.get(), new Item.Properties()));
    public static RegistryObject<Block> DRIED_BAMBOO_WALL = ModBlocks.register("dried_bamboo_wall", () -> new BambooWallBlock(Block.Properties.copy(Blocks.BAMBOO_PLANKS).strength(0.6F).noOcclusion()));
    public static RegistryObject<Item> DRIED_BAMBOO_WALL_ITEM = ModItems.register("dried_bamboo_wall", () -> new BlockItem(DRIED_BAMBOO_WALL.get(), new Item.Properties()));


    // FLOWERS 花朵
    public static RegistryObject<HybridizableFlowerBlock> CHRYSANTHEMUM = ModBlocks.register("chrysanthemum", () -> new HybridizableFlowerBlock(Block.Properties.copy(Blocks.DANDELION).noOcclusion()));
    public static RegistryObject<HybridizableFlowerBlockItem> CHRYSANTHEMUM_ITEM = ModItems.register("chrysanthemum", () -> new HybridizableFlowerBlockItem(CHRYSANTHEMUM.get(), new Item.Properties()));
    public static RegistryObject<HybridizableFlowerBlock> HYACINTH = ModBlocks.register("hyacinth", () -> new HybridizableFlowerBlock(Block.Properties.copy(Blocks.DANDELION).noOcclusion()));
    public static RegistryObject<HybridizableFlowerBlockItem> HYACINTH_ITEM = ModItems.register("hyacinth", () -> new HybridizableFlowerBlockItem(HYACINTH.get(), new Item.Properties()));
    public static RegistryObject<HybridizableFlowerBlock> ZINNIA = ModBlocks.register("zinnia", () -> new HybridizableFlowerBlock(Block.Properties.copy(Blocks.DANDELION).noOcclusion()));
    public static RegistryObject<HybridizableFlowerBlockItem> ZINNIA_ITEM = ModItems.register("zinnia", () -> new HybridizableFlowerBlockItem(ZINNIA.get(), new Item.Properties()));

}

