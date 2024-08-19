package cloud.lemonslice.teastory.tag;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags;

public final class NormalTags
{
    public static class Items
    {
        public final static TagKey<Item> ARID = ItemTags.create(new ResourceLocation("teastory:crops/arid_arid"));
        public final static TagKey<Item> ARID_DRY = ItemTags.create(new ResourceLocation("teastory:crops/arid_dry"));
        public final static TagKey<Item> ARID_AVERAGE = ItemTags.create(new ResourceLocation("teastory:crops/arid_average"));
        public final static TagKey<Item> ARID_MOIST = ItemTags.create(new ResourceLocation("teastory:crops/arid_moist"));
        public final static TagKey<Item> ARID_HUMID = ItemTags.create(new ResourceLocation("teastory:crops/arid_humid"));
        public final static TagKey<Item> DRY = ItemTags.create(new ResourceLocation("teastory:crops/dry_dry"));
        public final static TagKey<Item> DRY_AVERAGE = ItemTags.create(new ResourceLocation("teastory:crops/dry_average"));
        public final static TagKey<Item> DRY_MOIST = ItemTags.create(new ResourceLocation("teastory:crops/dry_moist"));
        public final static TagKey<Item> DRY_HUMID = ItemTags.create(new ResourceLocation("teastory:crops/dry_humid"));
        public final static TagKey<Item> AVERAGE = ItemTags.create(new ResourceLocation("teastory:crops/average_average"));
        public final static TagKey<Item> AVERAGE_MOIST = ItemTags.create(new ResourceLocation("teastory:crops/average_moist"));
        public final static TagKey<Item> AVERAGE_HUMID = ItemTags.create(new ResourceLocation("teastory:crops/average_humid"));
        public final static TagKey<Item> MOIST = ItemTags.create(new ResourceLocation("teastory:crops/moist_moist"));
        public final static TagKey<Item> MOIST_HUMID = ItemTags.create(new ResourceLocation("teastory:crops/moist_humid"));
        public final static TagKey<Item> HUMID = ItemTags.create(new ResourceLocation("teastory:crops/humid_humid"));

        public final static TagKey<Item> SPRING = ItemTags.create(new ResourceLocation("teastory:crops/spring"));
        public final static TagKey<Item> SUMMER = ItemTags.create(new ResourceLocation("teastory:crops/summer"));
        public final static TagKey<Item> AUTUMN = ItemTags.create(new ResourceLocation("teastory:crops/autumn"));
        public final static TagKey<Item> WINTER = ItemTags.create(new ResourceLocation("teastory:crops/winter"));
        public final static TagKey<Item> SP_SU = ItemTags.create(new ResourceLocation("teastory:crops/spring_summer"));
        public final static TagKey<Item> SP_AU = ItemTags.create(new ResourceLocation("teastory:crops/spring_autumn"));
        public final static TagKey<Item> SP_WI = ItemTags.create(new ResourceLocation("teastory:crops/spring_winter"));
        public final static TagKey<Item> SU_AU = ItemTags.create(new ResourceLocation("teastory:crops/summer_autumn"));
        public final static TagKey<Item> SU_WI = ItemTags.create(new ResourceLocation("teastory:crops/summer_winter"));
        public final static TagKey<Item> AU_WI = ItemTags.create(new ResourceLocation("teastory:crops/autumn_winter"));
        public final static TagKey<Item> SP_SU_AU = ItemTags.create(new ResourceLocation("teastory:crops/spring_summer_autumn"));
        public final static TagKey<Item> SP_SU_WI = ItemTags.create(new ResourceLocation("teastory:crops/spring_summer_winter"));
        public final static TagKey<Item> SP_AU_WI = ItemTags.create(new ResourceLocation("teastory:crops/spring_autumn_winter"));
        public final static TagKey<Item> SU_AU_WI = ItemTags.create(new ResourceLocation("teastory:crops/summer_autumn_winter"));
        public final static TagKey<Item> ALL_SEASONS = ItemTags.create(new ResourceLocation("teastory:crops/all_seasons"));

