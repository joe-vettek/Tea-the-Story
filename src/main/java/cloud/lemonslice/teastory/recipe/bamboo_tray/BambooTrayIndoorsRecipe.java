package cloud.lemonslice.teastory.recipe.bamboo_tray;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import xueluoanping.teastory.RecipeRegister;

public class BambooTrayIndoorsRecipe extends BambooTraySingleInRecipe {
    public BambooTrayIndoorsRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, int workTime) {
        super(idIn, groupIn, ingredientIn, resultIn, workTime);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegister.BAMBOO_TRAY_INDOORS_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegister.BAMBOO_TRAY_INDOORS.get();
    }
}
