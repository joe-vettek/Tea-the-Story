package com.teamtea.teastory.plugin.jei;


import com.teamtea.teastory.recipe.bamboo_tray.BambooTraySingleInRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.common.Constants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import com.teamtea.teastory.TeaStory;

public class BambooTraySingleInCategory<T extends BambooTraySingleInRecipe> implements IRecipeCategory<T> {
    private final RecipeType<T> uid;
    private final IDrawable icon;
    private final IGuiHelper guiHelper;
    protected final IDrawable arrow;
    protected final IDrawable slot;

    public BambooTraySingleInCategory(IGuiHelper guiHelper, RecipeType<T> uid, int i) {
        this.guiHelper = guiHelper;
        this.uid = uid;
        icon = guiHelper.createDrawable(TeaStory.rl("textures/gui/jei/bamboo_tray.png"), i * 20, 0, 20, 20);
        this.arrow = guiHelper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 82, 128, 24, 17).build();
        this.slot = guiHelper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 0, 227, 18, 19).build();
    }


    @Override
    public RecipeType<T> getRecipeType() {
        return uid;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("info.teastory." + uid.getUid().getPath());
    }

    @Override
    public IDrawable getBackground() {
        return guiHelper.createBlankDrawable(80, 20);
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BambooTraySingleInRecipe bambooTraySingleInRecipe, IFocusGroup iFocusGroup) {

        builder.addSlot(RecipeIngredientRole.INPUT, 5 + 1, 1 + 1)
                .setBackground(this.slot, -1, -2)
                .addIngredients(bambooTraySingleInRecipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 56 + 1, 1 + 1)
                .setBackground(this.slot, -1, -2)
                .addItemStack(bambooTraySingleInRecipe.getRecipeOutput());

    }

    @Override
    public void draw(BambooTraySingleInRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.arrow.draw(guiGraphics, 28, 2);
    }


}
