package cloud.lemonslice.teastory.recipe.drink;

import cloud.lemonslice.teastory.recipe.stone_mill.StoneRollerRecipe;
import com.google.common.collect.Lists;
import com.google.gson.*;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.NewRegistryEvent;
import xueluoanping.teastory.RecipeRegister;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

public class DrinkRecipe implements Recipe<RecipeWrapper> {
    protected final ResourceLocation id;
    protected final String group;
    protected final NonNullList<Ingredient> ingredients;
    protected final FluidIngredient fluidIngredient;
    protected final Fluid result;

    public DrinkRecipe(ResourceLocation idIn, String groupIn, NonNullList<Ingredient> ingredientIn, FluidIngredient fluidIn, Fluid resultIn) {
        this.id = idIn;
        this.group = groupIn;
        this.ingredients = ingredientIn;
        this.fluidIngredient = fluidIn;
        this.result = resultIn;
    }


    @Override
    public boolean matches(RecipeWrapper inv, Level worldIn) {
        List<ItemStack> inputs = Lists.newArrayList();
        return FluidUtil.getFluidHandler(inv.getItem(8).copy()).map(h ->
        {
            if (fluidIngredient.getMatchingFluidStacks().isEmpty()) {
                return false;
            } else {
                boolean flag = false;
                for (FluidStack fluidStack : fluidIngredient.getMatchingFluidStacks()) {
                    if (h.getFluidInTank(0).containsFluid(fluidStack)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) return false;

                for (int j = 0; j < 4; ++j) {
                    ItemStack itemstack = inv.getItem(j).copy();
                    if (!itemstack.isEmpty()) {
                        inputs.add(itemstack);
                    }
                }

                return RecipeMatcher.findMatches(inputs, this.ingredients) != null;
            }
        }).orElse(false);
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv, RegistryAccess p_267165_) {
        return FluidUtil.getFluidHandler(inv.getItem(8).copy()).map(h ->
        {
            ItemStack container = inv.getItem(8).copy();
            CompoundTag fluidTag = new CompoundTag();
            new FluidStack(result, h.getFluidInTank(0).getAmount()).writeToNBT(fluidTag);
            container.getOrCreateTag().put(FLUID_NBT_KEY, fluidTag);
            return container;
        }).orElse(ItemStack.EMPTY);
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return ItemStack.EMPTY;
    }


    @Override
    public NonNullList<ItemStack> getRemainingItems(RecipeWrapper inv) {
        return NonNullList.of(FluidUtil.getFluidHandler(inv.getItem(8).copy()).map(h ->
        {
            ItemStack container = inv.getItem(8).copy();
            CompoundTag fluidTag = new CompoundTag();
            new FluidStack(result, h.getFluidInTank(0).getAmount()).writeToNBT(fluidTag);
            container.getOrCreateTag().put(FLUID_NBT_KEY, fluidTag);
            return container;
        }).orElse(ItemStack.EMPTY));
    }


    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    public Fluid getFluidResult() {
        return result;
    }

    public FluidIngredient getFluidIngredient() {
        return fluidIngredient;
    }

    public int getFluidAmount() {
        return fluidIngredient.getMatchingFluidStacks().get(0).getAmount();
    }

    @Override
    public String getGroup() {
        return this.group;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegister.DRINK_MAKER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegister.DRINK_MAKER.get();
    }




    public static class DrinkRecipeSerializer extends NewRegistryEvent implements RecipeSerializer<DrinkRecipe> {


        private static NonNullList<Ingredient> readIngredients(JsonArray array) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();
            for (int i = 0; i < array.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(array.get(i));
                if (!ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }
            return nonnulllist;
        }


        @Override
        public DrinkRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String group = json.has("group") ? json.get("group").getAsString() : "";
            NonNullList<Ingredient> ingredients = readIngredients(json.getAsJsonArray("item_ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for drink recipe");
            } else {
                FluidIngredient fluidIngredient = FluidIngredient.deserialize(json.get("fluid_ingredient"));
                Fluid result = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(json.get("drink_result").getAsString()));
                if (result == null || result == Fluids.EMPTY) {
                    throw new JsonSyntaxException("Result cannot be null");
                }
                return new DrinkRecipe(recipeId, group, ingredients, fluidIngredient, result);
            }
        }

        @Override
        public @org.jetbrains.annotations.Nullable DrinkRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String group = buffer.readUtf(32767);

            int i = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(i, Ingredient.EMPTY);
            for (int j = 0; j < ingredients.size(); ++j) {
                ingredients.set(j, Ingredient.fromNetwork(buffer));
            }

            FluidIngredient fluidIngredient = FluidIngredient.read(buffer);
            Fluid result = buffer.readFluidStack().getFluid();
            return new DrinkRecipe(recipeId, group, ingredients, fluidIngredient, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, DrinkRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());
            buffer.writeVarInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }

            recipe.getFluidIngredient().write(buffer);
            buffer.writeFluidStack(new FluidStack(recipe.getFluidResult(), 1));
        }

    }
}
