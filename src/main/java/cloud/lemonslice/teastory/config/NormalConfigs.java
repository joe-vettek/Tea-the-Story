package cloud.lemonslice.teastory.config;


import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import xueluoanping.teastory.TeaStory;

// @Mod.EventBusSubscriber(modid = TeaStory.MOD_ID)
public final class NormalConfigs
{
    public static final ForgeConfigSpec SERVER_CONFIG = new ForgeConfigSpec.Builder().configure(ServerConfig::new).getRight();
    public static final ForgeConfigSpec CLIENT_CONFIG = new ForgeConfigSpec.Builder().configure(ClientConfig::new).getRight();

    // @SubscribeEvent
    // public static void onReload(ModConfig.Reloading event)
    // {
    //     ((CommentedFileConfig) event.getConfig().getConfigData()).load();
    // }
}
