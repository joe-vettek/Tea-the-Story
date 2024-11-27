package com.teamtea.teastory.plugin.ecliptic_seasons;

import com.teamtea.eclipticseasons.api.constant.crop.CropHumidityType;
import com.teamtea.eclipticseasons.api.constant.crop.CropSeasonType;
import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.data.tag.TeaStoryItemTagProvider;
import com.teamtea.teastory.registry.BlockRegister;
import com.teamtea.teastory.registry.ItemRegister;
import com.teamtea.teastory.tag.TeaTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ESDataEventHandler {
    public static ESDataEventHandler INSTANCE = new ESDataEventHandler();


    @SubscribeEvent
    public void onGatherDataEvent(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        if (event.includeServer()) {
            var blockTags = new TS_ESBlockTagProvider(packOutput,lookupProvider, TeaStory.MODID, helper);
            generator.addProvider(event.includeServer(),blockTags);
            generator.addProvider(event.includeServer(),new TS_ESItemTagProvider(packOutput, lookupProvider, blockTags.contentsGetter()));
        }
    }

    public static class TS_ESBlockTagProvider extends BlockTagsProvider {
        public TS_ESBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, modId, existingFileHelper);
        }

        @Override
        public String getName() {
            return "Tea the Story Block Tags + ES";
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
        }
    }

    public static  class TS_ESItemTagProvider extends ItemTagsProvider {

        public TS_ESItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, CompletableFuture<TagsProvider.TagLookup<Block>> tagLookupCompletableFuture) {
            super(packOutput, providerCompletableFuture, tagLookupCompletableFuture);
        }


        @Override
        public String getName() {
            return "Tea the Story Item Tags - ES";
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            tag(CropSeasonType.SPRING.getTag()).add(BlockRegister.TEA_SEEDS.value());
            tag(CropSeasonType.SP_SU.getTag()).add(
                    BlockRegister.RICE_GRAINS.value(),
                    BlockRegister.riceSeedlings.value(),
                    BlockRegister.CHILI_SEEDS.value()
            );
            tag(CropSeasonType.SP_AU.getTag()).add(BlockRegister.CHINESE_CABBAGE_SEEDS.value());

            tag(CropHumidityType.AVERAGE_MOIST.getTag()).add(BlockRegister.CHILI_SEEDS.value(),BlockRegister.TEA_SEEDS.value());
            tag(CropHumidityType.MOIST_HUMID.getTag()).add(BlockRegister.RICE_GRAINS.value(),
                    BlockRegister.riceSeedlings.value());

        }
    }
}
