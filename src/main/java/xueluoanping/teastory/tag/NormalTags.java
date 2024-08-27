package xueluoanping.teastory.tag;


import net.minecraft.core.registries.Registries;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import xueluoanping.teastory.TeaStory;

public final class NormalTags {
    public static class Items {
        public final static TagKey<Item> ARID = ItemTags.create(TeaStory.rl("crops/arid_arid"));
        public final static TagKey<Item> ARID_DRY = ItemTags.create(TeaStory.rl("crops/arid_dry"));
        public final static TagKey<Item> ARID_AVERAGE = ItemTags.create(TeaStory.rl("crops/arid_average"));
        public final static TagKey<Item> ARID_MOIST = ItemTags.create(TeaStory.rl("crops/arid_moist"));
        public final static TagKey<Item> ARID_HUMID = ItemTags.create(TeaStory.rl("crops/arid_humid"));
        public final static TagKey<Item> DRY = ItemTags.create(TeaStory.rl("crops/dry_dry"));
        public final static TagKey<Item> DRY_AVERAGE = ItemTags.create(TeaStory.rl("crops/dry_average"));
        public final static TagKey<Item> DRY_MOIST = ItemTags.create(TeaStory.rl("crops/dry_moist"));
        public final static TagKey<Item> DRY_HUMID = ItemTags.create(TeaStory.rl("crops/dry_humid"));
        public final static TagKey<Item> AVERAGE = ItemTags.create(TeaStory.rl("crops/average_average"));
        public final static TagKey<Item> AVERAGE_MOIST = ItemTags.create(TeaStory.rl("crops/average_moist"));
        public final static TagKey<Item> AVERAGE_HUMID = ItemTags.create(TeaStory.rl("crops/average_humid"));
        public final static TagKey<Item> MOIST = ItemTags.create(TeaStory.rl("crops/moist_moist"));
        public final static TagKey<Item> MOIST_HUMID = ItemTags.create(TeaStory.rl("crops/moist_humid"));
        public final static TagKey<Item> HUMID = ItemTags.create(TeaStory.rl("crops/humid_humid"));

        public final static TagKey<Item> SPRING = ItemTags.create(TeaStory.rl("crops/spring"));
        public final static TagKey<Item> SUMMER = ItemTags.create(TeaStory.rl("crops/summer"));
        public final static TagKey<Item> AUTUMN = ItemTags.create(TeaStory.rl("crops/autumn"));
        public final static TagKey<Item> WINTER = ItemTags.create(TeaStory.rl("crops/winter"));
        public final static TagKey<Item> SP_SU = ItemTags.create(TeaStory.rl("crops/spring_summer"));
        public final static TagKey<Item> SP_AU = ItemTags.create(TeaStory.rl("crops/spring_autumn"));
        public final static TagKey<Item> SP_WI = ItemTags.create(TeaStory.rl("crops/spring_winter"));
        public final static TagKey<Item> SU_AU = ItemTags.create(TeaStory.rl("crops/summer_autumn"));
        public final static TagKey<Item> SU_WI = ItemTags.create(TeaStory.rl("crops/summer_winter"));
        public final static TagKey<Item> AU_WI = ItemTags.create(TeaStory.rl("crops/autumn_winter"));
        public final static TagKey<Item> SP_SU_AU = ItemTags.create(TeaStory.rl("crops/spring_summer_autumn"));
        public final static TagKey<Item> SP_SU_WI = ItemTags.create(TeaStory.rl("crops/spring_summer_winter"));
        public final static TagKey<Item> SP_AU_WI = ItemTags.create(TeaStory.rl("crops/spring_autumn_winter"));
        public final static TagKey<Item> SU_AU_WI = ItemTags.create(TeaStory.rl("crops/summer_autumn_winter"));
        public final static TagKey<Item> ALL_SEASONS = ItemTags.create(TeaStory.rl("crops/all_seasons"));

