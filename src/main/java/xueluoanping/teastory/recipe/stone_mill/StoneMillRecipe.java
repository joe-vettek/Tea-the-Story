package xueluoanping.teastory.recipe.stone_mill;


import cloud.lemonslice.teastory.blockentity.StoneMillTileEntity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import xueluoanping.teastory.craft.BlockEntityRecipeWrapper;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import xueluoanping.teastory.RecipeRegister;

import java.util.List;
import java.util.Optional;


public class StoneMillRecipe implements Recipe<BlockEntityRecipeWrapper> {
    protected final String group;
    protected final SizedFluidIngredient inputFluid;
    protected final Ingredient inputItem;
    protected final List<Ingredient> outputItems;
    protected final SizedFluidIngredient outputFluid;
    protected final int workTime;

    public StoneMillRecipe(String groupIn, Ingredient inputItem, SizedFluidIngredient inputFluid, List<Ingredient> outputItems, SizedFluidIngredient outputFluid, int workTime) {
        this.group = groupIn;
        this.inputItem = inputItem;
        this.inputFluid = inputFluid;
        this.outputItems = outputItems;
        this.outputFluid = outputFluid;
        this.workTime = workTime;
    }

    public StoneMillRecipe(String groupIn, Ingredient inputItem, SizedFluidIngredient inputFluid, List<Ingredient> outputItems, int workTime) {
        this(groupIn, inputItem, inputFluid, outputItems, null, workTime);
    }

    public StoneMillRecipe(String groupIn, Ingredient inputItem, List<Ingredient> outputItems, SizedFluidIngredient outputFluid, int workTime) {
        this(groupIn, inputItem, null, outputItems, outputFluid, workTime);
    }

    public StoneMillRecipe(String groupIn, Ingredient inputItem, List<Ingredient> outputItems, int workTime) {
        this(groupIn, inputItem, null, outputItems, null, workTime);
    }

    public StoneMillRecipe(String groupIn, Ingredient ingredient, Optional<SizedFluidIngredient> sizedFluidIngredient, List<Ingredient> outputItems, Optional<SizedFluidIngredient> outputFluid, Integer workTime) {
        this(groupIn, ingredient, sizedFluidIngredient.orElse(null), outputItems, outputFluid.orElse(null), workTime);
    }

    @Override
    public boolean matches(BlockEntityRecipeWrapper inv, Level worldIn) {
        if (this.inputItem.test(inv.getItem(0))) {
            if (inv.getBlockEntity() instanceof StoneMillTileEntity stoneMillTileEntity) {
                FluidStack fluidStack = stoneMillTileEntity.getFluidTank().getFluidInTank(0).copy();
                // return outputFluid.test(fluidStack);
                return stoneMillTileEntity.getFluidTank().fill(getOutputFluid(), IFluidHandler.FluidAction.SIMULATE) == getOutputFluid().getAmount();
            }
        }
        return false;
    }


    @Override
    public ItemStack assemble(BlockEntityRecipeWrapper p_44001_, HolderLookup.Provider p_267165_) {
        return !this.outputItems.isEmpty() ? this.outputItems.get(0).getItems()[0].copy() : ItemStack.EMPTY.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }


    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return !this.outputItems.isEmpty() ? this.outputItems.get(0).getItems()[0] : ItemStack.EMPTY;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, getInputItem());
    }

    public Ingredient getInputItem() {
        return inputItem;
    }

    public FluidStack getOutputFluid() {
        return outputFluid==null||outputFluid.getFluids().length==0?
                FluidStack.EMPTY:outputFluid.getFluids()[0];
    }

    public FluidStack getInputFluid() {
        return inputFluid==null||inputFluid.getFluids().length==0?
                FluidStack.EMPTY:inputFluid.getFluids()[0];

    }

    public NonNullList<ItemStack> getOutputItems() {
        return NonNullList.copyOf(outputItems.stream().map(o -> o.getItems()[0]).toList());
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
        return RecipeRegister.STONE_MILL_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegister.STONE_MILL.get();
    }


    public static class StoneMillRecipeSerializer implements RecipeSerializer<StoneMillRecipe> {
        public static final MapCodec<StoneMillRecipe> codec = RecordCodecBuilder.mapCodec(
                recipeInstance -> recipeInstance.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                                Ingredient.CODEC_NONEMPTY.fieldOf("item_ingredients").forGetter(r -> r.inputItem),
                                SizedFluidIngredient.FLAT_CODEC.optionalFieldOf("fluid_ingredient").forGetter(r -> java.util.Optional.ofNullable(r.inputFluid)),
                                Ingredient.LIST_CODEC.fieldOf("output_items").forGetter(r -> r.outputItems),
                                SizedFluidIngredient.FLAT_CODEC.optionalFieldOf("output_fluid").forGetter(r -> java.util.Optional.ofNullable(r.outputFluid)),
                                Codec.INT.optionalFieldOf("work_time", 200).forGetter(r -> r.workTime)
                        )
                        .apply(recipeInstance, StoneMillRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, StoneMillRecipe> streamCodec =
                StreamCodec.of(StoneMillRecipe.StoneMillRecipeSerializer::toNetwork, StoneMillRecipe.StoneMillRecipeSerializer::fromNetwork);

        public static void toNetwork(RegistryFriendlyByteBuf buffer, StoneMillRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());

            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getInputItem());

            buffer.writeBoolean(recipe.inputFluid != null);
            if (recipe.inputFluid != null)
                SizedFluidIngredient.STREAM_CODEC.encode(buffer, recipe.inputFluid);


            buffer.writeVarInt(recipe.getOutputItems().size());
            for (ItemStack ingredient : recipe.getOutputItems()) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, Ingredient.of(ingredient));
            }

            buffer.writeBoolean(recipe.outputFluid != null);
            if (recipe.outputFluid != null)
                SizedFluidIngredient.STREAM_CODEC.encode(buffer, recipe.outputFluid);

            buffer.writeVarInt(recipe.getWorkTime());
        }

        public static StoneMillRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {

            String groupIn = buffer.readUtf(32767);
            Ingredient inputItem = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);

            var inputFluid =buffer.readBoolean()? SizedFluidIngredient.STREAM_CODEC.decode(buffer):null;

            int i = buffer.readVarInt();
            NonNullList<Ingredient> outputItems = NonNullList.withSize(i, Ingredient.EMPTY);
            for (int j = 0; j < i; ++j) {
                outputItems.set(j, Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            }

            var outputFluid = buffer.readBoolean()?SizedFluidIngredient.STREAM_CODEC.decode(buffer):null;

            int workTime = buffer.readVarInt();

            return new StoneMillRecipe(groupIn, inputItem, inputFluid, outputItems, outputFluid, workTime);
        }


        @Override
        public MapCodec<StoneMillRecipe> codec() {
            return codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, StoneMillRecipe> streamCodec() {
            return streamCodec;
        }
    }

}
