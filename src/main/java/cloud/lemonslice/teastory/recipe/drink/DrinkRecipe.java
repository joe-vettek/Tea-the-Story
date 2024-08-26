package cloud.lemonslice.teastory.recipe.drink;

import cloud.lemonslice.teastory.recipe.bamboo_tray.BambooTraySingleInRecipe;
import com.google.common.collect.Lists;
import com.google.gson.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import net.neoforged.neoforge.common.util.RecipeMatcher;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import xueluoanping.teastory.RecipeRegister;

import java.util.List;


public class DrinkRecipe implements Recipe<RecipeWrapper> {
    protected final String group;
    protected final List<Ingredient> ingredients;
    protected final SizedFluidIngredient fluidIngredient;
    protected final SizedFluidIngredient result;

    public DrinkRecipe(String groupIn, List<Ingredient> ingredientIn, SizedFluidIngredient fluidIn, SizedFluidIngredient resultIn) {
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
            if (fluidIngredient.getStacks().length == 0) {
                return false;
            } else {
                boolean flag = false;
                for (FluidStack fluidStack : fluidIngredient.getStacks()) {
                    if (FluidStack.isSameFluid(h.getFluidInTank(0), fluidStack)
                            && h.getFluidInTank(0).getAmount() > fluidStack.getAmount()) {
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
    public ItemStack assemble(RecipeWrapper inv, HolderLookup.Provider pRegistries) {
        return FluidUtil.getFluidHandler(inv.getItem(8).copy()).map(h ->
        {
            ItemStack container = inv.getItem(8).copy();
            CompoundTag fluidTag = new CompoundTag();
            new FluidStack(result, h.getFluidInTank(0).getAmount()).save(pRegistries, fluidTag);
            container.getOrCreateTag().put(FLUID_NBT_KEY, fluidTag);
            return container;
        }).orElse(ItemStack.EMPTY);
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider p_267052_) {
        return ItemStack.EMPTY;
    }


    @Override
    public NonNullList<ItemStack> getRemainingItems(RecipeWrapper inv) {
        return NonNullList.of(FluidUtil.getFluidHandler(inv.getItem(8).copy()).map(h ->
        {
            ItemStack container = inv.getItem(8).copy();
            CompoundTag fluidTag = new CompoundTag();
            (result.getFluids()[0]).save(fluidTag);
            container.getOrCreateTag().put(FLUID_NBT_KEY, fluidTag);
            return container;
        }).orElse(ItemStack.EMPTY));
    }


    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.copyOf(ingredients);
    }

    public Fluid getFluidResult() {
        return result.getFluids()[0].getFluid();
    }

    public SizedFluidIngredient getFluidIngredient() {
        return fluidIngredient;
    }

    public int getFluidAmount() {
        return fluidIngredient.getStacks()[0].getAmount();
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


    public static class DrinkRecipeSerializer implements RecipeSerializer<DrinkRecipe> {
        public static final MapCodec<DrinkRecipe> codec = RecordCodecBuilder.mapCodec(
                recipeInstance -> recipeInstance.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                                Ingredient.LIST_CODEC_NONEMPTY.fieldOf("item_ingredients").forGetter(r -> r.ingredients),
                                SizedFluidIngredient.FLAT_CODEC.optionalFieldOf("fluid_ingredient", SizedFluidIngredient.of(FluidStack.EMPTY)).forGetter(r -> r.fluidIngredient),
                                SizedFluidIngredient.FLAT_CODEC.fieldOf("drink_result").forGetter(r -> r.result)
                        )
                        .apply(recipeInstance, DrinkRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, DrinkRecipe> streamCodec =
                StreamCodec.of(DrinkRecipeSerializer::toNetwork, DrinkRecipeSerializer::fromNetwork);


        public static DrinkRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf(32767);

            int i = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(i, Ingredient.EMPTY);
            for (int j = 0; j < ingredients.size(); ++j) {
                ingredients.set(j, Ingredient.fromNetwork(buffer));
            }

            FluidIngredient fluidIngredient = FluidIngredient.read(buffer);
            Fluid result = buffer.readFluidStack().getFluid();
            return new DrinkRecipe(group, ingredients, fluidIngredient, result);
        }


        public static void toNetwork(RegistryFriendlyByteBuf buffer, DrinkRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());
            buffer.writeVarInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }

            recipe.getFluidIngredient().write(buffer);
            buffer.writeFluidStack(new FluidStack(recipe.getFluidResult(), 1));
        }

        @Override
        public MapCodec<DrinkRecipe> codec() {
            return codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, DrinkRecipe> streamCodec() {
            return streamCodec;
        }
    }
}
