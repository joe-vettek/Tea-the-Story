package com.teamtea.teastory.data.compat;

import com.mojang.serialization.Lifecycle;
import com.teamtea.eclipticseasons.api.data.season.SnowDefinition;
import com.teamtea.eclipticseasons.common.core.map.MapChecker;
import com.teamtea.eclipticseasons.common.registry.ESRegistries;
import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.registry.BlockRegister;
import com.teamtea.teastory.tag.TeaTags;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.holdersets.AndHolderSet;
import net.neoforged.neoforge.registries.holdersets.NotHolderSet;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

public class TSSnowDefinitionProvider {

    public static ResourceKey<SnowDefinition> createKey(String string) {
        return ResourceKey.create(ESRegistries.SNOW_DEFINITIONS,
                TeaStory.rl(string.toLowerCase(Locale.ROOT)));
    }

    public static void simpleRegister(BootstrapContext<SnowDefinition> context,
                                      String name,
                                      List<DeferredHolder<Block, ? extends Block>> blocks) {
        context.register(
                createKey(name),
                SnowDefinition.builder().blocks(HolderSet.direct(
                                blocks.stream().map(bo -> bo.get().builtInRegistryHolder()).toArray(Holder[]::new)
                        ))
                        .info(SnowDefinition.Info.builder().flag(MapChecker.FLAG_CUSTOM).build()).build()
        );
    }

    public static void registerSelf(BootstrapContext<SnowDefinition> context,
                                    DeferredHolder<Block, ? extends Block> block) {
        String snowyId = "snowy_" + block.getId().getPath();
        context.register(
                createKey(snowyId),
                SnowDefinition.builder().blocks(HolderSet.direct(
                                block
                        ))
                        .info(SnowDefinition.Info.builder().mid(TeaStory.rl(snowyId)).build()).build()
        );
    }

    public static void registerCouple(BootstrapContext<SnowDefinition> context,
                                      String snowyId,
                                      List<DeferredHolder<Block, ? extends Block>> blocks) {
        context.register(
                createKey(snowyId),
                SnowDefinition.builder().blocks(HolderSet.direct(
                                blocks.stream().map(bo -> bo.get().builtInRegistryHolder()).toArray(Holder[]::new)
                        ))
                        .info(SnowDefinition.Info.builder().mid(TeaStory.rl(snowyId)).build()).build()
        );
    }

    public static void bootstrap2(BootstrapContext<SnowDefinition> context) {
        HolderGetter<Block> blockHolderGetter = context.lookup(Registries.BLOCK);
        HolderLookup.RegistryLookup<Block> blockRegistryLookup = new BlockRegistryLookup(blockHolderGetter);

        // context.register(
        //         createKey("snowy_aqueduct"),
        //         SnowDefinition.builder().blocks(HolderSet.direct(
        //                         BlockRegister.cobblestoneAqueduct.get().builtInRegistryHolder(),
        //                         BlockRegister.mossyCobblestoneAqueduct.get().builtInRegistryHolder(),
        //                         BlockRegister.dirtAqueduct.get().builtInRegistryHolder()
        //                 )).map(List.of(SnowDefinition.PropertyTester.builder()
        //                         .name(BlockStateProperties.WATERLOGGED.getName())
        //                         .matcher(SnowDefinition.ExactMatcher.builder()
        //                                 .value(BlockStateProperties.WATERLOGGED.getName(false))
        //                                 .build())
        //                         .build()))
        //                 .info(SnowDefinition.Info.builder().flag(MapChecker.FLAG_CUSTOM).build()).build()
        // );

        registerSelf(context, BlockRegister.paddyField);
        
        registerCouple(context, "snowy_low_aqueduct", List.of(
                BlockRegister.dirtAqueduct));
        registerCouple(context, "snowy_aqueduct", List.of(
                BlockRegister.cobblestoneAqueduct,
                BlockRegister.mossyCobblestoneAqueduct));

        simpleRegister(context, "snowy_wall",
                List.of(BlockRegister.BAMBOO_LATTICE,
                        BlockRegister.DRIED_BAMBOO_WALL,
                        BlockRegister.FRESH_BAMBOO_WALL));
        registerCouple(context, "snowy_table", List.of(
                BlockRegister.BAMBOO_TABLE,
                BlockRegister.STONE_TABLE,
                BlockRegister.WOODEN_TABLE));

        // context.register(
        //         createKey("snowy_trellis"),
        //         SnowDefinition.builder().blocks(
        //                 new AndHolderSet<>(
        //                         List.of(blockHolderGetter.getOrThrow(TeaTags.Blocks.TRELLIS),
        //                                 new NotHolderSet<>(blockRegistryLookup,blockHolderGetter.getOrThrow(TeaTags.Blocks.TRELLIS_WITH_VINE))))
        //                 )
        //                 .map(List.of(SnowDefinition.PropertyTester.builder()
        //                         .name(TrellisBlock.UP.getName())
        //                         .matcher(SnowDefinition.ExactMatcher.builder()
        //                                 .value(TrellisBlock.UP.getName(false))
        //                                 .build())
        //                         .build()))
        //                 .info(SnowDefinition.Info.builder().flag(MapChecker.FLAG_CUSTOM).build()).build()
        // );

        context.register(
                createKey("snowy_trellis_with_vine"),
                SnowDefinition.builder().blocks( blockHolderGetter.getOrThrow(TeaTags.Blocks.TRELLIS_WITH_VINE))
                        .info(SnowDefinition.Info.builder().flag(MapChecker.FLAG_CUSTOM).build()).build()
        );
    }


    public record BlockRegistryLookup(
            HolderGetter<Block> blockHolderGetter) implements HolderLookup.RegistryLookup<Block> {

        @Override
        public @NotNull Optional<Holder.Reference<Block>> get(@NotNull ResourceKey<Block> pResourceKey) {
            return blockHolderGetter.get(pResourceKey);
        }

        @Override
        public @NotNull Optional<HolderSet.Named<Block>> get(@NotNull TagKey<Block> pTagKey) {
            return blockHolderGetter.get(pTagKey);
        }

        @Override
        public @NotNull Stream<Holder.Reference<Block>> listElements() {
            return Stream.empty();
        }

        @Override
        public @NotNull Stream<HolderSet.Named<Block>> listTags() {
            return Stream.empty();
        }

        @Override
        public @NotNull ResourceKey<? extends Registry<? extends Block>> key() {
            return Registries.BLOCK;
        }

        @Override
        public boolean canSerializeIn(@NotNull HolderOwner<Block> pOwner) {
            return true;
        }

        @Override
        public @NotNull Lifecycle registryLifecycle() {
            return Lifecycle.stable();
        }
    }
}
