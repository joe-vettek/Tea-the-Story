package cloud.lemonslice.teastory.item;


import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import xueluoanping.teastory.tag.NormalTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import xueluoanping.teastory.FluidRegistry;
import xueluoanping.teastory.ModCapabilities;
import xueluoanping.teastory.item.FluidContainerItem;

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
    public InteractionResult place(BlockPlaceContext pContext) {
        return super.place(pContext);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (canFillWater) {
            ItemStack itemStack = playerIn.getItemInHand(handIn);
            HitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);

            if (raytraceresult.getType() == HitResult.Type.MISS) {
                return new InteractionResultHolder<>(InteractionResult.PASS, itemStack);
            } else if (raytraceresult.getType() != HitResult.Type.BLOCK) {
                return new InteractionResultHolder<>(InteractionResult.PASS, itemStack);
            } else {
                BlockHitResult blockraytraceresult = (BlockHitResult) raytraceresult;
                BlockPos blockpos = blockraytraceresult.getBlockPos();
                if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos, blockraytraceresult.getDirection(), itemStack)) {
                    BlockState blockstate1 = worldIn.getBlockState(blockpos);
                    if (blockstate1.getBlock() instanceof LiquidBlock) {
                        Fluid fluid = ((LiquidBlock) blockstate1.getBlock()).fluid.getSource();
                        if (fluid != Fluids.EMPTY && fluid.is(FluidTags.WATER)) {
                            ((LiquidBlock) blockstate1.getBlock()).pickupBlock(playerIn, worldIn, blockpos, blockstate1);
                            playerIn.awardStat(Stats.ITEM_USED.get(this));

                            SoundEvent soundevent = SoundEvents.BOTTLE_FILL;
                            playerIn.playSound(soundevent, 1.0F, 1.0F);

                            if (!playerIn.isCreative()) {
                                ItemStack itemStack1 = new ItemStack(this);
                                itemStack1.set(ModCapabilities.SIMPLE_FLUID, SimpleFluidContent.copyOf(new FluidStack(fluid, capacity)));
                                ItemHandlerHelper.giveItemToPlayer(playerIn, itemStack1);

                                itemStack.shrink(1);
                            }

                            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
                        }
                    }
                    return new InteractionResultHolder<>(InteractionResult.FAIL, itemStack);
                } else {
                    return new InteractionResultHolder<>(InteractionResult.FAIL, itemStack);
                }
            }
        } else return super.use(worldIn, playerIn, handIn);
    }


    public void fillItemGroup(CreativeModeTab.Output group) {
        // for (Fluid fluid : FluidTags.getCollection().getTagByID(new ResourceLocation("teastory:drink")).getAllElements())
        for (var fluid : FluidRegistry.FLUIDS.getEntries()) {
            if (fluid.is(NormalTags.Fluids.DRINK))
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
                tooltip.add(((MutableComponent) (f.getFluidInTank(0).getHoverName())).append(String.format(": %d / %dmB", f.getFluidInTank(0).getAmount(), capacity)).withStyle(ChatFormatting.GRAY)));
    }


    @Override
    public int getCapacity() {
        return capacity;
    }
}
