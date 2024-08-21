package xueluoanping.teastory.data.provider;


import cloud.lemonslice.teastory.block.crops.TrellisBlock;
import cloud.lemonslice.teastory.block.crops.TrellisWithVineBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import xueluoanping.teastory.BlockRegister;
import xueluoanping.teastory.TeaStory;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;


public final class NormalBlockTagProvider extends BlockTagsProvider {
    public NormalBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Tea the Story Block Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.WOODEN_FENCES).add(BlockRegister.BAMBOO_LATTICE.get());
        tag(BlockTags.WALLS).add(BlockRegister.DRIED_BAMBOO_WALL.get(),BlockRegister.DRIED_BAMBOO_WALL.get());
        // BlockRegister.ModBlocks.getEntries().forEach(blockHolder -> {
        //     if (blockHolder.get() instanceof TrellisBlock) {
        //         tag(BlockTags.WOODEN_FENCES).add(blockHolder.get());
        //     }
        // });

        tag(BlockTags.WOODEN_DOORS).add(BlockRegister.BAMBOO_DOOR.get(), BlockRegister.BAMBOO_GLASS_DOOR.get());
        tag(BlockTags.SMALL_FLOWERS).add(BlockRegister.HYACINTH.get(), BlockRegister.CHRYSANTHEMUM.get(), BlockRegister.ZINNIA.get());

    }
}
