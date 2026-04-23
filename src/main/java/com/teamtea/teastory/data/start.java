package com.teamtea.teastory.data;


import com.teamtea.teastory.data.advancement.Advancements;
import com.teamtea.teastory.data.datamap.TSDataMapProvider;
import com.teamtea.teastory.data.datapack.DatapackRegistryGenerator;
import com.teamtea.teastory.data.tag.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;

import net.neoforged.neoforge.data.event.GatherDataEvent;
import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.data.lang.Lang_EN;
import com.teamtea.teastory.data.lang.Lang_ZH;
import com.teamtea.teastory.data.loot.GLMProvider;
import com.teamtea.teastory.data.loot.LFTLootTableProvider;
import com.teamtea.teastory.data.recipe.TeaStoryRecipeProvider;

import java.util.concurrent.CompletableFuture;


public final class start {
    public final static String MODID = TeaStory.MODID;

    public static void onDataGather(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        if (event instanceof GatherDataEvent.Server) {

            var blockTags = new TeaStoryBlockTagProvider(packOutput,lookupProvider, MODID);
            generator.addProvider(true,blockTags);
            generator.addProvider(true,new TeaStoryItemTagProvider(packOutput, lookupProvider));
            generator.addProvider(true,new TeaStoryFluidTagProvider(packOutput,lookupProvider, MODID));
            generator.addProvider(true,new TeaStoryEntityTypeTagsProvider(packOutput,lookupProvider, MODID));
            generator.addProvider(true,new TeaStoryBiomeTagProvider(packOutput,lookupProvider, MODID));

            generator.addProvider(true,new TeaStoryRecipeProvider.Runner(packOutput,lookupProvider));
            generator.addProvider(true,new GLMProvider(packOutput,lookupProvider, MODID));

            generator.addProvider(true,new LFTLootTableProvider(packOutput,lookupProvider));
            generator.addProvider(true,new DatapackRegistryGenerator(packOutput,lookupProvider));
            generator.addProvider(true,new Advancements(packOutput,lookupProvider));

            generator.addProvider(true,new TSDataMapProvider(packOutput,lookupProvider));

        }if (event instanceof GatherDataEvent.Client) {
            generator.addProvider(true,new Lang_EN(packOutput));
            generator.addProvider(true,new Lang_ZH(packOutput));
            // generator.addProvider(true, new BlockStatesDataProvider(packOutput));
            // generator.addProvider(true, new TeaItemModelProvider(packOutput, MODID));
            // generator.addProvider(true, new TSModelProvider(packOutput, MODID,lookupProvider));

        }
    }
}
