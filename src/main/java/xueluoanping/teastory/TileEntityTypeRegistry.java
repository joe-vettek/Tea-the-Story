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
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TileEntityTypeRegistry {
    public static final DeferredRegister<BlockEntityType<?>> DRBlockEntities = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TeaStory.MODID);
    public static final DeferredRegister<Item> ModItems = DeferredRegister.create(ForgeRegistries.ITEMS, TeaStory.MODID);
    public static final DeferredRegister<Block> ModBlocks = DeferredRegister.create(ForgeRegistries.BLOCKS, TeaStory.MODID);
    public static final DeferredRegister<MenuType<?>> DRMenuType = DeferredRegister.create(ForgeRegistries.MENU_TYPES, TeaStory.MODID);

    public static RegistryObject<Block> DIRT_STOVE = ModBlocks.register("dirt_stove", () -> new StoveBlock(Block.Properties.copy(Blocks.STONE).strength(3.5F).lightLevel(state -> state.getValue(StoveBlock.LIT) ? 15 : 0), 1));
    public static RegistryObject<Item> DIRT_STOVE_ITEM = ModItems.register("dirt_stove", () -> new BlockItem(DIRT_STOVE.get(), new Item.Properties()));

    public static RegistryObject<Block> STONE_STOVE = ModBlocks.register("stone_stove", () -> new StoveBlock(Block.Properties.copy(Blocks.STONE).strength(3.5F).lightLevel(state -> state.getValue(StoveBlock.LIT) ? 15 : 0), 2));
    public static RegistryObject<Item> STONE_STOVE_ITEM = ModItems.register("stone_stove", () -> new BlockItem(STONE_STOVE.get(), new Item.Properties()));

    public static RegistryObject<BlockEntityType<StoveTileEntity>> STOVE_TYPE = DRBlockEntities.register("stove",
            () -> BlockEntityType.Builder.of(StoveTileEntity::new, DIRT_STOVE.get(), STONE_STOVE.get()).build(null));
    public static RegistryObject<MenuType<StoveContainer>> STOVE_CONTAINER = DRMenuType.register("stove", () -> IForgeMenuType.create(StoveContainer::new));


    public static RegistryObject<Block> BAMBOO_TRAY = ModBlocks.register("bamboo_tray", () -> new BambooTrayBlock(Block.Properties.copy(Blocks.BAMBOO).strength(0.5F).noOcclusion().offsetType(BlockBehaviour.OffsetType.NONE)));
    public static RegistryObject<Block> STONE_CATAPULT_BOARD_WITH_TRAY = ModBlocks.register("stone_catapult_board_with_tray", () -> new CatapultBoardBlockWithTray(Block.Properties.copy(Blocks.STONE).sound(SoundType.BAMBOO).strength(1.0F).noOcclusion()));
    public static RegistryObject<Item> BAMBOO_TRAY_ITEM = ModItems.register("bamboo_tray", () -> new BlockItem(BAMBOO_TRAY.get(), new Item.Properties()));
    public static RegistryObject<BlockEntityType<BambooTrayTileEntity>> BAMBOO_TRAY_TYPE = DRBlockEntities.register("bamboo_tray",
            () -> BlockEntityType.Builder.of(BambooTrayTileEntity::new, BAMBOO_TRAY.get(),STONE_CATAPULT_BOARD_WITH_TRAY.get()).build(null));
    public static RegistryObject<MenuType<BambooTrayContainer>> BAMBOO_TRAY_CONTAINER = DRMenuType.register("bamboo_tray", () -> IForgeMenuType.create(BambooTrayContainer::new));


    public static RegistryObject<Block> DRINK_MAKER = ModBlocks.register("drink_maker", () -> new DrinkMakerBlock(Block.Properties.copy(Blocks.OAK_WOOD).strength(0.5F).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static RegistryObject<Item> DRINK_MAKER_ITEM = ModItems.register("drink_maker", () -> new DrinkMakerItem(DRINK_MAKER.get(), new Item.Properties()));
    public static RegistryObject<BlockEntityType<DrinkMakerTileEntity>> DRINK_MAKER_TYPE = DRBlockEntities.register("drink_maker",
            () -> BlockEntityType.Builder.of(DrinkMakerTileEntity::new, DRINK_MAKER.get()).build(null));
    public static RegistryObject<MenuType<DrinkMakerContainer>> DRINK_MAKER_CONTAINER = DRMenuType.register("drink_maker", () -> IForgeMenuType.create(DrinkMakerContainer::new));


    public static RegistryObject<Block> STONE_MILL = ModBlocks.register("stone_mill", () -> new StoneMillBlock(Block.Properties.copy(Blocks.STONE).strength(1.5F).noOcclusion()));
    public static RegistryObject<Item> STONE_MILL_ITEM = ModItems.register("stone_mill", () -> new BlockItem(STONE_MILL.get(), new Item.Properties()));
    public static RegistryObject<BlockEntityType<StoneMillTileEntity>> STONE_MILL_TYPE = DRBlockEntities.register("stone_mill",
            () -> BlockEntityType.Builder.of(StoneMillTileEntity::new, STONE_MILL.get()).build(null));
    public static RegistryObject<MenuType<StoneMillContainer>> STONE_MILL_CONTAINER = DRMenuType.register("stone_mill", () -> IForgeMenuType.create(StoneMillContainer::new));


    public static RegistryObject<Block> STONE_ROLLER = ModBlocks.register("stone_roller", () -> new StoneRollerBlock(Block.Properties.copy(Blocks.STONE).strength(1.5F).noOcclusion()));
    public static RegistryObject<Item> STONE_ROLLER_ITEM = ModItems.register("stone_roller", () -> new BlockItem(STONE_ROLLER.get(), new Item.Properties()));
    public static RegistryObject<BlockEntityType<StoneRollerTileEntity>> STONE_ROLLER_TYPE = DRBlockEntities.register("stone_roller",
            () -> BlockEntityType.Builder.of(StoneRollerTileEntity::new, STONE_ROLLER.get()).build(null));
    public static RegistryObject<MenuType<StoneRollerContainer>> STONE_ROLLER_CONTAINER = DRMenuType.register("stone_roller", () -> IForgeMenuType.create(StoneRollerContainer::new));


    public static RegistryObject<Block> TEAPOT = ModBlocks.register("porcelain_teapot", () -> new TeapotBlock(Block.Properties.copy(Blocks.STONE).strength(3.5F).noOcclusion()));
    public static RegistryObject<TeapotItem> PORCELAIN_TEAPOT = ModItems.register("porcelain_teapot", () -> new TeapotItem(TEAPOT.get(), new Item.Properties().stacksTo(1), 1000, false));
    public static RegistryObject<BlockEntityType<TeapotTileEntity>> TEAPOT_TYPE = DRBlockEntities.register("porcelain_teapot",
            () -> BlockEntityType.Builder.of((a, b) -> new TeapotTileEntity(1000, a, b), TEAPOT.get()).build(null));

    public static RegistryObject<Block> IRON_KETTLE = ModBlocks.register("iron_kettle", () -> new IronKettleBlock(Block.Properties.copy(Blocks.IRON_BLOCK).strength(3.5F).noOcclusion()));
    public static RegistryObject<IronKettleItem> IRON_KETTLE_ITEM = ModItems.register("iron_kettle", () -> new IronKettleItem(IRON_KETTLE.get(), new Item.Properties().stacksTo(1), 2000));
    public static RegistryObject<BlockEntityType<TeapotTileEntity>> IRON_KETTLE_TYPE = DRBlockEntities.register("iron_kettle",
            () -> BlockEntityType.Builder.of((a, b) -> new TeapotTileEntity(2000, a, b), IRON_KETTLE.get()).build(null));

    public static RegistryObject<Block> WOODEN_TRAY = ModBlocks.register("wooden_tray", () -> new WoodenTrayBlock(Block.Properties.copy(Blocks.OAK_WOOD).noOcclusion()));
    public static RegistryObject<BlockItem> WOODEN_TRAY_ITEM = ModItems.register("wooden_tray", () -> new BlockItem(WOODEN_TRAY.get(), new Item.Properties().stacksTo(1)));
    public static RegistryObject<BlockEntityType<TeaCupTileEntity>> WOODEN_TRAY_TYPE = DRBlockEntities.register("wooden_tray",
            () -> BlockEntityType.Builder.of((a, b) -> new TeaCupTileEntity(250, a, b), WOODEN_TRAY.get()).build(null));


    public static RegistryObject<Block> WOODEN_BARREL = ModBlocks.register("wooden_barrel", () -> new WoodenBarrelBlock(Block.Properties.copy(Blocks.OAK_WOOD).strength(0.6F).noOcclusion()));
    public static RegistryObject<BlockItem> WOODEN_BARREL_ITEM = ModItems.register("wooden_barrel", () -> new BlockItem(WOODEN_BARREL.get(), new Item.Properties().stacksTo(1)));
    public static RegistryObject<BlockEntityType<WoodenBarrelTileEntity>> WOODEN_BARREL_TYPE = DRBlockEntities.register("wooden_barrel",
            () -> BlockEntityType.Builder.of(WoodenBarrelTileEntity::new, IRON_KETTLE.get()).build(null));


}
