package com.teamtea.teastory.data.compat;

import com.teamtea.eclipticseasons.api.data.client.model.ESModelLoadedJson;
import com.teamtea.eclipticseasons.api.data.client.model.multipart.AndConditionLike;
import com.teamtea.eclipticseasons.api.data.client.model.multipart.KeyValueConditionLike;
import com.teamtea.eclipticseasons.api.data.client.model.multipart.MultiPartLike;
import com.teamtea.eclipticseasons.api.data.client.model.multipart.SelectorLike;
import com.teamtea.eclipticseasons.api.data.client.model.variant.MultiVariantLike;
import com.teamtea.eclipticseasons.api.data.client.model.variant.VariantLike;
import com.teamtea.eclipticseasons.client.core.ModelManager;
import com.teamtea.eclipticseasons.data.datapack.client.AbstractModelDefinitionProvider;
import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.block.crops.AqueductBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class    TSModelProvider extends AbstractModelDefinitionProvider {
    public TSModelProvider(PackOutput output, String modid, ExistingFileHelper helper, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, modid, helper, registries);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        add("snowy_table", ESModelLoadedJson.builder()
                .variant("", new MultiVariantLike(
                        List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_table")).build())
                ))
                .build());
        models()
                .withExistingParent("block/snowy/snowy_table", "block/block")
                .texture("top", ModelManager.snow)
                .texture("side", ModelManager.snow_overlay_tiny.toString())
                .element()
                .from(0, 14, 0)
                .to(16, 16, 16)
                .face(Direction.UP).cullface(Direction.UP).uvs(0, 0, 16, 16).texture("#top").end()
                .face(Direction.EAST).cullface(Direction.EAST).uvs(0, 1, 16, 3).texture("#side").end()
                .face(Direction.WEST).cullface(Direction.WEST).uvs(0, 1, 16, 3).texture("#side").end()
                .face(Direction.SOUTH).cullface(Direction.SOUTH).uvs(0, 1, 16, 3).texture("#side").end()
                .face(Direction.NORTH).cullface(Direction.NORTH).uvs(0, 1, 16, 3).texture("#side").end()
                .end();

        add("snowy_paddy_field", ESModelLoadedJson.builder()
                .multiPartLike(
                        new MultiPartLike(List.of(
                                new SelectorLike(new KeyValueConditionLike(BlockStateProperties.NORTH.getName(), BlockStateProperties.NORTH.getName(false)), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_paddy_field_wall")).uvLocked(true).build()))),
                                new SelectorLike(new KeyValueConditionLike(BlockStateProperties.EAST.getName(), BlockStateProperties.EAST.getName(false)), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_paddy_field_wall")).uvLocked(true).rotationY(90).build()))),
                                new SelectorLike(new KeyValueConditionLike(BlockStateProperties.SOUTH.getName(), BlockStateProperties.SOUTH.getName(false)), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_paddy_field_wall")).uvLocked(true).rotationY(180).build()))),
                                new SelectorLike(new KeyValueConditionLike(BlockStateProperties.WEST.getName(), BlockStateProperties.WEST.getName(false)), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_paddy_field_wall")).uvLocked(true).rotationY(270).build()))),
                                new SelectorLike(new KeyValueConditionLike(BlockStateProperties.WATERLOGGED.getName(), BlockStateProperties.WATERLOGGED.getName(false)), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_dry_paddy_field")).build())))
                        ))
                )
                .build());
        models()
                .withExistingParent("block/snowy/snowy_paddy_field_wall", "block/block")
                .texture("top", ModelManager.snow)
                .texture("side", ModelManager.snow_overlay.toString())
                .element()
                .from(0, 6, 0)
                .to(16, 16, 3)
                .face(Direction.UP).cullface(Direction.UP).uvs(0, 0, 16, 3).texture("#top").end()
                .face(Direction.NORTH).cullface(Direction.NORTH).uvs(0, 0, 16, 10).texture("#side").end()
                .face(Direction.EAST).cullface(Direction.EAST).uvs(13, 0, 16, 10).texture("#side").end()
                .face(Direction.SOUTH).cullface(Direction.SOUTH).uvs(0, 0, 16, 10).texture("#side").end()
                .face(Direction.WEST).cullface(Direction.WEST).uvs(0, 0, 3, 10).texture("#side").end()
                .end();
        models()
                .withExistingParent("block/snowy/snowy_dry_paddy_field", "block/block")
                .texture("top", ModelManager.snow)
                .element()
                .from(0, 0, 0)
                .to(16, 6, 16)
                .face(Direction.UP).cullface(Direction.UP).uvs(0, 0, 16, 16).texture("#top").end()
                .end();


        add("snowy_aqueduct", ESModelLoadedJson.builder()
                .multiPartLike(
                        new MultiPartLike(List.of(
                                new SelectorLike(new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_base")).build()))),
                                new SelectorLike(new KeyValueConditionLike(BlockStateProperties.WATERLOGGED.getName(), BlockStateProperties.WATERLOGGED.getName(false)), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_side_bottom")).build()))),
                                new SelectorLike(new KeyValueConditionLike(BlockStateProperties.NORTH.getName(), BlockStateProperties.NORTH.getName(false)), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_wall")).uvLocked(true).rotationY(90).build()))),
                                new SelectorLike(new KeyValueConditionLike(BlockStateProperties.EAST.getName(), BlockStateProperties.EAST.getName(false)), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_wall")).uvLocked(true).rotationY(180).build()))),
                                new SelectorLike(new KeyValueConditionLike(BlockStateProperties.SOUTH.getName(), BlockStateProperties.SOUTH.getName(false)), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_wall")).uvLocked(true).rotationY(270).build()))),
                                new SelectorLike(new KeyValueConditionLike(BlockStateProperties.WEST.getName(), BlockStateProperties.WEST.getName(false)), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_wall")).uvLocked(true).build()))),
                                new SelectorLike(new AndConditionLike(List.of(new KeyValueConditionLike(AqueductBlock.BOTTOM.getName(), AqueductBlock.BOTTOM.getName(false)),new KeyValueConditionLike(AqueductBlock.WATERLOGGED.getName(), AqueductBlock.WATERLOGGED.getName(false)))), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_bottom")).build()))),
                                new SelectorLike(new AndConditionLike(List.of(new KeyValueConditionLike(AqueductBlock.NORTH.getName(), AqueductBlock.NORTH.getName(true)),new KeyValueConditionLike(AqueductBlock.BLOCKED.getName(), AqueductBlock.BLOCKED.getName(true)))), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_blocker")).uvLocked(true).build()))),
                                new SelectorLike(new AndConditionLike(List.of(new KeyValueConditionLike(AqueductBlock.EAST.getName(), AqueductBlock.EAST.getName(true)),new KeyValueConditionLike(AqueductBlock.BLOCKED.getName(), AqueductBlock.BLOCKED.getName(true)))), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_blocker")).uvLocked(true).rotationY(90).build()))),
                                new SelectorLike(new AndConditionLike(List.of(new KeyValueConditionLike(AqueductBlock.SOUTH.getName(), AqueductBlock.SOUTH.getName(true)),new KeyValueConditionLike(AqueductBlock.BLOCKED.getName(), AqueductBlock.BLOCKED.getName(true)))), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_blocker")).uvLocked(true).rotationY(180).build()))),
                                new SelectorLike(new AndConditionLike(List.of(new KeyValueConditionLike(AqueductBlock.WEST.getName(), AqueductBlock.WEST.getName(true)),new KeyValueConditionLike(AqueductBlock.BLOCKED.getName(), AqueductBlock.BLOCKED.getName(true)))), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_blocker")).uvLocked(true).rotationY(270).build())))
                        ))
                )
                .build());
        models()
                .withExistingParent("block/snowy/snowy_aqueduct_base", TeaStory.rl("block/aqueduct_with_top"))
                .texture("top", ModelManager.snow)
                .texture("side", ModelManager.snow_overlay);
        models()
                .withExistingParent("block/snowy/snowy_aqueduct_side_bottom", TeaStory.rl("block/aqueduct_side_bottom_with_top"))
                .texture("top", ModelManager.snow)
                .texture("side", ModelManager.snow_overlay);
        models()
                .withExistingParent("block/snowy/snowy_aqueduct_wall", TeaStory.rl("block/aqueduct_wall_with_top"))
                .texture("top", ModelManager.snow)
                .texture("side", ModelManager.snow_overlay);
        models()
                .withExistingParent("block/snowy/snowy_aqueduct_blocker", TeaStory.rl("block/aqueduct_blocker_with_top"))
                .texture("top", ModelManager.snow)
                .texture("side", ModelManager.snow_overlay);
        models()
                .withExistingParent("block/snowy/snowy_aqueduct_bottom", TeaStory.rl("block/aqueduct_bottom_with_top"))
                .texture("top", ModelManager.snow);

        add("snowy_low_aqueduct", ESModelLoadedJson.builder()
                .multiPartLike(
                        new MultiPartLike(List.of(
                                new SelectorLike(new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_low_aqueduct_base")).build()))),
                                new SelectorLike(new KeyValueConditionLike(BlockStateProperties.WATERLOGGED.getName(), BlockStateProperties.WATERLOGGED.getName(false)), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_side_bottom")).build()))),
                                new SelectorLike(new KeyValueConditionLike(BlockStateProperties.NORTH.getName(), BlockStateProperties.NORTH.getName(false)), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_wall")).uvLocked(true).rotationY(90).build()))),
                                new SelectorLike(new KeyValueConditionLike(BlockStateProperties.EAST.getName(), BlockStateProperties.EAST.getName(false)), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_low_aqueduct_wall")).uvLocked(true).rotationY(180).build()))),
                                new SelectorLike(new KeyValueConditionLike(BlockStateProperties.SOUTH.getName(), BlockStateProperties.SOUTH.getName(false)), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_low_aqueduct_wall")).uvLocked(true).rotationY(270).build()))),
                                new SelectorLike(new KeyValueConditionLike(BlockStateProperties.WEST.getName(), BlockStateProperties.WEST.getName(false)), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_low_aqueduct_wall")).uvLocked(true).build()))),
                                new SelectorLike(new AndConditionLike(List.of(new KeyValueConditionLike(AqueductBlock.BOTTOM.getName(), AqueductBlock.BOTTOM.getName(false)),new KeyValueConditionLike(AqueductBlock.WATERLOGGED.getName(), AqueductBlock.WATERLOGGED.getName(false)))), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_bottom")).build()))),
                                new SelectorLike(new AndConditionLike(List.of(new KeyValueConditionLike(AqueductBlock.NORTH.getName(), AqueductBlock.NORTH.getName(true)),new KeyValueConditionLike(AqueductBlock.BLOCKED.getName(), AqueductBlock.BLOCKED.getName(true)))), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_blocker")).uvLocked(true).build()))),
                                new SelectorLike(new AndConditionLike(List.of(new KeyValueConditionLike(AqueductBlock.EAST.getName(), AqueductBlock.EAST.getName(true)),new KeyValueConditionLike(AqueductBlock.BLOCKED.getName(), AqueductBlock.BLOCKED.getName(true)))), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_blocker")).uvLocked(true).rotationY(90).build()))),
                                new SelectorLike(new AndConditionLike(List.of(new KeyValueConditionLike(AqueductBlock.SOUTH.getName(), AqueductBlock.SOUTH.getName(true)),new KeyValueConditionLike(AqueductBlock.BLOCKED.getName(), AqueductBlock.BLOCKED.getName(true)))), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_blocker")).uvLocked(true).rotationY(180).build()))),
                                new SelectorLike(new AndConditionLike(List.of(new KeyValueConditionLike(AqueductBlock.WEST.getName(), AqueductBlock.WEST.getName(true)),new KeyValueConditionLike(AqueductBlock.BLOCKED.getName(), AqueductBlock.BLOCKED.getName(true)))), new MultiVariantLike(List.of(VariantLike.builder(TeaStory.rl("block/snowy/snowy_aqueduct_blocker")).uvLocked(true).rotationY(270).build())))
                        ))
                )
                .build());
        models()
                .withExistingParent("block/snowy/snowy_low_aqueduct_base", TeaStory.rl("block/low_aqueduct_with_top"))
                .texture("top", ModelManager.snow)
                .texture("side", ModelManager.snow_overlay);
        models()
                .withExistingParent("block/snowy/snowy_low_aqueduct_wall", TeaStory.rl("block/low_aqueduct_wall_with_top"))
                .texture("top", ModelManager.snow)
                .texture("side", ModelManager.snow_overlay);
    }

}
