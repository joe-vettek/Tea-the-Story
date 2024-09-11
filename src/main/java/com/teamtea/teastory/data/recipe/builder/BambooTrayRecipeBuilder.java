package com.teamtea.teastory.data.recipe.builder;


import net.minecraft.world.item.crafting.RecipeSerializer;
import com.teamtea.teastory.recipe.bamboo_tray.BambooTraySingleInRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import com.teamtea.teastory.registry.RecipeRegister;
import com.teamtea.teastory.TeaStory;

public class BambooTrayRecipeBuilder {
    private final Item result;
    private final Ingredient ingredient;
    private final int workTime;
    private final DeferredHolder<RecipeSerializer<?>, BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<? extends BambooTraySingleInRecipe>> recipeSerializer;

    private BambooTrayRecipeBuilder(Item resultIn, Ingredient ingredientIn, int workTimeIn, DeferredHolder<RecipeSerializer<?>, BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<? extends BambooTraySingleInRecipe>> serializer) {
        this.result = resultIn.asItem();
        this.ingredient = ingredientIn;
        this.workTime = workTimeIn;
        this.recipeSerializer = serializer;
    }

    public static BambooTrayRecipeBuilder workingRecipe(Ingredient ingredientIn, Item resultIn, int workingTimeIn, DeferredHolder<RecipeSerializer<?>, BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<? extends BambooTraySingleInRecipe>> serializer) {
        return new BambooTrayRecipeBuilder(resultIn, ingredientIn, workingTimeIn, serializer);
    }

    public static BambooTrayRecipeBuilder outdoorsRecipe(Ingredient ingredientIn, Item resultIn, int workingTimeIn) {
        return workingRecipe(ingredientIn, resultIn, workingTimeIn, RecipeRegister.BAMBOO_TRAY_OUTDOORS_SERIALIZER);
    }

    public static BambooTrayRecipeBuilder indoorsRecipe(Ingredient ingredientIn, Item resultIn, int workingTimeIn) {
        return workingRecipe(ingredientIn, resultIn, workingTimeIn, RecipeRegister.BAMBOO_TRAY_INDOORS_SERIALIZER);
    }

    public static BambooTrayRecipeBuilder wetRecipe(Ingredient ingredientIn, Item resultIn, int workingTimeIn) {
        return workingRecipe(ingredientIn, resultIn, workingTimeIn, RecipeRegister.BAMBOO_TRAY_IN_RAIN_SERIALIZER);
    }

    public static BambooTrayRecipeBuilder bakeRecipe(Ingredient ingredientIn, @NotNull Item resultIn, int workingTimeIn) {
        return workingRecipe(ingredientIn, resultIn, workingTimeIn, RecipeRegister.BAMBOO_TRAY_BAKE_SERIALIZER);
    }

    public void build(RecipeOutput consumerIn) {
        this.build(consumerIn, BuiltInRegistries.ITEM.getKey(this.result));
    }

    public void build(RecipeOutput consumerIn, String save) {
        ResourceLocation originRes = BuiltInRegistries.ITEM.getKey(this.result);
        ResourceLocation saveRes = ResourceLocation.tryParse(save);
        if (saveRes.equals(originRes)) {
            throw new IllegalStateException("Recipe " + saveRes + " should remove its 'save' argument");
        } else {
            this.build(consumerIn, saveRes);
        }
    }

    public void build(RecipeOutput consumerIn, ResourceLocation id) {
        id = TeaStory.rl(id.getNamespace(), "%s/%s".formatted(recipeSerializer.getId().getPath().replace("bamboo_",""), id.getPath()));
        var c = recipeSerializer.get().factory.create("", this.ingredient, this.result.getDefaultInstance(), this.workTime);
        consumerIn.accept(id, c, null);
    }

}
