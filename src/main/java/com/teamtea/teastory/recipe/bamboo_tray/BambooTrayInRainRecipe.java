package com.teamtea.teastory.recipe.bamboo_tray;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import com.teamtea.teastory.RecipeRegister;

public class BambooTrayInRainRecipe extends BambooTraySingleInRecipe {
    public BambooTrayInRainRecipe(String groupIn, Ingredient ingredientIn, ItemStack resultIn, int workTime) {
        super(groupIn, ingredientIn, resultIn, workTime);
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
