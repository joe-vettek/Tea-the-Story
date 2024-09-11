package com.teamtea.teastory.plugin.jei;


import com.teamtea.teastory.recipe.drink.DrinkRecipe;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;

import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import net.neoforged.neoforge.fluids.FluidStack;
import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.registry.BlockEntityRegister;


public class DrinkMakerCategory implements IRecipeCategory<DrinkRecipe>
{
    private final IDrawable icon;
    private final IGuiHelper guiHelper;

    public DrinkMakerCategory(IGuiHelper guiHelper)
    {
        this.guiHelper = guiHelper;
        icon = guiHelper.createDrawableItemStack(BlockEntityRegister.DRINK_MAKER_ITEM.get().getDefaultInstance());
    }



    @Override
    public RecipeType<DrinkRecipe> getRecipeType() {
        return JEICompat.DRINK_MAKER_TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("info.teastory.drink_maker");
    }

    @Override
    public IDrawable getBackground()
    {
        return guiHelper.createDrawable(TeaStory.rl( "textures/gui/jei/recipes.png"), 0, 0, 149, 75);
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DrinkRecipe drinkRecipe, IFocusGroup iFocusGroup) {

        int n = drinkRecipe.getIngredients().size();
        for (int i = 0; i < n; i++)
        {
            builder.addSlot(RecipeIngredientRole.INPUT,39 + i * 18+1, 29+1)
                    .addIngredients(drinkRecipe.getIngredients().get(i));
        }

        // builder.addSlot(RecipeIngredientRole.INPUT, 6, 6)
        //         .addFluidStack(drinkRecipe.getFluidIngredient().getMatchingFluidStacks().get(0).getFluid(),1000);
        //
        // builder.addSlot(RecipeIngredientRole.OUTPUT, 127, 6)
        //         .addFluidStack(drinkRecipe.getFluidResult(),1000);

        builder.addSlot(RecipeIngredientRole.INPUT,  6, 6)
                .setFluidRenderer(1000, false, 16, 64)
                // .setOverlay(getBackground(), 0, 0)
                .addIngredient(NeoForgeTypes.FLUID_STACK,drinkRecipe.getFluidIngredient().getFluids()[0]);

        builder.addSlot(RecipeIngredientRole.OUTPUT,  127, 6)
                .setFluidRenderer(1000, false, 16, 64)
                // .setOverlay(getBackground(), 0, 0)
                .addIngredient(NeoForgeTypes.FLUID_STACK, new FluidStack(drinkRecipe.getFluidResult(),500));

    }

    @Override
    public void draw(DrinkRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    }
}
