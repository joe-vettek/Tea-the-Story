package cloud.lemonslice.teastory.item;


import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import xueluoanping.teastory.registry.FluidRegistry;

import java.util.List;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

public class TeapotItem extends BlockItem
{
    private final int capacity;
    private final boolean canFillWater;

    public TeapotItem(Block block, Item.Properties properties, int capacity, boolean fillWater)
    {
        super(block, properties);
        this.capacity = capacity;
        this.canFillWater = fillWater;
    }

    @Override
    public @org.jetbrains.annotations.Nullable ICapabilityProvider initCapabilities(ItemStack stack, @org.jetbrains.annotations.Nullable CompoundTag nbt) {
        return new FluidHandlerItemStack(stack, capacity);
    }

    // @Override
    // public InteractionResult onItemUse(ItemUseContext context)
    // {
    //     return this.onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand()).getType() != InteractionResult.SUCCESS ? this.tryPlace(new BlockItemUseContext(context)) : InteractionResult.SUCCESS;
    // }
    //
    //
    //

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level worldIn = pContext.getLevel();
        if (canFillWater) {
            Player playerIn = pContext.getPlayer();
            InteractionHand handIn = pContext.getHand();

            ItemStack itemStack = playerIn.getItemInHand(handIn);
            HitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);


            if (raytraceresult.getType() == HitResult.Type.BLOCK
                    && raytraceresult instanceof BlockHitResult blockHitResult) {
                BlockPos blockpos = blockHitResult.getBlockPos();
                if (worldIn.getFluidState(blockpos).isSource()) {
                    var resultItem = FluidUtil.tryPickUpFluid(itemStack, playerIn, worldIn, blockpos, blockHitResult.getDirection()).getResult();
                    if(!resultItem.isEmpty()) {

                        playerIn.awardStat(Stats.ITEM_USED.get(this));
                        SoundEvent soundevent = SoundEvents.BOTTLE_FILL;
                        playerIn.playSound(soundevent, 1.0F, 1.0F);

                        playerIn.setItemInHand(handIn, resultItem);
                        return InteractionResult.sidedSuccess(worldIn.isClientSide());
                    }else {
                        return InteractionResult.CONSUME_PARTIAL;
                    }

                }
                // if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos, blockHitResult.getDirection(), itemStack)) {
                //     BlockState state = worldIn.getBlockState(blockpos);
                //     if (state.getBlock() instanceof LiquidBlock liquidBlock) {
                //         Fluid fluid = liquidBlock.fluid.getSource();
                //         if (fluid != Fluids.EMPTY && fluid.is(FluidTags.WATER)) {
                //             // liquidBlock.pickupBlock(playerIn, worldIn, blockpos, state);
                //
                //             playerIn.awardStat(Stats.ITEM_USED.get(this));
                //             SoundEvent soundevent = SoundEvents.BOTTLE_FILL;
                //             playerIn.playSound(soundevent, 1.0F, 1.0F);
                //
                //             if (!playerIn.isCreative()) {
                //                 // ItemStack stack = new ItemStack(this);
                //                 // stack.set(ModCapabilities.SIMPLE_FLUID, SimpleFluidContent.copyOf(new FluidStack(fluid, FluidType.BUCKET_VOLUME)));
                //                 FluidUtil.tryPickUpFluid(itemStack,playerIn,worldIn,blockpos,blockHitResult.getDirection());
                //                 // ItemHandlerHelper.giveItemToPlayer(playerIn, stack);
                //                 // itemStack.shrink(1);
                //             }
                //             FluidUtil.tryPickUpFluid(itemStack,playerIn,worldIn,blockpos,blockHitResult.getDirection());
                //
                //             return InteractionResult.SUCCESS;
                //         }
                //     }
                //
                // }
                //
            }
        }
        return super.useOn(pContext);
    }


    public void fillItemGroup(CreativeModeTab.Output group)
    {
        // if (group == TeaStory.GROUP_DRINK)
        {
            // for (Fluid fluid : FluidTags.getCollection().getTagByID(new ResourceLocation("teastory:drink")).getAllElements())
            for (var fluid : FluidRegistry.FLUIDS.getEntries())
            {
                if(fluid.get() instanceof ForgeFlowingFluid.Source) {
                    ItemStack itemStack = new ItemStack(this);
                    CompoundTag fluidTag = new CompoundTag();
                    new FluidStack(fluid.get(), capacity).writeToNBT(fluidTag);
                    itemStack.getOrCreateTag().put(FLUID_NBT_KEY, fluidTag);
                    group.accept(itemStack);
                }
            }
            // ItemStack itemStack = new ItemStack(this);
            // CompoundNBT fluidTag = new CompoundNBT();
            // new FluidStack(FluidRegistry.BOILING_WATER_STILL.get(), capacity).writeToNBT(fluidTag);
            // itemStack.getOrCreateTag().put(FLUID_NBT_KEY, fluidTag);
            // items.add(itemStack);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (stack.getOrCreateTag().contains(FLUID_NBT_KEY))
        {
            FluidUtil.getFluidHandler(stack).ifPresent(f ->
                    tooltip.add(((MutableComponent) (f.getFluidInTank(0).getDisplayName())).append(String.format(": %d / %dmB", f.getFluidInTank(0).getAmount(), capacity)).withStyle(ChatFormatting.GRAY)));
        }
    }


}
