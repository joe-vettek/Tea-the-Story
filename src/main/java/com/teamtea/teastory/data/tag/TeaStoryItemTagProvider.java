package com.teamtea.teastory.data.tag;


import com.teamtea.teastory.tag.NormalTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import com.teamtea.teastory.BlockRegister;
import com.teamtea.teastory.ItemRegister;

import java.util.concurrent.CompletableFuture;


public final class TeaStoryItemTagProvider extends ItemTagsProvider {

    public TeaStoryItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, CompletableFuture<TagsProvider.TagLookup<Block>> tagLookupCompletableFuture) {
        super(packOutput, providerCompletableFuture, tagLookupCompletableFuture);
    }


    @Override
    public String getName() {
        return "Tea the Story Item Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(NormalTags.Items.FOOD_JERKY).add(ItemRegister.BEEF_JERKY.get(), ItemRegister.PORK_JERKY.get(), ItemRegister.CHICKEN_JERKY.get(), ItemRegister.RABBIT_JERKY.get(), ItemRegister.MUTTON_JERKY.get());
        tag(NormalTags.Items.FOOD_MEAT).addTag(NormalTags.Items.FOOD_JERKY).add(Items.RABBIT, Items.PORKCHOP, Items.BEEF, Items.MUTTON, Items.CHICKEN);
        tag(ItemTags.SMALL_FLOWERS).add(BlockRegister.CHRYSANTHEMUM_ITEM.get(), BlockRegister.HYACINTH_ITEM.get(), BlockRegister.ZINNIA_ITEM.get());
        tag(NormalTags.Items.DUSTS_ASH).add(ItemRegister.ASH.get());

        tag(NormalTags.Items.SEEDS_CARROT).add(Items.CARROT);

        tag(NormalTags.Items.SEEDS_CUCUMBER).add(BlockRegister.CUCUMBERS.get());


        tag(NormalTags.Items.SEEDS_GRAPE).add(BlockRegister.GRAPES.get());

        tag(NormalTags.Items.SEEDS_RICE).add(BlockRegister.RICE_GRAINS.get());

        tag(NormalTags.Items.SEEDS_POTATO).add(Items.POTATO);

        tag(NormalTags.Items.SEEDS_TEA_LEAF).add(BlockRegister.TEA_SEEDS.get());

        tag(NormalTags.Items.CROPS_BLACK_TEA_LEAF).add(ItemRegister.BLACK_TEA_LEAVES.get());
        tag(NormalTags.Items.CROPS_GREEN_TEA_LEAF).add(ItemRegister.GREEN_TEA_LEAVES.get());
        tag(NormalTags.Items.CROPS_TEA_LEAF).add(ItemRegister.TEA_LEAVES.get());
        tag(NormalTags.Items.CROPS_WHITE_TEA_LEAF).add(ItemRegister.WHITE_TEA_LEAVES.get());
        tag(NormalTags.Items.CROPS_GRAPE).add(BlockRegister.GRAPES.get());
        tag(NormalTags.Items.CROPS_CUCUMBER).add(BlockRegister.CUCUMBERS.get());
        tag(NormalTags.Items.CROPS_STRAW).add(ItemRegister.DRY_STRAW.get());
        tag(NormalTags.Items.CROPS_RICE).add(ItemRegister.RICE.get());
        tag(NormalTags.Items.CROPS_APPLE).add(Items.APPLE);
        tag(NormalTags.Items.CROPS_SUGAR_CANE).add(Items.SUGAR_CANE);
    }
}
