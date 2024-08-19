package cloud.lemonslice.teastory.recipe.stone_mill;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.NewRegistryEvent;
import xueluoanping.teastory.RecipeRegister;

import javax.annotation.Nullable;

public class StoneRollerRecipe implements Recipe<RecipeWrapper> {
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient inputItem;
    protected final NonNullList<ItemStack> outputItems;
    protected final int workTime;

    public StoneRollerRecipe(ResourceLocation idIn, String groupIn, Ingredient inputItem, NonNullList<ItemStack> outputItems, int workTime) {
        this.id = idIn;
        this.group = groupIn;
        this.inputItem = inputItem;
        this.outputItems = outputItems;
        this.workTime = workTime;
    }


    @Override
    public boolean matches(RecipeWrapper inv, Level worldIn) {
        if (this.inputItem.test(inv.getItem(0))) {
            return true;
        }
        return false;
    }

    @Override
    public ItemStack assemble(RecipeWrapper p_44001_, RegistryAccess p_267165_) {
        return !this.outputItems.isEmpty() ? this.outputItems.get(0).copy() : ItemStack.EMPTY.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return !this.outputItems.isEmpty() ? this.outputItems.get(0) : ItemStack.EMPTY;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, getInputItem());
    }

    public Ingredient getInputItem() {
        return inputItem;
    }

    public NonNullList<ItemStack> getOutputItems() {
        return outputItems;
    }


    public int getWorkTime() {
        return this.workTime;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegister.STONE_ROLLER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegister.STONE_ROLLER.get();
    }


    public static class StoneRollerRecipeSerializer extends NewRegistryEvent implements RecipeSerializer<StoneRollerRecipe> {


        private static NonNullList<ItemStack> readItems(JsonArray array) {
            NonNullList<ItemStack> nonnulllist = NonNullList.create();
            for (int i = 0; i < array.size(); ++i) {
                JsonElement item = array.get(i);
                ItemStack itemStack;
                if (item.isJsonObject()) {
                    itemStack = ShapedRecipe.itemStackFromJson((JsonObject) item);
                } else {
                    itemStack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(((JsonObject) item).get("output_items").getAsString())));
                }
                nonnulllist.add(itemStack);
            }
            return nonnulllist;
        }


        @Override
        public StoneRollerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String group = json.has("group") ? json.get("group").getAsString() : "";

            if (!json.has("item_ingredient"))
                throw new JsonSyntaxException("Missing input ingredient, expected to find a string or object");

            JsonElement jsonelement = json.get("item_ingredient");
            Ingredient inputItem = Ingredient.fromJson(jsonelement);

            if (!json.has("output_items"))
                throw new JsonSyntaxException("Missing output, expected to find a string or object");

            NonNullList<ItemStack> outputItems;
            if (json.has("output_items")) {
                outputItems = readItems(json.getAsJsonArray("output_items"));
            } else {
                outputItems = NonNullList.create();
            }

            int workTime = json.has("work_time") ? json.get("work_time").getAsInt() : 200;
            return new StoneRollerRecipe(recipeId, group, inputItem, outputItems, workTime);
        }

        @Override
        public @Nullable StoneRollerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String groupIn = buffer.readUtf(32767);
            Ingredient inputItem = Ingredient.fromNetwork(buffer);

            int i = buffer.readVarInt();
            NonNullList<ItemStack> outputItems = NonNullList.withSize(i, ItemStack.EMPTY);
            for (int j = 0; j < i; ++j) {
                outputItems.set(j, buffer.readItem());
            }

            int workTime = buffer.readVarInt();

            return new StoneRollerRecipe(recipeId, groupIn, inputItem, outputItems, workTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, StoneRollerRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());
            recipe.getInputItem().toNetwork(buffer);

            buffer.writeVarInt(recipe.getOutputItems().size());
            for (ItemStack ingredient : recipe.getOutputItems()) {
                buffer.writeItemStack(ingredient, false);
            }

            buffer.writeVarInt(recipe.getWorkTime());
        }


    }
}
