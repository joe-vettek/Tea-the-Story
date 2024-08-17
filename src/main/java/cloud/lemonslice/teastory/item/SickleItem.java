package cloud.lemonslice.teastory.item;


import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collections;

public class SickleItem extends DiggerItem {
    public SickleItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(attackDamageIn, attackSpeedIn, tier, BlockTags.MINEABLE_WITH_SHOVEL, builder);
    }


    // onBlockDestroyed
    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        stack.hurtAndBreak(1, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        harvestCrops(worldIn, pos, 0);
        return super.mineBlock(stack, worldIn, state, pos, entityLiving);
    }


    private static void harvestCrops(Level worldIn, BlockPos pos, int time) {
        Block block = worldIn.getBlockState(pos).getBlock();
        if (block instanceof BonemealableBlock) {
            if (!((BonemealableBlock) block).isValidBonemealTarget(worldIn, pos, worldIn.getBlockState(pos), worldIn.isClientSide())) {
                worldIn.destroyBlock(pos, true);
                if (time < 8) {
                    harvestCrops(worldIn, pos.east(), time + 1);
                    harvestCrops(worldIn, pos.north(), time + 1);
                    harvestCrops(worldIn, pos.west(), time + 1);
                    harvestCrops(worldIn, pos.south(), time + 1);
                }
            }
        }
    }
}
