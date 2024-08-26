package cloud.lemonslice.teastory.config;


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
            playerTemperatureX = builder.comment("The position X of Player Temperature UI")
                    .defineInRange("PlayerTemperatureX", 10, Integer.MIN_VALUE, Integer.MAX_VALUE);
            playerTemperatureY = builder.comment("The position Y of Player Temperature UI")
                    .defineInRange("PlayerTemperatureY", 40, Integer.MIN_VALUE, Integer.MAX_VALUE);
            debugInfo = builder.comment("Info used for development.")
                    .define("DebugInfo", false);
            builder.pop();
        }
    }

    public static class Renderer
    {
        public static ModConfigSpec.BooleanValue forceChunkRenderUpdate;

        private static void load(ModConfigSpec.Builder builder)
        {
            builder.push("Renderer");
            forceChunkRenderUpdate = builder.comment("Force to update chunk rendering.")
                    .define("ForceChunkRenderUpdate", false);
            builder.pop();
        }
    }
}