        public final static TagKey<Item> SEEDS_AGAVE = ItemTags.create(TeaStory.rl("c", "seeds/agave"));
        public final static TagKey<Item> SEEDS_AMARANTH = ItemTags.create(TeaStory.rl("c", "seeds/amaranth"));
        public final static TagKey<Item> SEEDS_ARROWROOT = ItemTags.create(TeaStory.rl("c", "seeds/arrowroot"));
        public final static TagKey<Item> SEEDS_ARTICHOKE = ItemTags.create(TeaStory.rl("c", "seeds/artichoke"));
        public final static TagKey<Item> SEEDS_ASPARAGUS = ItemTags.create(TeaStory.rl("c", "seeds/asparagus"));
        public final static TagKey<Item> SEEDS_BARLEY = ItemTags.create(TeaStory.rl("c", "seeds/barley"));
        public final static TagKey<Item> SEEDS_BEAN = ItemTags.create(TeaStory.rl("c", "seeds/bean"));
        public final static TagKey<Item> SEEDS_BELL_PEPPER = ItemTags.create(TeaStory.rl("c", "seeds/bell_pepper"));
        public final static TagKey<Item> SEEDS_BLACKBERRY = ItemTags.create(TeaStory.rl("c", "seeds/blackberry"));
        public final static TagKey<Item> SEEDS_BLUEBERRY = ItemTags.create(TeaStory.rl("c", "seeds/blueberry"));
        public final static TagKey<Item> SEEDS_BROCCOLI = ItemTags.create(TeaStory.rl("c", "seeds/broccoli"));
        public final static TagKey<Item> SEEDS_BRUSSELS_SPROUT = ItemTags.create(TeaStory.rl("c", "seeds/brussels_sprout"));
        public final static TagKey<Item> SEEDS_CABBAGE = ItemTags.create(TeaStory.rl("c", "seeds/cabbage"));
        public final static TagKey<Item> SEEDS_CACTUS_FRUIT = ItemTags.create(TeaStory.rl("c", "seeds/cactus_fruit"));
        public final static TagKey<Item> SEEDS_CANDLE_BERRY = ItemTags.create(TeaStory.rl("c", "seeds/candle_berry"));
        public final static TagKey<Item> SEEDS_CANTALOUPE = ItemTags.create(TeaStory.rl("c", "seeds/cantaloupe"));
        public final static TagKey<Item> SEEDS_CARROT = ItemTags.create(TeaStory.rl("c", "seeds/carrot"));
        public final static TagKey<Item> SEEDS_CASSAVA = ItemTags.create(TeaStory.rl("c", "seeds/cassava"));
        public final static TagKey<Item> SEEDS_CAULIFLOWER = ItemTags.create(TeaStory.rl("c", "seeds/cauliflower"));
        public final static TagKey<Item> SEEDS_CELERY = ItemTags.create(TeaStory.rl("c", "seeds/celery"));
        public final static TagKey<Item> SEEDS_CHICKPEA = ItemTags.create(TeaStory.rl("c", "seeds/chickpea"));
        public final static TagKey<Item> SEEDS_CHILI_PEPPER = ItemTags.create(TeaStory.rl("c", "seeds/chili_pepper"));
        public final static TagKey<Item> SEEDS_COFFEE_BEAN = ItemTags.create(TeaStory.rl("c", "seeds/coffee_bean"));
        public final static TagKey<Item> SEEDS_CORN = ItemTags.create(TeaStory.rl("c", "seeds/corn"));
        public final static TagKey<Item> SEEDS_COTTON = ItemTags.create(TeaStory.rl("c", "seeds/cotton"));
        public final static TagKey<Item> SEEDS_CRANBERRY = ItemTags.create(TeaStory.rl("c", "seeds/cranberry"));
        public final static TagKey<Item> SEEDS_CUCUMBER = ItemTags.create(TeaStory.rl("c", "seeds/cucumber"));
        public final static TagKey<Item> SEEDS_CUMIN = ItemTags.create(TeaStory.rl("c", "seeds/cumin"));
        public final static TagKey<Item> SEEDS_EGGPLANT = ItemTags.create(TeaStory.rl("c", "seeds/eggplant"));
        public final static TagKey<Item> SEEDS_ELDERBERRY = ItemTags.create(TeaStory.rl("c", "seeds/elderberry"));
        public final static TagKey<Item> SEEDS_FLAX = ItemTags.create(TeaStory.rl("c", "seeds/flax"));
        public final static TagKey<Item> SEEDS_GARLIC = ItemTags.create(TeaStory.rl("c", "seeds/garlic"));
        public final static TagKey<Item> SEEDS_GINGER = ItemTags.create(TeaStory.rl("c", "seeds/ginger"));
        public final static TagKey<Item> SEEDS_GRAPE = ItemTags.create(TeaStory.rl("c", "seeds/grape"));
        public final static TagKey<Item> SEEDS_GREEN_GRAPE = ItemTags.create(TeaStory.rl("c", "seeds/green_grape"));
        public final static TagKey<Item> SEEDS_HONEYDEW = ItemTags.create(TeaStory.rl("c", "seeds/honeydew"));
        public final static TagKey<Item> SEEDS_HUCKLEBERRY = ItemTags.create(TeaStory.rl("c", "seeds/huckleberry"));
        public final static TagKey<Item> SEEDS_JICAMA = ItemTags.create(TeaStory.rl("c", "seeds/jicama"));
        public final static TagKey<Item> SEEDS_JUNIPER_BERRY = ItemTags.create(TeaStory.rl("c", "seeds/juniper_berry"));
        public final static TagKey<Item> SEEDS_JUTE = ItemTags.create(TeaStory.rl("c", "seeds/jute"));
        public final static TagKey<Item> SEEDS_KALE = ItemTags.create(TeaStory.rl("c", "seeds/kale"));
        public final static TagKey<Item> SEEDS_KENAF = ItemTags.create(TeaStory.rl("c", "seeds/kenaf"));
        public final static TagKey<Item> SEEDS_KIWI = ItemTags.create(TeaStory.rl("c", "seeds/kiwi"));
        public final static TagKey<Item> SEEDS_KOHLRABI = ItemTags.create(TeaStory.rl("c", "seeds/kohlrabi"));
        public final static TagKey<Item> SEEDS_LEEK = ItemTags.create(TeaStory.rl("c", "seeds/leek"));
        public final static TagKey<Item> SEEDS_LENTIL = ItemTags.create(TeaStory.rl("c", "seeds/lentil"));
        public final static TagKey<Item> SEEDS_LETTUCE = ItemTags.create(TeaStory.rl("c", "seeds/lettuce"));
        public final static TagKey<Item> SEEDS_MILLET = ItemTags.create(TeaStory.rl("c", "seeds/millet"));
        public final static TagKey<Item> SEEDS_MULBERRY = ItemTags.create(TeaStory.rl("c", "seeds/mulberry"));
        public final static TagKey<Item> SEEDS_MUSTARD_SEEDS = ItemTags.create(TeaStory.rl("c", "seeds/mustard_seeds"));
        public final static TagKey<Item> SEEDS_OAT = ItemTags.create(TeaStory.rl("c", "seeds/oat"));
        public final static TagKey<Item> SEEDS_OKRA = ItemTags.create(TeaStory.rl("c", "seeds/okra"));
        public final static TagKey<Item> SEEDS_ONION = ItemTags.create(TeaStory.rl("c", "seeds/onion"));
        public final static TagKey<Item> SEEDS_PARSNIP = ItemTags.create(TeaStory.rl("c", "seeds/parsnip"));
        public final static TagKey<Item> SEEDS_PEA = ItemTags.create(TeaStory.rl("c", "seeds/pea"));
        public final static TagKey<Item> SEEDS_PEANUT = ItemTags.create(TeaStory.rl("c", "seeds/peanut"));
        public final static TagKey<Item> SEEDS_PEPPER = ItemTags.create(TeaStory.rl("c", "seeds/pepper"));
        public final static TagKey<Item> SEEDS_PINEAPPLE = ItemTags.create(TeaStory.rl("c", "seeds/pineapple"));
        public final static TagKey<Item> SEEDS_POTATO = ItemTags.create(TeaStory.rl("c", "seeds/potato"));
        public final static TagKey<Item> SEEDS_QUINOA = ItemTags.create(TeaStory.rl("c", "seeds/quinoa"));
        public final static TagKey<Item> SEEDS_RADISH = ItemTags.create(TeaStory.rl("c", "seeds/radish"));
        public final static TagKey<Item> SEEDS_RASPBERRY = ItemTags.create(TeaStory.rl("c", "seeds/raspberry"));
        public final static TagKey<Item> SEEDS_RHUBARB = ItemTags.create(TeaStory.rl("c", "seeds/rhubarb"));
        public final static TagKey<Item> SEEDS_RICE = ItemTags.create(TeaStory.rl("c", "seeds/rice"));
        public final static TagKey<Item> SEEDS_RUTABAGA = ItemTags.create(TeaStory.rl("c", "seeds/rutabaga"));
        public final static TagKey<Item> SEEDS_RYE = ItemTags.create(TeaStory.rl("c", "seeds/rye"));
        public final static TagKey<Item> SEEDS_SCALLION = ItemTags.create(TeaStory.rl("c", "seeds/scallion"));
        public final static TagKey<Item> SEEDS_SESAME_SEEDS = ItemTags.create(TeaStory.rl("c", "seeds/sesame_seeds"));
        public final static TagKey<Item> SEEDS_SISAL = ItemTags.create(TeaStory.rl("c", "seeds/sisal"));
        public final static TagKey<Item> SEEDS_SORGHUM = ItemTags.create(TeaStory.rl("c", "seeds/sorghum"));
        public final static TagKey<Item> SEEDS_SOYBEAN = ItemTags.create(TeaStory.rl("c", "seeds/soybean"));
        public final static TagKey<Item> SEEDS_SPINACH = ItemTags.create(TeaStory.rl("c", "seeds/spinach"));
        public final static TagKey<Item> SEEDS_STRAWBERRY = ItemTags.create(TeaStory.rl("c", "seeds/strawberry"));
        public final static TagKey<Item> SEEDS_SUNFLOWER = ItemTags.create(TeaStory.rl("c", "seeds/sunflower"));
        public final static TagKey<Item> SEEDS_SWEET_POTATO = ItemTags.create(TeaStory.rl("c", "seeds/sweet_potato"));
        public final static TagKey<Item> SEEDS_TARO = ItemTags.create(TeaStory.rl("c", "seeds/taro"));
        public final static TagKey<Item> SEEDS_TEA_LEAF = ItemTags.create(TeaStory.rl("c", "seeds/tea_leaf"));
        public final static TagKey<Item> SEEDS_TOMATILLO = ItemTags.create(TeaStory.rl("c", "seeds/tomatillo"));
        public final static TagKey<Item> SEEDS_TOMATO = ItemTags.create(TeaStory.rl("c", "seeds/tomato"));
        public final static TagKey<Item> SEEDS_TURNIP = ItemTags.create(TeaStory.rl("c", "seeds/turnip"));
        public final static TagKey<Item> SEEDS_WATER_CHESTNUT = ItemTags.create(TeaStory.rl("c", "seeds/water_chestnut"));
        public final static TagKey<Item> SEEDS_WHITE_MUSHROOM = ItemTags.create(TeaStory.rl("c", "seeds/white_mushroom"));
        public final static TagKey<Item> SEEDS_WINTER_SQUASH = ItemTags.create(TeaStory.rl("c", "seeds/winter_squash"));
        public final static TagKey<Item> SEEDS_YAM = ItemTags.create(TeaStory.rl("c", "seeds/yam"));
        public final static TagKey<Item> SEEDS_ZUCCHINI = ItemTags.create(TeaStory.rl("c", "seeds/zucchini"));

