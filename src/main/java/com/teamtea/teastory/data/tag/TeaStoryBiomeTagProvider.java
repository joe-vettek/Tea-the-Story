package com.teamtea.teastory.data.tag;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;


import java.util.concurrent.CompletableFuture;


public final class TeaStoryBiomeTagProvider extends BiomeTagsProvider {
    public TeaStoryBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(output, lookupProvider, modId);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // tag(TeaTags.Biomes.SPAWN_WILD_RICE).addTags(Tags.Biomes.IS_RIVER, Tags.Biomes.IS_SWAMP);
        // tag(TeaTags.Biomes.SPAWN_WILD_CHILI).addTags(Tags.Biomes.IS_WET_OVERWORLD);
        // tag(TeaTags.Biomes.SPAWN_WILD_CHINESE_CABBAGE).addTags(Tags.Biomes.IS_COLD_OVERWORLD);
        // tag(TeaTags.Biomes.SPAWN_WILD_GRAPE).addTags(Tags.Biomes.IS_DRY_OVERWORLD, Tags.Biomes.IS_FLORAL);
        // tag(TeaTags.Biomes.SPAWN_WILD_CUCUMBER).addTags(Tags.Biomes.IS_PLAINS, Tags.Biomes.IS_FOREST);
        // tag(TeaTags.Biomes.SPAWN_WILD_BITTER_GOURD).addTags(Tags.Biomes.IS_HOT_OVERWORLD);

    }

}
