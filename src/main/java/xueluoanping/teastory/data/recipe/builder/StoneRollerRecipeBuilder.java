package xueluoanping.teastory.data.recipe.builder;


import cloud.lemonslice.teastory.recipe.stone_mill.StoneRollerRecipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import xueluoanping.teastory.RecipeRegister;
import xueluoanping.teastory.TeaStory;

import java.util.function.Consumer;


public class StoneRollerRecipeBuilder {
    private final Ingredient inputItem;
    private final NonNullList<ItemStack> outputItems;
    private final int workTime;

    private StoneRollerRecipeBuilder(Ingredient inputItem, NonNullList<ItemStack> outputItems, int workTime) {
        this.inputItem = inputItem;
        this.outputItems = outputItems;
        this.workTime = workTime;
    }

    public static StoneRollerRecipeBuilder recipe(int workTime, Ingredient inputItem, ItemStack... outputItems) {
        return new StoneRollerRecipeBuilder(inputItem, NonNullList.of(ItemStack.EMPTY, outputItems), workTime);
    }

    public static StoneRollerRecipeBuilder recipeWithDefaultTime(Ingredient inputItem, ItemStack... outputItems) {
        return recipe(200, inputItem, outputItems);
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String save) {
        ResourceLocation saveRes = ResourceLocation.tryParse(save);
        this.build(consumerIn, saveRes);
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new Result(id, this.inputItem, this.outputItems, this.workTime));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        protected final Ingredient inputItem;
        protected final NonNullList<ItemStack> outputItems;
        protected final int workTime;
        private final RecipeSerializer<StoneRollerRecipe> serializer =  RecipeRegister.STONE_ROLLER_SERIALIZER.get();

        public Result(ResourceLocation idIn, Ingredient inputItem, NonNullList<ItemStack> outputItems, int workTime) {
            this.id = TeaStory.rl(idIn.getNamespace(), "stone_roller/" + idIn.getPath());
            this.inputItem = inputItem;
            this.outputItems = outputItems;
            this.workTime = workTime;
        }


        @Override
        public void serializeRecipeData(JsonObject json) {
            if (!inputItem.checkInvalidation()) {
                json.add("item_ingredient", inputItem.toJson());
            }

            JsonArray jsonarray = new JsonArray();
            for (ItemStack itemStack : this.outputItems) {
                JsonObject jsonItem = new JsonObject();
                jsonItem.addProperty("item", ForgeRegistries.ITEMS.getKey(itemStack.getItem()).toString());
                if (itemStack.getCount() > 1) {
                    jsonItem.addProperty("count", itemStack.getCount());
                }
                jsonarray.add(jsonItem);
            }
            json.add("output_items", jsonarray);

            json.addProperty("work_time", this.workTime);
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
