package xueluoanping.teastory.data.loot;

import com.google.common.collect.Maps;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import xueluoanping.teastory.BlockRegister;
import xueluoanping.teastory.TileEntityTypeRegistry;


import java.util.Map;
import java.util.Set;

public class TeaStotryBlockLootSubProvider extends BlockLootSubProvider {

    // I‘m not sure if I need a map for myself, but as you see the BlockLoot class have a same one
    // so while you add your block, and then you need to deal with the block you don't need
    // If you don't want to do some extra check, maybe my method is better,
    // so now you need override add,accept method yourself. Don't be lazy.
    private final Map<ResourceLocation, LootTable.Builder> map = Maps.newHashMap();


    public TeaStotryBlockLootSubProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }


    // @Override
    // protected void add(Block block, LootTable.Builder builder) {
    //     this.map.put(block.getLootTable(), builder);
    //
    // }

    @Override
    protected void generate() {
        generateSelfDrops();

    }

    private void generateSelfDrops() {
        dropSelf(TileEntityTypeRegistry.BAMBOO_TRAY.get());
        dropSelf(TileEntityTypeRegistry.STONE_MILL.get());
        dropSelf(TileEntityTypeRegistry.STONE_ROLLER.get());
        dropSelf(TileEntityTypeRegistry.DIRT_STOVE.get());
        dropSelf(TileEntityTypeRegistry.STONE_STOVE.get());
        dropSelf(TileEntityTypeRegistry.WOODEN_TRAY.get());

        dropSelf(BlockRegister.WOODEN_FRAME.get());
        dropSelf(BlockRegister.BAMBOO_LANTERN.get());

        createDoorTable(BlockRegister.BAMBOO_DOOR.get()).build();
        createDoorTable(BlockRegister.BAMBOO_GLASS_DOOR.get()).build();

    }

    // @Override
    // protected Iterable<Block> getKnownBlocks() {
    //     return ModContents.DREntityBlocks.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toList());
    // }


    public LootTable.Builder createSingleDrawerTable(ItemLike item) {
        return LootTable.lootTable()
                .withPool(
                        this.applyExplosionCondition(item.asItem(), LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(item))
                        )).apply(CopyNbtFunction
                        .copyData(ContextNbtProvider.BLOCK_ENTITY)
                        .copy("tanks","tanks")
                        .copy("Upgrades","Upgrades")
                        .copy("Lock","Lock")
                        .copy("Shr","Shr")
                        .copy("Qua","Qua")
                );
    }


}
