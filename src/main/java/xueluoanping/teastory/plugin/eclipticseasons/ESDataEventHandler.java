package xueluoanping.teastory.plugin.eclipticseasons;

import com.teamtea.eclipticseasons.api.constant.crop.CropHumidityType;
import com.teamtea.eclipticseasons.api.constant.crop.CropSeasonType;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.registry.BlockRegister;

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

        public TS_ESItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, CompletableFuture<TagLookup<Block>> tagLookupCompletableFuture) {
            super(packOutput, providerCompletableFuture, tagLookupCompletableFuture);
        }


        @Override
        public String getName() {
            return "Tea the Story Item Tags - ES";
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            tag(CropSeasonType.SPRING.getTag()).add(BlockRegister.TEA_SEEDS.get());
            tag(CropSeasonType.SP_SU.getTag()).add(
                    BlockRegister.RICE_GRAINS.get(),
                    BlockRegister.riceSeedlings.get(),
                    BlockRegister.CHILI_SEEDS.get()
            );
            tag(CropSeasonType.SP_AU.getTag()).add(BlockRegister.CHINESE_CABBAGE_SEEDS.get());

            tag(CropSeasonType.SP_SU_AU.getTag()).add(BlockRegister.CUCUMBERS.get());
            tag(CropSeasonType.SU_AU.getTag()).add(BlockRegister.GRAPES.get(),BlockRegister.BITTER_GOURDS.get());

            tag(CropHumidityType.AVERAGE_MOIST.getTag()).add(
                    BlockRegister.CHILI_SEEDS.get(),BlockRegister.TEA_SEEDS.get(),
                    BlockRegister.BITTER_GOURDS.get(),BlockRegister.CUCUMBERS.get());
            tag(CropHumidityType.MOIST_HUMID.getTag()).add(BlockRegister.RICE_GRAINS.get(),
                    BlockRegister.riceSeedlings.get());

            tag(CropHumidityType.DRY_MOIST.getTag()).add(BlockRegister.GRAPES.get());
        }
    }
}
