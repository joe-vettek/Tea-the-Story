package cloud.lemonslice.teastory.handler;


import cloud.lemonslice.teastory.capability.CapabilitySolarTermTime;
import cloud.lemonslice.teastory.environment.solar.SolarTerm;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelTimeAccess;

@SuppressWarnings("unused")
public class AsmHandler
{
    public static float getSeasonCelestialAngle(long worldTime, LevelTimeAccess world)
    {
        return getCelestialAngle(getSolarAngelTime(worldTime, world));
    }

    public static int getSolarAngelTime(long worldTime, LevelTimeAccess world)
    {
        if (world instanceof Level)
        {
            return ((Level) world).getCapability(CapabilitySolarTermTime.WORLD_SOLAR_TIME).map(data ->
            {
                int dayTime = SolarTerm.get(data.getSolarTermIndex()).getDayTime();
                int sunrise = 24000 - dayTime / 2;
                int sunset = dayTime / 2;
                int dayLevelTime = Math.toIntExact((worldTime + 18000) % 24000); // 0 for noon; 6000 for sunset; 18000 for sunrise.
                int solarAngelTime;
                if (0 <= dayLevelTime && dayLevelTime <= sunset)
                {
                    solarAngelTime = 6000 + dayLevelTime * 6000 / sunset;
                }
                else if (dayLevelTime > sunset && dayLevelTime <= sunrise)
                {
                    solarAngelTime = 12000 + (dayLevelTime - sunset) * 12000 / (24000 - dayTime);
                }
                else
                {
                    solarAngelTime = (dayLevelTime - sunrise) * 6000 / (24000 - sunrise);
                }
                return solarAngelTime;
            }).orElse(Math.toIntExact(worldTime % 24000));
        }
        return Math.toIntExact(worldTime % 24000);
    }

    public static float getCelestialAngle(long worldTime)
    {
        double d0 = Mth.frac((double) worldTime / 24000.0D - 0.25D);
        double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
        return (float) (d0 * 2.0D + d1) / 3.0F;
    }
}
