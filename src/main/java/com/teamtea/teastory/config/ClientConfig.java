package com.teamtea.teastory.config;


import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig
{

    protected ClientConfig(ModConfigSpec.Builder builder)
    {
        GUI.load(builder);
        Renderer.load(builder);
    }

    public static class GUI
    {
        public static ModConfigSpec.IntValue playerTemperatureX;
        public static ModConfigSpec.IntValue playerTemperatureY;
        public static ModConfigSpec.BooleanValue debugInfo;

        private static void load(ModConfigSpec.Builder builder)
        {
            builder.push("GUI");
            builder.pop();
        }
    }

    public static class Renderer
    {

        private static void load(ModConfigSpec.Builder builder)
        {
            builder.push("Renderer");
            builder.pop();
        }
    }
}
