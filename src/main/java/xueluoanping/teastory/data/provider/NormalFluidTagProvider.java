package xueluoanping.teastory.data.provider;


import cloud.lemonslice.teastory.tag.NormalTags;
import com.google.common.collect.Lists;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.ExistingFileHelper;
import xueluoanping.teastory.FluidRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public final class NormalFluidTagProvider extends FluidTagsProvider {
    public NormalFluidTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(NormalTags.Fluids.DRINK).add(FluidRegistry.BOILING_WATER_STILL.get(), FluidRegistry.SUGARY_WATER_STILL.get(), FluidRegistry.WEAK_BLACK_TEA_STILL.get(), FluidRegistry.BLACK_TEA_STILL.get(), FluidRegistry.STRONG_BLACK_TEA_STILL.get(),
                FluidRegistry.WEAK_GREEN_TEA_STILL.get(), FluidRegistry.GREEN_TEA_STILL.get(), FluidRegistry.STRONG_GREEN_TEA_STILL.get(), FluidRegistry.WEAK_WHITE_TEA_STILL.get(), FluidRegistry.WHITE_TEA_STILL.get(), FluidRegistry.STRONG_WHITE_TEA_STILL.get(),
                FluidRegistry.APPLE_JUICE_STILL.get(), FluidRegistry.CARROT_JUICE_STILL.get(), FluidRegistry.SUGAR_CANE_JUICE_STILL.get(), FluidRegistry.GRAPE_JUICE_STILL.get(), FluidRegistry.CUCUMBER_JUICE_STILL.get());

        List<Fluid> water = Lists.newArrayList();
        FluidRegistry.FLUIDS.getEntries().forEach(fluid -> water.add(fluid.get()));
        tag(FluidTags.WATER).add(water.toArray(new Fluid[0]));
    }


    @Override
    public String getName() {
        return "Tea the Story Fluid Tags";
    }
}
