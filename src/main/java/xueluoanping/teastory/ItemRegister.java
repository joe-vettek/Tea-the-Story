package xueluoanping.teastory;


import cloud.lemonslice.teastory.item.*;
import cloud.lemonslice.teastory.item.food.NormalFoods;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xueluoanping.teastory.client.SoundEventsRegistry;


// import static xueluoanping.fluiddrawerslegacy.FluidDrawersLegacyMod.CREATIVE_TAB;

// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)
public class ItemRegister {
    // DeferredItem is also good
    public static final DeferredRegister<Item> ModItems = DeferredRegister.create(Registries.ITEM, TeaStory.MODID);

    public static DeferredHolder<Item, ShennongChiItem> shennong_chi = ModItems.register("shennong_chi", () -> new ShennongChiItem(new Item.Properties().fireResistant()));
    public static DeferredHolder<Item, SickleItem> IRON_SICKLE = ModItems.register("iron_sickle", () -> new SickleItem(Tiers.IRON,  new Item.Properties().fireResistant().attributes(AxeItem.createAttributes(Tiers.IRON, 1.5F, -2.5F))));


    public static DeferredHolder<Item, AqueductShovelItem> WOODEN_AQUEDUCT_SHOVEL = ModItems.register("wooden_aqueduct_shovel", () -> new AqueductShovelItem(Tiers.WOOD,  new Item.Properties().attributes(AxeItem.createAttributes(Tiers.WOOD, 1.5F, -2.5F))));
    public static DeferredHolder<Item, AqueductShovelItem> STONE_AQUEDUCT_SHOVEL = ModItems.register("stone_aqueduct_shovel", () -> new AqueductShovelItem(Tiers.STONE,  new Item.Properties().attributes(AxeItem.createAttributes(Tiers.STONE, 1.5F, -2.5F))));
    public static DeferredHolder<Item, AqueductShovelItem> GOLD_AQUEDUCT_SHOVEL = ModItems.register("gold_aqueduct_shovel", () -> new AqueductShovelItem(Tiers.GOLD,  new Item.Properties().attributes(AxeItem.createAttributes(Tiers.GOLD, 1.5F, -2.5F))));
    public static DeferredHolder<Item, AqueductShovelItem> IRON_AQUEDUCT_SHOVEL = ModItems.register("iron_aqueduct_shovel", () -> new AqueductShovelItem(Tiers.IRON,  new Item.Properties().attributes(AxeItem.createAttributes(Tiers.IRON, 1.5F, -2.5F))));
    public static DeferredHolder<Item, AqueductShovelItem> DIAMOND_AQUEDUCT_SHOVEL = ModItems.register("diamond_aqueduct_shovel", () -> new AqueductShovelItem(Tiers.DIAMOND,  new Item.Properties().attributes(AxeItem.createAttributes(Tiers.DIAMOND, 1.5F, -2.5F))));

    public static DeferredHolder<Item, Item> WET_STRAW = ModItems.register("wet_straw", () -> new Item(new Item.Properties()));
    public static DeferredHolder<Item, Item> DRY_STRAW = ModItems.register("dry_straw", () -> new Item(new Item.Properties()));
    public static DeferredHolder<Item, Item> BAMBOO_PLANK = ModItems.register("bamboo_plank", () -> new Item(new Item.Properties()));
    public static DeferredHolder<Item, Item> ASH = ModItems.register("ash", () -> new Item(new Item.Properties()));
    public static DeferredHolder<Item, Item> TEA_RESIDUES = ModItems.register("tea_residues", () -> new Item(new Item.Properties()));
    public static DeferredHolder<Item, Item> CRUSHED_STRAW = ModItems.register("crushed_straw", () -> new Item(new Item.Properties()));


    // INGREDIENTS 原料
    public static DeferredHolder<Item, Item> TEA_LEAVES = ModItems.register("tea_leaves", () -> new Item(new Item.Properties()));
    public static DeferredHolder<Item, Item> GREEN_TEA_LEAVES = ModItems.register("green_tea_leaves", () -> new Item(new Item.Properties().craftRemainder(TEA_RESIDUES.get())));
    public static DeferredHolder<Item, Item> BLACK_TEA_LEAVES = ModItems.register("black_tea_leaves", () -> new Item(new Item.Properties().craftRemainder(TEA_RESIDUES.get())));
    public static DeferredHolder<Item, Item> WHITE_TEA_LEAVES = ModItems.register("white_tea_leaves", () -> new Item(new Item.Properties().craftRemainder(TEA_RESIDUES.get())));
    public static DeferredHolder<Item, Item> EMPTY_TEA_BAG = ModItems.register("empty_tea_bag", () -> new Item(new Item.Properties().craftRemainder(TEA_RESIDUES.get())));
    public static DeferredHolder<Item, Item> GREEN_TEA_BAG = ModItems.register("green_tea_bag", () -> new Item(new Item.Properties().craftRemainder(TEA_RESIDUES.get())));
    public static DeferredHolder<Item, Item> BLACK_TEA_BAG = ModItems.register("black_tea_bag", () -> new Item(new Item.Properties().craftRemainder(TEA_RESIDUES.get())));

