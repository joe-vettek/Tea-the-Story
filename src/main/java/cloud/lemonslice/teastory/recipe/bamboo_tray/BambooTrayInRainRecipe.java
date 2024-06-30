package cloud.lemonslice.teastory.recipe.bamboo_tray;

import cloud.lemonslice.teastory.blockentity.StoneMillTileEntity;
import cloud.lemonslice.teastory.recipe.BlockEntityRecipeWrapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.NewRegistryEvent;
import xueluoanping.teastory.RecipeRegister;

public class BambooTrayInRainRecipe extends BambooTraySingleInRecipe
{
    public BambooTrayInRainRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, int workTime)
    {
        super(idIn, groupIn, ingredientIn, resultIn, workTime);
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegister.BAMBOO_TRAY_IN_RAIN_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegister.BAMBOO_TRAY_IN_RAIN.get();
    }
}
