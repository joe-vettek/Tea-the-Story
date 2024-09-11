package com.teamtea.teastory.registry;


import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.recipe.bamboo_tray.*;
import com.teamtea.teastory.recipe.drink.DrinkRecipe;
import com.teamtea.teastory.recipe.special.FlowerDyeRecipe;
import com.teamtea.teastory.recipe.stone_mill.StoneMillRecipe;
import com.teamtea.teastory.recipe.stone_mill.StoneRollerRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.teamtea.teastory.TeaStory.MODID;


public class RecipeRegister {
    public static final DeferredRegister<RecipeSerializer<?>> DRRecipeSerializer =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MODID);
    public static final DeferredRegister<RecipeType<?>> DRRecipeType =
            DeferredRegister.create(Registries.RECIPE_TYPE, MODID);


    // @SubscribeEvent // ModBus, can't use addListener due to nested genetics.
    // public static void registerRecipeSerialziers(RegistryEvent.Register<RecipeSerializer<?>> event) {
    //     CraftingHelper.register(ConfigCondition.Serializer.INSTANCE);
    // }

    public static final DeferredHolder<RecipeSerializer<?>, BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<? extends BambooTraySingleInRecipe>> BAMBOO_TRAY_OUTDOORS_SERIALIZER = DRRecipeSerializer
            .register("bamboo_tray_outdoors", ()->new BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<>(BambooTrayOutdoorsRecipe::new,200));
    public static final DeferredHolder<RecipeType<?>, RecipeType<BambooTrayOutdoorsRecipe>> BAMBOO_TRAY_OUTDOORS =
            DRRecipeType.register("bamboo_tray_outdoors", () -> RecipeType.simple(TeaStory.rl("bamboo_tray_outdoors")));

    public static final DeferredHolder<RecipeSerializer<?>, BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<? extends BambooTraySingleInRecipe>> BAMBOO_TRAY_INDOORS_SERIALIZER = DRRecipeSerializer
            .register("bamboo_tray_indoors", ()->new BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<>(BambooTrayIndoorsRecipe::new,200));
    public static final DeferredHolder<RecipeType<?>, RecipeType<BambooTrayIndoorsRecipe>> BAMBOO_TRAY_INDOORS =
            DRRecipeType.register("bamboo_tray_indoors", () -> RecipeType.simple(TeaStory.rl("bamboo_tray_indoors")));

    public static final DeferredHolder<RecipeSerializer<?>, BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<? extends BambooTraySingleInRecipe>> BAMBOO_TRAY_IN_RAIN_SERIALIZER = DRRecipeSerializer
            .register("bamboo_tray_in_rain", ()->new BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<>(BambooTrayInRainRecipe::new,200));
    public static final DeferredHolder<RecipeType<?>, RecipeType<BambooTrayInRainRecipe>> BAMBOO_TRAY_IN_RAIN =
            DRRecipeType.register("bamboo_tray_in_rain", () -> RecipeType.simple(TeaStory.rl("bamboo_tray_in_rain")));

    public static final DeferredHolder<RecipeSerializer<?>, BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<? extends BambooTraySingleInRecipe>> BAMBOO_TRAY_BAKE_SERIALIZER = DRRecipeSerializer
            .register("bamboo_tray_bake",()->new BambooTraySingleInRecipe.BambooTraySingleInRecipeSerializer<>(BambooTrayBakeRecipe::new,200));
    public static final DeferredHolder<RecipeType<?>, RecipeType<BambooTrayBakeRecipe>> BAMBOO_TRAY_BAKE =
            DRRecipeType.register("bamboo_tray_bake", () -> RecipeType.simple(TeaStory.rl("bamboo_tray_bake")));

    public static final DeferredHolder<RecipeSerializer<?>, DrinkRecipe.DrinkRecipeSerializer> DRINK_MAKER_SERIALIZER = DRRecipeSerializer
            .register("drink_maker", DrinkRecipe.DrinkRecipeSerializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<DrinkRecipe>> DRINK_MAKER =
            DRRecipeType.register("drink_maker", () -> RecipeType.simple(TeaStory.rl("drink_maker")));


    public static final DeferredHolder<RecipeSerializer<?>, StoneMillRecipe.StoneMillRecipeSerializer> STONE_MILL_SERIALIZER = DRRecipeSerializer
            .register("stone_mill", StoneMillRecipe.StoneMillRecipeSerializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<StoneMillRecipe>> STONE_MILL =
            DRRecipeType.register("stone_mill", () -> RecipeType.simple(TeaStory.rl("stone_mill")));


    public static final DeferredHolder<RecipeSerializer<?>, StoneRollerRecipe.StoneRollerRecipeSerializer> STONE_ROLLER_SERIALIZER = DRRecipeSerializer
            .register("stone_roller", StoneRollerRecipe.StoneRollerRecipeSerializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<StoneRollerRecipe>> STONE_ROLLER =
            DRRecipeType.register("stone_roller", () -> RecipeType.simple(TeaStory.rl("stone_roller")));

    public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<FlowerDyeRecipe>> CRAFTING_SPECIAL_FLOWERDYE = DRRecipeSerializer
            .register("crafting_special_flowerdye", () -> new SimpleCraftingRecipeSerializer<>(FlowerDyeRecipe::new));
}
