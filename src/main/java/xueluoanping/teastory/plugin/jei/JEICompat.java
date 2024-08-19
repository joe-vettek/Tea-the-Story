package xueluoanping.teastory.plugin.jei;


import cloud.lemonslice.teastory.client.gui.BambooTrayGui;
import cloud.lemonslice.teastory.client.gui.DrinkMakerGui;
import cloud.lemonslice.teastory.client.gui.StoneMillGui;
import cloud.lemonslice.teastory.client.gui.StoneRollerGui;
import cloud.lemonslice.teastory.recipe.bamboo_tray.BambooTrayBakeRecipe;
import cloud.lemonslice.teastory.recipe.bamboo_tray.BambooTrayInRainRecipe;
import cloud.lemonslice.teastory.recipe.bamboo_tray.BambooTrayIndoorsRecipe;
import cloud.lemonslice.teastory.recipe.bamboo_tray.BambooTrayOutdoorsRecipe;
import cloud.lemonslice.teastory.recipe.drink.DrinkRecipe;
import cloud.lemonslice.teastory.recipe.stone_mill.StoneMillRecipe;
import cloud.lemonslice.teastory.recipe.stone_mill.StoneRollerRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;
import xueluoanping.teastory.*;

import java.util.List;
import java.util.stream.Collectors;


@JeiPlugin
public final class JEICompat implements IModPlugin {
    private static final ResourceLocation IN_RAIN = TeaStory.rl("bamboo_tray.mode.in_rain");
    private static final ResourceLocation OUTDOORS = TeaStory.rl("bamboo_tray.mode.outdoors");
    private static final ResourceLocation INDOORS = TeaStory.rl("bamboo_tray.mode.indoors");
    private static final ResourceLocation BAKE = TeaStory.rl("bamboo_tray.mode.bake");
    public static final ResourceLocation DRINK_MAKER = TeaStory.rl("drink_maker");
    public static final ResourceLocation STONE_MILL = TeaStory.rl("stone_mill");
    public static final ResourceLocation STONE_ROLLER = TeaStory.rl("stone_roller");

    public static final mezz.jei.api.recipe.RecipeType<BambooTrayInRainRecipe> IN_RAIN_TYPE = getType(IN_RAIN, BambooTrayInRainRecipe.class);
    public static final mezz.jei.api.recipe.RecipeType<BambooTrayOutdoorsRecipe> OUTDOORS_TYPE = getType(OUTDOORS, BambooTrayOutdoorsRecipe.class);
    public static final mezz.jei.api.recipe.RecipeType<BambooTrayIndoorsRecipe> INDOORS_TYPE = getType(INDOORS, BambooTrayIndoorsRecipe.class);
    public static final mezz.jei.api.recipe.RecipeType<BambooTrayBakeRecipe> BAKE_TYPE = getType(BAKE, BambooTrayBakeRecipe.class);
    public static final mezz.jei.api.recipe.RecipeType<DrinkRecipe> DRINK_MAKER_TYPE = getType(DRINK_MAKER, DrinkRecipe.class);
    public static final mezz.jei.api.recipe.RecipeType<StoneMillRecipe> STONE_MILL_TYPE = getType(STONE_MILL, StoneMillRecipe.class);
    public static final mezz.jei.api.recipe.RecipeType<StoneRollerRecipe> STONE_ROLLER_TYPE = getType(STONE_ROLLER, StoneRollerRecipe.class);

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return TeaStory.rl("recipe");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(
                new BambooTraySingleInCategory(registry.getJeiHelpers().getGuiHelper(), IN_RAIN_TYPE, 0),
                new BambooTraySingleInCategory(registry.getJeiHelpers().getGuiHelper(), OUTDOORS_TYPE, 1),
                new BambooTraySingleInCategory(registry.getJeiHelpers().getGuiHelper(), INDOORS_TYPE, 2),
                new BambooTraySingleInCategory(registry.getJeiHelpers().getGuiHelper(), BAKE_TYPE, 3));
        registry.addRecipeCategories(new DrinkMakerCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new StoneMillCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new StoneRollerCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.useNbtForSubtypes(
                BlockRegister.CHRYSANTHEMUM_ITEM.get(),
                BlockRegister.HYACINTH_ITEM.get(),
                BlockRegister.ZINNIA_ITEM.get(),
                ItemRegister.BOTTLE_DRINK.get(),
                ItemRegister.PORCELAIN_CUP_DRINK.get(),
                TileEntityTypeRegistry.PORCELAIN_TEAPOT.get());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(TileEntityTypeRegistry.BAMBOO_TRAY_ITEM.get()), IN_RAIN_TYPE, OUTDOORS_TYPE, INDOORS_TYPE, BAKE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(TileEntityTypeRegistry.DRINK_MAKER_ITEM.get()), DRINK_MAKER_TYPE);
        registration.addRecipeCatalyst(new ItemStack(TileEntityTypeRegistry.STONE_MILL_ITEM.get()), STONE_MILL_TYPE);
        registration.addRecipeCatalyst(new ItemStack(TileEntityTypeRegistry.STONE_ROLLER_ITEM.get()), STONE_ROLLER_TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(BambooTrayGui.class, 77, 32, 22, 17, IN_RAIN_TYPE, OUTDOORS_TYPE, INDOORS_TYPE, BAKE_TYPE);
        registration.addRecipeClickArea(DrinkMakerGui.class, 98, 37, 24, 17, DRINK_MAKER_TYPE);
        registration.addRecipeClickArea(StoneMillGui.class, 95, 37, 22, 17, STONE_MILL_TYPE);
        registration.addRecipeClickArea(StoneRollerGui.class, 77, 37, 22, 17, STONE_ROLLER_TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(IN_RAIN_TYPE, getRecipes(RecipeRegister.BAMBOO_TRAY_IN_RAIN.get()));
        registration.addRecipes(OUTDOORS_TYPE, getRecipes(RecipeRegister.BAMBOO_TRAY_OUTDOORS.get()));
        registration.addRecipes(INDOORS_TYPE, getRecipes(RecipeRegister.BAMBOO_TRAY_INDOORS.get()));
        registration.addRecipes(BAKE_TYPE, getRecipes(RecipeRegister.BAMBOO_TRAY_BAKE.get()));
        registration.addRecipes(DRINK_MAKER_TYPE, getRecipes(RecipeRegister.DRINK_MAKER.get()));
        registration.addRecipes(STONE_MILL_TYPE, getRecipes(RecipeRegister.STONE_MILL.get()));
        registration.addRecipes(STONE_ROLLER_TYPE, getRecipes(RecipeRegister.STONE_ROLLER.get()));

    }

    public static <T> mezz.jei.api.recipe.RecipeType<T> getType(ResourceLocation rs, Class<? extends T> recipeClass) {
        return mezz.jei.api.recipe.RecipeType.create(rs.getNamespace(), rs.getPath(), recipeClass);
    }


    private static <T extends Recipe<?>> List<T> getRecipes(@NotNull RecipeType<T> type) {
        return Minecraft.getInstance().level
                .getRecipeManager()
                .getRecipes()
                .stream()
                .filter(r -> r.getType() == type)
                .map(c -> (T) c)
                .collect(Collectors.toList());
    }
}
