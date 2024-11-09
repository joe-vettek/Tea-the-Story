package xueluoanping.teastory.data.model;


import cloud.lemonslice.teastory.block.crops.WildCropBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.registry.BlockRegister;
import xueluoanping.teastory.registry.FluidRegistry;


import javax.annotation.Nullable;
import java.util.List;

public class BlockStatesDataProvider extends BlockStateProvider {


    private final ExistingFileHelper existingFileHelper;

    public BlockStatesDataProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TeaStory.MODID, existingFileHelper);
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {


        for (var entry : FluidRegistry.BLOCKS.getEntries()) {
            simpleBlock(entry.get(), ConfiguredModel.builder().modelFile(models().withExistingParent("water", new ResourceLocation("water"))).build());
        }



        for (var holder : List.of(BlockRegister.WILD_CUCUMBER, BlockRegister.WILD_BITTER_GOURD, BlockRegister.WILD_RICE, BlockRegister.WILD_CHILI, BlockRegister.WILD_CHINESE_CABBAGE)) {
            simpleBlock(holder.get(), ConfiguredModel.builder().modelFile(models().withExistingParent(blockName(holder.get()), new ResourceLocation("cross"))
                    .texture("cross",resourceBlock(blockName(holder.get())))
            ).build());

        }

    }


    // Thanks vectorwing，great work
    // I am not proud of this method... But hey, it's runData. Only I shall have to deal with it.
    public void customStageBlock(Block block, @Nullable ResourceLocation parent, String textureKey, IntegerProperty ageProperty, List<Integer> suffixes, Property<?>... ignored) {
        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    int ageSuffix = state.getValue(ageProperty);
                    String stageName = blockName(block) + "_stage_";
                    stageName += suffixes.isEmpty() ? ageSuffix : suffixes.get(Math.min(suffixes.size() - 1, ageSuffix));
                    // Cuisine.logger(stageName);
                    if (parent == null) {
                        return ConfiguredModel.builder()
                                .modelFile(models().cross(stageName, resourceBlock(stageName))).build();
                    }
                    return ConfiguredModel.builder()
                            .modelFile(models().singleTexture(stageName, parent, textureKey, resourceBlock(stageName))).build();
                }, ignored);
    }

    private String blockName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    public static ResourceLocation resourceBlock(String path) {
        return TeaStory.rl("block/" + path);
    }

    public ResourceLocation resourceVanillaBlock(String path) {
        return new ResourceLocation("block/" + path);
    }


    public static int getRotateYByFacing(Direction state) {
        switch (state) {
            case EAST -> {
                return 90;
            }
            case SOUTH -> {
                return 180;
            }
            case WEST -> {
                return 270;
            }
            default -> {
                return 0;
            }
        }
    }

}
