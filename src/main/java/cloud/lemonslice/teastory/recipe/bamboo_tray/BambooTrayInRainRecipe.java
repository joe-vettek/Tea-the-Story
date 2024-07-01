package cloud.lemonslice.teastory.recipe.bamboo_tray;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
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
