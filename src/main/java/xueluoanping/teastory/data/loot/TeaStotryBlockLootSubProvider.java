package xueluoanping.teastory.data.loot;

import cloud.lemonslice.teastory.block.craft.SaucepanBlock;
import cloud.lemonslice.teastory.block.crops.*;
import cloud.lemonslice.teastory.block.decorations.BambooLatticeBlock;
import cloud.lemonslice.teastory.block.drink.DrinkMakerBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;
import xueluoanping.teastory.BlockRegister;
import xueluoanping.teastory.ItemRegister;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.variant.Planks;


import java.util.Set;

public class TeaStotryBlockLootSubProvider extends BlockLootSubProvider {

    // I‘m not sure if I need a map for myself, but as you see the BlockLoot class have a same one
    // so while you add your block, and then you need to deal with the block you don't need
    // If you don't want to do some extra check, maybe my method is better,
    // so now you need override add,accept method yourself. Don't be lazy.
    // private final Map<ResourceLocation, LootTable.Builder> map = Maps.newHashMap();


    public TeaStotryBlockLootSubProvider(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(),provider);
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
        generateMuiltWall();

        //     add walls drop
    }

    private void generateMuiltWall() {
        add(BlockRegister.BAMBOO_LATTICE.get(), this::createWallBlock);
        add(BlockRegister.FRESH_BAMBOO_WALL.get(), this::createWallBlock);
        add(BlockRegister.DRIED_BAMBOO_WALL.get(), this::createWallBlock);
    }

    private void generateCropsDrops() {

        dropCropBlock(BlockRegister.WATERMELON_VINE.get(), Items.MELON, Items.MELON_SEEDS, MelonVineBlock.AGE, 7);
        dropCropBlock(BlockRegister.GRAPE.get(), BlockRegister.GRAPES.get(), Items.AIR, StemFruitBlock.AGE_0_4, 4);
        dropCropBlock(BlockRegister.CUCUMBER.get(), BlockRegister.CUCUMBERS.get(), Items.AIR, StemFruitBlock.AGE_0_4, 4);
        dropCropBlock(BlockRegister.BITTER_GOURD.get(), BlockRegister.BITTER_GOURDS.get(), Items.AIR, StemFruitBlock.AGE_0_4, 4);
        dropCropBlock(BlockRegister.CHILI_PLANT.get(), BlockRegister.CHILI.get(), BlockRegister.CHILI_SEEDS.get(), ChiliBlock.AGE, 6);
        dropCropBlock(BlockRegister.CHINESE_CABBAGE_PLANT.get(), BlockRegister.CHINESE_CABBAGE.get(), BlockRegister.CHINESE_CABBAGE_SEEDS.get(), ChineseCabbageBlock.AGE, 6);

        add(BlockRegister.wild_tea_plant.get(), this::createMangroveLeavesDrops);
        add(BlockRegister.WILD_GRAPE.get(), this::createMangroveLeavesDrops);

        dropRice();
        dropTeaPlant();
    }

    private void dropTeaPlant() {
        add(BlockRegister.tea_plant.get(), (block -> {
            var lootPool=LootTable.lootTable().withPool(
                    this.applyExplosionCondition(block.asItem(),
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(BlockRegister.TEA_SEEDS.get()))));
            for (int i = 2; i <= 11; i++) {
                lootPool= lootPool.withPool(
                        this.applyExplosionCondition(block.asItem(),
                                LootPool.lootPool().setRolls(ConstantValue.exactly(2.0F))
                                        .when(createStateBuilder(block, RicePlantBlock.AGE, i))
                                        .add(LootItem.lootTableItem(Items.STICK))));
            }
            for (int i = 6; i <= 10; i++) {
                lootPool= lootPool.withPool(
                        this.applyExplosionCondition(block.asItem(),
                                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                        .setBonusRolls(ConstantValue.exactly(2.0f))
                                        .when(createStateBuilder(block, RicePlantBlock.AGE, i))
                                        .add(LootItem.lootTableItem(ItemRegister.TEA_LEAVES.get()))));
            }
            lootPool= lootPool.withPool(
                    this.applyExplosionCondition(block.asItem(),
                            LootPool.lootPool().when(createStateBuilder(block, RicePlantBlock.AGE, 11))
                                    .setRolls(ConstantValue.exactly(2.0F))
                                    .setBonusRolls(ConstantValue.exactly(2.0f))
                                    .add(LootItem.lootTableItem(BlockRegister.TEA_SEEDS.get()))));
            lootPool= lootPool.withPool(
                    this.applyExplosionCondition(block.asItem(),
                            LootPool.lootPool().when(createStateBuilder(block, RicePlantBlock.AGE, 11))
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(ItemRegister.TEA_LEAVES.get()))));
            return lootPool;
        }
        ));
    }

    private void dropRice() {

        add(BlockRegister.RiceSeedlingBlock.get(), (block -> {

            var lootPool=LootTable.lootTable().withPool(
                    this.applyExplosionCondition(block.asItem(),
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                    .when(createStateBuilder(block, RiceSeedlingBlock.AGE, 7))
                                    .add(LootItem.lootTableItem(block)))

            );
            for (int i = 0; i < 7; i++) {
                lootPool= lootPool.withPool(
                        this.applyExplosionCondition(block.asItem(),
                                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                        .when(createStateBuilder(block, RiceSeedlingBlock.AGE, i))
                                        .add(LootItem.lootTableItem(BlockRegister.RICE_GRAINS.get())))
                );
            }

            return lootPool;
        }
        ));

        add(BlockRegister.ricePlant.get(), (block -> {

            var lootPool=LootTable.lootTable();
            for (int i = 0; i < 3; i++) {
                lootPool= lootPool.withPool(
                        this.applyExplosionCondition(block.asItem(),
                                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                        .when(createStateBuilder(block, RicePlantBlock.AGE, i))
                                        .add(LootItem.lootTableItem(BlockRegister.riceSeedlings.get()))));
            }
            for (int i = 3; i <= 7; i++) {
                lootPool= lootPool.withPool(
                        this.applyExplosionCondition(block.asItem(),
                                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                        .when(createStateBuilder(block, RicePlantBlock.AGE, i))
                                        .add(LootItem.lootTableItem(ItemRegister.DRY_STRAW.get()))));
            }
            lootPool= lootPool.withPool(
                    this.applyExplosionCondition(block.asItem(),
                            LootPool.lootPool().when(createStateBuilder(block, RicePlantBlock.AGE, 7))
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .setBonusRolls(ConstantValue.exactly(3.0f))
                                    .add(LootItem.lootTableItem(BlockRegister.RICE_GRAINS.get()))));
            return lootPool;
        }
        ));

    }


    private void generateBSDrops() {
        add(BlockRegister.saucepan.get(), (block -> createSinglePropertyBlock(block, SaucepanBlock.LID)));
        add(BlockRegister.CHRYSANTHEMUM.get(), (block -> createSinglePropertyBlock(block, HybridizableFlowerBlock.FLOWER_COLOR)));
        add(BlockRegister.HYACINTH.get(), (block -> createSinglePropertyBlock(block, HybridizableFlowerBlock.FLOWER_COLOR)));
        add(BlockRegister.ZINNIA.get(), (block -> createSinglePropertyBlock(block, HybridizableFlowerBlock.FLOWER_COLOR)));

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

    protected void dropCropBlock(@NotNull Block block, Item melon, Item melonSeeds, IntegerProperty age, int i) {
        LootItemCondition.Builder builder =
                createStateBuilder(block, age, i);
        add(block, createCropDrops(block, melon, melonSeeds, builder));
    }


    protected LootTable.Builder createDrinkMakerBlock(Block pBlock) {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(pBlock, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(pBlock).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DrinkMakerBlock.LEFT, true))))));
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

    public LootTable.Builder createWallBlock(Block block) {
        return LootTable.lootTable()
                .withPool(
                        this.applyExplosionCondition(block.asItem(), LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                .when(createStateBuilder(block, BambooLatticeBlock.EAST, true))
                                .add(LootItem.lootTableItem(block)))

                ).withPool(
                        this.applyExplosionCondition(block.asItem(), LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                .when(createStateBuilder(block, BambooLatticeBlock.NORTH, true))
                                .add(LootItem.lootTableItem(block)))

                ).withPool(
                        this.applyExplosionCondition(block.asItem(), LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                .when(createStateBuilder(block, BambooLatticeBlock.WEST, true))
                                .add(LootItem.lootTableItem(block)))

                ).withPool(
                        this.applyExplosionCondition(block.asItem(), LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                .when(createStateBuilder(block, BambooLatticeBlock.SOUTH, true))
                                .add(LootItem.lootTableItem(block)))

                );
    }


    public static <T extends Comparable<T>> LootItemBlockStatePropertyCondition.Builder createStateBuilder(Block block, Property<T> pProperty, T value) {
        return LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(pProperty, value.toString()));
    }


}
