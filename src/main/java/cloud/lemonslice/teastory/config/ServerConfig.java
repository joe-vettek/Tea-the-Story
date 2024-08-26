package cloud.lemonslice.teastory.config;


import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig
{
    protected ServerConfig(ModConfigSpec.Builder builder)
    {
        BlockConfig.load(builder);
        Agriculture.load(builder);
        Temperature.load(builder);
        Season.load(builder);
        Others.load(builder);
    }

    public static class BlockConfig
    {
        public static ModConfigSpec.IntValue woodenBarrelCapacity;

        private static void load(ModConfigSpec.Builder builder)
        {
            builder.push("Block");
            woodenBarrelCapacity = builder.comment("The capacity of wooden barrel. (mB)")
                    .defineInRange("WoodenBarrelCapacity", 4000, 100, Integer.MAX_VALUE);
            builder.pop();
        }
    }

    public static class Temperature
    {
        public static ModConfigSpec.BooleanValue iceMelt;

        private static void load(ModConfigSpec.Builder builder)
        {
            builder.push("Temperature");
            iceMelt = builder.comment("Ice or snow layer will melt in warm place..")
                    .define("IceAndSnowMelt", true);
            builder.pop();
        }
    }

    public static class Season
    {
        public static ModConfigSpec.BooleanValue enable;
        public static ModConfigSpec.IntValue lastingDaysOfEachTerm;
        public static ModConfigSpec.IntValue initialSolarTermIndex;

        private static void load(ModConfigSpec.Builder builder)
        {
            builder.push("Season");
            enable = builder.comment("Enable solar term season system.")
                    .define("EnableSeason", true);
            lastingDaysOfEachTerm = builder.comment("The lasting days of each term (24 in total).")
                    .defineInRange("LastingDaysOfEachTerm", 7, 1, 30);
            initialSolarTermIndex = builder.comment("The index of the initial solar term.")
                    .defineInRange("InitialSolarTermIndex", 1, 1, 24);
            builder.pop();
        }
    }

    public static class Agriculture
    {
        public static ModConfigSpec.BooleanValue canUseBoneMeal;
        public static ModConfigSpec.BooleanValue useAshAsBoneMeal;
        public static ModConfigSpec.BooleanValue dropRiceGrains;

        private static void load(ModConfigSpec.Builder builder)
        {
            builder.push("Agriculture");
            canUseBoneMeal = builder.comment("Can bone meal be used to grow crops?")
                    .define("BoneMeal", true);
            useAshAsBoneMeal = builder.comment("Can ash be used as bone meal?")
                    .define("Ash", true);
            dropRiceGrains = builder.comment("Can grass drop rice grains?")
                    .define("DropRiceGrains", true);
            builder.pop();
        }
    }

    public static class Others
    {
        public static ModConfigSpec.BooleanValue woodDropsAshWhenBurning;

        private static void load(ModConfigSpec.Builder builder)
        {
            builder.push("Others");
            woodDropsAshWhenBurning = builder.comment("Wooden blocks will drop ashes when burning.")
                    .define("WoodDropsAshWhenBurning", true);
            builder.pop();
        }
    }
}

