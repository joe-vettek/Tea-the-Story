package xueluoanping.teastory.data.loot;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import xueluoanping.teastory.registry.BlockRegister;
import xueluoanping.teastory.loot.AddItemModifier;

import java.util.List;

public class GLMProvider extends GlobalLootModifierProvider {
    public GLMProvider(PackOutput gen, String modid) {
        super(gen, modid);
    }

    @Override
    protected void start() {


        for (var tableResourceKey : List.of(BuiltInLootTables.VILLAGE_DESERT_HOUSE)) {
            LootItemCondition lootItemCondition = LootTableIdCondition.builder(tableResourceKey)
                    .and(LootItemRandomChanceCondition.randomChance(0.5f))
                    .build();
            add(tableResourceKey.getPath() + "/add_" + BlockRegister.GRAPES.getId().getPath(), new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.GRAPES.get(), 2));
        }

        for (var tableResourceKey : List.of(BuiltInLootTables.VILLAGE_SNOWY_HOUSE, BuiltInLootTables.VILLAGE_TAIGA_HOUSE)) {
            LootItemCondition lootItemCondition = LootTableIdCondition.builder(tableResourceKey)
                    .and(LootItemRandomChanceCondition.randomChance(0.5f))
                    .build();
            add(tableResourceKey.getPath() + "/add_" + BlockRegister.CHINESE_CABBAGE_SEEDS.getId().getPath(), new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.CHINESE_CABBAGE_SEEDS.get(), 1));
        }

        for (var tableResourceKey : List.of(BuiltInLootTables.VILLAGE_SAVANNA_HOUSE)) {
            LootItemCondition lootItemCondition = LootTableIdCondition.builder(tableResourceKey)
                    .and(LootItemRandomChanceCondition.randomChance(0.5f))
                    .build();
            add(tableResourceKey.getPath() + "/add_" + BlockRegister.CHILI_SEEDS.getId().getPath(), new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.CHILI_SEEDS.get(), 2));
        }

        for (var tableResourceKey : List.of(BuiltInLootTables.VILLAGE_PLAINS_HOUSE)) {
            LootItemCondition lootItemCondition = LootTableIdCondition.builder(tableResourceKey)
                    .and(LootItemRandomChanceCondition.randomChance(0.5f))
                    .build();
            add(tableResourceKey.getPath() + "/add_" + BlockRegister.RICE_GRAINS.getId().getPath(), new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.RICE_GRAINS.get(), 2));
            add(tableResourceKey.getPath() + "/add_" + BlockRegister.BITTER_GOURDS.getId().getPath(), new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.BITTER_GOURDS.get(), 2));
            add(tableResourceKey.getPath() + "/add_" + BlockRegister.CUCUMBERS.getId().getPath(), new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.CUCUMBERS.get(), 2));
        }

        for (var tableResourceKey : List.of(BuiltInLootTables.VILLAGE_PLAINS_HOUSE,BuiltInLootTables.JUNGLE_TEMPLE)) {
            LootItemCondition lootItemCondition = LootTableIdCondition.builder(tableResourceKey)
                    .and(LootItemRandomChanceCondition.randomChance(0.6f))
                    .build();
            add(tableResourceKey.getPath() + "/add_" + BlockRegister.TEA_SEEDS.getId().getPath(), new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.TEA_SEEDS.get(), 2));
        }


        for (Block grass : List.of(Blocks.GRASS, Blocks.TALL_GRASS, Blocks.FERN, Blocks.LARGE_FERN)) {
            LootItemCondition lootItemCondition = LootTableIdCondition.builder(grass.getLootTable())
                    .and(LootItemRandomChanceCondition.randomChance(0.005f))
                    .build();
        }


    }


}
