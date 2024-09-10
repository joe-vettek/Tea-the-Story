package com.teamtea.teastory;


import com.teamtea.teastory.block.craft.*;
import com.teamtea.teastory.block.crops.*;
import com.teamtea.teastory.block.decorations.*;
import com.teamtea.teastory.item.HybridizableFlowerBlockItem;
import com.teamtea.teastory.item.VineSeedsItem;
import com.teamtea.teastory.item.food.NormalFoods;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
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
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;


// import static xueluoanping.fluiddrawerslegacy.FluidDrawersLegacyMod.CREATIVE_TAB;

// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)

public class BlockRegister {
    public static final DeferredRegister<Item> ModItems = DeferredRegister.create(Registries.ITEM, TeaStory.MODID);
    public static final DeferredRegister<Block> ModBlocks = DeferredRegister.create(Registries.BLOCK, TeaStory.MODID);


    public static DeferredHolder<Block, AqueductBlock> cobblestoneAqueduct = ModBlocks.register("cobblestone_aqueduct", () -> new AqueductBlock(Block.Properties.ofFullCopy(Blocks.COBBLESTONE)
            .sound(SoundType.STONE).strength(1.5F)
            .noOcclusion()));
    public static DeferredHolder<Block, LowAqueductBlock> dirtAqueduct = ModBlocks.register("dirt_aqueduct", () -> new LowAqueductBlock(Block.Properties.ofFullCopy(Blocks.DIRT_PATH)
            .sound(SoundType.GRASS).strength(0.8F)
            .noOcclusion()));

    public static DeferredHolder<Block, AqueductConnectorBlock> mossyCobblestoneAqueduct = ModBlocks.register("mossy_cobblestone_aqueduct", () -> new AqueductConnectorBlock(Block.Properties.ofFullCopy(cobblestoneAqueduct.get())));
    public static DeferredHolder<Block, PaddyFieldBlock> paddyField = ModBlocks.register("paddy_field", PaddyFieldBlock::new);


    public static DeferredHolder<Block, com.teamtea.teastory.block.crops.RiceSeedlingBlock> RiceSeedlingBlock = ModBlocks.register("rice_seedling", () -> new RiceSeedlingBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static DeferredHolder<Block, RicePlantBlock> ricePlant = ModBlocks.register("rice_plant", () -> new RicePlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));

    public static DeferredHolder<Item, BlockItem> RICE_GRAINS = ModItems.register("rice_grains", () -> new BlockItem(RiceSeedlingBlock.get(), new Item.Properties()));
    public static DeferredHolder<Item, BlockItem> riceSeedlings = ModItems.register("rice_seedlings", () -> new BlockItem(ricePlant.get(), new Item.Properties()));

    public static DeferredHolder<Block, TeaPlantBlock> tea_plant = ModBlocks.register("tea_plant", () -> new TeaPlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static DeferredHolder<Block, WildTeaPlantBlock> wild_tea_plant = ModBlocks.register("wild_tea_plant", () -> new WildTeaPlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));

    // public static RegistryObject<Item> tea_plant_item = ModItems.register("tea_plant", () -> new BlockItem(tea_plant.get(), new Item.Properties()));
    public static DeferredHolder<Item, BlockItem> wild_tea_plant_item = ModItems.register("wild_tea_plant", () -> new BlockItem(wild_tea_plant.get(), new Item.Properties()));
    public static DeferredHolder<Item, BlockItem> TEA_SEEDS = ModItems.register("tea_seeds", () -> new BlockItem(tea_plant.get(), new Item.Properties()));

    public static DeferredHolder<Block, MelonVineBlock> WATERMELON_VINE = ModBlocks.register("watermelon_vine", () -> new MelonVineBlock(Blocks.MELON, Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));

