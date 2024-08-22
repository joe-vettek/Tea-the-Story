package xueluoanping.teastory.data.lang;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

import xueluoanping.teastory.BlockRegister;
import xueluoanping.teastory.ItemRegister;
import xueluoanping.teastory.TeaStory;

public class Lang_EN extends LangHelper {
    public Lang_EN(PackOutput gen, ExistingFileHelper helper) {
        super(gen, helper, TeaStory.MODID, "en_us");
    }


    @Override
    protected void addTranslations() {
        add(BlockRegister.cobblestoneAqueduct.get(), "Cobblestone Aqueduct");
        add(BlockRegister.mossyCobblestoneAqueduct.get(), "Mossy Cobblestone Aqueduct Outlet");

        add(BlockRegister.RiceSeedlingBlock.get(), "Rice Grain");
        add(BlockRegister.ricePlant.get(), "Rice Seedlings");
        add(BlockRegister.TEA_SEEDS.get(), "Tea Seeds");
        add(BlockRegister.CHILI_PLANT.get(), "Chili Seeds");
        add(BlockRegister.CHILI.get(), "Chili");
        add(BlockRegister.CHINESE_CABBAGE_PLANT.get(), "Chinese Cabbage Seeds");
        add(BlockRegister.CHINESE_CABBAGE.get(), "Chinese Cabbage");
        add(BlockRegister.GRAPE.get(), "Grape");
        add(BlockRegister.CUCUMBER.get(), "Cucumber");
        add(BlockRegister.BITTER_GOURD.get(), "Bitter Gourd");
        add(BlockRegister.WOODEN_BOWL_ITEM.get(), "Wooden Bowl");

        add(ItemRegister.NETHER_WART_RICE_BOWL.get(), "Nether Wart Rice Bowl");
        add(ItemRegister.SPICY_BEEF_RICE_BOWL.get(), "Spicy Beef Rice Bowl");
        add(ItemRegister.BEEF_RICE_BOWL.get(), "Beef Rice Bowl");
        add(ItemRegister.RICE_BOWL.get(), "Rice Bowl");
        add(ItemRegister.PICKLED_CABBAGE_WITH_FISH.get(), "Pickled Cabbage With Fish");
        add(ItemRegister.STEAMED_CHINESE_CABBAGE.get(), "Steamed Chinese Cabbage");
        add(ItemRegister.HONEY_BITTER_GOURD.get(), "Honey Bitter Gourd");
        add(ItemRegister.SHREDDED_CUCUMBER_SALAD.get(), "Shredded Cucumber Salad");
        add(ItemRegister.PORK_BAOZI.get(), "Pork Baozi");
        add(ItemRegister.BEEF_BURGER.get(), "Beef Burger");
        add(ItemRegister.CHICKEN_BURGER.get(), "Chicken Burger");

        add("itemGroup.teastory.core", "Tea the Story: Core");
        add("itemGroup.teastory.drink", "Tea the Story: Drink");
        add("item.teastory.ash", "Ash");
        add("item.teastory.dried_beetroot", "Dried Beetroot");
        add("item.teastory.dried_carrot", "Dried Carrot");
        add("item.teastory.beef_jerky", "Beef Jerky");
        add("item.teastory.pork_jerky", "Pork Jerky");
        add("item.teastory.chicken_jerky", "Chicken Jerky");
        add("item.teastory.rabbit_jerky", "Rabbit Jerky");
        add("item.teastory.mutton_jerky", "Mutton Jerky");
        add("item.teastory.clay_teapot", "Clay Teapot");
        add("item.teastory.clay_cup", "Clay Cup");
        add("item.teastory.porcelain_cup", "Porcelain Cup");
        add("item.teastory.porcelain_cup_drink", "Porcelain Cup With Drink");
        add("item.teastory.bottle", "Bottle");
        add("item.teastory.bottle_drink", "Bottle With Drink");
        add("item.teastory.boiling_water_bucket", "Boiling Water Bucket");
        add("item.teastory.hot_water_80_bucket", "Hot Water (80℃) Bucket");
        add("item.teastory.hot_water_60_bucket", "Hot Water (60℃) Bucket");
        add("item.teastory.warm_water_bucket", "Warm Water Bucket");
        add("item.teastory.sugary_water_bucket", "Sugary Water Bucket");
        add("item.teastory.weak_green_tea_bucket", "Weak Green Tea Bucket");
        add("item.teastory.weak_black_tea_bucket", "Weak Black Tea Bucket");
        add("item.teastory.weak_white_tea_bucket", "Weak White Tea Bucket");
        add("item.teastory.green_tea_bucket", "Green Tea Bucket");
        add("item.teastory.black_tea_bucket", "Black Tea Bucket");
        add("item.teastory.white_tea_bucket", "White Tea Bucket");
        add("item.teastory.strong_green_tea_bucket", "Strong Green Tea Bucket");
        add("item.teastory.strong_black_tea_bucket", "Strong Black Tea Bucket");
        add("item.teastory.strong_white_tea_bucket", "Strong White Tea Bucket");
        add("item.teastory.apple_juice_bucket", "Apple Juice Bucket");
        add("item.teastory.sugar_cane_juice_bucket", "Sugar Cane Bucket");
        add("item.teastory.carrot_juice_bucket", "Carrot Juice Bucket");
        add("item.teastory.grape_juice_bucket", "Grape Juice Bucket");
        add("item.teastory.cucumber_juice_bucket", "Cucumber Juice Bucket");
        add("item.teastory.bamboo_plank", "Bamboo Plank");
        add("item.teastory.tea_seeds", "Tea Seeds");
        add("item.teastory.tea_leaves", "Tea Leaves");
        add("item.teastory.tea_residues", "Tea Residues");
        add("item.teastory.green_tea_leaves", "Green Tea Leaves");
        add("item.teastory.black_tea_leaves", "Black Tea Leaves");
        add("item.teastory.white_tea_leaves", "White Tea Leaves");
        add("item.teastory.empty_tea_bag", "Empty Tea Bag");
        add("item.teastory.green_tea_bag", "Green Tea Bag");
        add("item.teastory.black_tea_bag", "Black Tea Bag");
        add("item.teastory.rice_grains", "Rice Grains");
        add("item.teastory.rice_seedlings", "Rice Seedlings");
        add("item.teastory.rice", "Rice");
        add("item.teastory.washed_rice", "Washed Rice");
        add("item.teastory.grapes", "Grapes");
        add("item.teastory.cucumber", "Cucumber");
        add("item.teastory.wet_straw", "Fresh Straw");
        add("item.teastory.dry_straw", "Dry Straw");
        add("item.teastory.crushed_straw", "Crushed Straw");
        add("item.teastory.bamboo_charcoal", "Bamboo Charcoal");
        add("item.teastory.honeycomb_briquette", "Honeycomb Briquette");
        add("item.teastory.wooden_aqueduct_shovel", "Wooden Aqueduct Shovel");
        add("item.teastory.stone_aqueduct_shovel", "Stone Aqueduct Shovel");
        add("item.teastory.gold_aqueduct_shovel", "Gold Aqueduct Shovel");
        add("item.teastory.iron_aqueduct_shovel", "Iron Aqueduct Shovel");
        add("item.teastory.diamond_aqueduct_shovel", "Diamond Aqueduct Shovel");
        add("item.teastory.iron_sickle", "Iron Sickle");
        add("item.teastory.raisins", "Purple Raisins");
        add("item.teastory.rice_ball", "Rice Ball");
        add("item.teastory.rice_ball_with_kelp", "Instant Rice Ball");
        add("item.teastory.shennong_chi", "Shennong Chi");
        add("item.teastory.saucepan_lid", "Saucepan Lid");
        add("item.teastory.picking_tea", "Music Disc");
        add("item.teastory.picking_tea.desc", "Picking Tea");
        add("item.teastory.spring_festival_overture", "Music Disc");
        add("item.teastory.spring_festival_overture.desc", "Huanzhi Li - Spring Festival Overture");
        add("item.teastory.flowers_moon", "Music Disc");
        add("item.teastory.flowers_moon.desc", "Blooming Flowers & Full Moon");
        add("item.teastory.moving_up", "Music Disc");
        add("item.teastory.moving_up.desc", "Moving Up");
        add("item.teastory.joyful", "Music Disc");
        add("item.teastory.joyful.desc", "Joyful");
        add("item.teastory.dancing_golden_snake", "Music Disc");
        add("item.teastory.dancing_golden_snake.desc", "Dancing Golden Snake");
        add("item.teastory.green_willow", "Music Disc");
        add("item.teastory.green_willow.desc", "Green Willow");
        add("item.teastory.purple_bamboo_melody", "Music Disc");
        add("item.teastory.purple_bamboo_melody.desc", "Purple Bamboo Melody");
        add("item.teastory.welcome_march", "Music Disc");
        add("item.teastory.welcome_march.desc", "Welcome March");
        add("block.teastory.wooden_frame", "Wooden Frame");
        add("block.teastory.dirt_stove", "Dirt Stove");
        add("block.teastory.stone_stove", "Stone Stove");
        add("block.teastory.bamboo_tray", "Bamboo Tray");
        add("block.teastory.drink_maker", "Drink Maker");
        add("block.teastory.porcelain_teapot", "Porcelain Teapot");
        add("block.teastory.wild_tea_plant", "Wild Tea Plant");
        add("block.teastory.bamboo_door", "Bamboo Door");
        add("block.teastory.bamboo_glass_door", "Bamboo Glass Door");
        add("block.teastory.bamboo_table", "Bamboo Table");
        add("block.teastory.wooden_table", "Wooden Table");
        add("block.teastory.stone_chair", "Stone Bench");
        add("block.teastory.bamboo_lantern", "Bamboo Lantern");
        add("block.teastory.bamboo_chair", "Bamboo Chair");
        add("block.teastory.wooden_chair", "Wooden Chair");
        add("block.teastory.stone_table", "Stone Table");
        add("block.teastory.chrysanthemum", "Chrysanthemum");
        add("block.teastory.hyacinth", "Hyacinth");
        add("block.teastory.zinnia", "Zinnia");
        add("block.teastory.stone_catapult_board", "Stone Catapult Board");
        add("block.teastory.bamboo_catapult_board", "Bamboo Catapult Board");
        add("block.teastory.iron_catapult_board", "Iron Catapult Board");
        add("block.teastory.stone_catapult_board_with_tray", "Automatic Bamboo Tray");
        add("block.teastory.grass_block_with_hole", "Hole");
        add("block.teastory.filter_screen", "Filter Screen");
        add("block.teastory.bamboo_lattice", "Bamboo Lattice");
        add("block.teastory.wooden_tray", "Wooden Tray");
        add("block.teastory.fresh_bamboo_wall", "Fresh Bamboo Wall");
        add("block.teastory.dried_bamboo_wall", "Dried Bamboo Wall");
        add("block.teastory.iron_kettle", "Iron Kettle");
        add("block.teastory.wild_grape", "Wild Grape");
        add("block.teastory.instrument_shelter", "Instrument Shelter");
        add("block.teastory.dirt_aqueduct", "Dirt Aqueduct");
        add("block.teastory.dirt_aqueduct_pool", "Dirt Aqueduct Pool");
        add("block.teastory.paddy_field", "Paddy Field");
        add("block.teastory.scarecrow", "Scarecrow");
        add("block.teastory.dry_haystack", "Dry Haystack");
        add("block.teastory.wet_haystack", "Fresh Haystack");
        add("block.teastory.stone_mill", "Stone Mill");
        add("block.teastory.stone_roller", "Stone Roller");
        add("block.teastory.wooden_barrel", "Wooden Barrel");
        add("block.teastory.saucepan", "Saucepan");
        add("fluid_type.teastory.boiling_water", "Boiling Water");
        add("fluid_type.teastory.hot_water_80", "Hot Water (80℃)");
        add("fluid_type.teastory.hot_water_60", "Hot Water (60℃)");
        add("fluid_type.teastory.warm_water", "Warm Water");
        add("fluid_type.teastory.sugary_water", "Syrup");
        add("fluid_type.teastory.weak_green_tea", "Weak Green Tea");
        add("fluid_type.teastory.weak_black_tea", "Weak Black Tea");
        add("fluid_type.teastory.weak_white_tea", "Weak White Tea");
        add("fluid_type.teastory.green_tea", "Green Tea");
        add("fluid_type.teastory.black_tea", "Black Tea");
        add("fluid_type.teastory.white_tea", "White Tea");
        add("fluid_type.teastory.strong_green_tea", "Strong Green Tea");
        add("fluid_type.teastory.strong_black_tea", "Strong Black Tea");
        add("fluid_type.teastory.strong_white_tea", "Strong White Tea");
        add("fluid_type.teastory.apple_juice", "Apple Juice");
        add("fluid_type.teastory.sugar_cane_juice", "Sugar Cane Juice");
        add("fluid_type.teastory.carrot_juice", "Carrot Juice");
        add("fluid_type.teastory.grape_juice", "Grape Juice");
        add("fluid_type.teastory.cucumber_juice", "Cucumber Juice");
        add("container.teastory.stove", "Stove");
        add("container.teastory.bamboo_tray", "Bamboo Tray");
        add("container.teastory.drink_maker", "Drink Maker");
        add("container.teastory.stone_mill", "Stone Mill");
        add("container.teastory.stone_roller", "Stone Roller");
        add("effect.teastory.agility", "Agility");
        add("effect.teastory.life_drain", "Life Drain");
        add("effect.teastory.photosynthesis", "Photosynthesis");
        add("effect.teastory.defence", "Impenetrable Defence");
        add("effect.teastory.excitement", "Excitement");
        add("info.teastory.bamboo_tray.mode.in_rain", "In-rain Mode");
        add("info.teastory.bamboo_tray.mode.outdoors", "Outdoor Mode");
        add("info.teastory.bamboo_tray.mode.indoors", "Indoor Mode");
        add("info.teastory.bamboo_tray.mode.bake", "Bake Mode");
        add("info.teastory.bamboo_tray.mode.process", "Process Mode");
        add("info.teastory.drink_maker", "Making Drink");
        add("info.teastory.stone_mill", "Grinding");
        add("info.teastory.bed.excited", "You're too excited to go to sleep now");
        add("info.teastory.hyb.flower.color.yellow", "Yellow");
        add("info.teastory.hyb.flower.color.red", "Red");
        add("info.teastory.hyb.flower.color.blue", "Blue");
        add("info.teastory.hyb.flower.color.white", "White");
        add("info.teastory.hyb.flower.color.pink", "Pink");
        add("info.teastory.hyb.flower.color.gold", "Gold");
        add("info.teastory.hyb.flower.color.black", "Black");
        add("info.teastory.hyb.flower.color.orange", "Orange");
        add("info.teastory.tooltip.drink_maker.help.1", "Ingredient Slots");
        add("info.teastory.tooltip.drink_maker.help.2", "Residue Slots");
        add("info.teastory.tooltip.drink_maker.warn.1", "Can't find matched recipe!");
        add("info.teastory.tooltip.drink_maker.warn.2", "The amount of ingredient(s) in red is not enough.");
        add("info.teastory.tooltip.iron_kettle.to_fill", "Right click water to fill");
        add("info.teastory.tooltip.iron_kettle.to_boil", "Heat it on a stove");
        add("info.teastory.record", "You haven't installed Record Resources Pack");
        add("commands.teastory.solar.set", "Set the solar day to %s");
        add("death.attack.boiling", "%1$s was scalded by boiling water");
        add("misc.block.teastory.trellis_suffix", " Trellis");
    }
}
