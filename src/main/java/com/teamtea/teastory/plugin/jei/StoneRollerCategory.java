package com.teamtea.teastory.plugin.jei;


import com.teamtea.teastory.recipe.stone_mill.StoneRollerRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.BlockEntityRegistry;


public class StoneRollerCategory implements IRecipeCategory<StoneRollerRecipe> {
    private final IDrawable icon;
    private final IGuiHelper guiHelper;

    public StoneRollerCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        icon = guiHelper.createDrawableItemStack(BlockEntityRegistry.STONE_ROLLER_ITEM.get().getDefaultInstance());
    }


    @Override
    public RecipeType<StoneRollerRecipe> getRecipeType() {
        return JEICompat.STONE_ROLLER_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("info.teastory.stone_mill");
    }

    @Override
    public IDrawable getBackground() {
        return guiHelper.createDrawable(TeaStory.rl( "textures/gui/jei/recipes.png"), 37, 105, 74, 60);
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, StoneRollerRecipe recipe, IFocusGroup ingredients) {

        builder.addSlot(RecipeIngredientRole.INPUT, 3+1, 21+1)
                .addIngredients(recipe.getIngredients().get(0));


        int n = recipe.getOutputItems().size();
        for (int i = 0; i < n; i++) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 53+1, 3 + i * 18+1)
                    .addItemStack(recipe.getOutputItems().get(0));

        }
    }
}
