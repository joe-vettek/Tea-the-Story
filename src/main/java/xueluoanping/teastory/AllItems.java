package xueluoanping.teastory;


import cloud.lemonslice.teastory.item.ShennongChiItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import cloud.lemonslice.teastory.item.AqueductShovelItem;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


// import static xueluoanping.fluiddrawerslegacy.FluidDrawersLegacyMod.CREATIVE_TAB;

// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AllItems {
    public static final DeferredRegister<Item> ModItems = DeferredRegister.create(ForgeRegistries.ITEMS, TeaStory.MODID);

    public static RegistryObject<Item> shennong_chi = ModItems.register("shennong_chi", () -> new ShennongChiItem( new Item.Properties().fireResistant()));


    public static RegistryObject<Item> woodenAqueductShovel = ModItems.register("wooden_aqueduct_shovel", () -> new AqueductShovelItem(Tiers.WOOD, 1.5F, -2.5F, new Item.Properties()));
    public static RegistryObject<Item> stoneAqueductShovel = ModItems.register("stone_aqueduct_shovel", () -> new AqueductShovelItem(Tiers.STONE, 1.5F, -2.5F, new Item.Properties()));
    public static RegistryObject<Item> goldAqueductShovel = ModItems.register("gold_aqueduct_shovel", () -> new AqueductShovelItem(Tiers.GOLD, 1.5F, -2.5F, new Item.Properties()));
    public static RegistryObject<Item> ironAqueductShovel = ModItems.register("iron_aqueduct_shovel", () -> new AqueductShovelItem(Tiers.IRON, 1.5F, -2.5F, new Item.Properties()));
    public static RegistryObject<Item> diamondAqueductShovel = ModItems.register("diamond_aqueduct_shovel", () -> new AqueductShovelItem(Tiers.DIAMOND, 1.5F, -2.5F, new Item.Properties()));

    public static RegistryObject<Item> wetStraw = ModItems.register("wet_straw", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> dryStraw = ModItems.register("dry_straw", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> bambooPlank = ModItems.register("bamboo_plank", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> ash = ModItems.register("ash", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> teaResidues = ModItems.register("tea_residues", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> crushedStraw = ModItems.register("crushed_straw", () -> new Item(new Item.Properties()));


    // INGREDIENTS 原料
    public static RegistryObject<Item> TEA_LEAVES = ModItems.register("tea_leaves", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> GREEN_TEA_LEAVES = ModItems.register("green_tea_leaves", () -> new Item(new Item.Properties().craftRemainder(teaResidues.get())));
    public static RegistryObject<Item> BLACK_TEA_LEAVES = ModItems.register("black_tea_leaves", () -> new Item(new Item.Properties().craftRemainder(teaResidues.get())));
    public static RegistryObject<Item> WHITE_TEA_LEAVES = ModItems.register("white_tea_leaves", () -> new Item(new Item.Properties().craftRemainder(teaResidues.get())));
    public static RegistryObject<Item> EMPTY_TEA_BAG = ModItems.register("empty_tea_bag", () -> new Item(new Item.Properties().craftRemainder(teaResidues.get())));
    public static RegistryObject<Item> GREEN_TEA_BAG = ModItems.register("green_tea_bag", () -> new Item(new Item.Properties().craftRemainder(teaResidues.get())));
    public static RegistryObject<Item> BLACK_TEA_BAG = ModItems.register("black_tea_bag", () -> new Item(new Item.Properties().craftRemainder(teaResidues.get())));

    public static RegistryObject<Item> RICE = ModItems.register("rice", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> WASHED_RICE = ModItems.register("washed_rice", () -> new Item(new Item.Properties()));

    // DRINK 饮品
    public static RegistryObject<Item> CLAY_CUP = ModItems.register("clay_cup", () -> new Item(new Item.Properties()));
    public static RegistryObject<Item> CLAY_TEAPOT = ModItems.register("clay_teapot", () -> new Item(new Item.Properties()));
    // public static RegistryObject<Item> PORCELAIN_CUP = ModItems.register("porcelain_cup", () ->new ItemFluidContainer(new Item.Properties(), 250)
    // {
    //
    //     @Override
    //     public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @org.jetbrains.annotations.Nullable CompoundTag nbt) {
    //         return super.initCapabilities(new ItemStack(PORCELAIN_CUP_DRINK), nbt);
    //     }
    // });
    //
    //
    // public static final Item PORCELAIN_CUP = new ItemFluidContainer(getDrinkItemProperties(), 250)
    // {
    //     @Override
    //     public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt)
    //     {
    //         return super.initCapabilities(new ItemStack(ItemRegistry.PORCELAIN_CUP_DRINK), nbt);
    //     }
    // }.setRegistryName("porcelain_cup");
    // public static final Item BOTTLE = new ItemFluidContainer(getDrinkItemProperties(), 500)
    // {
    //     @Override
    //     public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt)
    //     {
    //         return super.initCapabilities(new ItemStack(ItemRegistry.BOTTLE_DRINK), nbt);
    //     }
    // }.setRegistryName("bottle");

}

