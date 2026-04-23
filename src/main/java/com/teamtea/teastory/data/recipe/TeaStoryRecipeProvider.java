package com.teamtea.teastory.data.recipe;


import com.teamtea.teastory.registry.BlockRegister;
import com.teamtea.teastory.registry.FluidRegister;
import com.teamtea.teastory.registry.ItemRegister;
import com.teamtea.teastory.registry.BlockEntityRegister;
import com.teamtea.teastory.recipe.special.FlowerDyeRecipe;
import com.teamtea.teastory.tag.TeaTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class TeaStoryRecipeProvider extends VanillaRecipeProvider {

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new TeaStoryRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "Tea Recipes";
        }
    }

    public TeaStoryRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        HolderLookup.RegistryLookup<Item> items = registries.lookupOrThrow(Registries.ITEM);


        // Special Custom Recipes 自定义特殊配方
        SpecialRecipeBuilder.special(items, FlowerDyeRecipe::new).save(output, "teastory:flower_dye");

        // Decoration Recipes 装饰品配方
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, ItemRegister.BAMBOO_PLANK.get()).define('x', Items.BAMBOO).pattern("xx").pattern("xx").group("bamboo_plank").unlockedBy("has_bamboo", has(Items.BAMBOO)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, BlockRegister.BAMBOO_DOOR.get(), 3).define('x', ItemRegister.BAMBOO_PLANK.get()).pattern("xx").pattern("xx").pattern("xx").group("bamboo_door").unlockedBy("has_bamboo_plank", has(ItemRegister.BAMBOO_PLANK.get())).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, BlockRegister.BAMBOO_GLASS_DOOR.get(), 3).define('x', ItemRegister.BAMBOO_PLANK.get()).define('#', Tags.Items.GLASS_PANES_COLORLESS).pattern("##").pattern("xx").pattern("xx").group("bamboo_glass_door").unlockedBy("has_bamboo_plank", has(ItemRegister.BAMBOO_PLANK.get())).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, BlockRegister.BAMBOO_CHAIR.get()).define('x', ItemRegister.BAMBOO_PLANK.get()).define('#', Items.BAMBOO).pattern("  #").pattern("xxx").pattern("# #").group("bamboo_chair").unlockedBy("has_bamboo_plank", has(ItemRegister.BAMBOO_PLANK.get())).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, BlockRegister.BAMBOO_LANTERN.get()).define('x', Blocks.TORCH).define('#', Items.BAMBOO).pattern("###").pattern("#x#").pattern("###").group("bamboo_lantern").unlockedBy("has_bamboo", has(Items.BAMBOO)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, BlockRegister.BAMBOO_TABLE.get()).define('x', ItemRegister.BAMBOO_PLANK.get()).define('#', Items.BAMBOO).pattern("xxx").pattern("# #").pattern("# #").group("bamboo_table").unlockedBy("has_bamboo_plank", has(ItemRegister.BAMBOO_PLANK.get())).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, BlockRegister.WOODEN_FRAME.get()).define('x', Tags.Items.RODS_WOODEN).define('#', ItemTags.PLANKS).pattern("#x#").pattern("x#x").pattern("x x").group("wooden_frame").unlockedBy("has_plank", has(ItemTags.PLANKS)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, BlockRegister.FRESH_BAMBOO_WALL.get(), 2).define('x', Items.BAMBOO).define('#', Tags.Items.STRINGS).pattern("xxx").pattern("###").pattern("xxx").group("fresh_bamboo_wall").unlockedBy("has_bamboo", has(Items.BAMBOO)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, BlockRegister.WOODEN_TABLE.get()).define('x', ItemTags.PLANKS).define('#', Tags.Items.RODS_WOODEN).pattern("xxx").pattern("# #").pattern("# #").group("wooden_table").unlockedBy("has_plank", has(ItemTags.PLANKS)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, BlockRegister.WOODEN_CHAIR.get()).define('x', ItemTags.PLANKS).define('#', Tags.Items.RODS_WOODEN).pattern("x  ").pattern("xxx").pattern("# #").group("wooden_chair").unlockedBy("has_plank", has(ItemTags.PLANKS)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, BlockRegister.STONE_TABLE.get()).define('x', Blocks.STONE).define('#', Blocks.COBBLESTONE_WALL).pattern("xxx").pattern("# #").pattern("# #").group("stone_table").unlockedBy("has_stone", has(Blocks.STONE)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, BlockRegister.STONE_CHAIR.get()).define('x', Blocks.STONE).define('#', Blocks.COBBLESTONE_WALL).pattern("xxx").pattern("# #").group("stone_chair").unlockedBy("has_stone", has(Blocks.STONE)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.REDSTONE, BlockEntityRegister.WOODEN_TRAY.get()).define('#', Tags.Items.RODS_WOODEN).pattern("# #").pattern("###").group("wooden_tray").unlockedBy("has_rod", has(Tags.Items.RODS_WOODEN)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, BlockRegister.BAMBOO_LATTICE.get(), 2).define('x', Items.BAMBOO).pattern("x x").pattern(" x ").pattern("x x").group("bamboo_lattice").unlockedBy("has_bamboo", has(Items.BAMBOO)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, BlockRegister.SCARECROW.get()).define('#', TeaTags.Items.CROPS_STRAW).define('*', BlockRegister.DRY_HAYSTACK.get()).define('/', Tags.Items.RODS_WOODEN).pattern(" # ").pattern("/*/").pattern(" / ").group("scarecrow").unlockedBy("has_haystack", has(BlockRegister.DRY_HAYSTACK.get())).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, BlockRegister.WET_HAYSTACK.get()).define('#', ItemRegister.WET_STRAW.get()).pattern(" # ").pattern("###").pattern("###").group("haystack").unlockedBy("has_straw", has(ItemRegister.WET_STRAW.get())).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, BlockRegister.DRY_HAYSTACK.get()).define('#', TeaTags.Items.CROPS_STRAW).pattern(" # ").pattern("###").pattern("###").group("haystack").unlockedBy("has_straw", has(TeaTags.Items.CROPS_STRAW)).save(output);

        // Drink Ingredient Recipes 茶饮配料配方
        ShapedRecipeBuilder.shaped(items, RecipeCategory.FOOD, ItemRegister.EMPTY_TEA_BAG.get(), 3).define('/', Items.STRING).define('x', Items.PAPER).pattern(" / ").pattern("xxx").pattern("xxx").group("empty_tea_bag").unlockedBy("has_paper", has(Items.PAPER)).save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, ItemRegister.BLACK_TEA_BAG.get()).requires(ItemRegister.EMPTY_TEA_BAG.get()).requires(Ingredient.of(TeaTags.Items.CROPS_BLACK_TEA_LEAF), 3).group("tea_bag").unlockedBy("has_tea_bag", has(ItemRegister.EMPTY_TEA_BAG.get())).save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, ItemRegister.GREEN_TEA_BAG.get()).requires(ItemRegister.EMPTY_TEA_BAG.get()).requires(Ingredient.of(TeaTags.Items.CROPS_GREEN_TEA_LEAF), 3).group("tea_bag").unlockedBy("has_tea_bag", has(ItemRegister.EMPTY_TEA_BAG.get())).save(output);

        // Tea Set Recipes 茶具配方
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ItemRegister.BOTTLE.get()).define('x', Tags.Items.NUGGETS_IRON).define('#', Tags.Items.GLASS_PANES_COLORLESS).pattern(" x ").pattern("# #").pattern("###").group("bottle").unlockedBy("has_glass_pane", has(Tags.Items.GLASS_PANES_COLORLESS)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ItemRegister.CLAY_CUP.get()).define('x', Items.CLAY_BALL).pattern("x x").pattern(" x ").group("clay_cup").unlockedBy("has_clay_ball", has(Items.CLAY_BALL)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ItemRegister.CLAY_TEAPOT.get()).define('x', Blocks.CLAY).pattern("x x").pattern(" x ").group("clay_teapot").unlockedBy("has_clay_ball", has(Items.CLAY_BALL)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, BlockEntityRegister.IRON_KETTLE.get()).define('*', Items.BUCKET).define('x', Tags.Items.INGOTS_IRON).pattern(" x ").pattern("x*x").pattern("xxx").group("iron_kettle").unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON)).save(output);

        // Craft Block Recipes 工艺方块配方
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, BlockRegister.saucepan.get()).define('#', Tags.Items.INGOTS_IRON).define('*', Items.BUCKET).pattern(" # ").pattern("#*#").pattern("###").group("saucepan").unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, BlockEntityRegister.WOODEN_BARREL.get()).define('#', ItemTags.PLANKS).define('*', ItemTags.WOODEN_SLABS).pattern("* *").pattern("# #").pattern("###").group("wooden_barrel").unlockedBy("has_planks", has(ItemTags.PLANKS)).save(output);

        // Tool & Ingredient Recipes 工具和原料配方
        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ItemRegister.WOODEN_AQUEDUCT_SHOVEL.get()).define('#', Items.WOODEN_SHOVEL).define('*', ItemTags.PLANKS).pattern(" * ").pattern(" # ").group("aqueduct_shovel").unlockedBy("has_shovel", has(Items.WOODEN_SHOVEL)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ItemRegister.STONE_AQUEDUCT_SHOVEL.get()).define('#', Items.STONE_SHOVEL).define('*', Tags.Items.COBBLESTONES).pattern(" * ").pattern(" # ").group("aqueduct_shovel").unlockedBy("has_shovel", has(Items.STONE_SHOVEL)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ItemRegister.GOLD_AQUEDUCT_SHOVEL.get()).define('#', Items.GOLDEN_SHOVEL).define('*', Tags.Items.INGOTS_GOLD).pattern(" * ").pattern(" # ").group("aqueduct_shovel").unlockedBy("has_shovel", has(Items.GOLDEN_SHOVEL)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ItemRegister.IRON_AQUEDUCT_SHOVEL.get()).define('#', Items.IRON_SHOVEL).define('*', Tags.Items.INGOTS_IRON).pattern(" * ").pattern(" # ").group("aqueduct_shovel").unlockedBy("has_shovel", has(Items.IRON_SHOVEL)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ItemRegister.DIAMOND_AQUEDUCT_SHOVEL.get()).define('#', Items.DIAMOND_SHOVEL).define('*', Tags.Items.GEMS_DIAMOND).pattern(" * ").pattern(" # ").group("aqueduct_shovel").unlockedBy("has_shovel", has(Items.DIAMOND_SHOVEL)).save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ItemRegister.IRON_SICKLE.get()).define('/', Tags.Items.RODS_WOODEN).define('*', Tags.Items.INGOTS_IRON).pattern("*/ ").pattern("*/ ").pattern(" / ").group("sickle").unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON)).save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ItemRegister.HONEYCOMB_BRIQUETTE.get()).define('#', ItemTags.COALS).define('*', Items.CLAY_BALL).pattern("# #").pattern(" # ").pattern("* *").group("honeycomb_briquette").unlockedBy("has_coal", has(ItemTags.COALS)).save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ItemRegister.WET_STRAW.get(), 7).requires(BlockRegister.WET_HAYSTACK.get()).group("wet_straw").unlockedBy("has_wet_haystack", has(BlockRegister.WET_HAYSTACK.get())).save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ItemRegister.DRY_STRAW.get(), 7).requires(BlockRegister.DRY_HAYSTACK.get()).group("dry_straw").unlockedBy("has_dry_haystack", has(BlockRegister.DRY_HAYSTACK.get())).save(output);

        // 食物
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, BlockRegister.WOODEN_BOWL_ITEM.get()).requires(Items.BOWL).unlockedBy("has_plank", has(ItemTags.PLANKS)).save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, ItemRegister.RICE_BALL_WITH_KELP.get()).requires(ItemRegister.RICE_BALL.get()).requires(Items.KELP).unlockedBy("has_rice", has(BlockRegister.RICE_GRAINS.get())).save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, ItemRegister.RICE_BOWL.get()).requires(ItemRegister.RICE_BALL.get()).requires(BlockRegister.WOODEN_BOWL_ITEM.get()).unlockedBy("has_rice", has(BlockRegister.RICE_GRAINS.get())).save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, ItemRegister.NETHER_WART_RICE_BOWL.get()).requires(ItemRegister.RICE_BOWL.get()).requires(Items.NETHER_WART).unlockedBy("has_rice", has(BlockRegister.RICE_GRAINS.get())).save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, ItemRegister.BEEF_RICE_BOWL.get()).requires(ItemRegister.RICE_BOWL.get()).requires(Items.COOKED_BEEF).unlockedBy("has_rice", has(BlockRegister.RICE_GRAINS.get())).save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, ItemRegister.PICKLED_CABBAGE_WITH_FISH.get()).requires(ItemRegister.STEAMED_CHINESE_CABBAGE.get()).requires(Items.COOKED_SALMON).unlockedBy("has_rice", has(BlockRegister.RICE_GRAINS.get())).save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, ItemRegister.PORK_BAOZI.get()).requires(BlockRegister.WOODEN_BOWL_ITEM.get()).requires(Items.BREAD).requires(Items.COOKED_PORKCHOP).unlockedBy("has_rice", has(BlockRegister.RICE_GRAINS.get())).save(output);

        // Smelting Recipes 熔炼配方

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.WATER_BUCKET), RecipeCategory.MISC, CookingBookCategory.MISC, FluidRegister.BOILING_WATER_BUCKET.get(), 0.2F, 200).unlockedBy("has_water_bucket", has(Items.WATER_BUCKET)).save(output);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegister.CLAY_CUP.get()), RecipeCategory.MISC, CookingBookCategory.MISC, ItemRegister.PORCELAIN_CUP.get(), 0.2F, 200).unlockedBy("has_clay_cup", has(ItemRegister.CLAY_CUP.get())).save(output);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegister.CLAY_TEAPOT.get()), RecipeCategory.MISC, CookingBookCategory.MISC, BlockEntityRegister.PORCELAIN_TEAPOT.get(), 0.2F, 200).unlockedBy("has_clay_teapot", has(ItemRegister.CLAY_TEAPOT.get())).save(output);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.BAMBOO), RecipeCategory.MISC, CookingBookCategory.MISC, ItemRegister.BAMBOO_CHARCOAL.get(), 0.2F, 200).unlockedBy("has_bamboo", has(Items.BAMBOO)).save(output);

    }
}
