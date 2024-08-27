package xueluoanping.teastory.recipe.bamboo_tray;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;
import xueluoanping.teastory.TeaStory;

public abstract class BambooTraySingleInRecipe implements Recipe<RecipeWrapper> {
    protected final String group;
    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final int workTime;

    public BambooTraySingleInRecipe(String groupIn, Ingredient ingredientIn, ItemStack resultIn, int workTime) {
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
    public ItemStack assemble(RecipeWrapper pInput, HolderLookup.Provider pRegistries) {
        return this.result.copy();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return this.result;
    }


    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
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


    public static class BambooTraySingleInRecipeSerializer<T extends BambooTraySingleInRecipe> implements RecipeSerializer<T> {
        public final MapCodec<T> codec;
        public final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

        private final int workTime;
        public final IFactory<T> factory;

        public BambooTraySingleInRecipeSerializer(IFactory<T> factory, int timeIn) {
            this.workTime = timeIn;
            this.factory = factory;
            this.codec = RecordCodecBuilder.mapCodec(
                    recipeInstance -> recipeInstance.group(
                                    Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                                    Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(BambooTraySingleInRecipe::getIngredient),
                                    ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                                    Codec.INT.optionalFieldOf("work_time", this.workTime).forGetter(BambooTraySingleInRecipe::getWorkTime)
                            )
                            .apply(recipeInstance, this.factory::create)
            );
            this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
        }

        public @Nullable T fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf(32767);
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
            int i = buffer.readVarInt();
            return this.factory.create(group, ingredient, itemstack, i);
        }

        public void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
            buffer.writeUtf(recipe.getGroup());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
            buffer.writeVarInt(recipe.getWorkTime());
        }

        @Override
        public MapCodec<T> codec() {
            return codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
            return streamCodec;
        }

        public interface IFactory<T extends BambooTraySingleInRecipe> {
            T create(String group, Ingredient ingredient, ItemStack result, int workingTime);
        }
    }
}
