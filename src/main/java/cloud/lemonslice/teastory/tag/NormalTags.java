package cloud.lemonslice.teastory.tag;


import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
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

        public final static TagKey<Item> SEEDS_AGAVE = ItemTags.create(TeaStory.rl("forge", "seeds/agave"));
        public final static TagKey<Item> SEEDS_AMARANTH = ItemTags.create(TeaStory.rl("forge", "seeds/amaranth"));
        public final static TagKey<Item> SEEDS_ARROWROOT = ItemTags.create(TeaStory.rl("forge", "seeds/arrowroot"));
        public final static TagKey<Item> SEEDS_ARTICHOKE = ItemTags.create(TeaStory.rl("forge", "seeds/artichoke"));
        public final static TagKey<Item> SEEDS_ASPARAGUS = ItemTags.create(TeaStory.rl("forge", "seeds/asparagus"));
        public final static TagKey<Item> SEEDS_BARLEY = ItemTags.create(TeaStory.rl("forge", "seeds/barley"));
        public final static TagKey<Item> SEEDS_BEAN = ItemTags.create(TeaStory.rl("forge", "seeds/bean"));
        public final static TagKey<Item> SEEDS_BELL_PEPPER = ItemTags.create(TeaStory.rl("forge", "seeds/bell_pepper"));
        public final static TagKey<Item> SEEDS_BLACKBERRY = ItemTags.create(TeaStory.rl("forge", "seeds/blackberry"));
        public final static TagKey<Item> SEEDS_BLUEBERRY = ItemTags.create(TeaStory.rl("forge", "seeds/blueberry"));
        public final static TagKey<Item> SEEDS_BROCCOLI = ItemTags.create(TeaStory.rl("forge", "seeds/broccoli"));
        public final static TagKey<Item> SEEDS_BRUSSELS_SPROUT = ItemTags.create(TeaStory.rl("forge", "seeds/brussels_sprout"));
        public final static TagKey<Item> SEEDS_CABBAGE = ItemTags.create(TeaStory.rl("forge", "seeds/cabbage"));
        public final static TagKey<Item> SEEDS_CACTUS_FRUIT = ItemTags.create(TeaStory.rl("forge", "seeds/cactus_fruit"));
        public final static TagKey<Item> SEEDS_CANDLE_BERRY = ItemTags.create(TeaStory.rl("forge", "seeds/candle_berry"));
        public final static TagKey<Item> SEEDS_CANTALOUPE = ItemTags.create(TeaStory.rl("forge", "seeds/cantaloupe"));
        public final static TagKey<Item> SEEDS_CARROT = ItemTags.create(TeaStory.rl("forge", "seeds/carrot"));
        public final static TagKey<Item> SEEDS_CASSAVA = ItemTags.create(TeaStory.rl("forge", "seeds/cassava"));
        public final static TagKey<Item> SEEDS_CAULIFLOWER = ItemTags.create(TeaStory.rl("forge", "seeds/cauliflower"));
        public final static TagKey<Item> SEEDS_CELERY = ItemTags.create(TeaStory.rl("forge", "seeds/celery"));
        public final static TagKey<Item> SEEDS_CHICKPEA = ItemTags.create(TeaStory.rl("forge", "seeds/chickpea"));
        public final static TagKey<Item> SEEDS_CHILI_PEPPER = ItemTags.create(TeaStory.rl("forge", "seeds/chili_pepper"));
        public final static TagKey<Item> SEEDS_COFFEE_BEAN = ItemTags.create(TeaStory.rl("forge", "seeds/coffee_bean"));
        public final static TagKey<Item> SEEDS_CORN = ItemTags.create(TeaStory.rl("forge", "seeds/corn"));
        public final static TagKey<Item> SEEDS_COTTON = ItemTags.create(TeaStory.rl("forge", "seeds/cotton"));
        public final static TagKey<Item> SEEDS_CRANBERRY = ItemTags.create(TeaStory.rl("forge", "seeds/cranberry"));
        public final static TagKey<Item> SEEDS_CUCUMBER = ItemTags.create(TeaStory.rl("forge", "seeds/cucumber"));
        public final static TagKey<Item> SEEDS_CUMIN = ItemTags.create(TeaStory.rl("forge", "seeds/cumin"));
        public final static TagKey<Item> SEEDS_EGGPLANT = ItemTags.create(TeaStory.rl("forge", "seeds/eggplant"));
        public final static TagKey<Item> SEEDS_ELDERBERRY = ItemTags.create(TeaStory.rl("forge", "seeds/elderberry"));
        public final static TagKey<Item> SEEDS_FLAX = ItemTags.create(TeaStory.rl("forge", "seeds/flax"));
        public final static TagKey<Item> SEEDS_GARLIC = ItemTags.create(TeaStory.rl("forge", "seeds/garlic"));
        public final static TagKey<Item> SEEDS_GINGER = ItemTags.create(TeaStory.rl("forge", "seeds/ginger"));
        public final static TagKey<Item> SEEDS_GRAPE = ItemTags.create(TeaStory.rl("forge", "seeds/grape"));
        public final static TagKey<Item> SEEDS_GREEN_GRAPE = ItemTags.create(TeaStory.rl("forge", "seeds/green_grape"));
        public final static TagKey<Item> SEEDS_HONEYDEW = ItemTags.create(TeaStory.rl("forge", "seeds/honeydew"));
        public final static TagKey<Item> SEEDS_HUCKLEBERRY = ItemTags.create(TeaStory.rl("forge", "seeds/huckleberry"));
        public final static TagKey<Item> SEEDS_JICAMA = ItemTags.create(TeaStory.rl("forge", "seeds/jicama"));
        public final static TagKey<Item> SEEDS_JUNIPER_BERRY = ItemTags.create(TeaStory.rl("forge", "seeds/juniper_berry"));
        public final static TagKey<Item> SEEDS_JUTE = ItemTags.create(TeaStory.rl("forge", "seeds/jute"));
        public final static TagKey<Item> SEEDS_KALE = ItemTags.create(TeaStory.rl("forge", "seeds/kale"));
        public final static TagKey<Item> SEEDS_KENAF = ItemTags.create(TeaStory.rl("forge", "seeds/kenaf"));
        public final static TagKey<Item> SEEDS_KIWI = ItemTags.create(TeaStory.rl("forge", "seeds/kiwi"));
        public final static TagKey<Item> SEEDS_KOHLRABI = ItemTags.create(TeaStory.rl("forge", "seeds/kohlrabi"));
        public final static TagKey<Item> SEEDS_LEEK = ItemTags.create(TeaStory.rl("forge", "seeds/leek"));
        public final static TagKey<Item> SEEDS_LENTIL = ItemTags.create(TeaStory.rl("forge", "seeds/lentil"));
        public final static TagKey<Item> SEEDS_LETTUCE = ItemTags.create(TeaStory.rl("forge", "seeds/lettuce"));
        public final static TagKey<Item> SEEDS_MILLET = ItemTags.create(TeaStory.rl("forge", "seeds/millet"));
        public final static TagKey<Item> SEEDS_MULBERRY = ItemTags.create(TeaStory.rl("forge", "seeds/mulberry"));
        public final static TagKey<Item> SEEDS_MUSTARD_SEEDS = ItemTags.create(TeaStory.rl("forge", "seeds/mustard_seeds"));
        public final static TagKey<Item> SEEDS_OAT = ItemTags.create(TeaStory.rl("forge", "seeds/oat"));
        public final static TagKey<Item> SEEDS_OKRA = ItemTags.create(TeaStory.rl("forge", "seeds/okra"));
        public final static TagKey<Item> SEEDS_ONION = ItemTags.create(TeaStory.rl("forge", "seeds/onion"));
        public final static TagKey<Item> SEEDS_PARSNIP = ItemTags.create(TeaStory.rl("forge", "seeds/parsnip"));
        public final static TagKey<Item> SEEDS_PEA = ItemTags.create(TeaStory.rl("forge", "seeds/pea"));
        public final static TagKey<Item> SEEDS_PEANUT = ItemTags.create(TeaStory.rl("forge", "seeds/peanut"));
        public final static TagKey<Item> SEEDS_PEPPER = ItemTags.create(TeaStory.rl("forge", "seeds/pepper"));
        public final static TagKey<Item> SEEDS_PINEAPPLE = ItemTags.create(TeaStory.rl("forge", "seeds/pineapple"));
        public final static TagKey<Item> SEEDS_POTATO = ItemTags.create(TeaStory.rl("forge", "seeds/potato"));
        public final static TagKey<Item> SEEDS_QUINOA = ItemTags.create(TeaStory.rl("forge", "seeds/quinoa"));
        public final static TagKey<Item> SEEDS_RADISH = ItemTags.create(TeaStory.rl("forge", "seeds/radish"));
        public final static TagKey<Item> SEEDS_RASPBERRY = ItemTags.create(TeaStory.rl("forge", "seeds/raspberry"));
        public final static TagKey<Item> SEEDS_RHUBARB = ItemTags.create(TeaStory.rl("forge", "seeds/rhubarb"));
        public final static TagKey<Item> SEEDS_RICE = ItemTags.create(TeaStory.rl("forge", "seeds/rice"));
        public final static TagKey<Item> SEEDS_RUTABAGA = ItemTags.create(TeaStory.rl("forge", "seeds/rutabaga"));
        public final static TagKey<Item> SEEDS_RYE = ItemTags.create(TeaStory.rl("forge", "seeds/rye"));
        public final static TagKey<Item> SEEDS_SCALLION = ItemTags.create(TeaStory.rl("forge", "seeds/scallion"));
        public final static TagKey<Item> SEEDS_SESAME_SEEDS = ItemTags.create(TeaStory.rl("forge", "seeds/sesame_seeds"));
        public final static TagKey<Item> SEEDS_SISAL = ItemTags.create(TeaStory.rl("forge", "seeds/sisal"));
        public final static TagKey<Item> SEEDS_SORGHUM = ItemTags.create(TeaStory.rl("forge", "seeds/sorghum"));
        public final static TagKey<Item> SEEDS_SOYBEAN = ItemTags.create(TeaStory.rl("forge", "seeds/soybean"));
        public final static TagKey<Item> SEEDS_SPINACH = ItemTags.create(TeaStory.rl("forge", "seeds/spinach"));
        public final static TagKey<Item> SEEDS_STRAWBERRY = ItemTags.create(TeaStory.rl("forge", "seeds/strawberry"));
        public final static TagKey<Item> SEEDS_SUNFLOWER = ItemTags.create(TeaStory.rl("forge", "seeds/sunflower"));
        public final static TagKey<Item> SEEDS_SWEET_POTATO = ItemTags.create(TeaStory.rl("forge", "seeds/sweet_potato"));
        public final static TagKey<Item> SEEDS_TARO = ItemTags.create(TeaStory.rl("forge", "seeds/taro"));
        public final static TagKey<Item> SEEDS_TEA_LEAF = ItemTags.create(TeaStory.rl("forge", "seeds/tea_leaf"));
        public final static TagKey<Item> SEEDS_TOMATILLO = ItemTags.create(TeaStory.rl("forge", "seeds/tomatillo"));
        public final static TagKey<Item> SEEDS_TOMATO = ItemTags.create(TeaStory.rl("forge", "seeds/tomato"));
        public final static TagKey<Item> SEEDS_TURNIP = ItemTags.create(TeaStory.rl("forge", "seeds/turnip"));
        public final static TagKey<Item> SEEDS_WATER_CHESTNUT = ItemTags.create(TeaStory.rl("forge", "seeds/water_chestnut"));
        public final static TagKey<Item> SEEDS_WHITE_MUSHROOM = ItemTags.create(TeaStory.rl("forge", "seeds/white_mushroom"));
        public final static TagKey<Item> SEEDS_WINTER_SQUASH = ItemTags.create(TeaStory.rl("forge", "seeds/winter_squash"));
        public final static TagKey<Item> SEEDS_YAM = ItemTags.create(TeaStory.rl("forge", "seeds/yam"));
        public final static TagKey<Item> SEEDS_ZUCCHINI = ItemTags.create(TeaStory.rl("forge", "seeds/zucchini"));

        public final static TagKey<Item> CROPS_BLACK_TEA_LEAF = ItemTags.create(TeaStory.rl("forge", "crops/black_tea_leaf"));
        public final static TagKey<Item> CROPS_GREEN_TEA_LEAF = ItemTags.create(TeaStory.rl("forge", "crops/green_tea_leaf"));
        public final static TagKey<Item> CROPS_TEA_LEAF = ItemTags.create(TeaStory.rl("forge", "crops/tea_leaf"));
        public final static TagKey<Item> CROPS_WHITE_TEA_LEAF = ItemTags.create(TeaStory.rl("forge", "crops/white_tea_leaf"));
        public final static TagKey<Item> CROPS_GRAPE = ItemTags.create(TeaStory.rl("forge", "crops/grape"));
        public final static TagKey<Item> CROPS_CUCUMBER = ItemTags.create(TeaStory.rl("forge", "crops/cucumber"));
        public final static TagKey<Item> CROPS_STRAW = ItemTags.create(TeaStory.rl("forge", "crops/straw"));
        public final static TagKey<Item> CROPS_RICE = ItemTags.create(TeaStory.rl("forge", "crops/rice"));
        public final static TagKey<Item> CROPS_APPLE = ItemTags.create(TeaStory.rl("forge", "crops/apple"));
        public final static TagKey<Item> CROPS_SUGAR_CANE = ItemTags.create(TeaStory.rl("forge", "crops/sugar_cane"));

        public final static TagKey<Item> FOOD_JERKY = ItemTags.create(TeaStory.rl("forge", "food/jerky"));
        public final static TagKey<Item> FOOD_MEAT = ItemTags.create(TeaStory.rl("forge", "food/meat"));

        public final static TagKey<Item> DIRT = ItemTags.DIRT;
        public final static TagKey<Item> DUSTS_ASH = ItemTags.create(TeaStory.rl("forge", "dusts/ash"));
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
