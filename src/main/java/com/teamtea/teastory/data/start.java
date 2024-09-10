package com.teamtea.teastory.data;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;

import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.data.lang.Lang_EN;
import com.teamtea.teastory.data.lang.Lang_ZH;
import com.teamtea.teastory.data.loot.GLMProvider;
import com.teamtea.teastory.data.loot.LFTLootTableProvider;
import com.teamtea.teastory.data.model.BlockStatesDataProvider;
import com.teamtea.teastory.data.model.TeaItemModelProvider;
import com.teamtea.teastory.data.tag.TeaStoryBlockTagProvider;
import com.teamtea.teastory.data.tag.TeaStoryEntityTypeTagsProvider;
import com.teamtea.teastory.data.tag.TeaStoryFluidTagProvider;
import com.teamtea.teastory.data.tag.TeaStoryItemTagProvider;
import com.teamtea.teastory.data.recipe.TeaStoryRecipeProvider;

import java.util.concurrent.CompletableFuture;


public final class start {
    public final static String MODID = TeaStory.MODID;

    public static void onDataGather(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        if (event.includeServer()) {

            var blockTags = new TeaStoryBlockTagProvider(packOutput,lookupProvider, MODID, helper);
            generator.addProvider(event.includeServer(),blockTags);
            generator.addProvider(event.includeServer(),new TeaStoryItemTagProvider(packOutput, lookupProvider, blockTags.contentsGetter()));
            generator.addProvider(event.includeServer(),new TeaStoryFluidTagProvider(packOutput,lookupProvider, MODID, helper));
            generator.addProvider(event.includeServer(),new TeaStoryEntityTypeTagsProvider(packOutput,lookupProvider, MODID, helper));

            generator.addProvider(event.includeServer(),new TeaStoryRecipeProvider(packOutput,lookupProvider));
            generator.addProvider(event.includeServer(),new GLMProvider(packOutput,lookupProvider, MODID));

            generator.addProvider(event.includeServer(),new LFTLootTableProvider(packOutput,lookupProvider));
        }if (event.includeClient()) {
            generator.addProvider(event.includeClient(),new Lang_EN(packOutput, helper));
            generator.addProvider(event.includeClient(),new Lang_ZH(packOutput, helper));
            generator.addProvider(event.includeClient(), new BlockStatesDataProvider(packOutput, helper));
            generator.addProvider(event.includeClient(), new TeaItemModelProvider(packOutput, MODID, helper));

        }
    }
}
