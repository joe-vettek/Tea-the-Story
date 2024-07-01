package xueluoanping.teastory.client;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import java.util.function.Supplier;

// TODO：
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RecipeCategories
{
	public static final Supplier<RecipeBookCategories> COOKING_SEARCH = Suppliers.memoize(() -> RecipeBookCategories.create("COOKING_SEARCH", new ItemStack(Items.COMPASS)));
	// public static final Supplier<RecipeBookCategories> COOKING_MEALS = Suppliers.memoize(() -> RecipeBookCategories.create("COOKING_MEALS", new ItemStack(ModItems.VEGETABLE_NOODLES.get())));
	// public static final Supplier<RecipeBookCategories> COOKING_DRINKS = Suppliers.memoize(() -> RecipeBookCategories.create("COOKING_DRINKS", new ItemStack(ModItems.APPLE_CIDER.get())));
	// public static final Supplier<RecipeBookCategories> COOKING_MISC = Suppliers.memoize(() -> RecipeBookCategories.create("COOKING_MISC", new ItemStack(ModItems.DUMPLINGS.get()), new ItemStack(ModItems.TOMATO_SAUCE.get())));

	@SubscribeEvent
	public static void init(RegisterRecipeBookCategoriesEvent event) {
		// event.registerBookCategories(FarmersDelight.RECIPE_TYPE_COOKING, ImmutableList.of(COOKING_SEARCH.get(), COOKING_MEALS.get(), COOKING_DRINKS.get(), COOKING_MISC.get()));
		// event.registerAggregateCategory(COOKING_SEARCH.get(), ImmutableList.of(COOKING_MEALS.get(), COOKING_DRINKS.get(), COOKING_MISC.get()));
		// event.registerRecipeCategoryFinder(ModRecipeTypes.COOKING.get(), recipe ->
		// {
		// 	if (recipe instanceof CookingPotRecipe cookingRecipe) {
		// 		CookingPotRecipeBookTab tab = cookingRecipe.getRecipeBookTab();
		// 		if (tab != null) {
		// 			return switch (tab) {
		// 				case MEALS -> COOKING_MEALS.get();
		// 				case DRINKS -> COOKING_DRINKS.get();
		// 				case MISC -> COOKING_MISC.get();
		// 			};
		// 		}
		// 	}
		// 	return COOKING_MISC.get();
		// });
	}
}