        public final static TagKey<Item> SEEDS_AGAVE = ItemTags.create(new ResourceLocation("forge:seeds/agave"));
        public final static TagKey<Item> SEEDS_AMARANTH = ItemTags.create(new ResourceLocation("forge:seeds/amaranth"));
        public final static TagKey<Item> SEEDS_ARROWROOT = ItemTags.create(new ResourceLocation("forge:seeds/arrowroot"));
        public final static TagKey<Item> SEEDS_ARTICHOKE = ItemTags.create(new ResourceLocation("forge:seeds/artichoke"));
        public final static TagKey<Item> SEEDS_ASPARAGUS = ItemTags.create(new ResourceLocation("forge:seeds/asparagus"));
        public final static TagKey<Item> SEEDS_BARLEY = ItemTags.create(new ResourceLocation("forge:seeds/barley"));
        public final static TagKey<Item> SEEDS_BEAN = ItemTags.create(new ResourceLocation("forge:seeds/bean"));
        public final static TagKey<Item> SEEDS_BELL_PEPPER = ItemTags.create(new ResourceLocation("forge:seeds/bell_pepper"));
        public final static TagKey<Item> SEEDS_BLACKBERRY = ItemTags.create(new ResourceLocation("forge:seeds/blackberry"));
        public final static TagKey<Item> SEEDS_BLUEBERRY = ItemTags.create(new ResourceLocation("forge:seeds/blueberry"));
        public final static TagKey<Item> SEEDS_BROCCOLI = ItemTags.create(new ResourceLocation("forge:seeds/broccoli"));
        public final static TagKey<Item> SEEDS_BRUSSELS_SPROUT = ItemTags.create(new ResourceLocation("forge:seeds/brussels_sprout"));
        public final static TagKey<Item> SEEDS_CABBAGE = ItemTags.create(new ResourceLocation("forge:seeds/cabbage"));
        public final static TagKey<Item> SEEDS_CACTUS_FRUIT = ItemTags.create(new ResourceLocation("forge:seeds/cactus_fruit"));
        public final static TagKey<Item> SEEDS_CANDLE_BERRY = ItemTags.create(new ResourceLocation("forge:seeds/candle_berry"));
        public final static TagKey<Item> SEEDS_CANTALOUPE = ItemTags.create(new ResourceLocation("forge:seeds/cantaloupe"));
        public final static TagKey<Item> SEEDS_CARROT = ItemTags.create(new ResourceLocation("forge:seeds/carrot"));
        public final static TagKey<Item> SEEDS_CASSAVA = ItemTags.create(new ResourceLocation("forge:seeds/cassava"));
        public final static TagKey<Item> SEEDS_CAULIFLOWER = ItemTags.create(new ResourceLocation("forge:seeds/cauliflower"));
        public final static TagKey<Item> SEEDS_CELERY = ItemTags.create(new ResourceLocation("forge:seeds/celery"));
        public final static TagKey<Item> SEEDS_CHICKPEA = ItemTags.create(new ResourceLocation("forge:seeds/chickpea"));
        public final static TagKey<Item> SEEDS_CHILI_PEPPER = ItemTags.create(new ResourceLocation("forge:seeds/chili_pepper"));
        public final static TagKey<Item> SEEDS_COFFEE_BEAN = ItemTags.create(new ResourceLocation("forge:seeds/coffee_bean"));
        public final static TagKey<Item> SEEDS_CORN = ItemTags.create(new ResourceLocation("forge:seeds/corn"));
        public final static TagKey<Item> SEEDS_COTTON = ItemTags.create(new ResourceLocation("forge:seeds/cotton"));
        public final static TagKey<Item> SEEDS_CRANBERRY = ItemTags.create(new ResourceLocation("forge:seeds/cranberry"));
        public final static TagKey<Item> SEEDS_CUCUMBER = ItemTags.create(new ResourceLocation("forge:seeds/cucumber"));
        public final static TagKey<Item> SEEDS_CUMIN = ItemTags.create(new ResourceLocation("forge:seeds/cumin"));
        public final static TagKey<Item> SEEDS_EGGPLANT = ItemTags.create(new ResourceLocation("forge:seeds/eggplant"));
        public final static TagKey<Item> SEEDS_ELDERBERRY = ItemTags.create(new ResourceLocation("forge:seeds/elderberry"));
        public final static TagKey<Item> SEEDS_FLAX = ItemTags.create(new ResourceLocation("forge:seeds/flax"));
        public final static TagKey<Item> SEEDS_GARLIC = ItemTags.create(new ResourceLocation("forge:seeds/garlic"));
        public final static TagKey<Item> SEEDS_GINGER = ItemTags.create(new ResourceLocation("forge:seeds/ginger"));
        public final static TagKey<Item> SEEDS_GRAPE = ItemTags.create(new ResourceLocation("forge:seeds/grape"));
        public final static TagKey<Item> SEEDS_GREEN_GRAPE = ItemTags.create(new ResourceLocation("forge:seeds/green_grape"));
        public final static TagKey<Item> SEEDS_HONEYDEW = ItemTags.create(new ResourceLocation("forge:seeds/honeydew"));
        public final static TagKey<Item> SEEDS_HUCKLEBERRY = ItemTags.create(new ResourceLocation("forge:seeds/huckleberry"));
        public final static TagKey<Item> SEEDS_JICAMA = ItemTags.create(new ResourceLocation("forge:seeds/jicama"));
        public final static TagKey<Item> SEEDS_JUNIPER_BERRY = ItemTags.create(new ResourceLocation("forge:seeds/juniper_berry"));
        public final static TagKey<Item> SEEDS_JUTE = ItemTags.create(new ResourceLocation("forge:seeds/jute"));
        public final static TagKey<Item> SEEDS_KALE = ItemTags.create(new ResourceLocation("forge:seeds/kale"));
        public final static TagKey<Item> SEEDS_KENAF = ItemTags.create(new ResourceLocation("forge:seeds/kenaf"));
        public final static TagKey<Item> SEEDS_KIWI = ItemTags.create(new ResourceLocation("forge:seeds/kiwi"));
        public final static TagKey<Item> SEEDS_KOHLRABI = ItemTags.create(new ResourceLocation("forge:seeds/kohlrabi"));
        public final static TagKey<Item> SEEDS_LEEK = ItemTags.create(new ResourceLocation("forge:seeds/leek"));
        public final static TagKey<Item> SEEDS_LENTIL = ItemTags.create(new ResourceLocation("forge:seeds/lentil"));
        public final static TagKey<Item> SEEDS_LETTUCE = ItemTags.create(new ResourceLocation("forge:seeds/lettuce"));
        public final static TagKey<Item> SEEDS_MILLET = ItemTags.create(new ResourceLocation("forge:seeds/millet"));
        public final static TagKey<Item> SEEDS_MULBERRY = ItemTags.create(new ResourceLocation("forge:seeds/mulberry"));
        public final static TagKey<Item> SEEDS_MUSTARD_SEEDS = ItemTags.create(new ResourceLocation("forge:seeds/mustard_seeds"));
        public final static TagKey<Item> SEEDS_OAT = ItemTags.create(new ResourceLocation("forge:seeds/oat"));
        public final static TagKey<Item> SEEDS_OKRA = ItemTags.create(new ResourceLocation("forge:seeds/okra"));
        public final static TagKey<Item> SEEDS_ONION = ItemTags.create(new ResourceLocation("forge:seeds/onion"));
        public final static TagKey<Item> SEEDS_PARSNIP = ItemTags.create(new ResourceLocation("forge:seeds/parsnip"));
        public final static TagKey<Item> SEEDS_PEA = ItemTags.create(new ResourceLocation("forge:seeds/pea"));
        public final static TagKey<Item> SEEDS_PEANUT = ItemTags.create(new ResourceLocation("forge:seeds/peanut"));
        public final static TagKey<Item> SEEDS_PEPPER = ItemTags.create(new ResourceLocation("forge:seeds/pepper"));
        public final static TagKey<Item> SEEDS_PINEAPPLE = ItemTags.create(new ResourceLocation("forge:seeds/pineapple"));
        public final static TagKey<Item> SEEDS_POTATO = ItemTags.create(new ResourceLocation("forge:seeds/potato"));
        public final static TagKey<Item> SEEDS_QUINOA = ItemTags.create(new ResourceLocation("forge:seeds/quinoa"));
        public final static TagKey<Item> SEEDS_RADISH = ItemTags.create(new ResourceLocation("forge:seeds/radish"));
        public final static TagKey<Item> SEEDS_RASPBERRY = ItemTags.create(new ResourceLocation("forge:seeds/raspberry"));
        public final static TagKey<Item> SEEDS_RHUBARB = ItemTags.create(new ResourceLocation("forge:seeds/rhubarb"));
        public final static TagKey<Item> SEEDS_RICE = ItemTags.create(new ResourceLocation("forge:seeds/rice"));
        public final static TagKey<Item> SEEDS_RUTABAGA = ItemTags.create(new ResourceLocation("forge:seeds/rutabaga"));
        public final static TagKey<Item> SEEDS_RYE = ItemTags.create(new ResourceLocation("forge:seeds/rye"));
        public final static TagKey<Item> SEEDS_SCALLION = ItemTags.create(new ResourceLocation("forge:seeds/scallion"));
        public final static TagKey<Item> SEEDS_SESAME_SEEDS = ItemTags.create(new ResourceLocation("forge:seeds/sesame_seeds"));
        public final static TagKey<Item> SEEDS_SISAL = ItemTags.create(new ResourceLocation("forge:seeds/sisal"));
        public final static TagKey<Item> SEEDS_SORGHUM = ItemTags.create(new ResourceLocation("forge:seeds/sorghum"));
        public final static TagKey<Item> SEEDS_SOYBEAN = ItemTags.create(new ResourceLocation("forge:seeds/soybean"));
        public final static TagKey<Item> SEEDS_SPINACH = ItemTags.create(new ResourceLocation("forge:seeds/spinach"));
        public final static TagKey<Item> SEEDS_STRAWBERRY = ItemTags.create(new ResourceLocation("forge:seeds/strawberry"));
        public final static TagKey<Item> SEEDS_SUNFLOWER = ItemTags.create(new ResourceLocation("forge:seeds/sunflower"));
        public final static TagKey<Item> SEEDS_SWEET_POTATO = ItemTags.create(new ResourceLocation("forge:seeds/sweet_potato"));
        public final static TagKey<Item> SEEDS_TARO = ItemTags.create(new ResourceLocation("forge:seeds/taro"));
        public final static TagKey<Item> SEEDS_TEA_LEAF = ItemTags.create(new ResourceLocation("forge:seeds/tea_leaf"));
        public final static TagKey<Item> SEEDS_TOMATILLO = ItemTags.create(new ResourceLocation("forge:seeds/tomatillo"));
        public final static TagKey<Item> SEEDS_TOMATO = ItemTags.create(new ResourceLocation("forge:seeds/tomato"));
        public final static TagKey<Item> SEEDS_TURNIP = ItemTags.create(new ResourceLocation("forge:seeds/turnip"));
        public final static TagKey<Item> SEEDS_WATER_CHESTNUT = ItemTags.create(new ResourceLocation("forge:seeds/water_chestnut"));
        public final static TagKey<Item> SEEDS_WHITE_MUSHROOM = ItemTags.create(new ResourceLocation("forge:seeds/white_mushroom"));
        public final static TagKey<Item> SEEDS_WINTER_SQUASH = ItemTags.create(new ResourceLocation("forge:seeds/winter_squash"));
        public final static TagKey<Item> SEEDS_YAM = ItemTags.create(new ResourceLocation("forge:seeds/yam"));
        public final static TagKey<Item> SEEDS_ZUCCHINI = ItemTags.create(new ResourceLocation("forge:seeds/zucchini"));

