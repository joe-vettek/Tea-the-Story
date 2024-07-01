package xueluoanping.teastory;

import cloud.lemonslice.teastory.block.craft.BambooTrayBlock;
import cloud.lemonslice.teastory.block.craft.StoneMillBlock;
import cloud.lemonslice.teastory.block.craft.StoneRollerBlock;
import cloud.lemonslice.teastory.block.craft.StoveBlock;
import cloud.lemonslice.teastory.block.drink.DrinkMakerBlock;
import cloud.lemonslice.teastory.blockentity.*;
import cloud.lemonslice.teastory.container.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
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

    public static RegistryObject<Block> DIRT_STOVE = ModBlocks.register("dirt_stove", () -> new StoveBlock(Block.Properties.copy(Blocks.STONE).strength(3.5F).lightLevel(state -> state.getValue(StoveBlock.LIT) ? 15 : 0),1));
    public static RegistryObject<Item> DIRT_STOVE_ITEM = ModItems.register("dirt_stove", () -> new BlockItem(DIRT_STOVE.get(), new Item.Properties()));

    public static RegistryObject<Block> STONE_STOVE = ModBlocks.register("stone_stove", () -> new StoveBlock(Block.Properties.copy(Blocks.STONE).strength(3.5F).lightLevel(state -> state.getValue(StoveBlock.LIT) ? 15 : 0),2));
    public static RegistryObject<Item> STONE_STOVE_ITEM = ModItems.register("stone_stove", () -> new BlockItem(STONE_STOVE.get(), new Item.Properties()));

    public static RegistryObject<BlockEntityType<StoveTileEntity>> STOVE_TYPE = DRBlockEntities.register("stove",
            () -> BlockEntityType.Builder.of(StoveTileEntity::new, DIRT_STOVE.get(), STONE_STOVE.get()).build(null));
    public static  RegistryObject<MenuType<StoveContainer>> STOVE_CONTAINER = DRMenuType.register("stove", () -> IForgeMenuType.create(StoveContainer::new));

    
    public static RegistryObject<Block> BAMBOO_TRAY = ModBlocks.register("bamboo_tray", () -> new BambooTrayBlock(Block.Properties.copy(Blocks.BAMBOO).strength(0.5F).noOcclusion()));
    public static RegistryObject<Item> BAMBOO_TRAY_ITEM = ModItems.register("bamboo_tray", () -> new BlockItem(BAMBOO_TRAY.get(), new Item.Properties()));
    public static RegistryObject<BlockEntityType<BambooTrayTileEntity>> BAMBOO_TRAY_TYPE = DRBlockEntities.register("bamboo_tray",
            () -> BlockEntityType.Builder.of(BambooTrayTileEntity::new, BAMBOO_TRAY.get()).build(null));
    public static  RegistryObject<MenuType<BambooTrayContainer>> BAMBOO_TRAY_CONTAINER = DRMenuType.register("bamboo_tray", () -> IForgeMenuType.create(BambooTrayContainer::new));


    public static RegistryObject<Block> DRINK_MAKER = ModBlocks.register("drink_maker", () -> new DrinkMakerBlock(Block.Properties.copy(Blocks.OAK_WOOD).strength(0.5F).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static RegistryObject<Item> DRINK_MAKER_ITEM = ModItems.register("drink_maker", () -> new BlockItem(DRINK_MAKER.get(), new Item.Properties()));
    public static RegistryObject<BlockEntityType<DrinkMakerTileEntity>> DRINK_MAKER_TYPE = DRBlockEntities.register("drink_maker",
            () -> BlockEntityType.Builder.of(DrinkMakerTileEntity::new, DRINK_MAKER.get()).build(null));
    public static  RegistryObject<MenuType<DrinkMakerContainer>> DRINK_MAKER_CONTAINER = DRMenuType.register("drink_maker", () -> IForgeMenuType.create(DrinkMakerContainer::new));



    public static RegistryObject<Block> STONE_MILL = ModBlocks.register("stone_mill", () -> new StoneMillBlock(Block.Properties.copy(Blocks.STONE).strength(1.5F).noOcclusion()));
    public static RegistryObject<Item> STONE_MILL_ITEM = ModItems.register("stone_mill", () -> new BlockItem(STONE_MILL.get(), new Item.Properties()));
    public static RegistryObject<BlockEntityType<StoneMillTileEntity>> STONE_MILL_TYPE = DRBlockEntities.register("stone_mill",
            () -> BlockEntityType.Builder.of(StoneMillTileEntity::new, STONE_MILL.get()).build(null));
    public static  RegistryObject<MenuType<StoneMillContainer>> STONE_MILL_CONTAINER = DRMenuType.register("stone_mill", () -> IForgeMenuType.create(StoneMillContainer::new));


    public static RegistryObject<Block> STONE_ROLLER = ModBlocks.register("stone_roller", () -> new StoneRollerBlock(Block.Properties.copy(Blocks.STONE).strength(1.5F).noOcclusion()));
    public static RegistryObject<Item> STONE_ROLLER_ITEM = ModItems.register("stone_roller", () -> new BlockItem(STONE_ROLLER.get(), new Item.Properties()));
    public static RegistryObject<BlockEntityType<StoneRollerTileEntity>> STONE_ROLLER_TYPE = DRBlockEntities.register("stone_roller",
            () -> BlockEntityType.Builder.of(StoneRollerTileEntity::new, STONE_ROLLER.get()).build(null));
    public static  RegistryObject<MenuType<StoneRollerContainer>> STONE_ROLLER_CONTAINER = DRMenuType.register("stone_roller", () -> IForgeMenuType.create(StoneRollerContainer::new));

}
