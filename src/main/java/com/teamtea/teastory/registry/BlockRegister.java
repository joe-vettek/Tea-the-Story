package com.teamtea.teastory.registry;


import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.block.craft.*;
import com.teamtea.teastory.block.crops.*;
import com.teamtea.teastory.block.decorations.*;
import com.teamtea.teastory.item.HybridizableFlowerBlockItem;
import com.teamtea.teastory.item.food.NormalFoods;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

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


    public static DeferredHolder<Block, com.teamtea.teastory.block.crops.RiceSeedlingBlock> RiceSeedlingBlock = ModBlocks.register("rice_seedling", () -> new RiceSeedlingBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollision().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static DeferredHolder<Block, RicePlantBlock> ricePlant = ModBlocks.register("rice_plant", () -> new RicePlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollision().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));

    public static DeferredHolder<Item, BlockItem> RICE_GRAINS = ModItems.register("rice_grains", () -> new BlockItem(RiceSeedlingBlock.get(), new Item.Properties()));
    public static DeferredHolder<Item, BlockItem> riceSeedlings = ModItems.register("rice_seedlings", () -> new BlockItem(ricePlant.get(), new Item.Properties()));

    public static DeferredHolder<Block, TeaPlantBlock> tea_plant = ModBlocks.register("tea_plant", () -> new TeaPlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollision().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static DeferredHolder<Block, WildCropBlock> wild_tea_plant = ModBlocks.register("wild_tea_plant", () -> new WildCropBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollision().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY),false,false));

    // public static RegistryObject<Item> tea_plant_item = ModItems.register("tea_plant", () -> new BlockItem(tea_plant.get(), new Item.Properties()));
    public static DeferredHolder<Item, BlockItem> wild_tea_plant_item = ModItems.register("wild_tea_plant", () -> new BlockItem(wild_tea_plant.get(), new Item.Properties()));
    public static DeferredHolder<Item, BlockItem> TEA_SEEDS = ModItems.register("tea_seeds", () -> new BlockItem(tea_plant.get(), new Item.Properties()));

    public static DeferredHolder<Block, MelonVineBlock> WATERMELON_VINE = ModBlocks.register("watermelon_vine", () -> new MelonVineBlock(Blocks.MELON, Block.Properties.of().mapColor(MapColor.PLANT).noCollision().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));

    public static DeferredHolder<Block, WildCropBlock> WILD_RICE = ModBlocks.register("wild_rice", () -> new WildCropBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollision().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).offsetType(BlockBehaviour.OffsetType.XZ),false,false));
    public static DeferredHolder<Item, BlockItem> WILD_RICE_ITEM = ModItems.register("wild_rice", () -> new BlockItem(WILD_RICE.get(), new Item.Properties()));

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
    public static DeferredHolder<Block, GrassBlockWithHole> GRASS_BLOCK_WITH_HOLE = ModBlocks.register("grass_block_with_hole", () -> new GrassBlockWithHole(Block.Properties.ofFullCopy(Blocks.GRASS_BLOCK).strength(0.6F).randomTicks()));
    public static DeferredHolder<Item, BlockItem> GRASS_BLOCK_WITH_HOLE_ITEM = ModItems.register("grass_block_with_hole", () -> new BlockItem(GRASS_BLOCK_WITH_HOLE.get(), new Item.Properties()));
    // public static RegistryObject<Block> WOODEN_BOWL = ModBlocks.register("wooden_bowl", () -> new BowlBlock(Block.Properties.ofFullCopy(Blocks.GRASS_BLOCK).strength(0.4F).noOcclusion()));
    public static DeferredHolder<Item, Item> WOODEN_BOWL_ITEM = ModItems.register("wooden_bowl", () -> new Item(new Item.Properties()));


    private static boolean predFalse(BlockState p_235436_0_, BlockGetter p_235436_1_, BlockPos p_235436_2_) {
        return false;
    }

    public static DeferredHolder<Block, WoodenFrameBlock> WOODEN_FRAME = ModBlocks.register("wooden_frame", () -> new WoodenFrameBlock(Block.Properties.ofFullCopy(Blocks.OAK_WOOD).strength(0.5F).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static DeferredHolder<Item, BlockItem> WOODEN_FRAME_ITEM = ModItems.register("wooden_frame", () -> new BlockItem(WOODEN_FRAME.get(), new Item.Properties()));

    public static DeferredHolder<Block, StoneCampfireBlock> stone_campfire = ModBlocks.register("stone_campfire", () -> new StoneCampfireBlock(Block.Properties.ofFullCopy(Blocks.STONE).strength(3.5F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> stone_campfire_ITEM = ModItems.register("stone_campfire", () -> new BlockItem(stone_campfire.get(), new Item.Properties()));


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
    public static DeferredHolder<Block, HybridizableFlowerBlock> CHRYSANTHEMUM = ModBlocks.register("chrysanthemum", () -> new HybridizableFlowerBlock(Block.Properties.of().noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).mapColor(state -> MapColor.byId(state.getValue(HybridizableFlowerBlock.FLOWER_COLOR).getColorValue()))));
    public static DeferredHolder<Item, HybridizableFlowerBlockItem> CHRYSANTHEMUM_ITEM = ModItems.register("chrysanthemum", () -> new HybridizableFlowerBlockItem(CHRYSANTHEMUM.get(), new Item.Properties()));
    public static DeferredHolder<Block, HybridizableFlowerBlock> HYACINTH = ModBlocks.register("hyacinth", () -> new HybridizableFlowerBlock(Block.Properties.of().noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).mapColor(state -> MapColor.byId(state.getValue(HybridizableFlowerBlock.FLOWER_COLOR).getColorValue()))));
    public static DeferredHolder<Item, HybridizableFlowerBlockItem> HYACINTH_ITEM = ModItems.register("hyacinth", () -> new HybridizableFlowerBlockItem(HYACINTH.get(), new Item.Properties()));
    public static DeferredHolder<Block, HybridizableFlowerBlock> ZINNIA = ModBlocks.register("zinnia", () -> new HybridizableFlowerBlock(Block.Properties.of().noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).mapColor(state -> MapColor.byId(state.getValue(HybridizableFlowerBlock.FLOWER_COLOR).getColorValue()))));
    public static DeferredHolder<Item, HybridizableFlowerBlockItem> ZINNIA_ITEM = ModItems.register("zinnia", () -> new HybridizableFlowerBlockItem(ZINNIA.get(), new Item.Properties()));

}

