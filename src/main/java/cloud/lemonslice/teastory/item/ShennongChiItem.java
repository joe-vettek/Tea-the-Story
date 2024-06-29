package cloud.lemonslice.teastory.item;


import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class ShennongChiItem extends SwordItem {
    public static final Tier HEAVENLY_WEAPON = new HeavenlyItemTier();

    public ShennongChiItem(Item.Properties properties) {
        super(HEAVENLY_WEAPON, 3, -1.0F,properties);
    }

    // hasEffect
    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!worldIn.isClientSide()) {
            playerIn.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 0));
        }
        playerIn.getItemInHand(handIn).hurtAndBreak(1, playerIn, player -> player.broadcastBreakEvent(handIn));
        return InteractionResultHolder.consume(playerIn.getItemInHand(handIn));
    }


    public static class HeavenlyItemTier implements Tier {

        @Override
        public int getUses() {
            return 2048;
        }

        @Override
        public float getSpeed() {
            return 10.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 2.0F;
        }

        @Override
        public int getLevel() {
            return 0;
        }

        @Override
        public int getEnchantmentValue() {
            return 20;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }
    }
}