    public static DeferredHolder<Item, Item> RICE = ModItems.register("rice", () -> new Item(new Item.Properties()));
    public static DeferredHolder<Item, Item> WASHED_RICE = ModItems.register("washed_rice", () -> new Item(new Item.Properties()));

    // DRINK 饮品
    public static DeferredHolder<Item, Item> CLAY_CUP = ModItems.register("clay_cup", () -> new Item(new Item.Properties()));
    public static DeferredHolder<Item, Item> CLAY_TEAPOT = ModItems.register("clay_teapot", () -> new Item(new Item.Properties()));
    public static DeferredHolder<Item, Item> PORCELAIN_CUP = ModItems.register("porcelain_cup", () -> new ItemFluidContainer(new Item.Properties(), 250) {

        @Override
        public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @org.jetbrains.annotations.Nullable CompoundTag nbt) {
            return super.initCapabilities(new ItemStack(PORCELAIN_CUP_DRINK.get()), nbt);
        }
    });
    public static DeferredHolder<Item, Item> BOTTLE = ModItems.register("bottle", () -> new ItemFluidContainer(new Item.Properties(), 500) {
        @Override
        public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @org.jetbrains.annotations.Nullable CompoundTag nbt) {
            return super.initCapabilities(new ItemStack(BOTTLE_DRINK.get()), nbt);
        }
    });

    public static  DeferredHolder<Item,CupDrinkItem> PORCELAIN_CUP_DRINK = ModItems.register("porcelain_cup_drink", () -> new CupDrinkItem(250, new Item.Properties().craftRemainder(PORCELAIN_CUP.get()).stacksTo(1)));
    public static  DeferredHolder<Item,CupDrinkItem> BOTTLE_DRINK = ModItems.register("bottle_drink", () -> new CupDrinkItem(500, new Item.Properties().craftRemainder(BOTTLE.get()).stacksTo(1)));


    public static DeferredHolder<Item, Item> STONE_MILL_TOP = ModItems.register("stone_mill_top", () -> new Item(new Item.Properties()));
    public static DeferredHolder<Item, Item> STONE_ROLLER_TOP = ModItems.register("stone_roller_top", () -> new Item(new Item.Properties()));
    public static DeferredHolder<Item, Item> STONE_ROLLER_WOODEN_FRAME = ModItems.register("stone_roller_wooden_frame", () -> new Item(new Item.Properties()));
    public static DeferredHolder<Item, Item> SAUCEPAN_LID = ModItems.register("saucepan_lid", () -> new Item(new Item.Properties()));

    public static DeferredHolder<Item, Item> RAISINS = ModItems.register("raisins", () -> new Item(new Item.Properties().food(NormalFoods.RAISINS)));
    public static DeferredHolder<Item, Item> RICE_BALL = ModItems.register("rice_ball", () -> new Item(new Item.Properties().food(NormalFoods.RICE_BALL)));
    public static DeferredHolder<Item, Item> RICE_BALL_WITH_KELP = ModItems.register("rice_ball_with_kelp", () -> new Item(new Item.Properties().food(NormalFoods.RICE_BALL_WITH_KELP)));

    public static DeferredHolder<Item, Item> BAMBOO_CHARCOAL = ModItems.register("bamboo_charcoal", () -> new Item(new Item.Properties()) {
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 800;
        }
    });
    public static DeferredHolder<Item, Item> HONEYCOMB_BRIQUETTE = ModItems.register("honeycomb_briquette", () -> new Item(new Item.Properties()) {
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 6000;
        }
    });


    public static DeferredHolder<Item, Item> DRIED_BEETROOT = ModItems.register("dried_beetroot", () -> new Item(new Item.Properties().food(NormalFoods.DRIED_BEETROOT)));
    public static DeferredHolder<Item, Item> DRIED_CARROT = ModItems.register("dried_carrot", () -> new Item(new Item.Properties().food(NormalFoods.DRIED_CARROT)));
    public static DeferredHolder<Item, Item> BEEF_JERKY = ModItems.register("beef_jerky", () -> new Item(new Item.Properties().food(NormalFoods.BEEF_JERKY)));
    public static DeferredHolder<Item, Item> PORK_JERKY = ModItems.register("pork_jerky", () -> new Item(new Item.Properties().food(NormalFoods.PORK_JERKY)));
    public static DeferredHolder<Item, Item> CHICKEN_JERKY = ModItems.register("chicken_jerky", () -> new Item(new Item.Properties().food(NormalFoods.CHICKEN_JERKY)));
    public static DeferredHolder<Item, Item> RABBIT_JERKY = ModItems.register("rabbit_jerky", () -> new Item(new Item.Properties().food(NormalFoods.RABBIT_JERKY)));
    public static DeferredHolder<Item, Item> MUTTON_JERKY = ModItems.register("mutton_jerky", () -> new Item(new Item.Properties().food(NormalFoods.MUTTON_JERKY)));

    public static DeferredHolder<Item, Item> NETHER_WART_RICE_BOWL = ModItems.register("nether_wart_rice_bowl", () -> new Item(new Item.Properties().food(NormalFoods.NETHER_WART_RICE_BOWL)));
    public static DeferredHolder<Item, Item> SPICY_BEEF_RICE_BOWL = ModItems.register("spicy_beef_rice_bowl", () -> new Item(new Item.Properties().food(NormalFoods.SPICY_BEEF_RICE_BOWL)));
    public static DeferredHolder<Item, Item> BEEF_RICE_BOWL = ModItems.register("beef_rice_bowl", () -> new Item(new Item.Properties().food(NormalFoods.BEEF_RICE_BOWL)));
    public static DeferredHolder<Item, Item> RICE_BOWL = ModItems.register("rice_bowl", () -> new Item(new Item.Properties().food(NormalFoods.RISE_BOWL)));
    public static DeferredHolder<Item, Item> PICKLED_CABBAGE_WITH_FISH = ModItems.register("pickled_cabbage_with_fish", () -> new Item(new Item.Properties().food(NormalFoods.PICKLED_CABBAGE_WITH_FISH)));
    public static DeferredHolder<Item, Item> STEAMED_CHINESE_CABBAGE = ModItems.register("steamed_chinese_cabbage", () -> new Item(new Item.Properties().food(NormalFoods.STEAMED_CHINESE_CABBAGE)));
    public static DeferredHolder<Item, Item> HONEY_BITTER_GOURD = ModItems.register("honey_bitter_gourd", () -> new Item(new Item.Properties().food(NormalFoods.HONEY_BITTER_GOURD)));
    public static DeferredHolder<Item, Item> SHREDDED_CUCUMBER_SALAD = ModItems.register("shredded_cucumber_salad", () -> new Item(new Item.Properties().food(NormalFoods.SHREDDED_CUCUMBER_SALAD)));
    public static DeferredHolder<Item, Item> PORK_BAOZI = ModItems.register("pork_baozi", () -> new Item(new Item.Properties().food(NormalFoods.PORK_BAOZI)));
    public static DeferredHolder<Item, Item> BEEF_BURGER = ModItems.register("beef_burger", () -> new Item(new Item.Properties().food(NormalFoods.BEEF_BURGER)));
    public static DeferredHolder<Item, Item> CHICKEN_BURGER = ModItems.register("chicken_burger", () -> new Item(new Item.Properties().food(NormalFoods.CHICKEN_BURGER)));

    public static DeferredHolder<Item, Item> PICKING_TEA_RECORD = ModItems.register("picking_tea", () -> new SRecordItem(1, () -> SoundEventsRegistry.RECORD_PICKING_TEA, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 10));
    public static DeferredHolder<Item, Item> SPRING_FESTIVAL_OVERTURE_RECORD = ModItems.register("spring_festival_overture", () -> new SRecordItem(2, () -> SoundEventsRegistry.RECORD_SPRING_FESTIVAL_OVERTURE, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 10));
    public static DeferredHolder<Item, Item> FLOWERS_AND_MOON_RECORD = ModItems.register("flowers_moon", () -> new SRecordItem(3, () -> SoundEventsRegistry.RECORD_FLOWERS_AND_MOON, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 10));
    public static DeferredHolder<Item, Item> MOVING_UP_RECORD = ModItems.register("moving_up", () -> new SRecordItem(4, () -> SoundEventsRegistry.RECORD_MOVING_UP, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 10));
    public static DeferredHolder<Item, Item> JOYFUL_RECORD = ModItems.register("joyful", () -> new SRecordItem(5, () -> SoundEventsRegistry.RECORD_JOYFUL, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 10));
    public static DeferredHolder<Item, Item> DANCING_GOLDEN_SNAKE_RECORD = ModItems.register("dancing_golden_snake", () -> new SRecordItem(6, () -> SoundEventsRegistry.RECORD_DANCING_GOLDEN_SNAKE, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 10));
    public static DeferredHolder<Item, Item> GREEN_WILLOW_RECORD = ModItems.register("green_willow", () -> new SRecordItem(7, () -> SoundEventsRegistry.RECORD_GREEN_WILLOW, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 10));
    public static DeferredHolder<Item, Item> PURPLE_BAMBOO_MELODY_RECORD = ModItems.register("purple_bamboo_melody", () -> new SRecordItem(8, () -> SoundEventsRegistry.RECORD_PURPLE_BAMBOO_MELODY, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 10));
    public static DeferredHolder<Item, Item> WELCOME_MARCH_RECORD = ModItems.register("welcome_march", () -> new SRecordItem(9, () -> SoundEventsRegistry.RECORD_WELCOME_MARCH, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 10));


}

