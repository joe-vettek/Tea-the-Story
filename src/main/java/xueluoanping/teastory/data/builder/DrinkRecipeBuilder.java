package xueluoanping.teastory.data.builder;

import cloud.lemonslice.teastory.recipe.drink.DrinkRecipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import cloud.lemonslice.silveroak.common.recipe.FluidIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import xueluoanping.teastory.FluidRegistry;
import xueluoanping.teastory.RecipeRegister;

import java.util.function.Consumer;


public class DrinkRecipeBuilder {
    private final Fluid result;
    private final FluidIngredient fluidIngredient;
    private final NonNullList<Ingredient> ingredients;

    private DrinkRecipeBuilder(Fluid resultIn, FluidIngredient fluidIngredientIn, NonNullList<Ingredient> ingredientsIn) {
        this.result = resultIn;
        this.fluidIngredient = fluidIngredientIn;
        this.ingredients = ingredientsIn;
    }

    public static DrinkRecipeBuilder drinkRecipe(Fluid resultIn, FluidIngredient fluidIngredientIn, Ingredient... ingredientsIn) {
        return new DrinkRecipeBuilder(resultIn, fluidIngredientIn, NonNullList.of(Ingredient.EMPTY, ingredientsIn));
    }

    public static DrinkRecipeBuilder boilingRecipe(Fluid resultIn, Ingredient... ingredientsIn) {
        return new DrinkRecipeBuilder(resultIn, FluidIngredient.fromFluid(FluidRegistry.BOILING_WATER_STILL.get(), 500), NonNullList.of(Ingredient.EMPTY, ingredientsIn));
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        this.build(consumerIn, ForgeRegistries.FLUIDS.getKey(this.result));
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String save) {
        ResourceLocation originRes = ForgeRegistries.FLUIDS.getKey(this.result);
        ResourceLocation saveRes = new ResourceLocation(save);
        if (saveRes.equals(originRes)) {
            throw new IllegalStateException("Recipe " + saveRes + " should remove its 'save' argument");
        } else {
            this.build(consumerIn, saveRes);
        }
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new Result(id, this.result, this.fluidIngredient, this.ingredients));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final FluidIngredient fluidIngredient;
        private final NonNullList<Ingredient> ingredients;
        private final Fluid result;
        private final RecipeSerializer<DrinkRecipe> serializer = RecipeRegister.DRINK_MAKER_SERIALIZER.get();

        public Result(ResourceLocation idIn, Fluid resultIn, FluidIngredient fluidIngredientIn, NonNullList<Ingredient> ingredientsIn) {
            this.id = new ResourceLocation(idIn.getNamespace(), "drink_maker/" + idIn.getPath());
            this.fluidIngredient = fluidIngredientIn;
            this.result = resultIn;
            this.ingredients = ingredientsIn;
        }


        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray jsonarray = new JsonArray();
            for (Ingredient ingredient : this.ingredients) {
                jsonarray.add(ingredient.toJson());
            }
            json.add("item_ingredients", jsonarray);
            json.add("fluid_ingredient", fluidIngredient.serialize());

            json.addProperty("drink_result", ForgeRegistries.FLUIDS.getKey(this.result).toString());
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

        @Override
        public @org.jetbrains.annotations.Nullable JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        public @org.jetbrains.annotations.Nullable ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
