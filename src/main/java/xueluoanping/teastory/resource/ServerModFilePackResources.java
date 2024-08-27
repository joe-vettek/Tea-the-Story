package xueluoanping.teastory.resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import cloud.lemonslice.teastory.block.crops.TrellisBlock;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.variant.Planks;


/*
 * Thanks Create MIT License
 * */
public class ServerModFilePackResources extends AbstractPackResources {
    protected final String sourcePath;

    public ServerModFilePackResources(PackLocationInfo name, String sourcePath) {
        super(name);
        this.sourcePath = sourcePath;
    }

    @Override
    public @Nullable <T> T getMetadataSection(MetadataSectionSerializer<T> pDeserializer) throws IOException {
        JsonObject jsonObject = new JsonObject();
        JsonObject pack = new JsonObject();
        pack.addProperty("description", "Tea the Story Auto");
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

    @Override
    public void listResources(PackType type, String namespace, String path, ResourceOutput resourceOutput) {

        // if(true)return;
// TeaStory.logger(11111,namespace,path);

        if (namespace.equals("minecraft") && path.equals("tags/block")) {
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            Planks.TrellisBlockMap.forEach((resourceLocation, blockBlockPair) -> {
                jsonArray.add(resourceLocation.toString());
            });
            for (Map.Entry<ResourceKey<Block>, Block> resourceKeyBlockEntry : BuiltInRegistries.BLOCK.entrySet()) {
                if (resourceKeyBlockEntry.getValue() instanceof TrellisBlock){
                    jsonArray.add(resourceKeyBlockEntry.getKey().location().toString());
                }
            }
            jsonObject.add("values", jsonArray);

            // here we need to use the method to lock the path
            var base = BlockTags.WOODEN_FENCES.location();
            ExistingFileHelper.ResourceType resourceType = new ExistingFileHelper.ResourceType(PackType.SERVER_DATA, ".json", Registries.tagsDirPath(Registries.BLOCK));
            var loc = TeaStory.rl(base.getNamespace(), resourceType.getPrefix() + "/" + base.getPath() + resourceType.getSuffix());
            resourceOutput.accept(loc, jsonObjectToIoSupplier(jsonObject));

        } else if (namespace.equals(TeaStory.MODID) && path.equals("recipe")) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("type", "minecraft:crafting_shaped");
            jsonObject.addProperty("category", "misc");
            jsonObject.addProperty("group", "trellis");

            JsonObject keyObject = new JsonObject();
            JsonObject rodObject = new JsonObject();
            rodObject.addProperty("tag", Tags.Items.RODS_WOODEN.location().toString());
            keyObject.add("#", rodObject);

            JsonObject itemObject = new JsonObject();
            itemObject.addProperty("item", "minecraft:acacia_fence");
            keyObject.add("*", itemObject);

            jsonObject.add("key", keyObject);

            JsonArray patternArray = new JsonArray();
            patternArray.add("#*#");
            patternArray.add(" # ");
            jsonObject.add("pattern", patternArray);

            JsonObject resultObject = new JsonObject();
            resultObject.addProperty("count", 2);
            resultObject.addProperty("id", "teastory:acacia_trellis");
            jsonObject.add("result", resultObject);

            jsonObject.addProperty("show_notification", true);


            int count = 0;
            for (var pairEntry : Planks.TrellisBlockMap.entrySet()) {
                // resultObject = new JsonObject();
                // resultObject.addProperty("count", 2);
                resultObject.addProperty("id", pairEntry.getKey().toString());
                var plankLoc = BuiltInRegistries.BLOCK.getKey(pairEntry.getValue().plank());
                var fence = ResourceLocation.tryParse((plankLoc).toString().replace("_planks", "_fence"));
                var fenceBlock = BuiltInRegistries.BLOCK.getOptional(fence);
                if (fenceBlock.isEmpty()) {
                    fence = plankLoc;
                    patternArray.remove(1);
                    patternArray.add("*#*");
                } else {
                    patternArray.remove(1);
                    patternArray.add(" # ");
                }

                itemObject.addProperty("item", fence.toString());
                // jsonObject.add("result", resultObject);
                resourceOutput.accept(TeaStory.rl("recipe/" + pairEntry.getKey().getPath() + ".json"), jsonObjectToIoSupplier(jsonObject));
                count++;
            }
            TeaStory.logger("Build %s recipes".formatted(count));

        } else if (namespace.equals(TeaStory.MODID) && path.equals("advancement")) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("parent", "minecraft:recipes/root");

            JsonObject criteriaObject = new JsonObject();
            JsonObject hasPlanksObject = new JsonObject();
            JsonObject hasPlanksConditions = new JsonObject();
            JsonArray itemsArray = new JsonArray();
            JsonObject itemObject = new JsonObject();
            JsonArray itemsArray1 = new JsonArray();
            itemsArray1.add("minecraft:acacia_fence");
            itemObject.add("items", itemsArray1);
            itemsArray.add(itemObject);
            hasPlanksConditions.add("items", itemsArray);
            hasPlanksObject.add("conditions", hasPlanksConditions);
            hasPlanksObject.addProperty("trigger", "minecraft:inventory_changed");
            criteriaObject.add("has_planks", hasPlanksObject);

            JsonObject hasTheRecipeObject = new JsonObject();
            JsonObject hasTheRecipeConditions = new JsonObject();
            hasTheRecipeConditions.addProperty("recipe", "teastory:acacia_trellis");
            hasTheRecipeObject.add("conditions", hasTheRecipeConditions);
            hasTheRecipeObject.addProperty("trigger", "minecraft:recipe_unlocked");
            criteriaObject.add("has_the_recipe", hasTheRecipeObject);

            jsonObject.add("criteria", criteriaObject);

            JsonArray requirementsArray = new JsonArray();
            JsonArray requirementArray = new JsonArray();
            requirementArray.add("has_planks");
            requirementArray.add("has_the_recipe");
            requirementsArray.add(requirementArray);
            jsonObject.add("requirements", requirementsArray);

            JsonObject rewardsObject = new JsonObject();
            JsonArray recipesArray = new JsonArray();
            recipesArray.add("teastory:acacia_trellis");
            rewardsObject.add("recipes", recipesArray);
            jsonObject.add("rewards", rewardsObject);

            jsonObject.addProperty("sends_telemetry_event", false);

            for (var pairEntry : Planks.TrellisBlockMap.entrySet()) {
                itemObject.addProperty("recipe", pairEntry.getKey().toString());
                var plankLoc = BuiltInRegistries.BLOCK.getKey(pairEntry.getValue().plank());
                var fenceLoc = ResourceLocation.tryParse((plankLoc).toString().replace("_planks", "_fence"));
                var fenceBlock = BuiltInRegistries.BLOCK.getOptional(fenceLoc);
                if (fenceBlock.isEmpty()) {
                    fenceLoc = plankLoc;
                }
                itemObject.addProperty("item", fenceLoc.toString());
                recipesArray = new JsonArray();
                recipesArray.add(pairEntry.getKey().toString());
                rewardsObject.add("recipes", recipesArray);

                itemsArray1 = new JsonArray();
                itemsArray1.add(fenceLoc.toString());
                itemObject.add("items", itemsArray1);
                // jsonObject.add("result", resultObject);

                resourceOutput.accept(TeaStory.rl("advancement/recipes/misc/" + pairEntry.getKey().getPath() + ".json"), jsonObjectToIoSupplier(jsonObject));
            }
        }

    }

    @Override
    public Set<String> getNamespaces(PackType pType) {
        return Set.of("minecraft", TeaStory.MODID);
    }

    @Override
    public void close() {

    }
}
