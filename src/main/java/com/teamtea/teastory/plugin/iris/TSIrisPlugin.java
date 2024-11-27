package com.teamtea.teastory.plugin.iris;

import com.teamtea.teastory.block.crops.TrellisWithVineBlock;
import com.teamtea.teastory.registry.BlockRegister;
import com.teamtea.teastory.variant.Planks;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.irisshaders.iris.Iris;
import net.irisshaders.iris.api.v0.IrisApi;
import net.irisshaders.iris.shaderpack.materialmap.WorldRenderingSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import java.util.List;
import java.util.Objects;

public class TSIrisPlugin {

    private static Object2IntMap<BlockState> blockStateIds = null;

    private static String shaderpack = null;

    public static void checkReload() {
        if (Iris.getIrisConfig() != null) {
            String nowshaderpack = Iris.getIrisConfig().getShaderPackName().orElse(null);
            // if (!Objects.equals(nowshaderpack, shaderpack))
            {
                if (IrisApi.getInstance().isShaderPackInUse()) {
                    if (WorldRenderingSettings.INSTANCE.getBlockStateIds() != blockStateIds)
                    {
                        blockStateIds = WorldRenderingSettings.INSTANCE.getBlockStateIds();
                        if (blockStateIds != null) {
                            shaderpack = nowshaderpack;
                            simpleCopyAddGrassUpper(BlockRegister.ricePlant.get());
                            List.of(BlockRegister.WILD_GRAPE,
                                            BlockRegister.WILD_RICE,
                                            BlockRegister.WILD_CHINESE_CABBAGE,
                                            BlockRegister.WILD_CHILI,
                                            BlockRegister.WILD_BITTER_GOURD,
                                            BlockRegister.WILD_CUCUMBER,
                                            BlockRegister.CHRYSANTHEMUM,
                                            BlockRegister.HYACINTH,
                                            BlockRegister.ZINNIA
                                    )
                                    .forEach(ob -> simpleCopyAddSmallWheatLike(ob.value()));
                            List.of(BlockRegister.RiceSeedlingBlock,
                                            BlockRegister.CHINESE_CABBAGE_PLANT,
                                            BlockRegister.CHILI_PLANT
                                    )
                                    .forEach(ob -> simpleCopyAddSmallWheatLike(ob.value()));
                            List.of(BlockRegister.GRAPE,
                                            BlockRegister.CUCUMBER,
                                            BlockRegister.BITTER_GOURD
                                    )
                                    .forEach(ob -> simpleCopyAdd(Blocks.CAVE_VINES.defaultBlockState(), ob.value()));
                            List.of(BlockRegister.wild_tea_plant,
                                            BlockRegister.tea_plant
                                            // ,
                                            // BlockRegister.WATERMELON_VINE
                                    )
                                    .forEach(ob -> simpleCopyAdd(Blocks.OAK_LEAVES.defaultBlockState(), ob.value()));
                            // for (Planks.PlankHolders value : Planks.TrellisBlockMap.values()) {
                            //     for (TrellisWithVineBlock trellisWithVineBlock : value.trellisWithVineBlocks()) {
                            //         simpleCopyAdd(Blocks.OAK_LEAVES.defaultBlockState(), trellisWithVineBlock);
                            //     }
                            // }
                        }

                    }
                }
            }
        }
    }

    public static void simpleCopyAddSmallWheatLike(Block block) {
        simpleCopyAdd(Blocks.WHEAT.defaultBlockState(), block);
    }

    public static void simpleCopyAddGrassUpper(Block block) {
        if (shaderpack != null && !shaderpack.toLowerCase().contains("photon"))
            simpleCopyAdd(Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), block);
        else simpleCopyAdd(Blocks.VINE.defaultBlockState(), block);
        // simpleCopyAdd(Blocks.OAK_LEAVES.defaultBlockState(), block);
        // simpleCopyAdd(Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), block);

    }

    public static void simpleCopyAdd(BlockState state, Block block) {
        if (blockStateIds != null) {
            int idsInt = blockStateIds.getInt(state);
            if (idsInt > 0) {
                block.getStateDefinition().getPossibleStates()
                        .forEach(b -> blockStateIds.putIfAbsent(b, idsInt));
            }
        }
    }


}
