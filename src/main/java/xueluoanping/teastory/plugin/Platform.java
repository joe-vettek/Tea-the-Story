package xueluoanping.teastory.plugin;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.List;

public class Platform {

    public static boolean isModLoaded(String id) {
        return ModList.get().isLoaded(id);
    }

    public static boolean isModsLoaded(List<String> ids) {
        return ids.stream().allMatch(Platform::isModLoaded);
    }

    public static boolean isPhysicalClient() {
        return FMLEnvironment.dist.isClient();
    }

    public static MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    public static boolean isProduction() {
        return FMLEnvironment.production;
    }

    public static IModFile getModFile(String s) {
        return ModList.get().getModFileById(s).getFile();
    }

}
