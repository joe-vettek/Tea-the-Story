package com.teamtea.teastory;


import com.teamtea.teastory.fluid.HotWaterFlowingFluidBlock;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import com.teamtea.teastory.fluid.TeaFluidType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;


public final class FluidRegistry {
    public static final String MODID = TeaStory.MODID;
    public static final Item.Properties BUCKET_PROPERTIES = new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1);

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);
    public static DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, MODID);


    public static final ResourceLocation WATER_STILL_TEXTURE =  ResourceLocation.tryParse("minecraft:block/water_still");
    public static final ResourceLocation WATER_FLOW_TEXTURE = ResourceLocation.tryParse("minecraft:block/water_flow");

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> BOILING_WATER_STILL = FLUIDS.register("boiling_water", () -> new BaseFlowingFluid.Source(FluidRegistry.BOILING_WATER_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> BOILING_WATER_FLOW = FLUIDS.register("boiling_water_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.BOILING_WATER_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> BOILING_WATER_BUCKET = ITEMS.register("boiling_water_bucket", () -> new BucketItem(FluidRegistry.BOILING_WATER_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, HotWaterFlowingFluidBlock> BOILING_WATER = BLOCKS.register("boiling_water", () -> new HotWaterFlowingFluidBlock(FluidRegistry.BOILING_WATER_STILL, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> BOILING_WATER_TYPE = FLUID_TYPES.register("boiling_water", () -> new TeaFluidType(FluidType.Properties.create().temperature(373)).color(0xFF4989E3));
    public static final BaseFlowingFluid.Properties BOILING_WATER_PROPERTIES = new BaseFlowingFluid.Properties(
            BOILING_WATER_TYPE, BOILING_WATER_STILL, BOILING_WATER_FLOW)
            .bucket(BOILING_WATER_BUCKET)
            .block(FluidRegistry.BOILING_WATER)
            .explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> HOT_WATER_80_STILL = FLUIDS.register("hot_water_80", () -> new BaseFlowingFluid.Source(FluidRegistry.HOT_WATER_80_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> HOT_WATER_80_FLOW = FLUIDS.register("hot_water_80_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.HOT_WATER_80_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> HOT_WATER_80_BUCKET = ITEMS.register("hot_water_80_bucket", () -> new BucketItem(FluidRegistry.HOT_WATER_80_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, HotWaterFlowingFluidBlock> HOT_WATER_80 = BLOCKS.register("hot_water_80", () -> new HotWaterFlowingFluidBlock(FluidRegistry.HOT_WATER_80_STILL, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> HOT_WATER_80_TYPE = FLUID_TYPES.register("hot_water_80", () -> new TeaFluidType(FluidType.Properties.create().temperature(353)).color(0xFF4989E3));
    public static final BaseFlowingFluid.Properties HOT_WATER_80_PROPERTIES = new BaseFlowingFluid.Properties(
            HOT_WATER_80_TYPE, HOT_WATER_80_STILL, HOT_WATER_80_FLOW).bucket(HOT_WATER_80_BUCKET)
            .block(FluidRegistry.HOT_WATER_80).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> HOT_WATER_60_STILL = FLUIDS.register("hot_water_60", () -> new BaseFlowingFluid.Source(FluidRegistry.HOT_WATER_60_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> HOT_WATER_60_FLOW = FLUIDS.register("hot_water_60_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.HOT_WATER_60_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> HOT_WATER_60_BUCKET = ITEMS.register("hot_water_60_bucket", () -> new BucketItem(FluidRegistry.HOT_WATER_60_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, HotWaterFlowingFluidBlock> HOT_WATER_60 = BLOCKS.register("hot_water_60", () -> new HotWaterFlowingFluidBlock(FluidRegistry.HOT_WATER_60_STILL, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> HOT_WATER_60_TYPE = FLUID_TYPES.register("hot_water_60", () -> new TeaFluidType(FluidType.Properties.create().temperature(333)).color(0xFF4989E3));
    public static final BaseFlowingFluid.Properties HOT_WATER_60_PROPERTIES = new BaseFlowingFluid.Properties(
            HOT_WATER_60_TYPE,
            HOT_WATER_60_STILL, HOT_WATER_60_FLOW).bucket(HOT_WATER_60_BUCKET).block(FluidRegistry.HOT_WATER_60).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> WARM_WATER_STILL = FLUIDS.register("warm_water", () -> new BaseFlowingFluid.Source(FluidRegistry.WARM_WATER_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> WARM_WATER_FLOW = FLUIDS.register("warm_water_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.WARM_WATER_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> WARM_WATER_BUCKET = ITEMS.register("warm_water_bucket", () -> new BucketItem(FluidRegistry.WARM_WATER_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, HotWaterFlowingFluidBlock> WARM_WATER = BLOCKS.register("warm_water", () -> new HotWaterFlowingFluidBlock(FluidRegistry.WARM_WATER_STILL, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> WARM_WATER_TYPE = FLUID_TYPES.register("warm_water", () -> new TeaFluidType(FluidType.Properties.create().temperature(308)).color(0xFF4989E3));
    public static final BaseFlowingFluid.Properties WARM_WATER_PROPERTIES = new BaseFlowingFluid.Properties(
            WARM_WATER_TYPE,
            WARM_WATER_STILL, WARM_WATER_FLOW).bucket(WARM_WATER_BUCKET).block(FluidRegistry.WARM_WATER).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> SUGARY_WATER_STILL = FLUIDS.register("sugary_water", () -> new BaseFlowingFluid.Source(FluidRegistry.SUGARY_WATER_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> SUGARY_WATER_FLOW = FLUIDS.register("sugary_water_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.SUGARY_WATER_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> SUGARY_WATER_BUCKET = ITEMS.register("sugary_water_bucket", () -> new BucketItem(FluidRegistry.SUGARY_WATER_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> SUGARY_WATER = BLOCKS.register("sugary_water", () -> new LiquidBlock(FluidRegistry.SUGARY_WATER_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> SUGARY_WATER_TYPE = FLUID_TYPES.register("sugary_water", () -> new TeaFluidType(FluidType.Properties.create()).color(0xFF5AB4E6));
    public static final BaseFlowingFluid.Properties SUGARY_WATER_PROPERTIES = new BaseFlowingFluid.Properties(
            SUGARY_WATER_TYPE,
            SUGARY_WATER_STILL, SUGARY_WATER_FLOW).bucket(SUGARY_WATER_BUCKET).block(FluidRegistry.SUGARY_WATER).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> WEAK_GREEN_TEA_STILL = FLUIDS.register("weak_green_tea", () -> new BaseFlowingFluid.Source(FluidRegistry.WEAK_GREEN_TEA_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> WEAK_GREEN_TEA_FLOW = FLUIDS.register("weak_green_tea_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.WEAK_GREEN_TEA_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> WEAK_GREEN_TEA_BUCKET = ITEMS.register("weak_green_tea_bucket", () -> new BucketItem(FluidRegistry.WEAK_GREEN_TEA_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> WEAK_GREEN_TEA = BLOCKS.register("weak_green_tea", () -> new LiquidBlock(FluidRegistry.WEAK_GREEN_TEA_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> WEAK_GREEN_TEA_TYPE = FLUID_TYPES.register("weak_green_tea", () -> new TeaFluidType(FluidType.Properties.create()).color(0x7FAAB919));
    public static final BaseFlowingFluid.Properties WEAK_GREEN_TEA_PROPERTIES = new BaseFlowingFluid.Properties(
            WEAK_GREEN_TEA_TYPE,
            WEAK_GREEN_TEA_STILL, WEAK_GREEN_TEA_FLOW).bucket(WEAK_GREEN_TEA_BUCKET).block(FluidRegistry.WEAK_GREEN_TEA).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> WEAK_BLACK_TEA_STILL = FLUIDS.register("weak_black_tea", () -> new BaseFlowingFluid.Source(FluidRegistry.WEAK_BLACK_TEA_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> WEAK_BLACK_TEA_FLOW = FLUIDS.register("weak_black_tea_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.WEAK_BLACK_TEA_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> WEAK_BLACK_TEA_BUCKET = ITEMS.register("weak_black_tea_bucket", () -> new BucketItem(FluidRegistry.WEAK_BLACK_TEA_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> WEAK_BLACK_TEA = BLOCKS.register("weak_black_tea", () -> new LiquidBlock(FluidRegistry.WEAK_BLACK_TEA_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> WEAK_BLACK_TEA_TYPE = FLUID_TYPES.register("weak_black_tea", () -> new TeaFluidType(FluidType.Properties.create()).color(0x7FCD511E));
    public static final BaseFlowingFluid.Properties WEAK_BLACK_TEA_PROPERTIES = new BaseFlowingFluid.Properties(
            WEAK_BLACK_TEA_TYPE,
            WEAK_BLACK_TEA_STILL, WEAK_BLACK_TEA_FLOW).bucket(WEAK_BLACK_TEA_BUCKET).block(FluidRegistry.WEAK_BLACK_TEA).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> WEAK_WHITE_TEA_STILL = FLUIDS.register("weak_white_tea", () -> new BaseFlowingFluid.Source(FluidRegistry.WEAK_WHITE_TEA_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> WEAK_WHITE_TEA_FLOW = FLUIDS.register("weak_white_tea_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.WEAK_WHITE_TEA_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> WEAK_WHITE_TEA_BUCKET = ITEMS.register("weak_white_tea_bucket", () -> new BucketItem(FluidRegistry.WEAK_WHITE_TEA_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> WEAK_WHITE_TEA = BLOCKS.register("weak_white_tea", () -> new LiquidBlock(FluidRegistry.WEAK_WHITE_TEA_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> WEAK_WHITE_TEA_TYPE = FLUID_TYPES.register("weak_white_tea", () -> new TeaFluidType(FluidType.Properties.create()).color(0x7FB4AA64));
    public static final BaseFlowingFluid.Properties WEAK_WHITE_TEA_PROPERTIES = new BaseFlowingFluid.Properties(
            WEAK_WHITE_TEA_TYPE,
            WEAK_WHITE_TEA_STILL, WEAK_WHITE_TEA_FLOW).bucket(WEAK_WHITE_TEA_BUCKET).block(FluidRegistry.WEAK_WHITE_TEA).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> GREEN_TEA_STILL = FLUIDS.register("green_tea", () -> new BaseFlowingFluid.Source(FluidRegistry.GREEN_TEA_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> GREEN_TEA_FLOW = FLUIDS.register("green_tea_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.GREEN_TEA_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> GREEN_TEA_BUCKET = ITEMS.register("green_tea_bucket", () -> new BucketItem(FluidRegistry.GREEN_TEA_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> GREEN_TEA = BLOCKS.register("green_tea", () -> new LiquidBlock(FluidRegistry.GREEN_TEA_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> GREEN_TEA_TYPE = FLUID_TYPES.register("green_tea", () -> new TeaFluidType(FluidType.Properties.create()).color(0xBFAAB919));
    public static final BaseFlowingFluid.Properties GREEN_TEA_PROPERTIES = new BaseFlowingFluid.Properties(
            GREEN_TEA_TYPE,
            GREEN_TEA_STILL, GREEN_TEA_FLOW).bucket(GREEN_TEA_BUCKET).block(FluidRegistry.GREEN_TEA).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> BLACK_TEA_STILL = FLUIDS.register("black_tea", () -> new BaseFlowingFluid.Source(FluidRegistry.BLACK_TEA_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> BLACK_TEA_FLOW = FLUIDS.register("black_tea_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.BLACK_TEA_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> BLACK_TEA_BUCKET = ITEMS.register("black_tea_bucket", () -> new BucketItem(FluidRegistry.BLACK_TEA_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> BLACK_TEA = BLOCKS.register("black_tea", () -> new LiquidBlock(FluidRegistry.BLACK_TEA_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> BLACK_TEA_TYPE = FLUID_TYPES.register("black_tea", () -> new TeaFluidType(FluidType.Properties.create()).color(0xBFCD511E));
    public static final BaseFlowingFluid.Properties BLACK_TEA_PROPERTIES = new BaseFlowingFluid.Properties(
            BLACK_TEA_TYPE,
            BLACK_TEA_STILL, BLACK_TEA_FLOW).bucket(BLACK_TEA_BUCKET).block(FluidRegistry.BLACK_TEA).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> WHITE_TEA_STILL = FLUIDS.register("white_tea", () -> new BaseFlowingFluid.Source(FluidRegistry.WHITE_TEA_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> WHITE_TEA_FLOW = FLUIDS.register("white_tea_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.WHITE_TEA_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> WHITE_TEA_BUCKET = ITEMS.register("white_tea_bucket", () -> new BucketItem(FluidRegistry.WHITE_TEA_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> WHITE_TEA = BLOCKS.register("white_tea", () -> new LiquidBlock(FluidRegistry.WHITE_TEA_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> WHITE_TEA_TYPE = FLUID_TYPES.register("white_tea", () -> new TeaFluidType(FluidType.Properties.create()).color(0xBFB4AA64));
    public static final BaseFlowingFluid.Properties WHITE_TEA_PROPERTIES = new BaseFlowingFluid.Properties(
            WHITE_TEA_TYPE,
            WHITE_TEA_STILL, WHITE_TEA_FLOW).bucket(WHITE_TEA_BUCKET).block(FluidRegistry.WHITE_TEA).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> STRONG_GREEN_TEA_STILL = FLUIDS.register("strong_green_tea", () -> new BaseFlowingFluid.Source(FluidRegistry.STRONG_GREEN_TEA_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> STRONG_GREEN_TEA_FLOW = FLUIDS.register("strong_green_tea_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.STRONG_GREEN_TEA_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> STRONG_GREEN_TEA_BUCKET = ITEMS.register("strong_green_tea_bucket", () -> new BucketItem(FluidRegistry.STRONG_GREEN_TEA_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> STRONG_GREEN_TEA = BLOCKS.register("strong_green_tea", () -> new LiquidBlock(FluidRegistry.STRONG_GREEN_TEA_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> STRONG_GREEN_TEA_TYPE = FLUID_TYPES.register("strong_green_tea", () -> new TeaFluidType(FluidType.Properties.create()).color(0xFFAAB919));
    public static final BaseFlowingFluid.Properties STRONG_GREEN_TEA_PROPERTIES = new BaseFlowingFluid.Properties(
            STRONG_GREEN_TEA_TYPE,
            STRONG_GREEN_TEA_STILL, STRONG_GREEN_TEA_FLOW).bucket(STRONG_GREEN_TEA_BUCKET).block(FluidRegistry.STRONG_GREEN_TEA).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> STRONG_BLACK_TEA_STILL = FLUIDS.register("strong_black_tea", () -> new BaseFlowingFluid.Source(FluidRegistry.STRONG_BLACK_TEA_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> STRONG_BLACK_TEA_FLOW = FLUIDS.register("strong_black_tea_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.STRONG_BLACK_TEA_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> STRONG_BLACK_TEA_BUCKET = ITEMS.register("strong_black_tea_bucket", () -> new BucketItem(FluidRegistry.STRONG_BLACK_TEA_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> STRONG_BLACK_TEA = BLOCKS.register("strong_black_tea", () -> new LiquidBlock(FluidRegistry.STRONG_BLACK_TEA_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> STRONG_BLACK_TEA_TYPE = FLUID_TYPES.register("strong_black_tea", () -> new TeaFluidType(FluidType.Properties.create()).color(0xFFCD511E));
    public static final BaseFlowingFluid.Properties STRONG_BLACK_TEA_PROPERTIES = new BaseFlowingFluid.Properties(
            STRONG_BLACK_TEA_TYPE,
            STRONG_BLACK_TEA_STILL, STRONG_BLACK_TEA_FLOW).bucket(STRONG_BLACK_TEA_BUCKET).block(FluidRegistry.STRONG_BLACK_TEA).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> STRONG_WHITE_TEA_STILL = FLUIDS.register("strong_white_tea", () -> new BaseFlowingFluid.Source(FluidRegistry.STRONG_WHITE_TEA_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> STRONG_WHITE_TEA_FLOW = FLUIDS.register("strong_white_tea_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.STRONG_WHITE_TEA_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> STRONG_WHITE_TEA_BUCKET = ITEMS.register("strong_white_tea_bucket", () -> new BucketItem(FluidRegistry.STRONG_WHITE_TEA_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> STRONG_WHITE_TEA = BLOCKS.register("strong_white_tea", () -> new LiquidBlock(FluidRegistry.STRONG_WHITE_TEA_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> STRONG_WHITE_TEA_TYPE = FLUID_TYPES.register("strong_white_tea", () -> new TeaFluidType(FluidType.Properties.create()).color(0xFFB4AA64));
    public static final BaseFlowingFluid.Properties STRONG_WHITE_TEA_PROPERTIES = new BaseFlowingFluid.Properties(
            STRONG_WHITE_TEA_TYPE,
            STRONG_WHITE_TEA_STILL, STRONG_WHITE_TEA_FLOW).bucket(STRONG_WHITE_TEA_BUCKET).block(FluidRegistry.STRONG_WHITE_TEA).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> APPLE_JUICE_STILL = FLUIDS.register("apple_juice", () -> new BaseFlowingFluid.Source(FluidRegistry.APPLE_JUICE_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> APPLE_JUICE_FLOW = FLUIDS.register("apple_juice_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.APPLE_JUICE_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> APPLE_JUICE_BUCKET = ITEMS.register("apple_juice_bucket", () -> new BucketItem(FluidRegistry.APPLE_JUICE_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> APPLE_JUICE = BLOCKS.register("apple_juice", () -> new LiquidBlock(FluidRegistry.APPLE_JUICE_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> APPLE_JUICE_TYPE = FLUID_TYPES.register("apple_juice", () -> new TeaFluidType(FluidType.Properties.create()).color(0xffae6642));
    public static final BaseFlowingFluid.Properties APPLE_JUICE_PROPERTIES = new BaseFlowingFluid.Properties(
            APPLE_JUICE_TYPE,
            APPLE_JUICE_STILL, APPLE_JUICE_FLOW).bucket(APPLE_JUICE_BUCKET).block(FluidRegistry.APPLE_JUICE).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> SUGAR_CANE_JUICE_STILL = FLUIDS.register("sugar_cane_juice", () -> new BaseFlowingFluid.Source(FluidRegistry.SUGAR_CANE_JUICE_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> SUGAR_CANE_JUICE_FLOW = FLUIDS.register("sugar_cane_juice_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.SUGAR_CANE_JUICE_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> SUGAR_CANE_JUICE_BUCKET = ITEMS.register("sugar_cane_juice_bucket", () -> new BucketItem(FluidRegistry.SUGAR_CANE_JUICE_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> SUGAR_CANE_JUICE = BLOCKS.register("sugar_cane_juice", () -> new LiquidBlock(FluidRegistry.SUGAR_CANE_JUICE_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> SUGAR_CANE_JUICE_TYPE = FLUID_TYPES.register("sugar_cane_juice", () -> new TeaFluidType(FluidType.Properties.create()).color(0xfff0dc70));
    public static final BaseFlowingFluid.Properties SUGAR_CANE_JUICE_PROPERTIES = new BaseFlowingFluid.Properties(
            SUGAR_CANE_JUICE_TYPE,
            SUGAR_CANE_JUICE_STILL, SUGAR_CANE_JUICE_FLOW).bucket(SUGAR_CANE_JUICE_BUCKET).block(FluidRegistry.SUGAR_CANE_JUICE).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> CARROT_JUICE_STILL = FLUIDS.register("carrot_juice", () -> new BaseFlowingFluid.Source(FluidRegistry.CARROT_JUICE_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> CARROT_JUICE_FLOW = FLUIDS.register("carrot_juice_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.CARROT_JUICE_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> CARROT_JUICE_BUCKET = ITEMS.register("carrot_juice_bucket", () -> new BucketItem(FluidRegistry.CARROT_JUICE_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> CARROT_JUICE = BLOCKS.register("carrot_juice", () -> new LiquidBlock(FluidRegistry.CARROT_JUICE_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> CARROT_JUICE_TYPE = FLUID_TYPES.register("carrot_juice", () -> new TeaFluidType(FluidType.Properties.create()).color(0xfff47920));
    public static final BaseFlowingFluid.Properties CARROT_JUICE_PROPERTIES = new BaseFlowingFluid.Properties(
            CARROT_JUICE_TYPE,
            CARROT_JUICE_STILL, CARROT_JUICE_FLOW).bucket(CARROT_JUICE_BUCKET).block(FluidRegistry.CARROT_JUICE).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> GRAPE_JUICE_STILL = FLUIDS.register("grape_juice", () -> new BaseFlowingFluid.Source(FluidRegistry.GRAPE_JUICE_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> GRAPE_JUICE_FLOW = FLUIDS.register("grape_juice_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.GRAPE_JUICE_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> GRAPE_JUICE_BUCKET = ITEMS.register("grape_juice_bucket", () -> new BucketItem(FluidRegistry.GRAPE_JUICE_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> GRAPE_JUICE = BLOCKS.register("grape_juice", () -> new LiquidBlock(FluidRegistry.GRAPE_JUICE_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> GRAPE_JUICE_TYPE = FLUID_TYPES.register("grape_juice", () -> new TeaFluidType(FluidType.Properties.create()).color(0xff6950a1));
    public static final BaseFlowingFluid.Properties GRAPE_JUICE_PROPERTIES = new BaseFlowingFluid.Properties(
            GRAPE_JUICE_TYPE,
            GRAPE_JUICE_STILL, GRAPE_JUICE_FLOW).bucket(GRAPE_JUICE_BUCKET).block(FluidRegistry.GRAPE_JUICE).explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> CUCUMBER_JUICE_STILL = FLUIDS.register("cucumber_juice", () -> new BaseFlowingFluid.Source(FluidRegistry.CUCUMBER_JUICE_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> CUCUMBER_JUICE_FLOW = FLUIDS.register("cucumber_juice_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegistry.CUCUMBER_JUICE_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> CUCUMBER_JUICE_BUCKET = ITEMS.register("cucumber_juice_bucket", () -> new BucketItem(FluidRegistry.CUCUMBER_JUICE_STILL.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> CUCUMBER_JUICE = BLOCKS.register("cucumber_juice", () -> new LiquidBlock(FluidRegistry.CUCUMBER_JUICE_STILL.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> CUCUMBER_JUICE_TYPE = FLUID_TYPES.register("cucumber_juice", () -> new TeaFluidType(FluidType.Properties.create()).color(0xffcbc547));
    public static final BaseFlowingFluid.Properties CUCUMBER_JUICE_PROPERTIES = new BaseFlowingFluid.Properties(
            CUCUMBER_JUICE_TYPE,
            CUCUMBER_JUICE_STILL, CUCUMBER_JUICE_FLOW).bucket(CUCUMBER_JUICE_BUCKET).block(FluidRegistry.CUCUMBER_JUICE).explosionResistance(100F);
}