package com.teamtea.teastory.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.world.modifier.AddFeaturesByFilterBiomeModifier;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.holdersets.AndHolderSet;
import net.neoforged.neoforge.registries.holdersets.OrHolderSet;


import java.util.Optional;
import java.util.function.Supplier;

public class ModBiomeModifiers {
    public static DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.BIOME_MODIFIER_SERIALIZERS, TeaStory.MODID);

    public static Supplier<MapCodec<AddFeaturesByFilterBiomeModifier>> ADD_FEATURES_BY_FILTER = BIOME_MODIFIER_SERIALIZERS.register("add_features_by_filter", () ->
            RecordCodecBuilder.mapCodec(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("allowed_biomes").forGetter(AddFeaturesByFilterBiomeModifier::allowedBiomes),
                    Biome.LIST_CODEC.optionalFieldOf("denied_biomes").orElse(Optional.empty()).forGetter(AddFeaturesByFilterBiomeModifier::deniedBiomes),
                    Codec.FLOAT.optionalFieldOf("min_temperature").orElse(Optional.empty()).forGetter(AddFeaturesByFilterBiomeModifier::minimumTemperature),
                    Codec.FLOAT.optionalFieldOf("max_temperature").orElse(Optional.empty()).forGetter(AddFeaturesByFilterBiomeModifier::maximumTemperature),
                    Codec.FLOAT.optionalFieldOf("min_downfall").orElse(Optional.empty()).forGetter(AddFeaturesByFilterBiomeModifier::minimumDownfall),
                    Codec.FLOAT.optionalFieldOf("max_downfall").orElse(Optional.empty()).forGetter(AddFeaturesByFilterBiomeModifier::maximumDownfall),
                    PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(AddFeaturesByFilterBiomeModifier::features),
                    GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(AddFeaturesByFilterBiomeModifier::step)
            ).apply(builder, AddFeaturesByFilterBiomeModifier::new)));


    public static final ResourceKey<BiomeModifier> WILD_RICE = createKey("wild_rice");
    public static final ResourceKey<BiomeModifier> WILD_CHILI = createKey("wild_chili");
    public static final ResourceKey<BiomeModifier> WILD_CHINESE_CABBAGE = createKey("wild_chinese_cabbage");
    public static final ResourceKey<BiomeModifier> WILD_GRAPE = createKey("wild_grape");
    public static final ResourceKey<BiomeModifier> WILD_CUCUMBER = createKey("wild_cucumber");
    public static final ResourceKey<BiomeModifier> WILD_BITTER_GOURD = createKey("wild_bitter_gourd");
    public static final ResourceKey<BiomeModifier> WILD_TEA_PLANT = createKey("wild_tea_plant");

    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, TeaStory.rl(name));
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatureHolderGetter = context.lookup(Registries.PLACED_FEATURE);
        context.register(WILD_RICE, new AddFeaturesByFilterBiomeModifier(
                new OrHolderSet<>(holderGetter.getOrThrow(Tags.Biomes.IS_RIVER), holderGetter.getOrThrow(Tags.Biomes.IS_SWAMP)),
                Optional.empty(),
                Optional.of(0.3f),
                Optional.empty(),
                Optional.of(0.5f),
                Optional.empty(),
                HolderSet.direct(placedFeatureHolderGetter.getOrThrow(ModBiomeFeatures.TeaPlacedFeature.WILD_RICE)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(WILD_CHILI, new AddFeaturesByFilterBiomeModifier(
                new AndHolderSet<>(holderGetter.getOrThrow(Tags.Biomes.IS_WET_OVERWORLD), new OrHolderSet<>(holderGetter.getOrThrow(Tags.Biomes.IS_FOREST), holderGetter.getOrThrow(Tags.Biomes.IS_PLAINS))),
                Optional.empty(),
                Optional.of(0.35f),
                Optional.empty(),
                Optional.of(0.4f),
                Optional.of(0.9f),
                HolderSet.direct(placedFeatureHolderGetter.getOrThrow(ModBiomeFeatures.TeaPlacedFeature.WILD_CHILI)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(WILD_CHINESE_CABBAGE, new AddFeaturesByFilterBiomeModifier(
                holderGetter.getOrThrow(Tags.Biomes.IS_COLD_OVERWORLD),
                Optional.empty(),
                Optional.of(0.0f),
                Optional.of(0.55f),
                Optional.empty(),
                Optional.empty(),
                HolderSet.direct(placedFeatureHolderGetter.getOrThrow(ModBiomeFeatures.TeaPlacedFeature.WILD_CHINESE_CABBAGE)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(WILD_GRAPE, new AddFeaturesByFilterBiomeModifier(
                new OrHolderSet<>(holderGetter.getOrThrow(Tags.Biomes.IS_DRY_OVERWORLD), holderGetter.getOrThrow(Tags.Biomes.IS_FLORAL)),
                Optional.empty(),
                Optional.of(0.1f),
                Optional.of(0.85f),
                Optional.empty(),
                Optional.empty(),
                HolderSet.direct(placedFeatureHolderGetter.getOrThrow(ModBiomeFeatures.TeaPlacedFeature.WILD_GRAPE)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(WILD_CUCUMBER, new AddFeaturesByFilterBiomeModifier(
                new OrHolderSet<>(holderGetter.getOrThrow(Tags.Biomes.IS_PLAINS), holderGetter.getOrThrow(Tags.Biomes.IS_FOREST)),
                Optional.empty(),
                Optional.of(0.15f),
                Optional.of(0.95f),
                Optional.of(0.4f),
                Optional.of(0.9f),
                HolderSet.direct(placedFeatureHolderGetter.getOrThrow(ModBiomeFeatures.TeaPlacedFeature.WILD_CUCUMBER)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(WILD_BITTER_GOURD, new AddFeaturesByFilterBiomeModifier(
                new AndHolderSet<>(holderGetter.getOrThrow(Tags.Biomes.IS_HOT_OVERWORLD), holderGetter.getOrThrow(Tags.Biomes.IS_FOREST)),
                Optional.empty(),
                Optional.of(0.1f),
                Optional.empty(),
                Optional.of(0.45f),
                Optional.of(0.9f),
                HolderSet.direct(placedFeatureHolderGetter.getOrThrow(ModBiomeFeatures.TeaPlacedFeature.WILD_BITTER_GOURD)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(WILD_TEA_PLANT, new AddFeaturesByFilterBiomeModifier(
                new AndHolderSet<>(holderGetter.getOrThrow(Tags.Biomes.IS_HILL),holderGetter.getOrThrow(Tags.Biomes.IS_WET_OVERWORLD)),
                Optional.empty(),
                Optional.of(0.1f),
                Optional.empty(),
                Optional.of(0.45f),
                Optional.of(0.9f),
                HolderSet.direct(placedFeatureHolderGetter.getOrThrow(ModBiomeFeatures.TeaPlacedFeature.WILD_TEA_PLANT)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

    }
}
