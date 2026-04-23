package com.teamtea.teastory.client;

import com.teamtea.teastory.block.crops.TrellisBlock;
import com.teamtea.teastory.block.crops.TrellisWithVineBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.neoforge.client.model.DynamicBlockStateModel;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.blockentity.VineBlockEntity;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class WarpBakeModel implements DynamicBlockStateModel {

    // private final BakedModel bakedModel;
    // private final Map<BlockState, List<BakedQuad>> stateListMap = new IdentityHashMap<>();
    // private final TextureAtlasSprite cache;
    public static final StandaloneModelKey<BlockStateModel> grape_leaves_on_beam = mrl("block/grape_leaves_on_beam");
    public static final StandaloneModelKey<BlockStateModel> grape_on_post_0 = mrl("block/grape_on_post_0");
    public static final StandaloneModelKey<BlockStateModel> grape_on_post_1 = mrl("block/grape_on_post_1");
    public static final StandaloneModelKey<BlockStateModel> grape_on_post_2 = mrl("block/grape_on_post_2");
    public static final StandaloneModelKey<BlockStateModel> grape_on_post_3 = mrl("block/grape_on_post_3");
    public static final List<StandaloneModelKey<BlockStateModel>> grapesRes = List.of(grape_on_post_0, grape_on_post_1, grape_on_post_2, grape_on_post_3);
    public static final List<BlockStateModel> grapes = new ArrayList<>();

    public static StandaloneModelKey<BlockStateModel> mrl(String s) {
        return new StandaloneModelKey<>(()->TeaStory.rl(s).toLanguageKey());
    }

    // public WarpBakeModel(BakedModel bakedModel, TextureAtlasSprite cache) {
    //     this.bakedModel = bakedModel;
    //     this.cache = cache;
    //     itemOverrides = new SelfItemOverrides(this);
    //     // We can use it to rotate
    //     // QuadTransformers.applying().process()
    // }

    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockStateModelPart> parts) {

    }

    @Override
    public Material.Baked particleMaterial() {
        return null;
    }

    @Override
    public @BakedQuad.MaterialFlags int materialFlags() {
        return 0;
    }

    //
    // @Override
    // public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
    //     // List<BakedQuad> bakedQuads = stateListMap.get(state);
    //     // if (ll == null)
    //     List<BakedQuad> bakedQuads = new ArrayList<>();
    //     {
    //         List<BakedQuad> bakedQuads2 = new ArrayList<>();
    //         if (state == null)
    //         {
    //             if (side != null) return List.of();
    //             bakedQuads2 = new ArrayList<>(bakedModel.getQuads(null, null, rand, extraData, renderType));
    //             for (Direction value : Direction.values()) {
    //                 bakedQuads2.addAll(bakedModel.getQuads(null, value, rand, extraData, renderType));
    //             }
    //             // stateListMap.put(null, bakedQuads);
    //         }
    //         else {
    //             bakedQuads2 = bakedModel.getQuads(state, side, rand, extraData, renderType);
    //             // stateListMap.put(state, bakedQuads);
    //         }
    //
    //         bakedQuads = new ArrayList<>(bakedQuads2.size());
    //         for (int i = 0; i < bakedQuads2.size(); i++) {
    //             bakedQuads.add(new BakedQuadRetextured(bakedQuads2.get(i), cache));
    //         }
    //
    //         if (state != null && side == null && state.getBlock() instanceof TrellisWithVineBlock) {
    //             if (state.getValue(TrellisBlock.EAST)
    //                     || state.getValue(TrellisBlock.WEST)
    //                     || state.getValue(TrellisBlock.SOUTH)
    //                     || state.getValue(TrellisBlock.NORTH)) {
    //                 bakedQuads.addAll(Minecraft.getInstance().getModelManager().getModel(grape_leaves_on_beam).getQuads(null, null, rand));
    //             }
    //             if (state.getValue(TrellisBlock.POST)) {
    //                 int age = extraData.get(VineBlockEntity.AGE_PROPERTY) instanceof Integer integer ? integer : 0;
    //                 List<BakedQuad> bakedQuads1 = grapes.get(age).getQuads(null, null, rand);
    //                 // bakedQuads1 = QuadTransformers.applying(new Transformation(new Vector3f(0.5f, 0.5f, 0.5f), new Quaternionf(), new Vector3f(0.625f, 0.625f, 0.625f), new Quaternionf())).process(bakedQuads1);
    //                 // net.neoforged.neoforge.client.model.QuadTransformers.applyingLightmap()
    //                 bakedQuads.addAll(bakedQuads1);
    //             }
    //         }
    //     }
    //
    //     return bakedQuads;
    // }
}
