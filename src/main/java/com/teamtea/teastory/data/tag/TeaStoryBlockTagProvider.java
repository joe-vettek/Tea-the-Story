package com.teamtea.teastory.data.tag;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;

import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import com.teamtea.teastory.BlockRegister;
import com.teamtea.teastory.BlockEntityRegistry;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;


public final class TeaStoryBlockTagProvider extends BlockTagsProvider {
    public TeaStoryBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Tea the Story Block Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.WOODEN_FENCES).add(BlockRegister.BAMBOO_LATTICE.get(),BlockRegister.DRIED_BAMBOO_WALL.get(),BlockRegister.DRIED_BAMBOO_WALL.get());
        tag(BlockTags.WALLS).add(BlockRegister.DRIED_BAMBOO_WALL.get(),BlockRegister.DRIED_BAMBOO_WALL.get());

        // BlockRegister.ModBlocks.getEntries().forEach(blockHolder -> {
        //     if (blockHolder.get() instanceof TrellisBlock) {
        //         tag(BlockTags.WOODEN_FENCES).add(blockHolder.get());
        //     }
        // });

        tag(BlockTags.WOODEN_DOORS).add(BlockRegister.BAMBOO_DOOR.get(), BlockRegister.BAMBOO_GLASS_DOOR.get());
        tag(BlockTags.SMALL_FLOWERS).add(BlockRegister.HYACINTH.get(), BlockRegister.CHRYSANTHEMUM.get(), BlockRegister.ZINNIA.get());

        tag(BlockTags.MINEABLE_WITH_AXE).add(BlockRegister.WOODEN_TABLE.get(),
                BlockRegister.WOODEN_CHAIR.get(),
                BlockRegister.WOODEN_FRAME.get(),
                BlockRegister.BAMBOO_TABLE.get(),
                BlockRegister.BAMBOO_CHAIR.get(),
                BlockRegister.BAMBOO_CATAPULT_BOARD.get(),
                BlockEntityRegistry.WOODEN_TRAY.get(),
                BlockEntityRegistry.WOODEN_BARREL.get(),
                BlockEntityRegistry.BAMBOO_TRAY.get(),
                BlockRegister.stone_campfire.value());

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockRegister.STONE_TABLE.get(),
                BlockRegister.STONE_CHAIR.get(),
                BlockRegister.STONE_CATAPULT_BOARD.get(),
                BlockRegister.IRON_CATAPULT_BOARD.get(),
                BlockRegister.saucepan.get(),
                BlockEntityRegistry.STONE_STOVE.get(),
                BlockEntityRegistry.STONE_ROLLER.get(),
                BlockEntityRegistry.STONE_MILL.get());

        tag(BlockTags.CAMPFIRES).add(BlockRegister.stone_campfire.value());
    }
}
