package xueluoanping.teastory.resource;

import net.minecraft.server.packs.CompositePackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.neoforged.neoforgespi.locating.IModFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public  class PathResourcesSupplier implements Pack.ResourcesSupplier {
    private final Path content;
    protected final IModFile modFile;

    public PathResourcesSupplier(IModFile modFile, Path pContent) {
        this.content = pContent;
        this.modFile = modFile;
    }

    @Override
    public ClientModFilePackResources openPrimary(PackLocationInfo pLocation) {
        return new ClientModFilePackResources(pLocation,this.modFile, this.content.toString());
    }

    @Override
    public ClientModFilePackResources openFull(PackLocationInfo pLocation, Pack.Metadata pMetadata) {
        ClientModFilePackResources packresources = this.openPrimary(pLocation);
        List<String> list = pMetadata.overlays();
        if (list.isEmpty()) {
            return packresources;
        } else {
            List<PackResources> list1 = new ArrayList<>(list.size());
            for (String s : list) {
                Path path = this.content.resolve(s);
                list1.add(new PathPackResources(pLocation, path));
            }
            // return new CompositePackResources(packresources, list1);
            return null;
        }
    }
}