        public final static TagKey<Item> CROPS_BLACK_TEA_LEAF = ItemTags.create(new ResourceLocation("forge:crops/black_tea_leaf"));
        public final static TagKey<Item> CROPS_GREEN_TEA_LEAF = ItemTags.create(new ResourceLocation("forge:crops/green_tea_leaf"));
        public final static TagKey<Item> CROPS_TEA_LEAF = ItemTags.create(new ResourceLocation("forge:crops/tea_leaf"));
        public final static TagKey<Item> CROPS_WHITE_TEA_LEAF = ItemTags.create(new ResourceLocation("forge:crops/white_tea_leaf"));
        public final static TagKey<Item> CROPS_GRAPE = ItemTags.create(new ResourceLocation("forge:crops/grape"));
        public final static TagKey<Item> CROPS_CUCUMBER = ItemTags.create(new ResourceLocation("forge:crops/cucumber"));
        public final static TagKey<Item> CROPS_STRAW = ItemTags.create(new ResourceLocation("forge:crops/straw"));
        public final static TagKey<Item> CROPS_RICE = ItemTags.create(new ResourceLocation("forge:crops/rice"));
        public final static TagKey<Item> CROPS_APPLE = ItemTags.create(new ResourceLocation("forge:crops/apple"));
        public final static TagKey<Item> CROPS_SUGAR_CANE = ItemTags.create(new ResourceLocation("forge:crops/sugar_cane"));

        public final static TagKey<Item> FOOD_JERKY = ItemTags.create(new ResourceLocation("forge:food/jerky"));
        public final static TagKey<Item> FOOD_MEAT = ItemTags.create(new ResourceLocation("forge:food/meat"));

        public final static TagKey<Item> DIRT = ItemTags.DIRT;
        public final static TagKey<Item> DUSTS_ASH = ItemTags.create(new ResourceLocation("forge:dusts/ash"));
    }

    public static class Blocks
    {

    }

    public static class Fluids
    {
        public final static TagKey<Fluid> DRINK = FluidTags.create(new ResourceLocation("teastory:drink"));
    }
}
