package com.teamtea.teastory.config;


import com.teamtea.teastory.plugin.CompatManager;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    protected ServerConfig(ModConfigSpec.Builder builder) {
        BlockConfig.load(builder);
        Agriculture.load(builder);
        Others.load(builder);
        CompatManager.initConfig(builder, true);
    }

    public static class BlockConfig {
        public static ModConfigSpec.IntValue woodenBarrelCapacity;

        private static void load(ModConfigSpec.Builder builder) {
            builder.push("Block");
            woodenBarrelCapacity = builder.comment("The capacity of wooden barrel. (mB)")
                    .defineInRange("WoodenBarrelCapacity", 4000, 100, Integer.MAX_VALUE);
            builder.pop();
        }
    }

    public static class Agriculture {
        public static ModConfigSpec.BooleanValue useAshAsBoneMeal;
        public static ModConfigSpec.BooleanValue addSeedToHouseChest;
        public static ModConfigSpec.BooleanValue betterMelon;

        private static void load(ModConfigSpec.Builder builder) {
            builder.push("Agriculture");

            useAshAsBoneMeal = builder.comment("Can ash be used as bone meal?")
                    .define("Ash", true);
            addSeedToHouseChest = builder.comment("Can players find seeds in the chest of village?")
                    .define("AddSeedToHouseChest", true);
            betterMelon = builder.comment("Let melon vine more interesting.")
                    .define("BetterMelon", true);
            builder.pop();
        }
    }

    public static class Others {
        public static ModConfigSpec.BooleanValue woodDropsAshWhenBurning;

        private static void load(ModConfigSpec.Builder builder) {
            builder.push("Others");
            woodDropsAshWhenBurning = builder.comment("Wooden blocks will drop ashes when burning.")
                    .define("WoodDropsAshWhenBurning", true);
            builder.pop();
        }
    }
}