    public static DeferredHolder<Block, WildGrapeBlock> WILD_GRAPE = ModBlocks.register("wild_grape", () -> new WildGrapeBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static DeferredHolder<Item, BlockItem> WILD_GRAPE_ITEM = ModItems.register("wild_grape", () -> new BlockItem(WILD_GRAPE.get(), new Item.Properties()));

    public static DeferredHolder<Block, ChiliBlock> CHILI_PLANT = ModBlocks.register("chili_plant", () -> new ChiliBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static DeferredHolder<Item, Item> CHILI = ModItems.register("chili", () -> new Item(new Item.Properties()));
    public static DeferredHolder<Item, BlockItem> CHILI_SEEDS = ModItems.register("chili_seeds", () -> new BlockItem(CHILI_PLANT.get(), new Item.Properties()));

    public static DeferredHolder<Block, ChineseCabbageBlock> CHINESE_CABBAGE_PLANT = ModBlocks.register("chinese_cabbage_plant", () -> new ChineseCabbageBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static DeferredHolder<Item, Item> CHINESE_CABBAGE = ModItems.register("chinese_cabbage", () -> new Item(new Item.Properties()));
    public static DeferredHolder<Item, BlockItem> CHINESE_CABBAGE_SEEDS = ModItems.register("chinese_cabbage_seeds", () -> new BlockItem(CHINESE_CABBAGE_PLANT.get(), new Item.Properties()));

    public static DeferredHolder<Block, TrellisBlock> OAK_TRELLIS = ModBlocks.register("oak_trellis", () -> new TrellisBlock(Block.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(0.6F).sound(SoundType.WOOD)));
    public static DeferredHolder<Item, Item> OAK_TRELLIS_ITEM = ModItems.register("oak_trellis", () -> new Item(new Item.Properties()));

    public static DeferredHolder<Block, StemFruitBlock> GRAPE = ModBlocks.register("grape_plant", () -> new StemFruitBlock(VineType.GRAPE, Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).instabreak().randomTicks().noCollission().pushReaction(PushReaction.DESTROY).sound(SoundType.CROP)));
    public static DeferredHolder<Block, StemFruitBlock> CUCUMBER = ModBlocks.register("cucumber_plant", () -> new StemFruitBlock(VineType.CUCUMBER, Block.Properties.of().mapColor(MapColor.GRASS).instabreak().randomTicks().noCollission().pushReaction(PushReaction.DESTROY).sound(SoundType.CROP)));
    public static DeferredHolder<Block, StemFruitBlock> BITTER_GOURD = ModBlocks.register("bitter_gourd_plant", () -> new StemFruitBlock(VineType.BITTER_GOURD, Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).instabreak().randomTicks().noCollission().pushReaction(PushReaction.DESTROY).sound(SoundType.CROP)));

    public static DeferredHolder<Item, VineSeedsItem> GRAPES = ModItems.register("grapes", () -> new VineSeedsItem(VineType.GRAPE, new Item.Properties().food(NormalFoods.GRAPE)));
    public static DeferredHolder<Item, VineSeedsItem> CUCUMBERS = ModItems.register("cucumber", () -> new VineSeedsItem(VineType.CUCUMBER, new Item.Properties().food(NormalFoods.CUCUMBER)));
    public static DeferredHolder<Item, VineSeedsItem> BITTER_GOURDS = ModItems.register("bitter_gourd", () -> new VineSeedsItem(VineType.BITTER_GOURD, new Item.Properties().food(NormalFoods.BITTER_GOURD)));


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
    public static DeferredHolder<Block, GrassBlock> GRASS_BLOCK_WITH_HOLE = ModBlocks.register("grass_block_with_hole", () -> new GrassBlock(Block.Properties.ofFullCopy(Blocks.GRASS_BLOCK).strength(0.6F).randomTicks()));
    public static DeferredHolder<Item, BlockItem> GRASS_BLOCK_WITH_HOLE_ITEM = ModItems.register("grass_block_with_hole", () -> new BlockItem(GRASS_BLOCK_WITH_HOLE.get(), new Item.Properties()));
    // public static RegistryObject<Block> WOODEN_BOWL = ModBlocks.register("wooden_bowl", () -> new BowlBlock(Block.Properties.ofFullCopy(Blocks.GRASS_BLOCK).strength(0.4F).noOcclusion()));
    public static DeferredHolder<Item, Item> WOODEN_BOWL_ITEM = ModItems.register("wooden_bowl", () -> new Item( new Item.Properties()));


    private static boolean predFalse(BlockState p_235436_0_, BlockGetter p_235436_1_, BlockPos p_235436_2_) {
        return false;
    }

    public static DeferredHolder<Block, WoodenFrameBlock> WOODEN_FRAME = ModBlocks.register("wooden_frame", () -> new WoodenFrameBlock(Block.Properties.ofFullCopy(Blocks.OAK_WOOD).strength(0.5F).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static DeferredHolder<Item, BlockItem> WOODEN_FRAME_ITEM = ModItems.register("wooden_frame", () -> new BlockItem(WOODEN_FRAME.get(), new Item.Properties()));

    public static DeferredHolder<Block, StoneCampfireBlock> stone_campfire = ModBlocks.register("stone_campfire", () -> new StoneCampfireBlock(Block.Properties.ofFullCopy(Blocks.STONE).strength(3.5F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> stone_campfire_ITEM = ModItems.register("stone_campfire", () -> new BlockItem(stone_campfire.get(), new Item.Properties()));

    public static DeferredHolder<Block, SaucepanBlock> saucepan = ModBlocks.register("saucepan", () -> new SaucepanBlock(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(3.5F).randomTicks()));
    public static DeferredHolder<Item, BlockItem> saucepan_ITEM = ModItems.register("saucepan", () -> new BlockItem(saucepan.get(), new Item.Properties()));

    public static DeferredHolder<Block, FilterScreenBlock> filter_screen = ModBlocks.register("filter_screen", () -> new FilterScreenBlock(Block.Properties.ofFullCopy(Blocks.OAK_WOOD).strength(0.2F).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static DeferredHolder<Item, BlockItem> filter_screen_ITEM = ModItems.register("filter_screen", () -> new BlockItem(filter_screen.get(), new Item.Properties()));

    public static DeferredHolder<Block, CatapultBoardBlock> STONE_CATAPULT_BOARD = ModBlocks.register("stone_catapult_board", () -> new CatapultBoardBlock(0.25F, Block.Properties.ofFullCopy(Blocks.STONE).strength(3.5F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> STONE_CATAPULT_BOARD_ITEM = ModItems.register("stone_catapult_board", () -> new BlockItem(STONE_CATAPULT_BOARD.get(), new Item.Properties()));
    public static DeferredHolder<Block, CatapultBoardBlock> BAMBOO_CATAPULT_BOARD = ModBlocks.register("bamboo_catapult_board", () -> new CatapultBoardBlock(0.5F, Block.Properties.ofFullCopy(Blocks.BAMBOO).offsetType(BlockBehaviour.OffsetType.NONE).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> BAMBOO_CATAPULT_BOARD_ITEM = ModItems.register("bamboo_catapult_board", () -> new BlockItem(BAMBOO_CATAPULT_BOARD.get(), new Item.Properties()));
    public static DeferredHolder<Block, CatapultBoardBlock> IRON_CATAPULT_BOARD = ModBlocks.register("iron_catapult_board", () -> new CatapultBoardBlock(0.75F, Block.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(3.5F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> IRON_CATAPULT_BOARD_ITEM = ModItems.register("iron_catapult_board", () -> new BlockItem(IRON_CATAPULT_BOARD.get(), new Item.Properties()));


    public static DeferredHolder<Block, ScarecrowBlock> SCARECROW = ModBlocks.register("scarecrow", () -> new ScarecrowBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).randomTicks().strength(0.5F).sound(SoundType.GRASS).pushReaction(PushReaction.IGNORE)));
    public static DeferredHolder<Item, BlockItem> SCARECROW_ITEM = ModItems.register("scarecrow", () -> new BlockItem(SCARECROW.get(), new Item.Properties()));
    public static DeferredHolder<Block, HaystackBlock> DRY_HAYSTACK = ModBlocks.register("dry_haystack", () -> new HaystackBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).randomTicks().strength(0.5F).sound(SoundType.GRASS).pushReaction(PushReaction.IGNORE)));
    public static DeferredHolder<Item, BlockItem> DRY_HAYSTACK_ITEM = ModItems.register("dry_haystack", () -> new BlockItem(DRY_HAYSTACK.get(), new Item.Properties()));
    public static DeferredHolder<Block, HaystackBlock> WET_HAYSTACK = ModBlocks.register("wet_haystack", () -> new HaystackBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).randomTicks().strength(0.5F).sound(SoundType.GRASS).pushReaction(PushReaction.IGNORE)));
    public static DeferredHolder<Item, BlockItem> WET_HAYSTACK_ITEM = ModItems.register("wet_haystack", () -> new BlockItem(WET_HAYSTACK.get(), new Item.Properties()));

    public static DeferredHolder<Block, TableBlock> WOODEN_TABLE = ModBlocks.register("wooden_table", () -> new TableBlock(Block.Properties.ofFullCopy(Blocks.OAK_WOOD).strength(0.6F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> WOODEN_TABLE_ITEM = ModItems.register("wooden_table", () -> new BlockItem(WOODEN_TABLE.get(), new Item.Properties()));
    public static DeferredHolder<Block, ChairBlock> WOODEN_CHAIR = ModBlocks.register("wooden_chair", () -> new ChairBlock(Block.Properties.ofFullCopy(Blocks.OAK_WOOD).strength(0.6F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> WOODEN_CHAIR_ITEM = ModItems.register("wooden_chair", () -> new BlockItem(WOODEN_CHAIR.get(), new Item.Properties()));

    public static DeferredHolder<Block, TableBlock> STONE_TABLE = ModBlocks.register("stone_table", () -> new TableBlock(Block.Properties.ofFullCopy(Blocks.STONE).strength(1.5F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> STONE_TABLE_ITEM = ModItems.register("stone_table", () -> new BlockItem(STONE_TABLE.get(), new Item.Properties()));
    public static DeferredHolder<Block, StoneChairBlock> STONE_CHAIR = ModBlocks.register("stone_chair", () -> new StoneChairBlock(Block.Properties.ofFullCopy(Blocks.STONE).strength(1.5F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> STONE_CHAIR_ITEM = ModItems.register("stone_chair", () -> new BlockItem(STONE_CHAIR.get(), new Item.Properties()));

    public static DeferredHolder<Block, TableBlock> BAMBOO_TABLE = ModBlocks.register("bamboo_table", () -> new TableBlock(Block.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).strength(0.5F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> BAMBOO_TABLE_ITEM = ModItems.register("bamboo_table", () -> new BlockItem(BAMBOO_TABLE.get(), new Item.Properties()));
    public static DeferredHolder<Block, ChairBlock> BAMBOO_CHAIR = ModBlocks.register("bamboo_chair", () -> new ChairBlock(Block.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).strength(0.5F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> BAMBOO_CHAIR_ITEM = ModItems.register("bamboo_chair", () -> new BlockItem(BAMBOO_CHAIR.get(), new Item.Properties()));

    public static DeferredHolder<Block, BambooLanternBlock> BAMBOO_LANTERN = ModBlocks.register("bamboo_lantern", () -> new BambooLanternBlock(Block.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).strength(0.5F).lightLevel(state -> 15).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> BAMBOO_LANTERN_ITEM = ModItems.register("bamboo_lantern", () -> new BlockItem(BAMBOO_LANTERN.get(), new Item.Properties()));

    public static DeferredHolder<Block, BambooDoorBlock> BAMBOO_DOOR = ModBlocks.register("bamboo_door", () -> new BambooDoorBlock(Block.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).strength(0.5F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> BAMBOO_DOOR_ITEM = ModItems.register("bamboo_door", () -> new BlockItem(BAMBOO_DOOR.get(), new Item.Properties()));
    public static DeferredHolder<Block, BambooDoorBlock> BAMBOO_GLASS_DOOR = ModBlocks.register("bamboo_glass_door", () -> new BambooDoorBlock(Block.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).strength(0.5F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> BAMBOO_GLASS_DOOR_ITEM = ModItems.register("bamboo_glass_door", () -> new BlockItem(BAMBOO_GLASS_DOOR.get(), new Item.Properties()));

    public static DeferredHolder<Block, BambooLatticeBlock> BAMBOO_LATTICE = ModBlocks.register("bamboo_lattice", () -> new BambooLatticeBlock(Block.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).strength(0.6F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> BAMBOO_LATTICE_ITEM = ModItems.register("bamboo_lattice", () -> new BlockItem(BAMBOO_LATTICE.get(), new Item.Properties()));

    public static DeferredHolder<Block, BambooWallBlock> FRESH_BAMBOO_WALL = ModBlocks.register("fresh_bamboo_wall", () -> new BambooWallBlock(Block.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).strength(0.6F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> FRESH_BAMBOO_WALL_ITEM = ModItems.register("fresh_bamboo_wall", () -> new BlockItem(FRESH_BAMBOO_WALL.get(), new Item.Properties()));
    public static DeferredHolder<Block, BambooWallBlock> DRIED_BAMBOO_WALL = ModBlocks.register("dried_bamboo_wall", () -> new BambooWallBlock(Block.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).strength(0.6F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> DRIED_BAMBOO_WALL_ITEM = ModItems.register("dried_bamboo_wall", () -> new BlockItem(DRIED_BAMBOO_WALL.get(), new Item.Properties()));


    // FLOWERS 花朵
    public static DeferredHolder<Block, HybridizableFlowerBlock> CHRYSANTHEMUM = ModBlocks.register("chrysanthemum", () -> new HybridizableFlowerBlock(Block.Properties.ofFullCopy(Blocks.DANDELION).noOcclusion()));
    public static DeferredHolder<Item, HybridizableFlowerBlockItem> CHRYSANTHEMUM_ITEM = ModItems.register("chrysanthemum", () -> new HybridizableFlowerBlockItem(CHRYSANTHEMUM.get(), new Item.Properties()));
    public static DeferredHolder<Block, HybridizableFlowerBlock> HYACINTH = ModBlocks.register("hyacinth", () -> new HybridizableFlowerBlock(Block.Properties.ofFullCopy(Blocks.DANDELION).noOcclusion()));
    public static DeferredHolder<Item, HybridizableFlowerBlockItem> HYACINTH_ITEM = ModItems.register("hyacinth", () -> new HybridizableFlowerBlockItem(HYACINTH.get(), new Item.Properties()));
    public static DeferredHolder<Block, HybridizableFlowerBlock> ZINNIA = ModBlocks.register("zinnia", () -> new HybridizableFlowerBlock(Block.Properties.ofFullCopy(Blocks.DANDELION).noOcclusion()));
    public static DeferredHolder<Item, HybridizableFlowerBlockItem> ZINNIA_ITEM = ModItems.register("zinnia", () -> new HybridizableFlowerBlockItem(ZINNIA.get(), new Item.Properties()));

}

