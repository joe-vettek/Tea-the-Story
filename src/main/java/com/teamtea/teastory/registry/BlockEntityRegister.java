package com.teamtea.teastory.registry;

import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.block.craft.*;
import com.teamtea.teastory.block.drink.IronKettleBlock;
import com.teamtea.teastory.block.drink.TeapotBlock;
import com.teamtea.teastory.block.drink.WoodenTrayBlock;
import com.teamtea.teastory.blockentity.*;
import com.teamtea.teastory.item.IronKettleItem;
import com.teamtea.teastory.item.TeapotItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityRegister {
    public static final DeferredRegister<BlockEntityType<?>> DRBlockEntities = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TeaStory.MODID);
    public static final DeferredRegister<Item> ModItems = DeferredRegister.create(Registries.ITEM, TeaStory.MODID);
    public static final DeferredRegister<Block> ModBlocks = DeferredRegister.create(Registries.BLOCK, TeaStory.MODID);
    public static final DeferredRegister<MenuType<?>> DRMenuType = DeferredRegister.create(Registries.MENU, TeaStory.MODID);


    public static DeferredHolder<Block, TeapotBlock> TEAPOT = ModBlocks.register("porcelain_teapot", () -> new TeapotBlock(Block.Properties.ofFullCopy(Blocks.STONE).strength(3.5F).noOcclusion()));
    public static DeferredHolder<Item, TeapotItem> PORCELAIN_TEAPOT = ModItems.register("porcelain_teapot", () -> new TeapotItem(TEAPOT.get(), new Item.Properties().stacksTo(1), 1000, false));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<TeapotBlockEntity>> TEAPOT_TYPE = DRBlockEntities.register("porcelain_teapot",
            () -> new BlockEntityType<>((a, b) -> new TeapotBlockEntity(1000, a, b), TEAPOT.get()));

    public static DeferredHolder<Block, IronKettleBlock> IRON_KETTLE = ModBlocks.register("iron_kettle", () -> new IronKettleBlock(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(3.5F).noOcclusion()));
    public static DeferredHolder<Item, IronKettleItem> IRON_KETTLE_ITEM = ModItems.register("iron_kettle", () -> new IronKettleItem(IRON_KETTLE.get(), new Item.Properties().stacksTo(1), 2000));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<TeapotBlockEntity>> IRON_KETTLE_TYPE = DRBlockEntities.register("iron_kettle",
            () -> new BlockEntityType<>((a, b) -> new TeapotBlockEntity(2000, a, b), IRON_KETTLE.get()));

    public static DeferredHolder<Block, WoodenTrayBlock> WOODEN_TRAY = ModBlocks.register("wooden_tray", () -> new WoodenTrayBlock(Block.Properties.ofFullCopy(Blocks.OAK_WOOD).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> WOODEN_TRAY_ITEM = ModItems.register("wooden_tray", () -> new BlockItem(WOODEN_TRAY.get(), new Item.Properties().stacksTo(1)));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<TeaCupBlockEntity>> WOODEN_TRAY_TYPE = DRBlockEntities.register("wooden_tray",
            () -> new BlockEntityType<>((a, b) -> new TeaCupBlockEntity(250, a, b), WOODEN_TRAY.get()));


    public static DeferredHolder<Block, WoodenBarrelBlock> WOODEN_BARREL = ModBlocks.register("wooden_barrel", () -> new WoodenBarrelBlock(Block.Properties.ofFullCopy(Blocks.OAK_WOOD).strength(0.6F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> WOODEN_BARREL_ITEM = ModItems.register("wooden_barrel", () -> new BlockItem(WOODEN_BARREL.get(), new Item.Properties().stacksTo(1)));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<WoodenBarrelBlockEntity>> WOODEN_BARREL_TYPE = DRBlockEntities.register("wooden_barrel",
            () -> new BlockEntityType<>(WoodenBarrelBlockEntity::new, WOODEN_BARREL.get()));

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<StoneCampfireBlockEntity>> stone_campfire_TYPE = DRBlockEntities.register("stone_campfire",
            () -> new BlockEntityType<>(StoneCampfireBlockEntity::new, BlockRegister.stone_campfire.get()));

}
