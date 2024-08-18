package xueluoanping.teastory.data;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.data.provider.*;

import java.util.concurrent.CompletableFuture;


public final class start {
    public final static String MODID = TeaStory.MODID;

    public static void onDataGather(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        if (event.includeServer()) {

            var blockTags = new NormalBlockTagProvider(packOutput,lookupProvider, MODID, helper);
            generator.addProvider(event.includeServer(),blockTags);
            generator.addProvider(event.includeServer(),new NormalItemTagProvider(packOutput, lookupProvider, blockTags.contentsGetter()));
            generator.addProvider(event.includeServer(),new NormalFluidTagProvider(packOutput,lookupProvider, MODID, helper));


            generator.addProvider(event.includeServer(),new TRecipeProvider(packOutput));
        }
    }
}
