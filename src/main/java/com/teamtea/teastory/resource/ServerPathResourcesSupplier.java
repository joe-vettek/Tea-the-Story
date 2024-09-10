package com.teamtea.teastory.resource;

import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.repository.Pack;

import java.util.ArrayList;
import java.util.List;

public  class ServerPathResourcesSupplier implements Pack.ResourcesSupplier {
    private final String content;

    public ServerPathResourcesSupplier( String pContent) {
        this.content = pContent;
    }

    @Override
    public ServerModFilePackResources openPrimary(PackLocationInfo pLocation) {
        return new ServerModFilePackResources(pLocation,content);
    }

    @Override
    public ServerModFilePackResources openFull(PackLocationInfo pLocation, Pack.Metadata pMetadata) {
        ServerModFilePackResources packresources = this.openPrimary(pLocation);
        List<String> list = pMetadata.overlays();
        if (list.isEmpty()) {
            return packresources;
        } else {
            List<PackResources> list1 = new ArrayList<>(list.size());

            for (String s : list) {
                // Path path = this.content.resolve(s);
                // list1.add(new PathPackResources(pLocation, path));
            }

            // return new CompositePackResources(packresources, list1);
            return null;
        }
    }
}
