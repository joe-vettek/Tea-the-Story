package xueluoanping.teastory;

import cloud.lemonslice.teastory.block.craft.*;
import cloud.lemonslice.teastory.block.drink.DrinkMakerBlock;
import cloud.lemonslice.teastory.block.drink.IronKettleBlock;
import cloud.lemonslice.teastory.block.drink.TeapotBlock;
import cloud.lemonslice.teastory.block.drink.WoodenTrayBlock;
import cloud.lemonslice.teastory.blockentity.*;
import cloud.lemonslice.teastory.container.*;
import cloud.lemonslice.teastory.item.DrinkMakerItem;
import cloud.lemonslice.teastory.item.IronKettleItem;
import cloud.lemonslice.teastory.item.TeapotItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xueluoanping.teastory.blockentity.VineEntity;

import java.util.function.Supplier;

public class TileEntityTypeRegistry {
    public static final DeferredRegister<BlockEntityType<?>> DRBlockEntities = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TeaStory.MODID);
    public static final DeferredRegister<Item> ModItems = DeferredRegister.create(Registries.ITEM, TeaStory.MODID);
    public static final DeferredRegister<Block> ModBlocks = DeferredRegister.create(Registries.BLOCK, TeaStory.MODID);
    public static final DeferredRegister<MenuType<?>> DRMenuType = DeferredRegister.create(Registries.MENU, TeaStory.MODID);

    public static DeferredHolder<Block, StoveBlock> DIRT_STOVE = ModBlocks.register("dirt_stove", () -> new StoveBlock(Block.Properties.ofFullCopy(Blocks.STONE).strength(3.5F).lightLevel(state -> state.getValue(StoveBlock.LIT) ? 15 : 0), 1));
    public static DeferredHolder<Item, BlockItem> DIRT_STOVE_ITEM = ModItems.register("dirt_stove", () -> new BlockItem(DIRT_STOVE.get(), new Item.Properties()));

    public static DeferredHolder<Block, StoveBlock> STONE_STOVE = ModBlocks.register("stone_stove", () -> new StoveBlock(Block.Properties.ofFullCopy(Blocks.STONE).strength(3.5F).lightLevel(state -> state.getValue(StoveBlock.LIT) ? 15 : 0), 2));
    public static DeferredHolder<Item, BlockItem> STONE_STOVE_ITEM = ModItems.register("stone_stove", () -> new BlockItem(STONE_STOVE.get(), new Item.Properties()));

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<StoveTileEntity>> STOVE_TYPE = DRBlockEntities.register("stove",
            () -> BlockEntityType.Builder.of(StoveTileEntity::new, DIRT_STOVE.get(), STONE_STOVE.get()).build(null));
    public static Supplier<MenuType<StoveContainer>> STOVE_CONTAINER = DRMenuType.register("stove", () -> IMenuTypeExtension.create(StoveContainer::new));


    public static DeferredHolder<Block, BambooTrayBlock> BAMBOO_TRAY = ModBlocks.register("bamboo_tray", () -> new BambooTrayBlock(Block.Properties.ofFullCopy(Blocks.BAMBOO).strength(0.5F).noOcclusion().offsetType(BlockBehaviour.OffsetType.NONE)));
    public static DeferredHolder<Block, CatapultBoardBlockWithTray> STONE_CATAPULT_BOARD_WITH_TRAY = ModBlocks.register("stone_catapult_board_with_tray", () -> new CatapultBoardBlockWithTray(Block.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.BAMBOO).strength(1.0F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> BAMBOO_TRAY_ITEM = ModItems.register("bamboo_tray", () -> new BlockItem(BAMBOO_TRAY.get(), new Item.Properties()));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BambooTrayTileEntity>> BAMBOO_TRAY_TYPE = DRBlockEntities.register("bamboo_tray",
            () -> BlockEntityType.Builder.of(BambooTrayTileEntity::new, BAMBOO_TRAY.get(),STONE_CATAPULT_BOARD_WITH_TRAY.get()).build(null));
    public static Supplier<MenuType<BambooTrayContainer>> BAMBOO_TRAY_CONTAINER = DRMenuType.register("bamboo_tray", () -> IMenuTypeExtension.create(BambooTrayContainer::new));


    public static DeferredHolder<Block, DrinkMakerBlock> DRINK_MAKER = ModBlocks.register("drink_maker", () -> new DrinkMakerBlock(Block.Properties.ofFullCopy(Blocks.OAK_WOOD).strength(0.5F).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static DeferredHolder<Item, DrinkMakerItem> DRINK_MAKER_ITEM = ModItems.register("drink_maker", () -> new DrinkMakerItem(DRINK_MAKER.get(), new Item.Properties()));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<DrinkMakerTileEntity>> DRINK_MAKER_TYPE = DRBlockEntities.register("drink_maker",
            () -> BlockEntityType.Builder.of(DrinkMakerTileEntity::new, DRINK_MAKER.get()).build(null));
    public static Supplier<MenuType<DrinkMakerContainer>> DRINK_MAKER_CONTAINER = DRMenuType.register("drink_maker", () -> IMenuTypeExtension.create(DrinkMakerContainer::new));


    public static DeferredHolder<Block, StoneMillBlock> STONE_MILL = ModBlocks.register("stone_mill", () -> new StoneMillBlock(Block.Properties.ofFullCopy(Blocks.STONE).strength(1.5F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> STONE_MILL_ITEM = ModItems.register("stone_mill", () -> new BlockItem(STONE_MILL.get(), new Item.Properties()));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<StoneMillTileEntity>> STONE_MILL_TYPE = DRBlockEntities.register("stone_mill",
            () -> BlockEntityType.Builder.of(StoneMillTileEntity::new, STONE_MILL.get()).build(null));
    public static Supplier<MenuType<StoneMillContainer>> STONE_MILL_CONTAINER = DRMenuType.register("stone_mill", () -> IMenuTypeExtension.create(StoneMillContainer::new));


    public static DeferredHolder<Block, StoneRollerBlock> STONE_ROLLER = ModBlocks.register("stone_roller", () -> new StoneRollerBlock(Block.Properties.ofFullCopy(Blocks.STONE).strength(1.5F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> STONE_ROLLER_ITEM = ModItems.register("stone_roller", () -> new BlockItem(STONE_ROLLER.get(), new Item.Properties()));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<StoneRollerTileEntity>> STONE_ROLLER_TYPE = DRBlockEntities.register("stone_roller",
            () -> BlockEntityType.Builder.of(StoneRollerTileEntity::new, STONE_ROLLER.get()).build(null));
    public static Supplier<MenuType<StoneRollerContainer>> STONE_ROLLER_CONTAINER = DRMenuType.register("stone_roller", () -> IMenuTypeExtension.create(StoneRollerContainer::new));


    public static DeferredHolder<Block, TeapotBlock> TEAPOT = ModBlocks.register("porcelain_teapot", () -> new TeapotBlock(Block.Properties.ofFullCopy(Blocks.STONE).strength(3.5F).noOcclusion()));
    public static DeferredHolder<Item, TeapotItem> PORCELAIN_TEAPOT = ModItems.register("porcelain_teapot", () -> new TeapotItem(TEAPOT.get(), new Item.Properties().stacksTo(1), 1000, false));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<TeapotTileEntity>> TEAPOT_TYPE = DRBlockEntities.register("porcelain_teapot",
            () -> BlockEntityType.Builder.of((a, b) -> new TeapotTileEntity(1000, a, b), TEAPOT.get()).build(null));

    public static DeferredHolder<Block, IronKettleBlock> IRON_KETTLE = ModBlocks.register("iron_kettle", () -> new IronKettleBlock(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(3.5F).noOcclusion()));
    public static DeferredHolder<Item, IronKettleItem> IRON_KETTLE_ITEM = ModItems.register("iron_kettle", () -> new IronKettleItem(IRON_KETTLE.get(), new Item.Properties().stacksTo(1), 2000));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<TeapotTileEntity>> IRON_KETTLE_TYPE = DRBlockEntities.register("iron_kettle",
            () -> BlockEntityType.Builder.of((a, b) -> new TeapotTileEntity(2000, a, b), IRON_KETTLE.get()).build(null));

    public static DeferredHolder<Block, WoodenTrayBlock> WOODEN_TRAY = ModBlocks.register("wooden_tray", () -> new WoodenTrayBlock(Block.Properties.ofFullCopy(Blocks.OAK_WOOD).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> WOODEN_TRAY_ITEM = ModItems.register("wooden_tray", () -> new BlockItem(WOODEN_TRAY.get(), new Item.Properties().stacksTo(1)));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<TeaCupTileEntity>> WOODEN_TRAY_TYPE = DRBlockEntities.register("wooden_tray",
            () -> BlockEntityType.Builder.of((a, b) -> new TeaCupTileEntity(250, a, b), WOODEN_TRAY.get()).build(null));


    public static DeferredHolder<Block, WoodenBarrelBlock> WOODEN_BARREL = ModBlocks.register("wooden_barrel", () -> new WoodenBarrelBlock(Block.Properties.ofFullCopy(Blocks.OAK_WOOD).strength(0.6F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> WOODEN_BARREL_ITEM = ModItems.register("wooden_barrel", () -> new BlockItem(WOODEN_BARREL.get(), new Item.Properties().stacksTo(1)));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<WoodenBarrelTileEntity>> WOODEN_BARREL_TYPE = DRBlockEntities.register("wooden_barrel",
            () -> BlockEntityType.Builder.of(WoodenBarrelTileEntity::new, WOODEN_BARREL.get()).build(null));

    public static DeferredHolder<BlockEntityType<?>,BlockEntityType<VineEntity>> VINE_TYPE = null;


}
