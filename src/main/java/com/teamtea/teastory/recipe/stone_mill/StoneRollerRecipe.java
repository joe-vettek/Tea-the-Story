package com.teamtea.teastory.recipe.stone_mill;

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
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import com.teamtea.teastory.RecipeRegister;

import java.util.List;

public class StoneRollerRecipe implements Recipe<RecipeWrapper> {
    protected final String group;
    protected final Ingredient inputItem;
    protected final SizedIngredient outputItems;
    protected final int workTime;

    public StoneRollerRecipe(String groupIn, Ingredient inputItem, SizedIngredient outputItems, int workTime) {
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
    public ItemStack assemble(RecipeWrapper p_44001_, HolderLookup.Provider p_267165_) {
        return this.outputItems.getItems().length > 0 ? this.outputItems.getItems()[0].copy() : ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider p_267052_) {
        return this.outputItems.getItems().length > 0 ? this.outputItems.getItems()[0] : ItemStack.EMPTY;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, getInputItem());
    }

    public Ingredient getInputItem() {
        return inputItem;
    }

    public NonNullList<ItemStack> getOutputItems() {
        return NonNullList.copyOf(List.of(outputItems.getItems()));
    }


    public int getWorkTime() {
        return this.workTime;
    }

    @Override
    public String getGroup() {
        return this.group;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegister.STONE_ROLLER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegister.STONE_ROLLER.get();
    }


    public static class StoneRollerRecipeSerializer implements RecipeSerializer<StoneRollerRecipe> {

        public static final MapCodec<StoneRollerRecipe> codec = RecordCodecBuilder.mapCodec(
                recipeInstance -> recipeInstance.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                                Ingredient.CODEC_NONEMPTY.fieldOf("item_ingredients").forGetter(r -> r.inputItem),
                                SizedIngredient.FLAT_CODEC.fieldOf("output_items").forGetter(r -> r.outputItems),
                                Codec.INT.optionalFieldOf("work_time", 200).forGetter(r -> r.workTime)
                        )
                        .apply(recipeInstance, StoneRollerRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, StoneRollerRecipe> streamCodec =
                StreamCodec.of(StoneRollerRecipe.StoneRollerRecipeSerializer::toNetwork, StoneRollerRecipe.StoneRollerRecipeSerializer::fromNetwork);


        public static void toNetwork(RegistryFriendlyByteBuf buffer, StoneRollerRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());

            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getInputItem());

            // buffer.writeVarInt(recipe.getOutputItems().size());
            // for (ItemStack ingredient : recipe.getOutputItems()) {
            //     Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, Ingredient.of(ingredient));
            // }
            SizedIngredient.STREAM_CODEC.encode(buffer, recipe.outputItems);

            buffer.writeVarInt(recipe.getWorkTime());
        }

        public static StoneRollerRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {

            String groupIn = buffer.readUtf(32767);
            Ingredient inputItem = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);

            // int i = buffer.readVarInt();
            // NonNullList<Ingredient> outputItems = NonNullList.withSize(i, Ingredient.EMPTY);
            // for (int j = 0; j < i; ++j) {
            //     outputItems.set(j, Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            // }
            var outputItems = SizedIngredient.STREAM_CODEC.decode(buffer);


            int workTime = buffer.readVarInt();

            return new StoneRollerRecipe(groupIn, inputItem, outputItems, workTime);
        }


        @Override
        public MapCodec<StoneRollerRecipe> codec() {
            return codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, StoneRollerRecipe> streamCodec() {
            return streamCodec;
        }
    }
}
