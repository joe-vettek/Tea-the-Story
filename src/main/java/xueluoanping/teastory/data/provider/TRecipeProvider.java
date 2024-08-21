package xueluoanping.teastory.data.provider;


import cloud.lemonslice.teastory.tag.NormalTags;
import cloud.lemonslice.silveroak.common.recipe.FluidIngredient;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;
import xueluoanping.teastory.*;
import xueluoanping.teastory.data.builder.BambooTrayRecipeBuilder;
import xueluoanping.teastory.data.builder.DrinkRecipeBuilder;
import xueluoanping.teastory.data.builder.StoneMillRecipeBuilder;
import xueluoanping.teastory.data.builder.StoneRollerRecipeBuilder;

import java.util.function.Consumer;

public final class TRecipeProvider extends RecipeProvider {

    public TRecipeProvider(PackOutput generator) {
        super(generator);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // Special Custom Recipes 自定义特殊配方
        SpecialRecipeBuilder.special(RecipeRegister.CRAFTING_SPECIAL_FLOWERDYE.get()).save(consumer, "teastory:flower_dye");

        // Decoration Recipes 装饰品配方
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ItemRegister.BAMBOO_PLANK.get()).define('x', Items.BAMBOO).pattern("xx").pattern("xx").group("bamboo_plank").unlockedBy("has_bamboo", has(Items.BAMBOO)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.BAMBOO_DOOR.get(), 3).define('x', ItemRegister.BAMBOO_PLANK.get()).pattern("xx").pattern("xx").pattern("xx").group("bamboo_door").unlockedBy("has_bamboo_plank", has(ItemRegister.BAMBOO_PLANK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.BAMBOO_GLASS_DOOR.get(), 3).define('x', ItemRegister.BAMBOO_PLANK.get()).define('#', Tags.Items.GLASS_COLORLESS).pattern("##").pattern("xx").pattern("xx").group("bamboo_glass_door").unlockedBy("has_bamboo_plank", has(ItemRegister.BAMBOO_PLANK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.BAMBOO_CHAIR.get()).define('x', ItemRegister.BAMBOO_PLANK.get()).define('#', Items.BAMBOO).pattern("  #").pattern("xxx").pattern("# #").group("bamboo_chair").unlockedBy("has_bamboo_plank", has(ItemRegister.BAMBOO_PLANK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.BAMBOO_LANTERN.get()).define('x', Blocks.TORCH).define('#', Items.BAMBOO).pattern("###").pattern("#x#").pattern("###").group("bamboo_lantern").unlockedBy("has_bamboo", has(Items.BAMBOO)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.BAMBOO_TABLE.get()).define('x', ItemRegister.BAMBOO_PLANK.get()).define('#', Items.BAMBOO).pattern("xxx").pattern("# #").pattern("# #").group("bamboo_table").unlockedBy("has_bamboo_plank", has(ItemRegister.BAMBOO_PLANK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, TileEntityTypeRegistry.BAMBOO_TRAY.get()).define('x', ItemRegister.BAMBOO_PLANK.get()).define('#', Items.BAMBOO).pattern("# #").pattern("#x#").group("bamboo_tray").unlockedBy("has_bamboo_plank", has(ItemRegister.BAMBOO_PLANK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.WOODEN_FRAME.get()).define('x', Tags.Items.RODS_WOODEN).define('#', ItemTags.PLANKS).pattern("#x#").pattern("x#x").pattern("x x").group("wooden_frame").unlockedBy("has_plank", has(ItemTags.PLANKS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.FRESH_BAMBOO_WALL.get(), 2).define('x', Items.BAMBOO).define('#', Tags.Items.STRING).pattern("xxx").pattern("###").pattern("xxx").group("fresh_bamboo_wall").unlockedBy("has_bamboo", has(Items.BAMBOO)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.WOODEN_TABLE.get()).define('x', ItemTags.PLANKS).define('#', Tags.Items.RODS_WOODEN).pattern("xxx").pattern("# #").pattern("# #").group("wooden_table").unlockedBy("has_plank", has(ItemTags.PLANKS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.WOODEN_CHAIR.get()).define('x', ItemTags.PLANKS).define('#', Tags.Items.RODS_WOODEN).pattern("x  ").pattern("xxx").pattern("# #").group("wooden_chair").unlockedBy("has_plank", has(ItemTags.PLANKS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.STONE_TABLE.get()).define('x', Blocks.STONE).define('#', Blocks.COBBLESTONE_WALL).pattern("xxx").pattern("# #").pattern("# #").group("stone_table").unlockedBy("has_stone", has(Blocks.STONE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.STONE_CHAIR.get()).define('x', Blocks.STONE).define('#', Blocks.COBBLESTONE_WALL).pattern("xxx").pattern("# #").group("stone_chair").unlockedBy("has_stone", has(Blocks.STONE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, TileEntityTypeRegistry.WOODEN_TRAY.get()).define('#', Tags.Items.RODS_WOODEN).pattern("# #").pattern("###").group("wooden_tray").unlockedBy("has_rod", has(Tags.Items.RODS_WOODEN)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.BAMBOO_LATTICE.get(), 2).define('x', Items.BAMBOO).pattern("x x").pattern(" x ").pattern("x x").group("bamboo_lattice").unlockedBy("has_bamboo", has(Items.BAMBOO)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.SCARECROW.get()).define('#', NormalTags.Items.CROPS_STRAW).define('*', BlockRegister.DRY_HAYSTACK.get()).define('/', Tags.Items.RODS_WOODEN).pattern(" # ").pattern("/*/").pattern(" / ").group("scarecrow").unlockedBy("has_haystack", has(BlockRegister.DRY_HAYSTACK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.WET_HAYSTACK.get()).define('#', ItemRegister.WET_STRAW.get()).pattern(" # ").pattern("###").pattern("###").group("haystack").unlockedBy("has_straw", has(ItemRegister.WET_STRAW.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegister.DRY_HAYSTACK.get()).define('#', NormalTags.Items.CROPS_STRAW).pattern(" # ").pattern("###").pattern("###").group("haystack").unlockedBy("has_straw", has(NormalTags.Items.CROPS_STRAW)).save(consumer);

        // Drink Ingredient Recipes 茶饮配料配方
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemRegister.EMPTY_TEA_BAG.get(), 3).define('/', Items.STRING).define('x', Items.PAPER).pattern(" / ").pattern("xxx").pattern("xxx").group("empty_tea_bag").unlockedBy("has_paper", has(Items.PAPER)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegister.BLACK_TEA_BAG.get()).requires(ItemRegister.EMPTY_TEA_BAG.get()).requires(Ingredient.of(NormalTags.Items.CROPS_BLACK_TEA_LEAF), 3).group("tea_bag").unlockedBy("has_tea_bag", has(ItemRegister.EMPTY_TEA_BAG.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegister.GREEN_TEA_BAG.get()).requires(ItemRegister.EMPTY_TEA_BAG.get()).requires(Ingredient.of(NormalTags.Items.CROPS_GREEN_TEA_LEAF), 3).group("tea_bag").unlockedBy("has_tea_bag", has(ItemRegister.EMPTY_TEA_BAG.get())).save(consumer);

        // Tea Set Recipes 茶具配方
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegister.BOTTLE.get()).define('x', Tags.Items.NUGGETS_IRON).define('#', Tags.Items.GLASS_PANES_COLORLESS).pattern(" x ").pattern("# #").pattern("###").group("bottle").unlockedBy("has_glass_pane", has(Tags.Items.GLASS_PANES_COLORLESS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegister.CLAY_CUP.get()).define('x', Items.CLAY_BALL).pattern("x x").pattern(" x ").group("clay_cup").unlockedBy("has_clay_ball", has(Items.CLAY_BALL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegister.CLAY_TEAPOT.get()).define('x', Blocks.CLAY).pattern("x x").pattern(" x ").group("clay_teapot").unlockedBy("has_clay_ball", has(Items.CLAY_BALL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TileEntityTypeRegistry.IRON_KETTLE.get()).define('*', Items.BUCKET).define('x', Tags.Items.INGOTS_IRON).pattern(" x ").pattern("x*x").pattern("xxx").group("iron_kettle").unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON)).save(consumer);

        // Craft Block Recipes 工艺方块配方
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TileEntityTypeRegistry.DIRT_STOVE.get()).define('x', NormalTags.Items.DIRT).pattern("xxx").pattern("x x").pattern("xxx").group("stove").unlockedBy("has_dirt", has(NormalTags.Items.DIRT)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TileEntityTypeRegistry.STONE_STOVE.get()).define('x', Tags.Items.STONE).define('#', TileEntityTypeRegistry.DIRT_STOVE.get()).pattern("xxx").pattern("x#x").pattern("xxx").group("stove").unlockedBy("has_dirt", has(NormalTags.Items.DIRT)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TileEntityTypeRegistry.DRINK_MAKER.get()).define('x', ItemTags.PLANKS).define('#', Tags.Items.RODS_WOODEN).pattern("# #").pattern("xxx").group("drink_maker").unlockedBy("has_planks", has(ItemTags.PLANKS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegister.filter_screen.get()).define('x', Tags.Items.RODS_WOODEN).define('#', Tags.Items.STRING).define('*', Tags.Items.DUSTS_REDSTONE).pattern("x#x").pattern("#*#").pattern("x#x").group("filter_screen").unlockedBy("has_string", has(Tags.Items.STRING)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegister.BAMBOO_CATAPULT_BOARD.get()).define('x', ItemRegister.BAMBOO_PLANK.get()).define('#', Tags.Items.STONE).define('*', Tags.Items.DUSTS_REDSTONE).pattern("xxx").pattern("xxx").pattern("#*#").group("catapult_board").unlockedBy("has_bamboo_plank", has(ItemRegister.BAMBOO_PLANK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegister.IRON_CATAPULT_BOARD.get()).define('x', Tags.Items.INGOTS_IRON).define('#', Tags.Items.STONE).define('*', Tags.Items.DUSTS_REDSTONE).pattern("xxx").pattern("xxx").pattern("#*#").group("catapult_board").unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegister.STONE_CATAPULT_BOARD.get()).define('x', Blocks.STONE).define('#', Tags.Items.STONE).define('*', Tags.Items.DUSTS_REDSTONE).pattern("xxx").pattern("xxx").pattern("#*#").group("catapult_board").unlockedBy("has_stone", has(Blocks.STONE)).save(consumer);
        // ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegister.OAK_TRELLIS.get(), 2).define('#', Tags.Items.RODS_WOODEN).define('*', Blocks.OAK_FENCE).pattern("#*#").pattern(" # ").group("trellis").unlockedBy("has_planks", has(Blocks.OAK_FENCE)).save(consumer);
        // ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegister.BIRCH_TRELLIS.get(), 2).define('#', Tags.Items.RODS_WOODEN).define('*', Blocks.BIRCH_FENCE).pattern("#*#").pattern(" # ").group("trellis").unlockedBy("has_planks", has(Blocks.BIRCH_FENCE)).save(consumer);
        // ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegister.SPRUCE_TRELLIS.get(), 2).define('#', Tags.Items.RODS_WOODEN).define('*', Blocks.SPRUCE_FENCE).pattern("#*#").pattern(" # ").group("trellis").unlockedBy("has_planks", has(Blocks.SPRUCE_FENCE)).save(consumer);
        // ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegister.JUNGLE_TRELLIS.get(), 2).define('#', Tags.Items.RODS_WOODEN).define('*', Blocks.JUNGLE_FENCE).pattern("#*#").pattern(" # ").group("trellis").unlockedBy("has_planks", has(Blocks.JUNGLE_FENCE)).save(consumer);
        // ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegister.DARK_OAK_TRELLIS.get(), 2).define('#', Tags.Items.RODS_WOODEN).define('*', Blocks.DARK_OAK_FENCE).pattern("#*#").pattern(" # ").group("trellis").unlockedBy("has_planks", has(Blocks.DARK_OAK_FENCE)).save(consumer);
        // ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegister.ACACIA_TRELLIS.get(), 2).define('#', Tags.Items.RODS_WOODEN).define('*', Blocks.ACACIA_FENCE).pattern("#*#").pattern(" # ").group("trellis").unlockedBy("has_planks", has(Blocks.ACACIA_FENCE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TileEntityTypeRegistry.STONE_MILL.get()).define('#', Blocks.SMOOTH_STONE_SLAB).define('*', Blocks.SMOOTH_STONE).define('/', Tags.Items.RODS_WOODEN).pattern(" / ").pattern(" * ").pattern("###").group("stone_mill").unlockedBy("has_smooth_stone", has(Blocks.SMOOTH_STONE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TileEntityTypeRegistry.STONE_ROLLER.get()).define('#', Blocks.SMOOTH_STONE_SLAB).define('*', Blocks.SMOOTH_STONE).define('/', Tags.Items.RODS_WOODEN).define('+', BlockRegister.WOODEN_FRAME.get()).pattern(" / ").pattern(" +*").pattern("###").group("stone_roller").unlockedBy("has_smooth_stone", has(Blocks.SMOOTH_STONE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegister.saucepan.get()).define('#', Tags.Items.INGOTS_IRON).define('*', Items.BUCKET).pattern(" # ").pattern("#*#").pattern("###").group("saucepan").unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TileEntityTypeRegistry.WOODEN_BARREL.get()).define('#', ItemTags.PLANKS).define('*', ItemTags.WOODEN_SLABS).pattern("* *").pattern("# #").pattern("###").group("wooden_barrel").unlockedBy("has_planks", has(ItemTags.PLANKS)).save(consumer);

        // Tool & Ingredient Recipes 工具和原料配方
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegister.WOODEN_AQUEDUCT_SHOVEL.get()).define('#', Items.WOODEN_SHOVEL).define('*', ItemTags.PLANKS).pattern(" * ").pattern(" # ").group("aqueduct_shovel").unlockedBy("has_shovel", has(Items.WOODEN_SHOVEL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegister.STONE_AQUEDUCT_SHOVEL.get()).define('#', Items.STONE_SHOVEL).define('*', Tags.Items.COBBLESTONE).pattern(" * ").pattern(" # ").group("aqueduct_shovel").unlockedBy("has_shovel", has(Items.STONE_SHOVEL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegister.GOLD_AQUEDUCT_SHOVEL.get()).define('#', Items.GOLDEN_SHOVEL).define('*', Tags.Items.INGOTS_GOLD).pattern(" * ").pattern(" # ").group("aqueduct_shovel").unlockedBy("has_shovel", has(Items.GOLDEN_SHOVEL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegister.IRON_AQUEDUCT_SHOVEL.get()).define('#', Items.IRON_SHOVEL).define('*', Tags.Items.INGOTS_IRON).pattern(" * ").pattern(" # ").group("aqueduct_shovel").unlockedBy("has_shovel", has(Items.IRON_SHOVEL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegister.DIAMOND_AQUEDUCT_SHOVEL.get()).define('#', Items.DIAMOND_SHOVEL).define('*', Tags.Items.GEMS_DIAMOND).pattern(" * ").pattern(" # ").group("aqueduct_shovel").unlockedBy("has_shovel", has(Items.DIAMOND_SHOVEL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegister.IRON_SICKLE.get()).define('/', Tags.Items.RODS_WOODEN).define('*', Tags.Items.INGOTS_IRON).pattern("** ").pattern("*/ ").pattern(" / ").group("sickle").unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON)).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegister.HONEYCOMB_BRIQUETTE.get()).define('#', ItemTags.COALS).define('*', Items.CLAY_BALL).pattern("# #").pattern(" # ").pattern("* *").group("honeycomb_briquette").unlockedBy("has_coal", has(ItemTags.COALS)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegister.WET_STRAW.get(), 7).requires(BlockRegister.WET_HAYSTACK.get()).group("wet_straw").unlockedBy("has_wet_haystack", has(BlockRegister.WET_HAYSTACK.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegister.DRY_STRAW.get(), 7).requires(BlockRegister.DRY_HAYSTACK.get()).group("dry_straw").unlockedBy("has_dry_haystack", has(BlockRegister.DRY_HAYSTACK.get())).save(consumer);

        // Smelting Recipes 熔炼配方

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.WATER_BUCKET), RecipeCategory.MISC, FluidRegistry.BOILING_WATER_BUCKET.get(), 0.2F, 200).unlockedBy("has_water_bucket", has(Items.WATER_BUCKET)).save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegister.CLAY_CUP.get()), RecipeCategory.MISC, ItemRegister.PORCELAIN_CUP.get(), 0.2F, 200).unlockedBy("has_clay_cup", has(ItemRegister.CLAY_CUP.get())).save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegister.CLAY_TEAPOT.get()), RecipeCategory.MISC, TileEntityTypeRegistry.PORCELAIN_TEAPOT.get(), 0.2F, 200).unlockedBy("has_clay_teapot", has(ItemRegister.CLAY_TEAPOT.get())).save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.BAMBOO), RecipeCategory.MISC, ItemRegister.BAMBOO_CHARCOAL.get(), 0.2F, 200).unlockedBy("has_bamboo", has(Items.BAMBOO)).save(consumer);

        // Bamboo Tray In-rain Recipes 竹篾匾淋雨配方
        BambooTrayRecipeBuilder.wetRecipe(Ingredient.of(ItemRegister.RABBIT_JERKY.get()), Items.RABBIT, 200).build(consumer, "teastory:rabbit");
        BambooTrayRecipeBuilder.wetRecipe(Ingredient.of(ItemRegister.PORK_JERKY.get()), Items.PORKCHOP, 200).build(consumer, "teastory:porkchop");
        BambooTrayRecipeBuilder.wetRecipe(Ingredient.of(ItemRegister.BEEF_JERKY.get()), Items.BEEF, 200).build(consumer, "teastory:beef");
        BambooTrayRecipeBuilder.wetRecipe(Ingredient.of(ItemRegister.MUTTON_JERKY.get()), Items.MUTTON, 200).build(consumer, "teastory:mutton");
        BambooTrayRecipeBuilder.wetRecipe(Ingredient.of(ItemRegister.CHICKEN_JERKY.get()), Items.CHICKEN, 200).build(consumer, "teastory:chicken");
        BambooTrayRecipeBuilder.wetRecipe(Ingredient.of(ItemRegister.DRIED_CARROT.get()), Items.CARROT, 200).build(consumer, "teastory:carrot");
        BambooTrayRecipeBuilder.wetRecipe(Ingredient.of(ItemRegister.DRIED_BEETROOT.get()), Items.BEETROOT, 200).build(consumer, "teastory:beetroot");
        BambooTrayRecipeBuilder.wetRecipe(Ingredient.of(NormalTags.Items.CROPS_TEA_LEAF), ItemRegister.TEA_RESIDUES.get(), 200).build(consumer, "teastory:tea_residue");
        BambooTrayRecipeBuilder.wetRecipe(Ingredient.of(NormalTags.Items.CROPS_BLACK_TEA_LEAF), ItemRegister.TEA_RESIDUES.get(), 200).build(consumer, "teastory:black_tea_residue");
        BambooTrayRecipeBuilder.wetRecipe(Ingredient.of(NormalTags.Items.CROPS_GREEN_TEA_LEAF), ItemRegister.TEA_RESIDUES.get(), 200).build(consumer, "teastory:green_tea_residue");
        BambooTrayRecipeBuilder.wetRecipe(Ingredient.of(NormalTags.Items.CROPS_WHITE_TEA_LEAF), ItemRegister.TEA_RESIDUES.get(), 200).build(consumer, "teastory:white_tea_residue");

        // Bamboo Tray Outdoors Recipes 竹篾匾户外配方
        BambooTrayRecipeBuilder.outdoorsRecipe(Ingredient.of(NormalTags.Items.CROPS_TEA_LEAF), ItemRegister.GREEN_TEA_LEAVES.get(), 200).build(consumer);
        BambooTrayRecipeBuilder.outdoorsRecipe(Ingredient.of(Items.ROTTEN_FLESH), Items.LEATHER, 200).build(consumer, "teastory:leather");
        BambooTrayRecipeBuilder.outdoorsRecipe(Ingredient.of(Items.RABBIT), ItemRegister.RABBIT_JERKY.get(), 200).build(consumer);
        BambooTrayRecipeBuilder.outdoorsRecipe(Ingredient.of(Items.PORKCHOP), ItemRegister.PORK_JERKY.get(), 200).build(consumer);
        BambooTrayRecipeBuilder.outdoorsRecipe(Ingredient.of(Items.BEEF), ItemRegister.BEEF_JERKY.get(), 200).build(consumer);
        BambooTrayRecipeBuilder.outdoorsRecipe(Ingredient.of(Items.MUTTON), ItemRegister.MUTTON_JERKY.get(), 200).build(consumer);
        BambooTrayRecipeBuilder.outdoorsRecipe(Ingredient.of(Items.CHICKEN), ItemRegister.CHICKEN_JERKY.get(), 200).build(consumer);
        BambooTrayRecipeBuilder.outdoorsRecipe(Ingredient.of(Items.BEETROOT), ItemRegister.DRIED_BEETROOT.get(), 200).build(consumer);
        BambooTrayRecipeBuilder.outdoorsRecipe(Ingredient.of(Items.CARROT), ItemRegister.DRIED_CARROT.get(), 200).build(consumer);
        BambooTrayRecipeBuilder.outdoorsRecipe(Ingredient.of(NormalTags.Items.FOOD_JERKY), Items.LEATHER, 200).build(consumer, "teastory:jerky_leather");
        BambooTrayRecipeBuilder.outdoorsRecipe(Ingredient.of(NormalTags.Items.CROPS_GRAPE), ItemRegister.RAISINS.get(), 200).build(consumer);
        BambooTrayRecipeBuilder.outdoorsRecipe(Ingredient.of(BlockRegister.FRESH_BAMBOO_WALL.get()), BlockRegister.DRIED_BAMBOO_WALL_ITEM.get(), 200).build(consumer);

        // Bamboo Tray Indoors Recipes 竹篾匾室内配方
        BambooTrayRecipeBuilder.indoorsRecipe(Ingredient.of(NormalTags.Items.FOOD_MEAT), Items.ROTTEN_FLESH, 200).build(consumer, "teastory:rotten_flesh");
        BambooTrayRecipeBuilder.indoorsRecipe(Ingredient.of(Items.SPIDER_EYE), Items.FERMENTED_SPIDER_EYE, 200).build(consumer, "teastory:fermented_spider_eye");
        BambooTrayRecipeBuilder.indoorsRecipe(Ingredient.of(NormalTags.Items.CROPS_TEA_LEAF), ItemRegister.BLACK_TEA_LEAVES.get(), 200).build(consumer);

        // Bamboo Tray Bake Recipes 竹篾匾烘焙配方
        BambooTrayRecipeBuilder.bakeRecipe(Ingredient.of(NormalTags.Items.CROPS_TEA_LEAF), ItemRegister.WHITE_TEA_LEAVES.get(), 200).build(consumer);
        BambooTrayRecipeBuilder.bakeRecipe(Ingredient.of(Items.ROTTEN_FLESH), Items.LEATHER, 200).build(consumer, "teastory:bake_flesh");
        BambooTrayRecipeBuilder.bakeRecipe(Ingredient.of(Items.RABBIT), ItemRegister.RABBIT_JERKY.get(), 200).build(consumer, "teastory:bake_rabbit");
        BambooTrayRecipeBuilder.bakeRecipe(Ingredient.of(Items.PORKCHOP), ItemRegister.PORK_JERKY.get(), 200).build(consumer, "teastory:bake_porkchop");
        BambooTrayRecipeBuilder.bakeRecipe(Ingredient.of(Items.BEEF), ItemRegister.BEEF_JERKY.get(), 200).build(consumer, "teastory:bake_beef");
        BambooTrayRecipeBuilder.bakeRecipe(Ingredient.of(Items.MUTTON), ItemRegister.MUTTON_JERKY.get(), 200).build(consumer, "teastory:bake_mutton");
        BambooTrayRecipeBuilder.bakeRecipe(Ingredient.of(Items.CHICKEN), ItemRegister.CHICKEN_JERKY.get(), 200).build(consumer, "teastory:bake_chicken");
        BambooTrayRecipeBuilder.bakeRecipe(Ingredient.of(Items.BEETROOT), ItemRegister.DRIED_BEETROOT.get(), 200).build(consumer, "teastory:bake_beetroot");
        BambooTrayRecipeBuilder.bakeRecipe(Ingredient.of(Items.CARROT), ItemRegister.DRIED_CARROT.get(), 200).build(consumer, "teastory:bake_carrot");
        BambooTrayRecipeBuilder.bakeRecipe(Ingredient.of(NormalTags.Items.FOOD_JERKY), Items.LEATHER, 200).build(consumer, "teastory:bake_leather");

        // Drink Maker Recipes 沏茶台配方
        DrinkRecipeBuilder.boilingRecipe(FluidRegistry.SUGARY_WATER_STILL.get(), Ingredient.of(Items.SUGAR), Ingredient.of(Items.SUGAR), Ingredient.of(Items.SUGAR), Ingredient.of(Items.SUGAR)).build(consumer);

        DrinkRecipeBuilder.boilingRecipe(FluidRegistry.WEAK_BLACK_TEA_STILL.get(), Ingredient.of(NormalTags.Items.CROPS_BLACK_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_BLACK_TEA_LEAF)).build(consumer);
        DrinkRecipeBuilder.boilingRecipe(FluidRegistry.BLACK_TEA_STILL.get(), Ingredient.of(ItemRegister.BLACK_TEA_BAG.get())).build(consumer, "teastory:black_tea_bag");
        DrinkRecipeBuilder.drinkRecipe(FluidRegistry.BLACK_TEA_STILL.get(), FluidIngredient.fromFluid(FluidRegistry.WEAK_BLACK_TEA_STILL.get(), 500), Ingredient.of(NormalTags.Items.CROPS_BLACK_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_BLACK_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_BLACK_TEA_LEAF)).build(consumer, "teastory:weak_to_black_tea");
        DrinkRecipeBuilder.boilingRecipe(FluidRegistry.BLACK_TEA_STILL.get(), Ingredient.of(NormalTags.Items.CROPS_BLACK_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_BLACK_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_BLACK_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_BLACK_TEA_LEAF)).build(consumer);
        DrinkRecipeBuilder.boilingRecipe(FluidRegistry.STRONG_BLACK_TEA_STILL.get(), Ingredient.of(ItemRegister.BLACK_TEA_BAG.get()), Ingredient.of(ItemRegister.BLACK_TEA_BAG.get())).build(consumer, "teastory:strong_black_tea_bag");
        DrinkRecipeBuilder.drinkRecipe(FluidRegistry.STRONG_BLACK_TEA_STILL.get(), FluidIngredient.fromFluid(FluidRegistry.BLACK_TEA_STILL.get(), 500), Ingredient.of(NormalTags.Items.CROPS_BLACK_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_BLACK_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_BLACK_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_BLACK_TEA_LEAF)).build(consumer);

        DrinkRecipeBuilder.boilingRecipe(FluidRegistry.WEAK_GREEN_TEA_STILL.get(), Ingredient.of(NormalTags.Items.CROPS_GREEN_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_GREEN_TEA_LEAF)).build(consumer);
        DrinkRecipeBuilder.boilingRecipe(FluidRegistry.GREEN_TEA_STILL.get(), Ingredient.of(ItemRegister.GREEN_TEA_BAG.get())).build(consumer, "teastory:green_tea_bag");
        DrinkRecipeBuilder.drinkRecipe(FluidRegistry.GREEN_TEA_STILL.get(), FluidIngredient.fromFluid(FluidRegistry.WEAK_GREEN_TEA_STILL.get(), 500), Ingredient.of(NormalTags.Items.CROPS_GREEN_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_GREEN_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_GREEN_TEA_LEAF)).build(consumer, "teastory:weak_to_green_tea");
        DrinkRecipeBuilder.boilingRecipe(FluidRegistry.GREEN_TEA_STILL.get(), Ingredient.of(NormalTags.Items.CROPS_GREEN_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_GREEN_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_GREEN_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_GREEN_TEA_LEAF)).build(consumer);
        DrinkRecipeBuilder.boilingRecipe(FluidRegistry.STRONG_GREEN_TEA_STILL.get(), Ingredient.of(ItemRegister.GREEN_TEA_BAG.get()), Ingredient.of(ItemRegister.GREEN_TEA_BAG.get())).build(consumer, "teastory:strong_green_tea_bag");
        DrinkRecipeBuilder.drinkRecipe(FluidRegistry.STRONG_GREEN_TEA_STILL.get(), FluidIngredient.fromFluid(FluidRegistry.GREEN_TEA_STILL.get(), 500), Ingredient.of(NormalTags.Items.CROPS_GREEN_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_GREEN_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_GREEN_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_GREEN_TEA_LEAF)).build(consumer);

        DrinkRecipeBuilder.boilingRecipe(FluidRegistry.WEAK_WHITE_TEA_STILL.get(), Ingredient.of(NormalTags.Items.CROPS_WHITE_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_WHITE_TEA_LEAF)).build(consumer);
        DrinkRecipeBuilder.drinkRecipe(FluidRegistry.WHITE_TEA_STILL.get(), FluidIngredient.fromFluid(FluidRegistry.WEAK_WHITE_TEA_STILL.get(), 500), Ingredient.of(NormalTags.Items.CROPS_WHITE_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_WHITE_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_WHITE_TEA_LEAF)).build(consumer, "teastory:weak_to_white_tea");
        DrinkRecipeBuilder.boilingRecipe(FluidRegistry.WHITE_TEA_STILL.get(), Ingredient.of(NormalTags.Items.CROPS_WHITE_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_WHITE_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_WHITE_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_WHITE_TEA_LEAF)).build(consumer);
        DrinkRecipeBuilder.drinkRecipe(FluidRegistry.STRONG_WHITE_TEA_STILL.get(), FluidIngredient.fromFluid(FluidRegistry.WHITE_TEA_STILL.get(), 500), Ingredient.of(NormalTags.Items.CROPS_WHITE_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_WHITE_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_WHITE_TEA_LEAF), Ingredient.of(NormalTags.Items.CROPS_WHITE_TEA_LEAF)).build(consumer);

        // Stone Mill Recipes 石磨配方
        StoneMillRecipeBuilder.recipe(600, Ingredient.of(Tags.Items.STONE), FluidIngredient.fromFluid(Fluids.WATER, 100), new FluidStack(Fluids.WATER, 100), new ItemStack(Blocks.GRAVEL)).build(consumer, "teastory:gravel");
        StoneMillRecipeBuilder.recipeWithDefaultTime(Ingredient.of(NormalTags.Items.CROPS_CUCUMBER), FluidIngredient.EMPTY, new FluidStack(FluidRegistry.CUCUMBER_JUICE_STILL.get(), 100), ItemStack.EMPTY).build(consumer, "teastory:cucumber_juice");
        StoneMillRecipeBuilder.recipeWithDefaultTime(Ingredient.of(NormalTags.Items.CROPS_GRAPE), FluidIngredient.EMPTY, new FluidStack(FluidRegistry.GRAPE_JUICE_STILL.get(), 100), ItemStack.EMPTY).build(consumer, "teastory:grape_juice");
        StoneMillRecipeBuilder.recipeWithDefaultTime(Ingredient.of(NormalTags.Items.CROPS_SUGAR_CANE), FluidIngredient.EMPTY, new FluidStack(FluidRegistry.SUGAR_CANE_JUICE_STILL.get(), 100), ItemStack.EMPTY).build(consumer, "teastory:sugar_cane_juice");
        StoneMillRecipeBuilder.recipeWithDefaultTime(Ingredient.of(Tags.Items.CROPS_CARROT), FluidIngredient.EMPTY, new FluidStack(FluidRegistry.CARROT_JUICE_STILL.get(), 100), ItemStack.EMPTY).build(consumer, "teastory:carrot_juice");
        StoneMillRecipeBuilder.recipeWithDefaultTime(Ingredient.of(NormalTags.Items.CROPS_APPLE), FluidIngredient.EMPTY, new FluidStack(FluidRegistry.APPLE_JUICE_STILL.get(), 100), ItemStack.EMPTY).build(consumer, "teastory:apple_juice");
        StoneMillRecipeBuilder.recipeWithDefaultTimeWithoutFluid(Ingredient.of(NormalTags.Items.CROPS_STRAW), new ItemStack(ItemRegister.CRUSHED_STRAW.get(), 2)).build(consumer, "teastory:crushed_straw");
        StoneMillRecipeBuilder.recipeWithDefaultTimeWithoutFluid(Ingredient.of(Tags.Items.BONES), new ItemStack(Items.BONE_MEAL, 4)).build(consumer, "teastory:bone_meal");

        // Stone Roller Recipes 石碾配方
        StoneRollerRecipeBuilder.recipeWithDefaultTime(Ingredient.of(NormalTags.Items.SEEDS_RICE), new ItemStack(ItemRegister.RICE.get())).build(consumer, "teastory:rice");
        StoneRollerRecipeBuilder.recipeWithDefaultTime(Ingredient.of(Tags.Items.BONES), new ItemStack(Items.BONE_MEAL, 4)).build(consumer, "teastory:bone_meal");
    }
    //
    // @Override
    // public String getName()
    // {
    //     return "Tea the Story Recipes";
    // }
}
