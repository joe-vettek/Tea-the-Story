package xueluoanping.teastory.data.model;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import xueluoanping.teastory.BlockRegister;
import xueluoanping.teastory.TeaStory;


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

    @Override
    protected void registerModels() {

        List.of(BlockRegister.stone_campfire_ITEM.value()).forEach(
                b -> simpleParent(blockName(b))
        );

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


}
