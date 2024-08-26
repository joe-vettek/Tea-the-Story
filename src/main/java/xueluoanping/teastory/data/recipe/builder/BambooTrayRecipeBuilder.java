package xueluoanping.teastory.data.recipe.builder;


import cloud.lemonslice.teastory.recipe.bamboo_tray.BambooTraySingleInRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;
import xueluoanping.teastory.RecipeRegister;

public class BambooTrayRecipeBuilder {
    private final Item result;
    private final Ingredient ingredient;
    private final int workTime;
    private final BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<?> recipeSerializer;

    private BambooTrayRecipeBuilder(Item resultIn, Ingredient ingredientIn, int workTimeIn, BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<?> serializer) {
        this.result = resultIn.asItem();
        this.ingredient = ingredientIn;
        this.workTime = workTimeIn;
        this.recipeSerializer = serializer;
    }

    public static BambooTrayRecipeBuilder workingRecipe(Ingredient ingredientIn, Item resultIn, int workingTimeIn, BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<?> serializer) {
        return new BambooTrayRecipeBuilder(resultIn, ingredientIn, workingTimeIn, serializer);
    }

    public static BambooTrayRecipeBuilder outdoorsRecipe(Ingredient ingredientIn, Item resultIn, int workingTimeIn) {
        return workingRecipe(ingredientIn, resultIn, workingTimeIn, (BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<?>) RecipeRegister.BAMBOO_TRAY_OUTDOORS_SERIALIZER.get());
    }

    public static BambooTrayRecipeBuilder indoorsRecipe(Ingredient ingredientIn, Item resultIn, int workingTimeIn) {
        return workingRecipe(ingredientIn, resultIn, workingTimeIn, (BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<?>) RecipeRegister.BAMBOO_TRAY_INDOORS_SERIALIZER.get());
    }

    public static BambooTrayRecipeBuilder wetRecipe(Ingredient ingredientIn, Item resultIn, int workingTimeIn) {
        return workingRecipe(ingredientIn, resultIn, workingTimeIn, (BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<?>) RecipeRegister.BAMBOO_TRAY_IN_RAIN_SERIALIZER.get());
    }

    public static BambooTrayRecipeBuilder bakeRecipe(Ingredient ingredientIn, @NotNull Item resultIn, int workingTimeIn) {
        return workingRecipe(ingredientIn, resultIn, workingTimeIn, (BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<?>) RecipeRegister.BAMBOO_TRAY_BAKE_SERIALIZER.get());
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
        consumerIn.accept(id, new BambooTraySingleInRecipe("", this.ingredient, this.result.getDefaultInstance(), this.workTime) {
            @Override
            public RecipeSerializer<?> getSerializer() {
                return null;
            }

            @Override
            public RecipeType<?> getType() {
                return null;
            }
        }, null);
    }

}
