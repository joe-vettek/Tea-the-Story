package com.teamtea.teastory.data.recipe.builder;


import com.teamtea.teastory.recipe.stone_mill.StoneRollerRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import com.teamtea.teastory.registry.RecipeRegister;
import com.teamtea.teastory.TeaStory;


public class StoneRollerRecipeBuilder {
    private final Ingredient inputItem;
    private final SizedIngredient outputItems;
    private final int workTime;

    private StoneRollerRecipeBuilder(Ingredient inputItem, SizedIngredient outputItems, int workTime) {
        this.inputItem = inputItem;
        this.outputItems = outputItems;
        this.workTime = workTime;
    }

    public static StoneRollerRecipeBuilder recipe(int workTime, Ingredient inputItem, ItemStack outputItems) {
        return new StoneRollerRecipeBuilder(inputItem, SizedIngredient.of(outputItems.getItem(), outputItems.getCount()), workTime);
    }

    public static StoneRollerRecipeBuilder recipeWithDefaultTime(Ingredient inputItem, ItemStack outputItems) {
        return recipe(200, inputItem, outputItems);
    }

    public void build(RecipeOutput consumerIn, String save) {
        ResourceLocation saveRes = ResourceLocation.tryParse(save);
        this.build(consumerIn, saveRes);
    }

    public void build(RecipeOutput consumerIn, ResourceLocation id) {
        id = TeaStory.rl(id.getNamespace(), "%s/%s".formatted(RecipeRegister.STONE_ROLLER.getId().getPath(), id.getPath()));

        consumerIn.accept(id, new StoneRollerRecipe("", this.inputItem, this.outputItems, this.workTime), null);
    }

}
