package com.teamtea.teastory.world.modifier;

import com.mojang.serialization.MapCodec;
import com.teamtea.teastory.registry.ModBiomeModifiers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeGenerationSettingsBuilder;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

import java.util.Optional;

public record AddFeaturesByFilterBiomeModifier(
        HolderSet<Biome> allowedBiomes,
        Optional<HolderSet<Biome>> deniedBiomes,
        Optional<Float> minimumTemperature,
        Optional<Float> maximumTemperature,
        Optional<Float> minimumDownfall,
        Optional<Float> maximumDownfall,
        HolderSet<PlacedFeature> features,
        GenerationStep.Decoration step
) implements BiomeModifier {

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD && this.allowedBiomes.contains(biome)) {
            if (deniedBiomes.isPresent() && this.deniedBiomes.get().contains(biome)) {
                return;
            }
            if (minimumTemperature.isPresent() && biome.value().getBaseTemperature() < minimumTemperature.get()) {
                return;
            }
            if (maximumTemperature.isPresent() && biome.value().getBaseTemperature() > maximumTemperature.get()) {
                return;
            }
            if (minimumDownfall.isPresent() && biome.value().getBaseTemperature() < minimumDownfall.get()) {
                return;
            }
            if (maximumDownfall.isPresent() && biome.value().getBaseTemperature() > maximumDownfall.get()) {
                return;
            }
            BiomeGenerationSettingsBuilder generationSettings = builder.getGenerationSettings();
            this.features.forEach(holder -> generationSettings.addFeature(this.step, holder));
        }
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return ModBiomeModifiers.ADD_FEATURES_BY_FILTER.get();
    }
}
