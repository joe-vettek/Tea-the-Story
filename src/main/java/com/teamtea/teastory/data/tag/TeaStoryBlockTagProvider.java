package com.teamtea.teastory.data.tag;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;

import net.neoforged.neoforge.common.data.BlockTagsProvider;

import com.teamtea.teastory.registry.BlockRegister;
import com.teamtea.teastory.registry.BlockEntityRegister;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;


public final class TeaStoryBlockTagProvider extends BlockTagsProvider {
    public TeaStoryBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(output, lookupProvider, modId);
    }

    @Override
    public String getName() {
        return "Tea the Story Block Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.WOODEN_FENCES).add(BlockRegister.BAMBOO_LATTICE.get(), BlockRegister.DRIED_BAMBOO_WALL.get(), BlockRegister.DRIED_BAMBOO_WALL.get());
        tag(BlockTags.WALLS).add(BlockRegister.DRIED_BAMBOO_WALL.get(), BlockRegister.DRIED_BAMBOO_WALL.get());


        tag(BlockTags.WOODEN_DOORS).add(BlockRegister.BAMBOO_DOOR.get(), BlockRegister.BAMBOO_GLASS_DOOR.get());
        tag(BlockTags.SMALL_FLOWERS).add(BlockRegister.HYACINTH.get(), BlockRegister.CHRYSANTHEMUM.get(), BlockRegister.ZINNIA.get());

        tag(BlockTags.MINEABLE_WITH_AXE).add(BlockRegister.WOODEN_TABLE.get(),
                BlockRegister.WOODEN_CHAIR.get(),
                BlockRegister.WOODEN_FRAME.get(),
                BlockRegister.BAMBOO_TABLE.get(),
                BlockRegister.BAMBOO_CHAIR.get(),
                BlockEntityRegister.WOODEN_TRAY.get(),
                BlockEntityRegister.WOODEN_BARREL.get(),
                BlockRegister.stone_campfire.value());

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockRegister.STONE_TABLE.get(),
                BlockRegister.STONE_CHAIR.get(),
                BlockRegister.saucepan.get(),
                BlockRegister.cobblestoneAqueduct.get(),
                BlockRegister.mossyCobblestoneAqueduct.get()
        );
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                BlockRegister.dirtAqueduct.get()
        );

        tag(BlockTags.CAMPFIRES).add(BlockRegister.stone_campfire.value());

        tag(BlockTags.DIRT).add(BlockRegister.GRASS_BLOCK_WITH_HOLE.value());

    }
}
