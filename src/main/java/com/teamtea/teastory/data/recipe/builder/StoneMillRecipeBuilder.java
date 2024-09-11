package com.teamtea.teastory.data.recipe.builder;

import com.teamtea.teastory.recipe.stone_mill.StoneMillRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import com.teamtea.teastory.registry.RecipeRegister;
import com.teamtea.teastory.TeaStory;

import java.util.Arrays;
import java.util.List;


public class StoneMillRecipeBuilder {
    private final SizedFluidIngredient inputFluid;
    private final Ingredient inputItem;
    private final List<Ingredient> outputItems;
    private final SizedFluidIngredient outputFluid;
    private final int workTime;

    private StoneMillRecipeBuilder(Ingredient inputItem, SizedFluidIngredient inputFluid, List<Ingredient> outputItems, SizedFluidIngredient outputFluid, int workTime) {
        this.inputItem = inputItem;
        this.inputFluid = inputFluid;
        this.outputItems = outputItems;
        this.outputFluid = outputFluid;
        this.workTime = workTime;
    }

    public static StoneMillRecipeBuilder recipe(int workTime, Ingredient inputItem, SizedFluidIngredient inputFluid, SizedFluidIngredient outputFluid, ItemStack... outputItems) {
        var items=NonNullList.of(Ingredient.EMPTY, Arrays.stream(outputItems).filter(itemStack -> !itemStack.isEmpty()).map(Ingredient::of).toArray( Ingredient[]::new));
        return new StoneMillRecipeBuilder(inputItem, inputFluid, items, outputFluid, workTime);
    }

    public static StoneMillRecipeBuilder recipeWithDefaultTime(Ingredient inputItem, SizedFluidIngredient inputFluid, FluidStack outputFluid, ItemStack... outputItems) {
        return recipe(200, inputItem, inputFluid, SizedFluidIngredient.of(outputFluid), outputItems);
    }

    public static StoneMillRecipeBuilder recipeWithoutFluid(int workTime, Ingredient inputItem, ItemStack... outputItems) {
        return recipe(workTime, inputItem, null,null, outputItems);
    }

    public static StoneMillRecipeBuilder recipeWithDefaultTimeWithoutFluid(Ingredient inputItem, ItemStack... outputItems) {
        return recipeWithoutFluid(200, inputItem, outputItems);
    }

    public void build(RecipeOutput consumerIn, String save) {
        ResourceLocation saveRes = ResourceLocation.tryParse(save);
        this.build(consumerIn, saveRes);
    }

    public void build(RecipeOutput consumerIn, ResourceLocation id) {
        id = TeaStory.rl(id.getNamespace(), "%s/%s".formatted(RecipeRegister.STONE_MILL.getId().getPath(), id.getPath()));

        consumerIn.accept(id,new StoneMillRecipe("",this.inputItem, this.inputFluid, this.outputItems, this.outputFluid, this.workTime),null);
    }

}
