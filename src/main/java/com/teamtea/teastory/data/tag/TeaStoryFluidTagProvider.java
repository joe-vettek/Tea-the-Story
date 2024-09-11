package com.teamtea.teastory.data.tag;


import com.teamtea.teastory.tag.TeaTags;
import com.google.common.collect.Lists;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import com.teamtea.teastory.registry.FluidRegister;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public final class TeaStoryFluidTagProvider extends FluidTagsProvider {
    public TeaStoryFluidTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(TeaTags.Fluids.DRINK).add(FluidRegister.BOILING_WATER_STILL.get(), FluidRegister.SUGARY_WATER_STILL.get(), FluidRegister.WEAK_BLACK_TEA_STILL.get(), FluidRegister.BLACK_TEA_STILL.get(), FluidRegister.STRONG_BLACK_TEA_STILL.get(),
                FluidRegister.WEAK_GREEN_TEA_STILL.get(), FluidRegister.GREEN_TEA_STILL.get(), FluidRegister.STRONG_GREEN_TEA_STILL.get(), FluidRegister.WEAK_WHITE_TEA_STILL.get(), FluidRegister.WHITE_TEA_STILL.get(), FluidRegister.STRONG_WHITE_TEA_STILL.get(),
                FluidRegister.APPLE_JUICE_STILL.get(), FluidRegister.CARROT_JUICE_STILL.get(), FluidRegister.SUGAR_CANE_JUICE_STILL.get(), FluidRegister.GRAPE_JUICE_STILL.get(), FluidRegister.CUCUMBER_JUICE_STILL.get());

        List<Fluid> water = Lists.newArrayList();
        FluidRegister.FLUIDS.getEntries().forEach(fluid -> water.add(fluid.get()));
        tag(FluidTags.WATER).add(water.toArray(new Fluid[0]));
    }


    @Override
    public String getName() {
        return "Tea the Story Fluid Tags";
    }
}
