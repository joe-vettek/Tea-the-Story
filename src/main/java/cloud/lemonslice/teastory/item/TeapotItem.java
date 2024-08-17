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
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import xueluoanping.teastory.FluidRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn)
    {
        if (canFillWater)
        {
            ItemStack itemStack = playerIn.getItemInHand(handIn);
            HitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);

            if (raytraceresult.getType() == HitResult.Type.MISS)
            {
                return new InteractionResultHolder<>(InteractionResult.PASS, itemStack);
            }
            else if (raytraceresult.getType() != HitResult.Type.BLOCK)
            {
                return new InteractionResultHolder<>(InteractionResult.PASS, itemStack);
            }
            else
            {
                BlockHitResult blockraytraceresult = (BlockHitResult) raytraceresult;
                BlockPos blockpos = blockraytraceresult.getBlockPos();
                if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos, blockraytraceresult.getDirection(), itemStack))
                {
                    BlockState blockstate1 = worldIn.getBlockState(blockpos);
                    if (blockstate1.getBlock() instanceof LiquidBlock)
                    {
                        Fluid fluid = ((LiquidBlock) blockstate1.getBlock()).getFluid();
                        if (fluid != Fluids.EMPTY && fluid.is(FluidTags.WATER))
                        {
                            ((LiquidBlock) blockstate1.getBlock()).pickupBlock(worldIn, blockpos, blockstate1);
                            playerIn.awardStat(Stats.ITEM_USED.get(this));

                            SoundEvent soundevent = SoundEvents.BOTTLE_FILL;
                            playerIn.playSound(soundevent, 1.0F, 1.0F);

                            if (!playerIn.isCreative())
                            {
                                ItemStack itemStack1 = new ItemStack(this);
                                CompoundTag fluidTag = new CompoundTag();
                                new FluidStack(fluid, capacity).writeToNBT(fluidTag);
                                itemStack1.getOrCreateTag().put(FLUID_NBT_KEY, fluidTag);
                                ItemHandlerHelper.giveItemToPlayer(playerIn, itemStack1);

                                itemStack.shrink(1);
                            }

                            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
                        }
                    }
                    return new InteractionResultHolder<>(InteractionResult.FAIL, itemStack);
                }
                else
                {
                    return new InteractionResultHolder<>(InteractionResult.FAIL, itemStack);
                }
            }
        }
        else return super.use(worldIn, playerIn, handIn);
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
