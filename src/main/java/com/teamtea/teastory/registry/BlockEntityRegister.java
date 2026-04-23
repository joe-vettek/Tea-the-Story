package com.teamtea.teastory.registry;

import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.block.craft.*;
import com.teamtea.teastory.blockentity.*;
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

    public static DeferredHolder<Block, WoodenBarrelBlock> WOODEN_BARREL = ModBlocks.register("wooden_barrel", () -> new WoodenBarrelBlock(Block.Properties.ofFullCopy(Blocks.OAK_WOOD).strength(0.6F).noOcclusion()));
    public static DeferredHolder<Item, BlockItem> WOODEN_BARREL_ITEM = ModItems.register("wooden_barrel", () -> new BlockItem(WOODEN_BARREL.get(), new Item.Properties().stacksTo(1)));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<WoodenBarrelBlockEntity>> WOODEN_BARREL_TYPE = DRBlockEntities.register("wooden_barrel",
            () -> new BlockEntityType<>(WoodenBarrelBlockEntity::new, WOODEN_BARREL.get()));

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<StoneCampfireBlockEntity>> stone_campfire_TYPE = DRBlockEntities.register("stone_campfire",
            () -> new BlockEntityType<>(StoneCampfireBlockEntity::new, BlockRegister.stone_campfire.get()));

}
