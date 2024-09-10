package com.teamtea.teastory.item.food;


import net.minecraft.world.food.FoodProperties;

public final class NormalFoods
{
    public static final FoodProperties DRIED_BEETROOT = new FoodProperties.Builder().nutrition(3).saturationModifier(0.6F).build();
    public static final FoodProperties DRIED_CARROT = new FoodProperties.Builder().nutrition(5).saturationModifier(0.6F).build();

    public static final FoodProperties BEEF_JERKY = new FoodProperties.Builder().nutrition(9).saturationModifier(1.0F).alwaysEdible().build();
    public static final FoodProperties PORK_JERKY = new FoodProperties.Builder().nutrition(9).saturationModifier(1.0F).alwaysEdible().build();
    public static final FoodProperties CHICKEN_JERKY = new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).alwaysEdible().build();
    public static final FoodProperties RABBIT_JERKY = new FoodProperties.Builder().nutrition(6).saturationModifier(1.0F).alwaysEdible().build();
    public static final FoodProperties MUTTON_JERKY = new FoodProperties.Builder().nutrition(8).saturationModifier(1.0F).alwaysEdible().build();

    public static final FoodProperties GRAPE = new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).build();
    public static final FoodProperties CUCUMBER = new FoodProperties.Builder().nutrition(1).saturationModifier(0.2F).build();
    public static final FoodProperties BITTER_GOURD = new FoodProperties.Builder().nutrition(1).saturationModifier(0.2F).build();
    public static final FoodProperties RAISINS = new FoodProperties.Builder().nutrition(2).saturationModifier(0.4F).fast().build();
    public static final FoodProperties RICE_BALL = new FoodProperties.Builder().nutrition(4).saturationModifier(0.5F).build();
    public static final FoodProperties RICE_BALL_WITH_KELP = new FoodProperties.Builder().nutrition(4).saturationModifier(0.5F).fast().build();

    public static final FoodProperties BEEF_BURGER = new FoodProperties.Builder().nutrition(8).saturationModifier(1.2F).build();
    public static final FoodProperties CHICKEN_BURGER = new FoodProperties.Builder().nutrition(8).saturationModifier(1.2F).build();
    //碗装类
    public static final FoodProperties NETHER_WART_RICE_BOWL = new FoodProperties.Builder().nutrition(10).saturationModifier(1.2F).build();
    public static final FoodProperties SPICY_BEEF_RICE_BOWL = new FoodProperties.Builder().nutrition(10).saturationModifier(1.2F).build();
    public static final FoodProperties BEEF_RICE_BOWL = new FoodProperties.Builder().nutrition(10).saturationModifier(1.2F).build();
    public static final FoodProperties RISE_BOWL = new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).build();

    public static final FoodProperties PICKLED_CABBAGE_WITH_FISH = new FoodProperties.Builder().nutrition(8).saturationModifier(1.0F).build();
    //碟装类
    public static final FoodProperties STEAMED_CHINESE_CABBAGE = new FoodProperties.Builder().nutrition(5).saturationModifier(0.7F).build();
    public static final FoodProperties HONEY_BITTER_GOURD = new FoodProperties.Builder().nutrition(5).saturationModifier(0.5F).build();
    public static final FoodProperties SHREDDED_CUCUMBER_SALAD = new FoodProperties.Builder().nutrition(4).saturationModifier(1.0F).build();
    public static final FoodProperties PORK_BAOZI = new FoodProperties.Builder().nutrition(6).saturationModifier(1.2F).build();
}
