package xueluoanping.teastory.data.loot;

import cloud.lemonslice.teastory.block.craft.SaucepanBlock;
import cloud.lemonslice.teastory.block.crops.HybridizableFlowerBlock;
import cloud.lemonslice.teastory.block.crops.MelonVineBlock;
import cloud.lemonslice.teastory.block.drink.DrinkMakerBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarrotBlock;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import xueluoanping.teastory.BlockRegister;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.variant.Planks;


import java.util.Set;

public class TeaStotryBlockLootSubProvider extends BlockLootSubProvider {

    // I‘m not sure if I need a map for myself, but as you see the BlockLoot class have a same one
    // so while you add your block, and then you need to deal with the block you don't need
    // If you don't want to do some extra check, maybe my method is better,
    // so now you need override add,accept method yourself. Don't be lazy.
    // private final Map<ResourceLocation, LootTable.Builder> map = Maps.newHashMap();


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
        generateDoubleBlockDrops();
        generateDropOthers();
        generateBSDrops();
        generateCropsDrops();

    //     add walls drop
    }

    private void generateCropsDrops() {
        LootItemCondition.Builder lootitemcondition$builder2 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegister.WATERMELON_VINE.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MelonVineBlock.AGE, 7));
        add(BlockRegister.WATERMELON_VINE.get(),(b)->createCropDrops(b, Items.MELON,Items.MELON_SEEDS,lootitemcondition$builder2));
    }

    private void generateBSDrops() {
        add(BlockRegister.saucepan.get(),(block ->createSinglePropertyBlock(block, SaucepanBlock.LID)));
        add(BlockRegister.CHRYSANTHEMUM.get(),(block ->createSinglePropertyBlock(block, HybridizableFlowerBlock.FLOWER_COLOR)));
        add(BlockRegister.HYACINTH.get(),(block ->createSinglePropertyBlock(block, HybridizableFlowerBlock.FLOWER_COLOR)));
        add(BlockRegister.ZINNIA.get(),(block ->createSinglePropertyBlock(block, HybridizableFlowerBlock.FLOWER_COLOR)));

    }

    private void generateDropOthers() {
        dropOther(BlockRegister.cobblestoneAqueduct.get(), Blocks.COBBLESTONE);
        dropOther(BlockRegister.mossyCobblestoneAqueduct.get(), Blocks.MOSSY_COBBLESTONE);

        dropOther(BlockRegister.dirtAqueduct.get(), Blocks.DIRT);
        dropOther(BlockRegister.paddyField.get(), Blocks.DIRT);
    }

    private void generateDoubleBlockDrops() {
        add(BlockRegister.BAMBOO_DOOR.get(), this::createDoorTable);
        add(BlockRegister.BAMBOO_GLASS_DOOR.get(), this::createDoorTable);
        add(BlockRegister.SCARECROW.get(), this::createDoorTable);
        add(BlockRegister.WET_HAYSTACK.get(), this::createDoorTable);
        add(BlockRegister.DRY_HAYSTACK.get(), this::createDoorTable);
        add(TileEntityTypeRegistry.DRINK_MAKER.get(), this::createDrinkMakerBlock);
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
        dropSelf(BlockRegister.BAMBOO_CHAIR.get());
        dropSelf(BlockRegister.WOODEN_CHAIR.get());
        dropSelf(BlockRegister.STONE_CHAIR.get());
        dropSelf(BlockRegister.BAMBOO_TABLE.get());
        dropSelf(BlockRegister.WOODEN_TABLE.get());
        dropSelf(BlockRegister.STONE_TABLE.get());

        dropSelf(BlockRegister.filter_screen.get());
        dropSelf(BlockRegister.STONE_CATAPULT_BOARD.get());
        dropSelf(BlockRegister.BAMBOO_CATAPULT_BOARD.get());
        dropSelf(BlockRegister.IRON_CATAPULT_BOARD.get());

        Planks.resourceLocationBlockMap.forEach((resourceLocation, plankHolders) -> {
            dropSelf(plankHolders.trellisBlock());
            plankHolders.trellisWithVineBlocks().forEach(trellisWithVineBlock -> {
                dropOther(trellisWithVineBlock,plankHolders.trellisBlock());
            });
        });
    }

    protected LootTable.Builder createDrinkMakerBlock(Block pBlock) {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(pBlock, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(pBlock).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DrinkMakerBlock.LEFT, true))))));
    }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return map.entrySet()
                .stream()
                .map(e -> BuiltInRegistries.BLOCK.stream()
                        .filter(block -> block.getLootTable().equals(e.getKey()))
                        .findFirst()
                        .get())
                .toList();
    }


    public LootTable.Builder createSinglePropertyBlock(Block item, Property<?> pProperty) {
        return LootTable.lootTable()
                .withPool(
                        this.applyExplosionCondition(item.asItem(), LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(item))
                        ))
                .apply(CopyBlockState.copyState(item).copy(pProperty)
                );
    }


}
