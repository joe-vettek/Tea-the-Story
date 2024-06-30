package xueluoanping.teastory;

import cloud.lemonslice.teastory.block.craft.StoneMillBlock;
import cloud.lemonslice.teastory.blockentity.StoneMillTileEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TileEntityTypeRegistry {
    public static final DeferredRegister<BlockEntityType<?>> DRBlockEntities = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TeaStory.MODID);
    public static final DeferredRegister<Item> ModItems = DeferredRegister.create(ForgeRegistries.ITEMS, TeaStory.MODID);
    public static final DeferredRegister<Block> ModBlocks = DeferredRegister.create(ForgeRegistries.BLOCKS, TeaStory.MODID);

    public static RegistryObject<Block> STONE_MILL = ModBlocks.register("stone_mill", () -> new StoneMillBlock(Block.Properties.copy(Blocks.GRASS).strength(0.6F)));
    public static RegistryObject<Item> STONE_MILL_ITEM = ModItems.register("stone_mill", () -> new BlockItem(STONE_MILL.get(), new Item.Properties()));
    public static RegistryObject<BlockEntityType<StoneMillTileEntity>> STONE_MILL_TYPE = DRBlockEntities.register("stone_mill",
            () -> BlockEntityType.Builder.of(StoneMillTileEntity::new, STONE_MILL.get()).build(null));

}
