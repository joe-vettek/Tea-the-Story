package xueluoanping.teastory.recipe.bamboo_tray;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import xueluoanping.teastory.RecipeRegister;

public class BambooTrayBakeRecipe extends BambooTraySingleInRecipe
{
    public BambooTrayBakeRecipe(String groupIn, Ingredient ingredientIn, ItemStack resultIn, int workTime)
    {
        super( groupIn, ingredientIn, resultIn, workTime);
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegister.BAMBOO_TRAY_BAKE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegister.BAMBOO_TRAY_BAKE.get();
    }
}
