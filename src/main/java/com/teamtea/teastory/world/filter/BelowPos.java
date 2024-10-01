package com.teamtea.teastory.world.filter;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamtea.teastory.registry.ModPlacementModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class BelowPos extends PlacementFilter
{
	public static final MapCodec<BelowPos> CODEC = RecordCodecBuilder.mapCodec((builder) ->
			builder.group(
					TagKey.codec(Registries.BIOME).fieldOf("tag").forGetter((instance) -> instance.biomeTag)
			).apply(builder, BelowPos::new));
	private final TagKey<Biome> biomeTag;

	private BelowPos(TagKey<Biome> biomeTag) {
		this.biomeTag = biomeTag;
	}

	public static BelowPos biomeIsInTag(TagKey<Biome> biomeTag) {
		return new BelowPos(biomeTag);
	}

	@Override
	protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
		Holder<Biome> biome = context.getLevel().getBiome(pos);
		return biome.is(biomeTag);
	}



	@Override
	public PlacementModifierType<?> type() {
		return ModPlacementModifiers.BIOME_TAG.get();
	}
}
