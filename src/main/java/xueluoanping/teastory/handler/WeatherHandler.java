package xueluoanping.teastory.handler;


import cloud.lemonslice.teastory.config.ServerConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xueluoanping.teastory.TeaStory;

@Mod.EventBusSubscriber(modid = TeaStory.MODID)
public class WeatherHandler {
    @SubscribeEvent
    public static void onWorldTick(SleepFinishedTimeEvent event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            // TODO: 根据季节更新概率
            if(!serverLevel.isRaining()&&serverLevel.getRandom().nextFloat()>0.8) {
                serverLevel.setWeatherParameters(0,
                        ServerLevel.RAIN_DURATION.sample(serverLevel.getRandom()),
                        true, false);
            }
        }
    }
}
