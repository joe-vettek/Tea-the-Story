package xueluoanping.teastory.data.recipe.builder;

import cloud.lemonslice.teastory.recipe.stone_mill.StoneMillRecipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xueluoanping.teastory.craft.FluidIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import xueluoanping.teastory.RecipeRegister;
import net.minecraftforge.fluids.FluidStack;
import xueluoanping.teastory.TeaStory;

import java.util.function.Consumer;


public class StoneMillRecipeBuilder {
    private final FluidIngredient inputFluid;
    private final Ingredient inputItem;
    private final NonNullList<ItemStack> outputItems;
    private final FluidStack outputFluid;
    private final int workTime;

    private StoneMillRecipeBuilder(Ingredient inputItem, FluidIngredient inputFluid, NonNullList<ItemStack> outputItems, FluidStack outputFluid, int workTime) {
        this.inputItem = inputItem;
        this.inputFluid = inputFluid;
        this.outputItems = outputItems;
        this.outputFluid = outputFluid;
        this.workTime = workTime;
    }

    public static StoneMillRecipeBuilder recipe(int workTime, Ingredient inputItem, FluidIngredient inputFluid, FluidStack outputFluid, ItemStack... outputItems) {
        return new StoneMillRecipeBuilder(inputItem, inputFluid, NonNullList.of(ItemStack.EMPTY, outputItems), outputFluid, workTime);
    }

    public static StoneMillRecipeBuilder recipeWithDefaultTime(Ingredient inputItem, FluidIngredient inputFluid, FluidStack outputFluid, ItemStack... outputItems) {
        return recipe(200, inputItem, inputFluid, outputFluid, outputItems);
    }

    public static StoneMillRecipeBuilder recipeWithoutFluid(int workTime, Ingredient inputItem, ItemStack... outputItems) {
        return recipe(workTime, inputItem, FluidIngredient.EMPTY, FluidStack.EMPTY, outputItems);
    }

    public static StoneMillRecipeBuilder recipeWithDefaultTimeWithoutFluid(Ingredient inputItem, ItemStack... outputItems) {
        return recipeWithoutFluid(200, inputItem, outputItems);
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String save) {
        ResourceLocation saveRes = ResourceLocation.tryParse(save);
        this.build(consumerIn, saveRes);
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new Result(id, this.inputItem, this.inputFluid, this.outputItems, this.outputFluid, this.workTime));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        protected final FluidIngredient inputFluid;
        protected final Ingredient inputItem;
        protected final NonNullList<ItemStack> outputItems;
        protected final FluidStack outputFluid;
        protected final int workTime;
        private final RecipeSerializer<StoneMillRecipe> serializer = RecipeRegister.STONE_MILL_SERIALIZER.get();

        public Result(ResourceLocation idIn, Ingredient inputItem, FluidIngredient inputFluid, NonNullList<ItemStack> outputItems, FluidStack outputFluid, int workTime) {
            this.id = TeaStory.rl(idIn.getNamespace(), "stone_mill/" + idIn.getPath());
            this.inputItem = inputItem;
            this.inputFluid = inputFluid;
            this.outputItems = outputItems;
            this.outputFluid = outputFluid;
            this.workTime = workTime;
        }


        @Override
        public void serializeRecipeData(JsonObject json) {
            if (!(inputFluid.matchingFluidStacks == null || inputFluid.getMatchingFluidStacks().isEmpty())) {
                json.add("fluid_ingredient", inputFluid.serialize());
            }
            if (!inputItem.checkInvalidation()) {
                json.add("item_ingredient", inputItem.toJson());
            }

            JsonArray jsonarray = new JsonArray();
            boolean use = false;
            for (ItemStack itemStack : this.outputItems) {
                if (itemStack.isEmpty()) continue;
                JsonObject jsonItem = new JsonObject();
                jsonItem.addProperty("item", ForgeRegistries.ITEMS.getKey(itemStack.getItem()).toString());
                if (itemStack.getCount() > 1) {
                    jsonItem.addProperty("count", itemStack.getCount());
                }
                jsonarray.add(jsonItem);
                use = true;
            }
            if (use)
                json.add("output_items", jsonarray);

            if (!outputFluid.isEmpty()) {
                JsonObject jsonItem = new JsonObject();
                jsonItem.addProperty("fluid", ForgeRegistries.FLUIDS.getKey(this.outputFluid.getFluid()).toString());
                jsonItem.addProperty("amount", this.outputFluid.getAmount());
                json.add("output_fluid", jsonItem);
            }

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
