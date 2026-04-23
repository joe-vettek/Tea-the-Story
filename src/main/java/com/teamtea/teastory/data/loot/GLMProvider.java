package com.teamtea.teastory.data.loot;

import com.teamtea.teastory.TeaStory;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
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
        for (var tableResourceKey : List.of(BuiltInLootTables.VILLAGE_PLAINS_HOUSE)) {
            LootItemCondition lootItemCondition = LootTableIdCondition.builder(tableResourceKey.identifier())
                    .and(LootItemRandomChanceCondition.randomChance(0.5f))
                    .build();
            add(tableResourceKey.identifier().getPath() + "/add_" + BlockRegister.RICE_GRAINS.getId().getPath(), new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.RICE_GRAINS.get(), 2));
          }

        for (var tableResourceKey : List.of(BuiltInLootTables.VILLAGE_PLAINS_HOUSE,BuiltInLootTables.JUNGLE_TEMPLE)) {
            LootItemCondition lootItemCondition = LootTableIdCondition.builder(tableResourceKey.identifier())
                    .and(LootItemRandomChanceCondition.randomChance(0.6f))
                    .build();
            add(tableResourceKey.identifier().getPath() + "/add_" + BlockRegister.TEA_SEEDS.getId().getPath(), new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.TEA_SEEDS.get(), 2));
        }


        for (Block grass : List.of(Blocks.SHORT_GRASS, Blocks.TALL_GRASS, Blocks.FERN, Blocks.LARGE_FERN)) {
            LootItemCondition lootItemCondition = LootTableIdCondition.builder(grass.getLootTable().get().identifier())
                    .and(LootItemRandomChanceCondition.randomChance(0.005f))
                    .build();
        }

    }


}
