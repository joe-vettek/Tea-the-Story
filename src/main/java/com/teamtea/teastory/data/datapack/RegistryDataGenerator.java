package com.teamtea.teastory.data.datapack;


import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.registry.ModBiomeFeatures;
import com.teamtea.teastory.registry.ModBiomeModifiers;
import com.teamtea.teastory.registry.ModDamageType;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;


import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModBiomeFeatures.TeaConfiguredFeature::bootstrap)
            .add(Registries.PLACED_FEATURE, ModBiomeFeatures.TeaPlacedFeature::bootstrap)
            .add(Registries.DAMAGE_TYPE, ModDamageType::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
            ;

    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(TeaStory.MODID));
    }

}