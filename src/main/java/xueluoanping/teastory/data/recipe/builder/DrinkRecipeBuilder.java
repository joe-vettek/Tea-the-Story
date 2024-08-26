package xueluoanping.teastory.data.recipe.builder;

import cloud.lemonslice.teastory.recipe.drink.DrinkRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import xueluoanping.teastory.FluidRegistry;

import java.util.List;


public class DrinkRecipeBuilder {
    private final FluidIngredient result;
    private final SizedFluidIngredient fluidIngredient;
    private final List<Ingredient> ingredients;

    private DrinkRecipeBuilder(FluidIngredient resultIn, SizedFluidIngredient fluidIngredientIn, NonNullList<Ingredient> ingredientsIn) {
        this.result = resultIn;
        this.fluidIngredient = fluidIngredientIn;
        this.ingredients = ingredientsIn;
    }

    public static DrinkRecipeBuilder drinkRecipe(FluidIngredient resultIn, SizedFluidIngredient fluidIngredientIn, Ingredient... ingredientsIn) {
        return new DrinkRecipeBuilder(resultIn, fluidIngredientIn, NonNullList.of(Ingredient.EMPTY, ingredientsIn));
    }

    public static DrinkRecipeBuilder boilingRecipe(FluidIngredient resultIn, Ingredient... ingredientsIn) {
        return new DrinkRecipeBuilder(resultIn, SizedFluidIngredient.of(FluidRegistry.BOILING_WATER_STILL.get(), 500), NonNullList.of(Ingredient.EMPTY, ingredientsIn));
    }

    public static DrinkRecipeBuilder boilingRecipe(BaseFlowingFluid.Source source, Ingredient of, Ingredient of1, Ingredient of2, Ingredient of3) {
        return boilingRecipe(FluidIngredient.of(source), of, of1, of2, of3);
    }

    public void build(RecipeOutput consumerIn) {
        this.build(consumerIn, BuiltInRegistries.FLUID.getKey(this.result.getStacks()[0].getFluid()));
    }

    public void build(RecipeOutput consumerIn, String save) {
        ResourceLocation originRes = BuiltInRegistries.FLUID.getKey(this.result.getStacks()[0].getFluid());
        ResourceLocation saveRes = ResourceLocation.tryParse(save);
        if (saveRes.equals(originRes)) {
            throw new IllegalStateException("Recipe " + saveRes + " should remove its 'save' argument");
        } else {
            this.build(consumerIn, saveRes);
        }
    }

    public void build(RecipeOutput consumerIn, ResourceLocation id) {
        consumerIn.accept(id, new DrinkRecipe("", this.ingredients, this.fluidIngredient, this.result), null);
    }
}
