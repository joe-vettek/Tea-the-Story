package com.teamtea.teastory.data.datamap;

import com.teamtea.teastory.registry.ItemRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class TSDataMapProvider extends DataMapProvider {

    public TSDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        final var compostables = builder(NeoForgeDataMaps.COMPOSTABLES);
        compostables.add(ItemRegister.TEA_RESIDUES, new Compostable(0.2f, true), false);
        compostables.add(ItemRegister.CRUSHED_STRAW, new Compostable(0.3f, true), false);

    }
}
