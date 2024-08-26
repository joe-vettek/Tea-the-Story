package cloud.lemonslice.teastory.config;


import net.neoforged.neoforge.common.ModConfigSpec;

public final class NormalConfigs
{
    public static final ModConfigSpec SERVER_CONFIG = new ModConfigSpec.Builder().configure(ServerConfig::new).getRight();
    public static final ModConfigSpec CLIENT_CONFIG = new ModConfigSpec.Builder().configure(ClientConfig::new).getRight();

    // @SubscribeEvent
    // public static void onReload(ModConfig.Reloading event)
    // {
    //     ((CommentedFileConfig) event.getConfig().getConfigData()).load();
    // }
}
