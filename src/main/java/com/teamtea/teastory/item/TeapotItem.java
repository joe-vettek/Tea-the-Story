package com.teamtea.teastory.item;


import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.fluids.*;
import com.teamtea.teastory.tag.TeaTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import com.teamtea.teastory.registry.FluidRegister;
import com.teamtea.teastory.registry.ModCapabilities;

import java.util.List;


public class TeapotItem extends BlockItem implements FluidContainerItem {
    private final int capacity;
    private final boolean canFillWater;

    public TeapotItem(Block block, Item.Properties properties, int capacity, boolean fillWater) {
        super(block, properties);
        this.capacity = capacity;
        this.canFillWater = fillWater;
    }


    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level worldIn = pContext.getLevel();
        if (canFillWater && !worldIn.isClientSide()) {
            Player playerIn = pContext.getPlayer();
            InteractionHand handIn = pContext.getHand();

            ItemStack itemStack = playerIn.getItemInHand(handIn);
            HitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);


            if (raytraceresult.getType() == HitResult.Type.BLOCK
                    && raytraceresult instanceof BlockHitResult blockHitResult) {
                BlockPos blockpos = blockHitResult.getBlockPos();
                if (worldIn.getFluidState(blockpos).isSource()) {
                    var resultItem = FluidUtil.tryPickUpFluid(itemStack, playerIn, worldIn, blockpos, blockHitResult.getDirection()).getResult();
                    playerIn.setItemInHand(handIn, resultItem);
                    return InteractionResult.SUCCESS;
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


    public void fillItemGroup(CreativeModeTab.Output group) {
        // for (Fluid fluid : FluidTags.getCollection().getTagByID(new ResourceLocation("teastory:drink")).getAllElements())
        for (var fluid : FluidRegister.FLUIDS.getEntries()) {
            if (fluid.is(TeaTags.Fluids.DRINK))
                if (fluid.get() instanceof BaseFlowingFluid.Source) {
                    ItemStack itemStack = new ItemStack(this);
                    itemStack.set(ModCapabilities.SIMPLE_FLUID, SimpleFluidContent.copyOf(new FluidStack(fluid.get(), capacity)));
                    group.accept(itemStack);
                    group.accept(itemStack);
                }
        }
    }


    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, tooltipContext, tooltip, flagIn);
        FluidUtil.getFluidHandler(stack).ifPresent(f ->
                {
                    var fluid = f.getFluidInTank(0);
                    if (!fluid.isEmpty())
                        tooltip.add(((MutableComponent) (fluid.getHoverName())).append(String.format(": %d / %dmB", f.getFluidInTank(0).getAmount(), capacity)).withStyle(ChatFormatting.GRAY));
                }
        );
    }


    @Override
    public int getCapacity() {
        return capacity;
    }
}
