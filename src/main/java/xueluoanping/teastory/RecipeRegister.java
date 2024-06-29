package xueluoanping.teastory;


import cloud.lemonslice.teastory.recipe.stone_mill.StoneMillRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static xueluoanping.teastory.TeaStory.MODID;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RecipeRegister {
    public static final DeferredRegister<RecipeSerializer<?>> DRRecipeSerializer =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);
    public static final DeferredRegister<RecipeType<?>> DRRecipeType =
            DeferredRegister.create(Registries.RECIPE_TYPE, MODID);


    // @SubscribeEvent // ModBus, can't use addListener due to nested genetics.
    // public static void registerRecipeSerialziers(RegistryEvent.Register<RecipeSerializer<?>> event) {
    //     CraftingHelper.register(ConfigCondition.Serializer.INSTANCE);
    // }

    public static final RegistryObject<RecipeSerializer<StoneMillRecipe>> STONE_MILL_SERIALIZER = DRRecipeSerializer
            .register("stone_mill", StoneMillRecipe.StoneMillRecipeSerializer::new);
    public static final RegistryObject<RecipeType<StoneMillRecipe>> STONE_MILL =
            DRRecipeType.register("stone_mill", () -> RecipeType.simple(TeaStory.rl("stone_mill")));


}
