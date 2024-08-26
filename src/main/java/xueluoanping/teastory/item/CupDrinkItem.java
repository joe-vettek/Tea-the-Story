package xueluoanping.teastory.item;


import cloud.lemonslice.teastory.recipe.drink.DrinkEffectManager;
import cloud.lemonslice.teastory.tag.NormalTags;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import xueluoanping.teastory.FluidRegistry;
import xueluoanping.teastory.ModCapabilities;

import java.util.List;
import java.util.function.BiConsumer;


public class CupDrinkItem extends Item implements FluidContainerItem {

    public int capacity;

    public CupDrinkItem(int capacity, Properties name) {
        super(name);
        this.capacity = capacity;
    }


    @Override
    public int getCapacity() {
        return this.capacity;
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        FluidUtil.getFluidHandler(stack).ifPresent(f ->
                tooltip.add(MutableComponent.create((f.getFluidInTank(0).getHoverName()).getContents()).append(String.format(": %d / %dmB", f.getFluidInTank(0).getAmount(), capacity)).withStyle(ChatFormatting.GRAY)));
    }


    // @Override
    public void fillItemGroup(CreativeModeTab.Output group) {
        // for (Fluid fluid : FluidTags.getCollection().getTagByID(new ResourceLocation("teastory:drink")).getAllElements())
        for (var fluid : FluidRegistry.FLUIDS.getEntries()) {
            if (fluid.is(NormalTags.Fluids.DRINK))
                if (fluid.get() instanceof BaseFlowingFluid.Source) {
                    ItemStack itemStack = new ItemStack(this);
                    itemStack.set(ModCapabilities.SIMPLE_FLUID, SimpleFluidContent.copyOf(new FluidStack(fluid.get(), capacity)));
                    group.accept(itemStack);
                }
        }
    }


    // getUseAction
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return canDrink(stack) ? UseAnim.DRINK : UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity pEntity) {
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
                    // TeaStory.logger(foodata.getSaturationLevel());
                    ((Player) entityLiving).getFoodData().setFoodLevel(foodata.getFoodLevel() + (int) (1.2F * this.capacity / 100));
                    ((Player) entityLiving).getFoodData().setSaturation(foodata.getSaturationLevel() + 0.4F);
                    // TeaStory.logger(foodata.getSaturationLevel());
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
        return FluidUtil.getFluidContained(stack).map(f -> f.getFluid().is(NormalTags.Fluids.DRINK)).orElse(false);
        // return true;
    }
}
