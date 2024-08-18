package xueluoanping.teastory.data.builder;


import cloud.lemonslice.teastory.recipe.bamboo_tray.BambooTraySingleInRecipe;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import xueluoanping.teastory.RecipeRegister;

import java.util.function.Consumer;

public class BambooTrayRecipeBuilder
{
    private final Item result;
    private final Ingredient ingredient;
    private final int workTime;
    private final BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<?> recipeSerializer;

    private BambooTrayRecipeBuilder(Item resultIn, Ingredient ingredientIn, int workTimeIn, BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<?> serializer)
    {
        this.result = resultIn.asItem();
        this.ingredient = ingredientIn;
        this.workTime = workTimeIn;
        this.recipeSerializer = serializer;
    }

    public static BambooTrayRecipeBuilder workingRecipe(Ingredient ingredientIn, Item resultIn, int workingTimeIn, BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<?> serializer)
    {
        return new BambooTrayRecipeBuilder(resultIn, ingredientIn, workingTimeIn, serializer);
    }

    public static BambooTrayRecipeBuilder outdoorsRecipe(Ingredient ingredientIn, Item resultIn, int workingTimeIn)
    {
        return workingRecipe(ingredientIn, resultIn, workingTimeIn, (BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<?>) RecipeRegister.BAMBOO_TRAY_OUTDOORS_SERIALIZER.get());
    }

    public static BambooTrayRecipeBuilder indoorsRecipe(Ingredient ingredientIn, Item resultIn, int workingTimeIn)
    {
        return workingRecipe(ingredientIn, resultIn, workingTimeIn, (BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<?>) RecipeRegister.BAMBOO_TRAY_INDOORS_SERIALIZER.get());
    }

    public static BambooTrayRecipeBuilder wetRecipe(Ingredient ingredientIn, Item resultIn, int workingTimeIn)
    {
        return workingRecipe(ingredientIn, resultIn, workingTimeIn, (BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<?>) RecipeRegister.BAMBOO_TRAY_IN_RAIN_SERIALIZER.get());
    }

    public static BambooTrayRecipeBuilder bakeRecipe(Ingredient ingredientIn, @NotNull Item resultIn, int workingTimeIn)
    {
        return workingRecipe(ingredientIn, resultIn, workingTimeIn, (BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<?>) RecipeRegister.BAMBOO_TRAY_BAKE_SERIALIZER.get());
    }

    public void build(Consumer<FinishedRecipe> consumerIn)
    {
        this.build(consumerIn, ForgeRegistries.ITEMS.getKey(this.result));
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String save)
    {
        ResourceLocation originRes = ForgeRegistries.ITEMS.getKey(this.result);
        ResourceLocation saveRes = new ResourceLocation(save);
        if (saveRes.equals(originRes))
        {
            throw new IllegalStateException("Recipe " + saveRes + " should remove its 'save' argument");
        }
        else
        {
            this.build(consumerIn, saveRes);
        }
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id)
    {
        consumerIn.accept(new Result(id, this.ingredient, this.result, this.workTime, this.recipeSerializer));
    }

    public static class Result implements FinishedRecipe
    {
        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final Item result;
        private final int workingTime;
        private final RecipeSerializer<? extends BambooTraySingleInRecipe> serializer;

        public Result(ResourceLocation idIn, Ingredient ingredientIn, Item resultIn, int workingTimeIn, RecipeSerializer<? extends BambooTraySingleInRecipe> serializerIn)
        {
            this.id = new ResourceLocation(idIn.getNamespace(), "bamboo_tray/" + idIn.getPath());
            this.ingredient = ingredientIn;
            this.result = resultIn;
            this.workingTime = workingTimeIn;
            this.serializer = serializerIn;
        }
        
        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("ingredient", this.ingredient.toJson());
            json.addProperty("result", ForgeRegistries.ITEMS.getKey(this.result).toString());
            json.addProperty("work_time", this.workingTime);
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
