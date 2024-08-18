package xueluoanping.teastory;


import cloud.lemonslice.teastory.item.*;
import cloud.lemonslice.teastory.item.food.NormalFoods;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


// import static xueluoanping.fluiddrawerslegacy.FluidDrawersLegacyMod.CREATIVE_TAB;

// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegister {
    public static final DeferredRegister<Item> ModItems = DeferredRegister.create(ForgeRegistries.ITEMS, TeaStory.MODID);

    public static RegistryObject<Item> shennong_chi = ModItems.register("shennong_chi", () -> new ShennongChiItem(new Item.Properties().fireResistant()));
    public static RegistryObject<Item> ironSickle = ModItems.register("iron_sickle", () -> new SickleItem(Tiers.IRON, 1.5F, -2.5F, new Item.Properties().fireResistant()));


    public static RegistryObject<Item> woodenAqueductShovel = ModItems.register("wooden_aqueduct_shovel", () -> new AqueductShovelItem(Tiers.WOOD, 1.5F, -2.5F, new Item.Properties()));
    public static RegistryObject<Item> stoneAqueductShovel = ModItems.register("stone_aqueduct_shovel", () -> new AqueductShovelItem(Tiers.STONE, 1.5F, -2.5F, new Item.Properties()));
    public static RegistryObject<Item> goldAqueductShovel = ModItems.register("gold_aqueduct_shovel", () -> new AqueductShovelItem(Tiers.GOLD, 1.5F, -2.5F, new Item.Properties()));
    public static RegistryObject<Item> ironAqueductShovel = ModItems.register("iron_aqueduct_shovel", () -> new AqueductShovelItem(Tiers.IRON, 1.5F, -2.5F, new Item.Properties()));
    public static RegistryObject<Item> diamondAqueductShovel = ModItems.register("diamond_aqueduct_shovel", () -> new AqueductShovelItem(Tiers.DIAMOND, 1.5F, -2.5F, new Item.Properties()));

    public static RegistryObject<Item> WET_STRAW = ModItems.register("wet_straw", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> DRY_STRAW = ModItems.register("dry_straw", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> BAMBOO_PLANK = ModItems.register("bamboo_plank", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> ASH = ModItems.register("ash", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> TEA_RESIDUES = ModItems.register("tea_residues", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> CRUSHED_STRAW = ModItems.register("crushed_straw", () -> new Item(new Item.Properties()));


    // INGREDIENTS 原料
    public static RegistryObject<Item> TEA_LEAVES = ModItems.register("tea_leaves", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> GREEN_TEA_LEAVES = ModItems.register("green_tea_leaves", () -> new Item(new Item.Properties().craftRemainder(TEA_RESIDUES.get())));
    public static RegistryObject<Item> BLACK_TEA_LEAVES = ModItems.register("black_tea_leaves", () -> new Item(new Item.Properties().craftRemainder(TEA_RESIDUES.get())));
    public static RegistryObject<Item> WHITE_TEA_LEAVES = ModItems.register("white_tea_leaves", () -> new Item(new Item.Properties().craftRemainder(TEA_RESIDUES.get())));
    public static RegistryObject<Item> EMPTY_TEA_BAG = ModItems.register("empty_tea_bag", () -> new Item(new Item.Properties().craftRemainder(TEA_RESIDUES.get())));
    public static RegistryObject<Item> GREEN_TEA_BAG = ModItems.register("green_tea_bag", () -> new Item(new Item.Properties().craftRemainder(TEA_RESIDUES.get())));
    public static RegistryObject<Item> BLACK_TEA_BAG = ModItems.register("black_tea_bag", () -> new Item(new Item.Properties().craftRemainder(TEA_RESIDUES.get())));

    public static RegistryObject<Item> RICE = ModItems.register("rice", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> WASHED_RICE = ModItems.register("washed_rice", () -> new Item(new Item.Properties()));

    // DRINK 饮品
    public static RegistryObject<Item> CLAY_CUP = ModItems.register("clay_cup", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> CLAY_TEAPOT = ModItems.register("clay_teapot", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> PORCELAIN_CUP = ModItems.register("porcelain_cup", () -> new ItemFluidContainer(new Item.Properties(), 250) {

        @Override
        public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @org.jetbrains.annotations.Nullable CompoundTag nbt) {
            return super.initCapabilities(new ItemStack(PORCELAIN_CUP_DRINK.get()), nbt);
        }
    });
    public static RegistryObject<Item> BOTTLE = ModItems.register("bottle", () -> new ItemFluidContainer(new Item.Properties(), 500) {
        @Override
        public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @org.jetbrains.annotations.Nullable CompoundTag nbt) {
            return super.initCapabilities(new ItemStack(BOTTLE_DRINK.get()), nbt);
        }
    });

    public static RegistryObject<CupDrinkItem> PORCELAIN_CUP_DRINK = ModItems.register("porcelain_cup_drink", () -> new CupDrinkItem(250, new Item.Properties().craftRemainder(PORCELAIN_CUP.get()).stacksTo(1)));
    public static RegistryObject<CupDrinkItem> BOTTLE_DRINK = ModItems.register("bottle_drink", () -> new CupDrinkItem(500, new Item.Properties().craftRemainder(BOTTLE.get()).stacksTo(1)));


    public static RegistryObject<Item> STONE_MILL_TOP = ModItems.register("stone_mill_top", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> STONE_ROLLER_TOP = ModItems.register("stone_roller_top", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> STONE_ROLLER_WOODEN_FRAME = ModItems.register("stone_roller_wooden_frame", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> SAUCEPAN_LID = ModItems.register("saucepan_lid", () -> new Item(new Item.Properties()));

    public static RegistryObject<Item> RAISINS = ModItems.register("raisins", () -> new Item(new Item.Properties().food(NormalFoods.RAISINS)));
    public static RegistryObject<Item> RICE_BALL = ModItems.register("rice_ball", () -> new Item(new Item.Properties().food(NormalFoods.RICE_BALL)));
    public static RegistryObject<Item> RICE_BALL_WITH_KELP = ModItems.register("rice_ball_with_kelp", () -> new Item(new Item.Properties().food(NormalFoods.RICE_BALL_WITH_KELP)));

    public static RegistryObject<Item> BAMBOO_CHARCOAL = ModItems.register("bamboo_charcoal", () -> new Item(new Item.Properties()) {
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 800;
        }
    });
    public static RegistryObject<Item> HONEYCOMB_BRIQUETTE = ModItems.register("honeycomb_briquette", () -> new Item(new Item.Properties()) {
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 6000;
        }
    });


    public static RegistryObject<Item> DRIED_BEETROOT = ModItems.register("dried_beetroot",() -> new Item(new Item.Properties().food(NormalFoods.DRIED_BEETROOT)));
    public static RegistryObject<Item> DRIED_CARROT = ModItems.register("dried_carrot",() -> new Item(new Item.Properties().food(NormalFoods.DRIED_CARROT)));
    public static RegistryObject<Item> BEEF_JERKY = ModItems.register("beef_jerky",() -> new Item(new Item.Properties().food(NormalFoods.BEEF_JERKY)));
    public static RegistryObject<Item> PORK_JERKY = ModItems.register("pork_jerky",() -> new Item(new Item.Properties().food(NormalFoods.PORK_JERKY)));
    public static RegistryObject<Item> CHICKEN_JERKY = ModItems.register("chicken_jerky",() -> new Item(new Item.Properties().food(NormalFoods.CHICKEN_JERKY)));
    public static RegistryObject<Item> RABBIT_JERKY = ModItems.register("rabbit_jerky",() -> new Item(new Item.Properties().food(NormalFoods.RABBIT_JERKY)));
    public static RegistryObject<Item> MUTTON_JERKY = ModItems.register("mutton_jerky",() -> new Item(new Item.Properties().food(NormalFoods.MUTTON_JERKY)));
    
    public static RegistryObject<Item> NETHER_WART_RICE_BOWL = ModItems.register("nether_wart_rice_bowl",() -> new Item(new Item.Properties().food(NormalFoods.NETHER_WART_RICE_BOWL)));
    public static RegistryObject<Item> SPICY_BEEF_RICE_BOWL = ModItems.register("spicy_beef_rice_bowl",() -> new Item(new Item.Properties().food(NormalFoods.SPICY_BEEF_RICE_BOWL)));
    public static RegistryObject<Item> BEEF_RICE_BOWL = ModItems.register("beef_rice_bowl",() -> new Item(new Item.Properties().food(NormalFoods.BEEF_RICE_BOWL)));
    public static RegistryObject<Item> RISE_BOWL = ModItems.register("rise_bowl",() -> new Item(new Item.Properties().food(NormalFoods.RISE_BOWL)));
    public static RegistryObject<Item> PICKLED_CABBAGE_WITH_FISH = ModItems.register("pickled_cabbage_with_fish",() -> new Item(new Item.Properties().food(NormalFoods.PICKLED_CABBAGE_WITH_FISH)));
    public static RegistryObject<Item> STEAMED_CHINESE_CABBAGE = ModItems.register("steamed_chinese_cabbage",() -> new Item(new Item.Properties().food(NormalFoods.STEAMED_CHINESE_CABBAGE)));
    public static RegistryObject<Item> HONEY_BITTER_GOURD = ModItems.register("honey_bitter_gourd",() -> new Item(new Item.Properties().food(NormalFoods.HONEY_BITTER_GOURD)));
    public static RegistryObject<Item> SHREDDED_CUCUMBER_SALAD = ModItems.register("shredded_cucumber_salad",() -> new Item(new Item.Properties().food(NormalFoods.SHREDDED_CUCUMBER_SALAD)));
    public static RegistryObject<Item> PORK_BAOZI = ModItems.register("pork_baozi",() -> new Item(new Item.Properties().food(NormalFoods.PORK_BAOZI)));
    public static RegistryObject<Item> BEEF_BURGER = ModItems.register("beef_burger",() -> new Item(new Item.Properties().food(NormalFoods.BEEF_BURGER)));
    public static RegistryObject<Item> CHICKEN_BURGER = ModItems.register("chicken_burger",() -> new Item(new Item.Properties().food(NormalFoods.CHICKEN_BURGER)));

}

