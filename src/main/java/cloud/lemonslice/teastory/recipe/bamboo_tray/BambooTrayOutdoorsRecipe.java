package cloud.lemonslice.teastory.recipe.bamboo_tray;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import xueluoanping.teastory.RecipeRegister;

public class BambooTrayOutdoorsRecipe extends BambooTraySingleInRecipe
{
    public BambooTrayOutdoorsRecipe( String groupIn, Ingredient ingredientIn, ItemStack resultIn, int workTime)
    {
        super( groupIn, ingredientIn, resultIn, workTime);
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegister.BAMBOO_TRAY_OUTDOORS_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegister.BAMBOO_TRAY_OUTDOORS.get();
    }
}
