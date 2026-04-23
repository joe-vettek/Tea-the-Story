package com.teamtea.teastory.plugin.eclipticseasons;

import com.teamtea.eclipticseasons.api.constant.crop.CropHumidityType;
import com.teamtea.eclipticseasons.api.constant.crop.CropSeasonType;
import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.registry.BlockRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class ESDataEventHandler {
    public static ESDataEventHandler INSTANCE = new ESDataEventHandler();



    @SubscribeEvent
    public void onGatherDataEvent(GatherDataEvent.Server event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        var blockTags = new TS_ESBlockTagProvider(packOutput, lookupProvider, TeaStory.MODID);
        generator.addProvider(true,blockTags);
        generator.addProvider(true,new TS_ESItemTagProvider(packOutput, lookupProvider, blockTags.contentsGetter()));
    }

    public static class TS_ESBlockTagProvider extends BlockTagsProvider {
        public TS_ESBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
            super(output, lookupProvider, modId);
        }

        @Override
        public String getName() {
            return "Tea the Story Block Tags + ES";
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            tag(CropSeasonType.SUMMER.getBlockTag()).add(BlockRegister.WATERMELON_VINE.get());
            this.tag(CropHumidityType.DRY_HUMID.getBlockTag()).add(BlockRegister.WATERMELON_VINE.get());


            tag(CropSeasonType.SPRING.getBlockTag()).add(BlockRegister.tea_plant.get());
            tag(CropSeasonType.SP_SU.getBlockTag()).add(
                    BlockRegister.RiceSeedlingBlock.get(),
                    BlockRegister.ricePlant.get(),
                    BlockRegister.CHILI_PLANT.get()
            );
            tag(CropSeasonType.SP_AU.getBlockTag()).add(BlockRegister.CHINESE_CABBAGE_PLANT.get());

            tag(CropHumidityType.AVERAGE_MOIST.getBlockTag()).add(
                    BlockRegister.CHILI_PLANT.get(),BlockRegister.tea_plant.get());
            tag(CropHumidityType.MOIST_HUMID.getBlockTag()).add(BlockRegister.RiceSeedlingBlock.get(),
                    BlockRegister.ricePlant.get());

        }
    }

    public static  class TS_ESItemTagProvider extends ItemTagsProvider {

        public TS_ESItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, CompletableFuture<TagsProvider.TagLookup<Block>> tagLookupCompletableFuture) {
            super(packOutput, providerCompletableFuture, TeaStory.MODID);
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

            tag(CropSeasonType.SP_SU_AU.getTag()).add(BlockRegister.CUCUMBERS.value());
            tag(CropSeasonType.SU_AU.getTag()).add(BlockRegister.GRAPES.value(),BlockRegister.BITTER_GOURDS.value());

            tag(CropHumidityType.AVERAGE_MOIST.getTag()).add(
                    BlockRegister.CHILI_SEEDS.value(),BlockRegister.TEA_SEEDS.value(),
                    BlockRegister.BITTER_GOURDS.value(),BlockRegister.CUCUMBERS.value());
            tag(CropHumidityType.MOIST_HUMID.getTag()).add(BlockRegister.RICE_GRAINS.value(),
                    BlockRegister.riceSeedlings.value());

            tag(CropHumidityType.DRY_MOIST.getTag()).add(BlockRegister.GRAPES.value());
        }
    }
}
