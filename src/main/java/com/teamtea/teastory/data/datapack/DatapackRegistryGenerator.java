package com.teamtea.teastory.data.datapack;


import com.teamtea.eclipticseasons.common.registry.ESRegistries;
import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.data.compat.TSSnowDefinitionProvider;
import com.teamtea.teastory.registry.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;


import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DatapackRegistryGenerator extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder REGISTRY_SET_BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModBiomeFeatures.TeaConfiguredFeature::bootstrap)
            .add(Registries.PLACED_FEATURE, ModBiomeFeatures.TeaPlacedFeature::bootstrap)
            .add(Registries.DAMAGE_TYPE, ModDamageType::bootstrap)
            .add(Registries.JUKEBOX_SONG, ModSong::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
            .add(TeaStoryRegistries.DRINK_EFFECT, DrinkRegistry::bootstrap)
            .add(ESRegistries.SNOW_DEFINITIONS, TSSnowDefinitionProvider::bootstrap2)
            ;

    public DatapackRegistryGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, REGISTRY_SET_BUILDER, Set.of(TeaStory.MODID));
    }

}