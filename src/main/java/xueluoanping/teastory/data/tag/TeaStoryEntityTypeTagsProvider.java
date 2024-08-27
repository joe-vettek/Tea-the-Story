package xueluoanping.teastory.data.tag;

import xueluoanping.teastory.tag.NormalTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TeaStoryEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public TeaStoryEntityTypeTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, providerCompletableFuture, modId, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider lookupProvider) {
        tag(NormalTags.Entities.BIRDS).add(EntityType.PARROT);
    }
}
