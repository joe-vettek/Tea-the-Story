package com.teamtea.teastory.registry;

import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.world.configuration.WildCropConfiguration;
import com.teamtea.teastory.world.feature.WildCropFeature;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.commands.PlaceCommand;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.placement.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredRegister;


import java.util.List;
import java.util.function.Supplier;

public class ModBiomeFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, TeaStory.MODID);

    public static final Supplier<Feature<WildCropConfiguration>> WILD_CROP = FEATURES.register("wild_crop", () -> new WildCropFeature(WildCropConfiguration.CODEC));


    public static class TeaConfiguredFeature {
        public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_RICE = createKey("wild_rice");
        public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_CHILI = createKey("wild_chili");
        public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_CHINESE_CABBAGE = createKey("wild_chinese_cabbage");
        public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_GRAPE = createKey("wild_grape");
        public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_CUCUMBER = createKey("wild_cucumber");
        public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_BITTER_GOURD = createKey("wild_bitter_gourd");
        public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_TEA_PLANT = createKey("wild_tea_plant");
        public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_BLOCK_WITH_HOLE = createKey("grass_block_with_hole");

        private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
            return ResourceKey.create(Registries.CONFIGURED_FEATURE, TeaStory.rl(name));
        }

        private static RandomPatchConfiguration grassLikePatch(BlockStateProvider pStateProvider, int pTries) {
            return simpleRandomPatchConfiguration(
                    pTries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(pStateProvider))
            );
        }

        private static RandomPatchConfiguration onDirtCrop(BlockStateProvider pStateProvider, int pTries) {
            var predicate = BlockPredicate.allOf(
                    // BlockPredicate.anyOf(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), Tags.Blocks.MU))
                    BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.MUD, Blocks.CLAY, Blocks.GRAVEL),
                    BlockPredicate.matchesBlocks(Blocks.AIR));
            return simpleRandomPatchConfiguration(
                    pTries, PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(pStateProvider), predicate)
            );
        }

        private static RandomPatchConfiguration onDirtReplace(BlockState blockState, int pTries) {
            var predicate = BlockPredicate.allOf(
                    BlockPredicate.matchesBlocks(Direction.UP.getNormal(), Blocks.AIR),
                    BlockPredicate.matchesBlocks(Blocks.GRASS_BLOCK)
            );
            return simpleRandomPatchConfiguration(
                    pTries, PlacementUtils.filtered(Feature.REPLACE_SINGLE_BLOCK, new ReplaceBlockConfiguration(Blocks.GRASS_BLOCK.defaultBlockState(), blockState), predicate)
            );
        }


        public static RandomPatchConfiguration simpleRandomPatchConfiguration(int pTries, Holder<PlacedFeature> pFeature) {
            return new RandomPatchConfiguration(pTries, 4, 3, pFeature);
        }

        public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
            FeatureUtils.register(context, WILD_RICE, Feature.RANDOM_PATCH, onDirtCrop(BlockStateProvider.simple(BlockRegister.WILD_RICE.get()), 4));
            FeatureUtils.register(context, WILD_CHILI, Feature.RANDOM_PATCH, grassLikePatch(BlockStateProvider.simple(BlockRegister.WILD_CHILI.get()), 4));
            FeatureUtils.register(context, WILD_CHINESE_CABBAGE, Feature.RANDOM_PATCH, grassLikePatch(BlockStateProvider.simple(BlockRegister.WILD_CHINESE_CABBAGE.get()), 5));
            FeatureUtils.register(context, WILD_GRAPE, Feature.RANDOM_PATCH, grassLikePatch(BlockStateProvider.simple(BlockRegister.WILD_GRAPE.get()), 42));
            FeatureUtils.register(context, WILD_CUCUMBER, Feature.RANDOM_PATCH, grassLikePatch(BlockStateProvider.simple(BlockRegister.WILD_CUCUMBER.get()), 68));
            FeatureUtils.register(context, WILD_BITTER_GOURD, Feature.RANDOM_PATCH, grassLikePatch(BlockStateProvider.simple(BlockRegister.WILD_BITTER_GOURD.get()), 72));
            FeatureUtils.register(context, WILD_TEA_PLANT, Feature.RANDOM_PATCH, grassLikePatch(BlockStateProvider.simple(BlockRegister.wild_tea_plant.get()), 6));
            FeatureUtils.register(context, GRASS_BLOCK_WITH_HOLE, Feature.RANDOM_PATCH, onDirtReplace(BlockRegister.GRASS_BLOCK_WITH_HOLE.get().defaultBlockState(), 4));
        }
    }

    public static class TeaPlacedFeature {
        public static final ResourceKey<PlacedFeature> WILD_RICE = createKey("wild_rice");
        public static final ResourceKey<PlacedFeature> WILD_CHILI = createKey("wild_chili");
        public static final ResourceKey<PlacedFeature> WILD_CHINESE_CABBAGE = createKey("wild_chinese_cabbage");
        public static final ResourceKey<PlacedFeature> WILD_GRAPE = createKey("wild_grape");
        public static final ResourceKey<PlacedFeature> WILD_CUCUMBER = createKey("wild_cucumber");
        public static final ResourceKey<PlacedFeature> WILD_BITTER_GOURD = createKey("wild_bitter_gourd");
        public static final ResourceKey<PlacedFeature> WILD_TEA_PLANT = createKey("wild_tea_plant");
        public static final ResourceKey<PlacedFeature> GRASS_BLOCK_WITH_HOLE = createKey("grass_block_with_hole");

        private static ResourceKey<PlacedFeature> createKey(String name) {
            return ResourceKey.create(Registries.PLACED_FEATURE, TeaStory.rl(name));
        }

        public static List<PlacementModifier> worldSurfaceSquaredWithCount(int pCount, int chance) {
            return List.of(CountPlacement.of(pCount), RarityFilter.onAverageOnceEvery(chance), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        }

        public static void bootstrap(BootstrapContext<PlacedFeature> context) {
            HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);
            PlacementUtils.register(context, WILD_RICE, holdergetter.getOrThrow(TeaConfiguredFeature.WILD_RICE), worldSurfaceSquaredWithCount(2, 24));
            PlacementUtils.register(context, WILD_CHILI, holdergetter.getOrThrow(TeaConfiguredFeature.WILD_CHILI), worldSurfaceSquaredWithCount(3, 32));
            PlacementUtils.register(context, WILD_CHINESE_CABBAGE, holdergetter.getOrThrow(TeaConfiguredFeature.WILD_CHINESE_CABBAGE), worldSurfaceSquaredWithCount(4, 28));
            PlacementUtils.register(context, WILD_GRAPE, holdergetter.getOrThrow(TeaConfiguredFeature.WILD_GRAPE), worldSurfaceSquaredWithCount(3, 32));
            PlacementUtils.register(context, WILD_CUCUMBER, holdergetter.getOrThrow(TeaConfiguredFeature.WILD_CUCUMBER), worldSurfaceSquaredWithCount(2, 42));
            PlacementUtils.register(context, WILD_BITTER_GOURD, holdergetter.getOrThrow(TeaConfiguredFeature.WILD_BITTER_GOURD), worldSurfaceSquaredWithCount(3, 32));
            PlacementUtils.register(context, WILD_TEA_PLANT, holdergetter.getOrThrow(TeaConfiguredFeature.WILD_TEA_PLANT), worldSurfaceSquaredWithCount(5, 24));
            PlacementUtils.register(context, GRASS_BLOCK_WITH_HOLE, holdergetter.getOrThrow(TeaConfiguredFeature.GRASS_BLOCK_WITH_HOLE), worldSurfaceSquaredWithCount(3, 20));

        }
    }

}
