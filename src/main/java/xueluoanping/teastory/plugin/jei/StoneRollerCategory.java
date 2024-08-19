package xueluoanping.teastory.plugin.jei;


import cloud.lemonslice.teastory.recipe.stone_mill.StoneRollerRecipe;
import com.google.common.collect.Lists;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.TileEntityTypeRegistry;


import java.util.Collections;
import java.util.List;


public class StoneRollerCategory implements IRecipeCategory<StoneRollerRecipe> {
    private final IDrawable icon;
    private final IGuiHelper guiHelper;

    public StoneRollerCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        icon = guiHelper.createDrawableItemStack(TileEntityTypeRegistry.STONE_ROLLER_ITEM.get().getDefaultInstance());
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
        return guiHelper.createDrawable(new ResourceLocation(TeaStory.MODID, "textures/gui/jei/recipes.png"), 37, 105, 74, 60);
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
