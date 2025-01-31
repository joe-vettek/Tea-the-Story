package com.teamtea.teastory.recipe.drink;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public record DrinkEffect(FluidStack fluidStack,
                          List<DrinkEffectAttribute> drinkEffectAttribute) {

    public static final Codec<DrinkEffect> DIRECT_CODEC = RecordCodecBuilder.create(builder -> builder.group(
            FluidStack.CODEC.fieldOf("fluid").forGetter(DrinkEffect::fluidStack),
            DrinkEffectAttribute.DIRECT_CODEC.listOf().fieldOf("effects").forGetter(DrinkEffect::drinkEffectAttribute)
    ).apply(builder, DrinkEffect::new));

}
