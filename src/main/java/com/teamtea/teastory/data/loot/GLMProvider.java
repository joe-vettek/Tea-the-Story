package com.teamtea.teastory.data.loot;

import com.teamtea.teastory.TeaStory;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import com.teamtea.teastory.registry.BlockRegister;
import com.teamtea.teastory.loot.AddItemModifier;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GLMProvider extends GlobalLootModifierProvider {
    public GLMProvider(PackOutput gen, CompletableFuture<HolderLookup.Provider> registries, String modid) {
        super(gen, registries, modid);
    }


    @Override
    protected void start() {

        for (var tableResourceKey : List.of(BuiltInLootTables.VILLAGE_DESERT_HOUSE)) {
            LootItemCondition lootItemCondition = LootTableIdCondition.builder(tableResourceKey.location())
                    .and(LootItemRandomChanceCondition.randomChance(0.5f))
                    .build();
            add(tableResourceKey.location().getPath()+"/add_" + BlockRegister.GRAPES.getId().getPath(), new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.GRAPES.get(), 2));
        }

        for (var tableResourceKey : List.of(BuiltInLootTables.VILLAGE_SNOWY_HOUSE, BuiltInLootTables.VILLAGE_TAIGA_HOUSE)) {
            LootItemCondition lootItemCondition = LootTableIdCondition.builder(tableResourceKey.location())
                    .and(LootItemRandomChanceCondition.randomChance(0.5f))
                    .build();
            add(tableResourceKey.location().getPath()+"/add_" + BlockRegister.CHINESE_CABBAGE_SEEDS.getId().getPath(), new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.CHINESE_CABBAGE_SEEDS.get(), 1));
        }

        for (var tableResourceKey : List.of(BuiltInLootTables.VILLAGE_SAVANNA_HOUSE)) {
            LootItemCondition lootItemCondition = LootTableIdCondition.builder(tableResourceKey.location())
                    .and(LootItemRandomChanceCondition.randomChance(0.5f))
                    .build();
            add(tableResourceKey.location().getPath()+"/add_" + BlockRegister.CHILI_SEEDS.getId().getPath(), new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.CHILI_SEEDS.get(), 2));
        }

        for (var tableResourceKey : List.of(BuiltInLootTables.VILLAGE_PLAINS_HOUSE)) {
            LootItemCondition lootItemCondition = LootTableIdCondition.builder(tableResourceKey.location())
                    .and(LootItemRandomChanceCondition.randomChance(0.5f))
                    .build();
            add(tableResourceKey.location().getPath()+"/add_" + BlockRegister.RICE_GRAINS.getId().getPath(), new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.RICE_GRAINS.get(), 2));
            add(tableResourceKey.location().getPath()+"/add_" + BlockRegister.BITTER_GOURDS.getId().getPath(), new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.BITTER_GOURDS.get(), 2));
            add(tableResourceKey.location().getPath() + "/add_" + BlockRegister.CUCUMBERS.getId().getPath(), new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.CUCUMBERS.get(), 2));
        }


        for (Block grass : List.of(Blocks.SHORT_GRASS, Blocks.TALL_GRASS, Blocks.FERN, Blocks.LARGE_FERN)) {
            LootItemCondition lootItemCondition = LootTableIdCondition.builder(grass.getLootTable().location())
                    .and(LootItemRandomChanceCondition.randomChance(0.005f))
                    .build();
        }

    }


}
