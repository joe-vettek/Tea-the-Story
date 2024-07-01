package cloud.lemonslice.teastory.recipe.bamboo_tray;

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
import org.jetbrains.annotations.Nullable;

public abstract class BambooTraySingleInRecipe implements Recipe<RecipeWrapper> {
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final int workTime;

    public BambooTraySingleInRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, int workTime) {
        this.id = idIn;
        this.group = groupIn;
        this.ingredient = ingredientIn;
        this.result = resultIn;
        this.workTime = workTime;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level worldIn) {
        return this.ingredient.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeWrapper p_44001_, RegistryAccess p_267165_) {
        return this.result.copy();
    }


    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return this.result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, getIngredient());
    }


    public Ingredient getIngredient() {
        return ingredient;
    }

    public ItemStack getRecipeOutput() {
        return this.result;
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

    public static class BambooTraySingleInRecipeSerializer<T extends BambooTraySingleInRecipe> extends NewRegistryEvent implements RecipeSerializer<T> {
        private final int workTime;
        private final IFactory<T> factory;

        public BambooTraySingleInRecipeSerializer(IFactory<T> factory, int timeIn) {
            this.workTime = timeIn;
            this.factory = factory;
        }


        @Override
        public T fromJson(ResourceLocation recipeId, JsonObject json) {
            String group = json.has("group") ? json.get("group").getAsString() : "";
            JsonElement jsonelement = json.get("ingredient");
            Ingredient ingredient = Ingredient.fromJson(jsonelement);
            if (!json.has("result"))
                throw new JsonSyntaxException("Missing result, expected to find a string or object");
            ItemStack result;
            if (json.get("result").isJsonObject())
                result = ShapedRecipe.itemStackFromJson(json.getAsJsonObject("result"));
            else {
                result = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(json.get("result").getAsString())));
                if (result.isEmpty()) {
                    throw new JsonSyntaxException("Result cannot be null");
                }
            }
            int i = json.has("work_time") ? json.get("work_time").getAsInt() : this.workTime;
            return this.factory.create(recipeId, group, ingredient, result, i);
        }

        @Override
        public @Nullable T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String group = buffer.readUtf(32767);
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack itemstack = buffer.readItem();
            int i = buffer.readVarInt();
            return this.factory.create(recipeId, group, ingredient, itemstack, i);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, BambooTraySingleInRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());
            recipe.getIngredient().toNetwork(buffer);
            buffer.writeItemStack(recipe.getRecipeOutput(), false);
            buffer.writeVarInt(recipe.getWorkTime());
        }

        public interface IFactory<T extends BambooTraySingleInRecipe> {
            T create(ResourceLocation resourceLocation, String group, Ingredient ingredient, ItemStack result, int workingTime);
        }
    }
}
