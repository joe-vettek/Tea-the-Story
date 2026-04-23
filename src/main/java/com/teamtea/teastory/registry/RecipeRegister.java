package com.teamtea.teastory.registry;


import com.teamtea.teastory.recipe.special.FlowerDyeRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.teamtea.teastory.TeaStory.MODID;


public class RecipeRegister {
    public static final DeferredRegister<RecipeSerializer<?>> DRRecipeSerializer =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MODID);
    public static final DeferredRegister<RecipeType<?>> DRRecipeType =
            DeferredRegister.create(Registries.RECIPE_TYPE, MODID);

    public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<FlowerDyeRecipe>> CRAFTING_SPECIAL_FLOWERDYE = DRRecipeSerializer
            .register("crafting_special_flowerdye", () -> new SimpleCraftingRecipeSerializer<>(FlowerDyeRecipe::new));
}
