package xueluoanping.teastory.data;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.data.lang.Lang_EN;
import xueluoanping.teastory.data.lang.Lang_ZH;
import xueluoanping.teastory.data.loot.GLMProvider;
import xueluoanping.teastory.data.loot.LFTLootTableProvider;
import xueluoanping.teastory.data.tag.*;
import xueluoanping.teastory.data.recipe.TeaStoryRecipeProvider;

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

            generator.addProvider(event.includeServer(),new TeaStoryRecipeProvider(packOutput));
            generator.addProvider(event.includeServer(),new GLMProvider(packOutput, MODID));

            generator.addProvider(event.includeServer(),new LFTLootTableProvider(packOutput));
        }if (event.includeClient()) {
            generator.addProvider(event.includeClient(),new Lang_EN(packOutput, helper));
            generator.addProvider(event.includeClient(),new Lang_ZH(packOutput, helper));
        }
    }
}
