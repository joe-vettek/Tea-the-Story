package com.teamtea.teastory.data.model;

import com.teamtea.teastory.block.crops.WildCropBlock;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.CompositeModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import com.teamtea.teastory.registry.BlockRegister;
import com.teamtea.teastory.TeaStory;

import java.util.List;

public class TeaItemModelProvider extends ItemModelProvider {


    public static final String GENERATED = "item/generated";
    public static final String HANDHELD = "item/handheld";

    public TeaItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    private String blockName(BlockItem blockItem) {
        return BuiltInRegistries.BLOCK.getKey(blockItem.getBlock()).getPath();
    }
    private String blockName(Block blockItem) {
        return BuiltInRegistries.BLOCK.getKey(blockItem).getPath();
    }
    @Override
    protected void registerModels() {

        // List.of(BlockRegister.stone_campfire_ITEM.value()).forEach(
        //         b -> simpleParent(blockName(b))
        // );
        // getModel(TeaStory.rl("block/" + "stone_campfire")
        withExistingParent(itemName(BlockRegister.stone_campfire_ITEM.value()), ResourceLocation.withDefaultNamespace(GENERATED))
                .parent(new ModelFile.ExistingModelFile(ResourceLocation.withDefaultNamespace("block/block"), existingFileHelper))
                .guiLight(BlockModel.GuiLight.FRONT)
                .customLoader(CompositeModelBuilder::begin)
                .child("layer1", getModel(TeaStory.rl("block/" + "stone_campfire")))
                .child("layer2", getModel(TeaStory.rl("block/" + "stone_campfire_fire")))
        ;
        for (DeferredHolder<Block, WildCropBlock> blockWildCropBlockDeferredHolder : List.of(BlockRegister.WILD_CUCUMBER, BlockRegister.WILD_BITTER_GOURD, BlockRegister.WILD_RICE, BlockRegister.WILD_CHILI, BlockRegister.WILD_CHINESE_CABBAGE)) {
            withExistingParent(itemName(blockWildCropBlockDeferredHolder.value().asItem()), HANDHELD).texture("layer0", resourceBlock(itemName(blockWildCropBlockDeferredHolder.value().asItem())));
        }

    }

    public ItemModelBuilder getModel(ResourceLocation resourceLocation) {
        return new ItemModelBuilder(resourceLocation, existingFileHelper)
                .parent(new ModelFile.ExistingModelFile(resourceLocation, existingFileHelper));
    }


    private void simpleParent(String s) {
        withExistingParent(s, modLoc("block/" + s));
    }

    private void registerExistingCuisineBlockItem(DeferredHolder<Item, BlockItem> registryObject) {
        withExistingParent(resourceItem(registryObject.getId().getPath()).getPath(),
                BlockStatesDataProvider.resourceBlock(BuiltInRegistries.BLOCK.getKey(Block.byItem(registryObject.get())).getPath()));

    }

    private String itemName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    public ResourceLocation resourceItem(String path) {
        return TeaStory.rl("item/" + path);
    }
    public static ResourceLocation resourceBlock(String path) {
        return TeaStory.rl("block/" + path);
    }


}
