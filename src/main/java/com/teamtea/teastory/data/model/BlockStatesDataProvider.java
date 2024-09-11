package com.teamtea.teastory.data.model;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import com.teamtea.teastory.registry.BlockRegister;
import com.teamtea.teastory.registry.FluidRegister;
import com.teamtea.teastory.TeaStory;

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


        for (DeferredHolder<Block, ? extends Block> entry : FluidRegister.BLOCKS.getEntries()) {
            simpleBlock(entry.value(), ConfiguredModel.builder().modelFile(models().withExistingParent("water", ResourceLocation.withDefaultNamespace("water"))).build());
        }




        for (Direction direction : Direction.Plane.HORIZONTAL) {
            getMultipartBuilder(BlockRegister.stone_campfire.get())
                    .part()
                    .modelFile(models().getExistingFile(resourceBlock("stone_campfire")))
                    .rotationY(getRotateYByFacing(direction))
                    .addModel()
                    .condition(BlockStateProperties.HORIZONTAL_FACING,direction)
                    .end();
        }

        getMultipartBuilder(BlockRegister.stone_campfire.get())
                .part()
                .modelFile(models().getExistingFile(resourceBlock("stone_campfire_fire")))
                .addModel()
                .condition(BlockStateProperties.LIT, true)
                .end();

        getMultipartBuilder(BlockRegister.stone_campfire.get())
                .part()
                .modelFile(models().getExistingFile(resourceBlock("stone_campfire_fire")))
                .addModel()
                .condition(BlockStateProperties.LIT, true)
                .end();
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
        return ResourceLocation.withDefaultNamespace("block/" + path);
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
