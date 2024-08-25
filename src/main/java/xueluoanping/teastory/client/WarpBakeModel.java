package xueluoanping.teastory.client;

import cloud.lemonslice.teastory.block.crops.TrellisBlock;
import cloud.lemonslice.teastory.block.crops.TrellisWithVineBlock;
import cloud.lemonslice.teastory.client.gui.RenderUtil;
import cloud.lemonslice.teastory.client.render.XYZ;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.IdenticalMerger;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.util.ConcatenatedListView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.blockentity.VineEntity;

import java.util.*;

public class WarpBakeModel implements IDynamicBakedModel {

    private final BakedModel bakedModel;
    private final Map<BlockState, List<BakedQuad>> stateListMap = new IdentityHashMap<>();
    private final TextureAtlasSprite cache;
    private final ItemOverrides itemOverrides;
    public static final ResourceLocation grape_leaves_on_beam = TeaStory.rl("block/grape_leaves_on_beam");
    public static final ResourceLocation grape_on_post_0 = TeaStory.rl("block/grape_on_post_0");
    public static final ResourceLocation grape_on_post_1 = TeaStory.rl("block/grape_on_post_1");
    public static final ResourceLocation grape_on_post_2 = TeaStory.rl("block/grape_on_post_2");
    public static final ResourceLocation grape_on_post_3 = TeaStory.rl("block/grape_on_post_3");
    public static final List<ResourceLocation> grapesRes = List.of(grape_on_post_0, grape_on_post_1, grape_on_post_2, grape_on_post_3);
    public static final List<BakedModel> grapes = new ArrayList<>();

    public WarpBakeModel(BakedModel bakedModel, TextureAtlasSprite cache) {
        this.bakedModel = bakedModel;
        this.cache = cache;
        itemOverrides = new SelfItemOverrides(this);
    }


    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        // List<BakedQuad> bakedQuads = stateListMap.get(state);
        // if (ll == null)
        List<BakedQuad> bakedQuads = new ArrayList<>();
        {
            if (state == null) {
                if (side != null) return List.of();
                bakedQuads = new ArrayList<>(bakedModel.getQuads(null, null, rand, extraData, renderType));
                for (Direction value : Direction.values()) {
                    bakedQuads.addAll(bakedModel.getQuads(null, value, rand, extraData, renderType));
                }
                // stateListMap.put(null, bakedQuads);
            } else {
                bakedQuads = bakedModel.getQuads(state, side, rand, extraData, renderType);
                // stateListMap.put(state, bakedQuads);
            }
            if (!(bakedQuads instanceof ArrayList)) {
                bakedQuads = new ArrayList<>(bakedQuads);
            }

            for (int i = 0; i < bakedQuads.size(); i++) {
                bakedQuads.set(i, new BakedQuadRetextured(bakedQuads.get(i), cache));
            }
            if (state != null && side == null && state.getBlock() instanceof TrellisWithVineBlock) {
                if (state.getValue(TrellisBlock.EAST) || state.getValue(TrellisBlock.WEST) || state.getValue(TrellisBlock.SOUTH) || state.getValue(TrellisBlock.NORTH)) {
                    bakedQuads.addAll(Minecraft.getInstance().getModelManager().getModel(grape_leaves_on_beam).getQuads(null, null, rand));
                }
                if (state.getValue(TrellisBlock.POST)) {
                    int age = 0;
                    try {
                        age = extraData.get(VineEntity.AGE_PROPERTY);
                    } catch (Exception e) {
                    }
                    bakedQuads.addAll(grapes.get(age).getQuads(null, null, rand));
                }
            }
        }

        return bakedQuads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return bakedModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return bakedModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return bakedModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return bakedModel.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return cache == null ? bakedModel.getParticleIcon() : cache;
    }

    @Override
    public ItemOverrides getOverrides() {
        // return bakedModel.getOverrides();
        return itemOverrides;
    }

    @Override
    public @NotNull ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
        if (level.getBlockEntity(pos) instanceof VineEntity vineEntity)
            return modelData.derive().with(VineEntity.AGE_PROPERTY, vineEntity.getAge()).build();
        return modelData;
    }

    @Override
    public BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform) {

        // return bakedModel.applyTransform(transformType, poseStack, applyLeftHandTransform);
        // this.getTransforms().getTransform(transformType).apply(applyLeftHandTransform, poseStack);

        // poseStack.translate(0.9375F, 0.21875F, 0F);
        // poseStack.mulPose(XYZ.deg_to_rad(30, 45, 0 ));
        // poseStack.scale(0.75f, 0.75f, 0.75f);
        bakedModel.getTransforms().getTransform(transformType).apply(applyLeftHandTransform, poseStack);
        return this;
    }


    public static class SelfItemOverrides extends ItemOverrides {

        private final WarpBakeModel warpBakeModel;

        public SelfItemOverrides(WarpBakeModel warpBakeModel1) {
            this.warpBakeModel = warpBakeModel1;
        }

        @Override
        public @Nullable BakedModel resolve(BakedModel pModel, ItemStack pStack, @Nullable ClientLevel pLevel, @Nullable LivingEntity pEntity, int pSeed) {
            return warpBakeModel;
        }
    }
}
