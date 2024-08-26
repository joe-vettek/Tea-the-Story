package xueluoanping.teastory.data.recipe.builder;

import cloud.lemonslice.teastory.recipe.stone_mill.StoneMillRecipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import xueluoanping.teastory.RecipeRegister;
import xueluoanping.teastory.TeaStory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


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

    public static StoneMillRecipeBuilder recipe(int workTime, Ingredient inputItem, SizedFluidIngredient inputFluid, FluidStack outputFluid, ItemStack... outputItems) {
        return new StoneMillRecipeBuilder(inputItem, inputFluid, NonNullList.of(Ingredient.EMPTY, Arrays.stream(outputItems).map(Ingredient::of).toArray( Ingredient[]::new)), SizedFluidIngredient.of(outputFluid), workTime);
    }

    public static StoneMillRecipeBuilder recipeWithDefaultTime(Ingredient inputItem, SizedFluidIngredient inputFluid, FluidStack outputFluid, ItemStack... outputItems) {
        return recipe(200, inputItem, inputFluid, outputFluid, outputItems);
    }

    public static StoneMillRecipeBuilder recipeWithoutFluid(int workTime, Ingredient inputItem, ItemStack... outputItems) {
        return recipe(workTime, inputItem, SizedFluidIngredient.of(FluidStack.EMPTY), FluidStack.EMPTY, outputItems);
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
