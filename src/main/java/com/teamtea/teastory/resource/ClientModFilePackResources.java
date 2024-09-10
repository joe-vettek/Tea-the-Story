package com.teamtea.teastory.resource;

import com.teamtea.teastory.block.crops.VineType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.client.resources.model.BlockStateModelLoader;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.IoSupplier;
import net.neoforged.neoforgespi.locating.IModFile;
import org.jetbrains.annotations.Nullable;
import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.variant.Planks;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/*
 * Thanks Create MIT License
 * */
public class ClientModFilePackResources extends AbstractPackResources {
    protected final String sourcePath;
    protected final IModFile modFile;
    protected final Gson gson = new Gson();

    public ClientModFilePackResources(PackLocationInfo name, IModFile modFile, String sourcePath) {
        super(name);
        this.sourcePath = sourcePath;
        this.modFile = modFile;
    }

    @Override
    public @Nullable <T> T getMetadataSection(MetadataSectionSerializer<T> pDeserializer) throws IOException {
        JsonObject jsonObject = new JsonObject();
        JsonObject pack = new JsonObject();
        pack.addProperty("description", "Tea the Story Auto Client");
        pack.addProperty("pack_format", 9);
        jsonObject.add("pack", pack);
        return getMetadataFromStream(pDeserializer, jsonObjectToIoSupplier(jsonObject).get());
    }

    @Override
    public @Nullable IoSupplier<InputStream> getRootResource(String... pElements) {
        return null;
    }

    @Override
    public @Nullable IoSupplier<InputStream> getResource(PackType pPackType, ResourceLocation pLocation) {
        return null;
    }

    public static IoSupplier<InputStream> jsonObjectToIoSupplier(JsonObject jsonObject) {
        String jsonString = jsonObject.toString();
        InputStream inputStream = new ByteArrayInputStream(jsonString.getBytes());
        return () -> inputStream;
    }

    public static IoSupplier<InputStream> stringToIoSupplier(String jsonString) {
        InputStream inputStream = new ByteArrayInputStream(jsonString.getBytes());
        return () -> inputStream;
    }

    public JsonObject getJson(Path filePath) {
        try {
            return gson.fromJson(Files.readString(filePath), JsonObject.class);
        } catch (IOException e) {
            TeaStory.logger("Error read in %s".formatted(filePath));
            return new JsonObject();
        }
    }

    @Override
    public void listResources(PackType type, String namespace, String path, ResourceOutput resourceOutput) {

        // TeaStory.logger(namespace, path);

        if (namespace.equals(TeaStory.MODID) && path.equals(BlockStateModelLoader.BLOCKSTATE_LISTER.prefix)) {
            Path trellisPath = modFile.findResource("templates/assets/blockstates/trellis.json");
            Path bittergourdTrellisPath = modFile.findResource("templates/assets/blockstates/trellis_bitter_gourd.json");
            Path cucumberTrellisPath = modFile.findResource("templates/assets/blockstates/trellis_cucumber.json");
            Path grapeTrellisPath = modFile.findResource("templates/assets/blockstates/trellis_grape.json");

            JsonObject trellis = getJson(trellisPath);
            JsonObject bittergourdTrellis = getJson(bittergourdTrellisPath);
            JsonObject cucumberTrellis = getJson(cucumberTrellisPath);
            JsonObject grapeTrellis = getJson(grapeTrellisPath);

            Map<VineType, JsonObject> vineTypeJsonObjectMap = new HashMap<>();
            vineTypeJsonObjectMap.put(VineType.BITTER_GOURD, bittergourdTrellis);
            vineTypeJsonObjectMap.put(VineType.CUCUMBER, cucumberTrellis);
            vineTypeJsonObjectMap.put(VineType.GRAPE, grapeTrellis);

            String preholder = "\\{trellis}";
            Planks.TrellisBlockMap.forEach((resourceLocation, blockBlockPair) -> {
                String name = resourceLocation.getPath();
                resourceOutput.accept(TeaStory.rl("blockstates/%s.json".formatted(name)),
                        stringToIoSupplier(trellis.toString().replaceAll(preholder, name)));

                for (VineType value : VineType.values()) {
                    resourceOutput.accept(TeaStory.rl("blockstates/%s.json".formatted(name + "_with_" + value.getName() + "_vine")),
                            stringToIoSupplier(trellis.toString().replaceAll(preholder, name)));
                    // resourceOutput.accept(TeaStory.rl("blockstates/%s.json".formatted(name + "_with_" + value.getName() + "_vine")),
                    //         stringToIoSupplier(vineTypeJsonObjectMap.get(value).toString().replaceAll(preholder, name)));
                }
            });


        } else if (namespace.equals(TeaStory.MODID) && path.equals(ModelBakery.MODEL_LISTER.prefix)) {

            var list= List.of("bar","center","post","post_up","support");
            Map<String, JsonObject> typeMap = new HashMap<>();
            for (String s : list) {
                typeMap.put(s,getJson(modFile.findResource("templates/assets/models/block/trellis_%s.json".formatted(s))));
            }
            JsonObject inventoryjson=getJson(modFile.findResource("templates/assets/models/item/trellis.json"));

            // String preholder = "\\{trellis}";
            Planks.TrellisBlockMap.forEach((resourceLocation, blockBlockPair) -> {
                String name = resourceLocation.getPath();
                typeMap.forEach((s, jsonObject) -> {
                    resourceOutput.accept(TeaStory.rl("models/block/%s_%s.json".formatted(name,s)),
                            stringToIoSupplier(jsonObject.toString()));
                });
                resourceOutput.accept(TeaStory.rl("models/item/%s.json".formatted(name)),
                        stringToIoSupplier(inventoryjson.toString()));
            });
        }

    }

    @Override
    public Set<String> getNamespaces(PackType pType) {
        return Set.of(TeaStory.MODID);
    }

    @Override
    public void close() {

    }
}
