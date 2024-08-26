package cloud.lemonslice.teastory.recipe.stone_mill;


import cloud.lemonslice.teastory.blockentity.StoneMillTileEntity;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xueluoanping.teastory.craft.BlockEntityRecipeWrapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import xueluoanping.teastory.craft.FluidIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.NewRegistryEvent;
import xueluoanping.teastory.RecipeRegister;


public class StoneMillRecipe implements Recipe<BlockEntityRecipeWrapper> {
    protected final ResourceLocation id;
    protected final String group;
    protected final FluidIngredient inputFluid;
    protected final Ingredient inputItem;
    protected final NonNullList<ItemStack> outputItems;
    protected final FluidStack outputFluid;
    protected final int workTime;

    public StoneMillRecipe(ResourceLocation idIn, String groupIn, Ingredient inputItem, FluidIngredient inputFluid, NonNullList<ItemStack> outputItems, FluidStack outputFluid, int workTime) {
        this.id = idIn;
        this.group = groupIn;
        this.inputItem = inputItem;
        this.inputFluid = inputFluid;
        this.outputItems = outputItems;
        this.outputFluid = outputFluid;
        this.workTime = workTime;
    }

    @Override
    public boolean matches(BlockEntityRecipeWrapper inv, Level worldIn) {
        if (this.inputItem.test(inv.getItem(0))) {
            if (inv.getBlockEntity() instanceof StoneMillTileEntity stoneMillTileEntity) {
                FluidStack fluidStack = stoneMillTileEntity.getFluidTank().getFluidInTank(0).copy();
                // return outputFluid.test(fluidStack);
                return stoneMillTileEntity.getFluidTank().fill(getOutputFluid(), IFluidHandler.FluidAction.SIMULATE)==getOutputFluid().getAmount();
            }
        }
        return false;
    }

    @Override
    public ItemStack assemble(BlockEntityRecipeWrapper p_44001_, RegistryAccess p_267165_) {
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

    public FluidStack getOutputFluid() {
        return outputFluid;
    }

    public FluidIngredient getInputFluid() {
        return inputFluid;
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
        return RecipeRegister.STONE_MILL_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegister.STONE_MILL.get();
    }


    public static class StoneMillRecipeSerializer extends NewRegistryEvent implements RecipeSerializer<StoneMillRecipe> {
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
        public StoneMillRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String group = json.has("group") ? json.get("group").getAsString() : "";

            if (!json.has("item_ingredient"))
                throw new JsonSyntaxException("Missing input ingredient, expected to find a string or object");

            Ingredient inputItem = Ingredient.fromJson(json.get("item_ingredient"));

            FluidIngredient inputFluid;
            if (json.has("fluid_ingredient")) {
                inputFluid = FluidIngredient.deserialize(json.get("fluid_ingredient"));
            } else {
                inputFluid = FluidIngredient.EMPTY;
            }

            if (!json.has("output_fluid") && !json.has("output_items"))
                throw new JsonSyntaxException("Missing output, expected to find a string or object");

            FluidStack outputFluid;
            if (json.has("output_fluid")) {
                JsonObject jsonOutputFluid = json.getAsJsonObject("output_fluid");
                Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(jsonOutputFluid.get("fluid").getAsString()));
                if (fluid == null) {
                    outputFluid = FluidStack.EMPTY;
                } else {
                    int amount = jsonOutputFluid.get("amount").getAsInt();
                    outputFluid = new FluidStack(fluid, amount);
                }
            } else {
                outputFluid = FluidStack.EMPTY;
            }

            NonNullList<ItemStack> outputItems;
            if (json.has("output_items")) {
                outputItems = readItems(json.getAsJsonArray("output_items"));
            } else {
                outputItems = NonNullList.create();
            }

            int i = json.has("work_time") ? json.get("work_time").getAsInt() : 200;
            return new StoneMillRecipe(recipeId, group, inputItem, inputFluid, outputItems, outputFluid, i);
        }

        @Override
        public StoneMillRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

            String groupIn = buffer.readUtf(32767);
            Ingredient inputItem = Ingredient.fromNetwork(buffer);
            FluidIngredient inputFluid = FluidIngredient.read(buffer);

            int i = buffer.readVarInt();
            NonNullList<ItemStack> outputItems = NonNullList.withSize(i, ItemStack.EMPTY);
            for (int j = 0; j < i; ++j) {
                outputItems.set(j, buffer.readItem());
            }

            FluidStack outputFluid = buffer.readFluidStack();

            int workTime = buffer.readVarInt();

            return new StoneMillRecipe(recipeId, groupIn, inputItem, inputFluid, outputItems, outputFluid, i);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, StoneMillRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());
            recipe.getInputItem().toNetwork(buffer);
            // TeaStory.logger(recipe.getInputFluid().serialize());
            recipe.getInputFluid().write(buffer);

            buffer.writeVarInt(recipe.getOutputItems().size());
            for (ItemStack ingredient : recipe.getOutputItems()) {
                buffer.writeItemStack(ingredient, false);
            }

            FluidIngredient fluidIngredient=FluidIngredient.EMPTY;
            buffer.writeFluidStack(recipe.getOutputFluid());

            buffer.writeVarInt(recipe.getWorkTime());
        }


    }
}