        public final static TagKey<Item> CROPS_BLACK_TEA_LEAF = ItemTags.create(TeaStory.rl("c", "crops/black_tea_leaf"));
        public final static TagKey<Item> CROPS_GREEN_TEA_LEAF = ItemTags.create(TeaStory.rl("c", "crops/green_tea_leaf"));
        public final static TagKey<Item> CROPS_TEA_LEAF = ItemTags.create(TeaStory.rl("c", "crops/tea_leaf"));
        public final static TagKey<Item> CROPS_WHITE_TEA_LEAF = ItemTags.create(TeaStory.rl("c", "crops/white_tea_leaf"));
        public final static TagKey<Item> CROPS_GRAPE = ItemTags.create(TeaStory.rl("c", "crops/grape"));
        public final static TagKey<Item> CROPS_CUCUMBER = ItemTags.create(TeaStory.rl("c", "crops/cucumber"));
        public final static TagKey<Item> CROPS_STRAW = ItemTags.create(TeaStory.rl("c", "crops/straw"));
        public final static TagKey<Item> CROPS_RICE = ItemTags.create(TeaStory.rl("c", "crops/rice"));
        public final static TagKey<Item> CROPS_APPLE = ItemTags.create(TeaStory.rl("c", "crops/apple"));
        public final static TagKey<Item> CROPS_SUGAR_CANE = ItemTags.create(TeaStory.rl("c", "crops/sugar_cane"));

        public final static TagKey<Item> FOOD_JERKY = ItemTags.create(TeaStory.rl("c", "food/jerky"));
        public final static TagKey<Item> FOOD_MEAT = ItemTags.create(TeaStory.rl("c", "food/meat"));

        public final static TagKey<Item> DIRT = ItemTags.DIRT;
        public final static TagKey<Item> DUSTS_ASH = ItemTags.create(TeaStory.rl("c", "dusts/ash"));
    }

    public static class Blocks {

    }

    public static class Fluids {
        public final static TagKey<Fluid> DRINK = FluidTags.create(TeaStory.rl("drink"));
    }

    public static class Entities {
        public final static TagKey<EntityType<?>> BIRDS = TagKey.create(Registries.ENTITY_TYPE, TeaStory.rl("minecraft", "birds"));
    }
}
