package cloud.lemonslice.teastory.item;


import cloud.lemonslice.teastory.recipe.drink.DrinkEffectManager;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import xueluoanping.teastory.FluidRegistry;
import xueluoanping.teastory.TeaStory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiConsumer;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

public class CupDrinkItem extends ItemFluidContainer {

    public CupDrinkItem(int capacity, Properties name) {
        super(name, capacity);
    }


    @Override
    public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @org.jetbrains.annotations.Nullable CompoundTag nbt) {
        return new FluidHandlerItemStack(stack, capacity) {
            @Nonnull
            @Override
            @SuppressWarnings("deprecation")
            public ItemStack getContainer() {
                return getFluid().isEmpty() ? new ItemStack(CupDrinkItem.this.getCraftingRemainingItem()) : this.container;
            }

            @Override
            public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
                // return stack.getFluid().is(DRINK);
                //     TODO:
                return true;
            }
        };
    }


    @Override
    public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (stack.getOrCreateTag().contains(FLUID_NBT_KEY)) {
            FluidUtil.getFluidHandler(stack).ifPresent(f ->
                    tooltip.add(MutableComponent.create((f.getFluidInTank(0).getDisplayName()).getContents()).append(String.format(": %d / %dmB", f.getFluidInTank(0).getAmount(), capacity)).withStyle(ChatFormatting.GRAY)));
        }
    }

    // todo:
    // @Override
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


    // getUseAction
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return canDrink(stack) ? UseAnim.DRINK : UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return canDrink(stack) ? 32 : 0;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        var stack = playerIn.getItemInHand(handIn);

        if (canDrink(stack)) {

            playerIn.startUsingItem(handIn);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
        } else {
            return new InteractionResultHolder<>(InteractionResult.PASS, playerIn.getItemInHand(handIn));
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        if (canDrink(stack)) {
            worldIn.playSound(null, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), entityLiving.getEatingSound(stack), SoundSource.NEUTRAL, 1.0F, 1.0F + (worldIn.getRandom().nextFloat() - worldIn.getRandom().nextFloat()) * 0.4F);
            FluidUtil.getFluidContained(stack).ifPresent(handler ->
            {
                BiConsumer<LivingEntity, Integer> action = DrinkEffectManager.getEffects(handler.getFluid());
                if (action != null) {
                    action.accept(entityLiving, handler.getAmount());
                } else if (entityLiving instanceof Player && handler.getFluid() != FluidRegistry.BOILING_WATER_STILL.get()) {
                    var foodata = ((Player) entityLiving).getFoodData();
                   TeaStory.logger(foodata.getSaturationLevel());
                    ((Player) entityLiving).getFoodData().setFoodLevel(foodata.getFoodLevel() + (int) (1.2F * this.capacity / 100));
                    ((Player) entityLiving).getFoodData().setSaturation(foodata.getSaturationLevel() + 0.4F);
                    TeaStory.logger(foodata.getSaturationLevel());
                }
            });
            if (entityLiving instanceof Player) {
                ItemHandlerHelper.giveItemToPlayer((Player) entityLiving, new ItemStack(this.getCraftingRemainingItem()));
            }
            stack.shrink(1);
        }
        return stack;
    }


    public static boolean canDrink(ItemStack stack) {
        // TODO:
        // if (stack.getOrCreateTag().contains(FLUID_NBT_KEY))
        // {
        //     return FluidUtil.getFluidContained(stack).map(f -> f.getFluid().is(DRINK)).orElse(false);
        // }
        // return false;
        return true;
    }
}
