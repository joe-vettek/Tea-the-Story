package xueluoanping.teastory.plugin.jei;

import xueluoanping.teastory.recipe.stone_mill.StoneMillRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.TileEntityTypeRegistry;


public class StoneMillCategory implements IRecipeCategory<StoneMillRecipe> {
    private final IDrawable icon;
    private final IGuiHelper guiHelper;

    public StoneMillCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        icon = guiHelper.createDrawableItemStack(TileEntityTypeRegistry.STONE_MILL_ITEM.get().getDefaultInstance());
    }


    @Override
    public RecipeType<StoneMillRecipe> getRecipeType() {
        return JEICompat.STONE_MILL_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("info.teastory.stone_mill");
    }

    @Override
    public IDrawable getBackground() {
        return guiHelper.createDrawable(TeaStory.rl("textures/gui/jei/recipes.png"), 0, 105, 148, 60);
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, StoneMillRecipe stoneMillRecipe, IFocusGroup iFocusGroup) {

        builder.addSlot(RecipeIngredientRole.INPUT, 40+1, 21+1)
                .addIngredients(stoneMillRecipe.getIngredients().get(0));

        if (!stoneMillRecipe.getInputFluid().isEmpty()) {
            // builder.addSlot(RecipeIngredientRole.INPUT, 4, 6)
            //         .addFluidStack(stoneMillRecipe.getInputFluid().getMatchingFluidStacks().get(0).getFluid(), 2000);

            builder.addSlot(RecipeIngredientRole.INPUT,  4, 6)
                    .setFluidRenderer(2000, false, 16, 48)
                    // .setOverlay(getBackground(), 0, 0)
                    .addIngredient(NeoForgeTypes.FLUID_STACK, stoneMillRecipe.getInputFluid());
        }
        int n = stoneMillRecipe.getOutputItems().size();
        for (int i = 0; i < n; i++) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 90+1, 3 + i * 18+1)
                    .addItemStack(stoneMillRecipe.getOutputItems().get(i));
        }

        if (!stoneMillRecipe.getOutputFluid().isEmpty()) {
            // builder.addSlot(RecipeIngredientRole.OUTPUT, 128, 6)
            //         .addFluidStack(stoneMillRecipe.getOutputFluid().getFluid(), 2000);
            builder.addSlot(RecipeIngredientRole.OUTPUT,  128, 6)
                    .setFluidRenderer(2000, false, 16, 48)
                    // .setOverlay(getBackground(), 0, 0)
                    .addIngredient(NeoForgeTypes.FLUID_STACK, stoneMillRecipe.getOutputFluid());

        }
    }


}
