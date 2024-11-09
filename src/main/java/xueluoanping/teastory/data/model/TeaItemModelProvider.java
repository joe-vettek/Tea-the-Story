package xueluoanping.teastory.data.model;


import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.registry.BlockRegister;


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


        for (var blockWildCropBlockDeferredHolder : List.of(BlockRegister.WILD_CUCUMBER, BlockRegister.WILD_BITTER_GOURD, BlockRegister.WILD_RICE, BlockRegister.WILD_CHILI, BlockRegister.WILD_CHINESE_CABBAGE)) {
            withExistingParent(itemName(blockWildCropBlockDeferredHolder.get().asItem()), HANDHELD).texture("layer0", resourceBlock(itemName(blockWildCropBlockDeferredHolder.get().asItem())));
        }

    }

    public ItemModelBuilder getModel(ResourceLocation resourceLocation) {
        return new ItemModelBuilder(resourceLocation, existingFileHelper)
                .parent(new ModelFile.ExistingModelFile(resourceLocation, existingFileHelper));
    }


    private void simpleParent(String s) {
        withExistingParent(s, modLoc("block/" + s));
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
