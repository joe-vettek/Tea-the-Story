package cloud.lemonslice.teastory.item;


import cloud.lemonslice.teastory.block.crops.AqueductBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import xueluoanping.teastory.ItemBlocks;
import cloud.lemonslice.teastory.block.crops.PaddyFieldBlock;

public class AqueductShovelItem extends ShovelItem {
    public AqueductShovelItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    // onItemUse
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = world.getBlockState(blockPos);
        Player playerEntity = context.getPlayer();
        if (blockState.is(Tags.Blocks.COBBLESTONE)) {
            if (!world.isClientSide()) {
                world.setBlock(blockPos, ((AqueductBlock) ItemBlocks.cobblestoneAqueduct.get()).getStateForPlacement(world, blockPos), 3);
                world.scheduleTick(blockPos, ItemBlocks.cobblestoneAqueduct.get(), Fluids.WATER.getTickDelay(world));
                if (playerEntity != null) {
                    context.getItemInHand().hurtAndBreak(1, playerEntity, player -> player.broadcastBreakEvent(context.getHand()));
                }
            }
            world.playSound(playerEntity, blockPos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        } else if (blockState.getBlock() == Blocks.MOSSY_COBBLESTONE) {
            if (!world.isClientSide()) {
                world.setBlock(blockPos, ((AqueductBlock) ItemBlocks.mossyCobblestoneAqueduct.get()).getStateForPlacement(world, blockPos), 3);
                world.scheduleTick(blockPos, ItemBlocks.mossyCobblestoneAqueduct.get(), Fluids.WATER.getTickDelay(world));
                if (playerEntity != null) {
                    context.getItemInHand().hurtAndBreak(1, playerEntity, player -> player.broadcastBreakEvent(context.getHand()));
                }
            }
            world.playSound(playerEntity, blockPos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        } else if (blockState.getBlock() instanceof FarmBlock) {
            if (!world.isClientSide()) {
                world.setBlock(blockPos, ((PaddyFieldBlock) ItemBlocks.paddyField.get()).getStateForPlacement(world, blockPos), 3);
                if (playerEntity != null) {
                    context.getItemInHand().hurtAndBreak(1, playerEntity, player -> player.broadcastBreakEvent(context.getHand()));
                }
            }
            world.playSound(playerEntity, blockPos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);

            return InteractionResult.SUCCESS;
        } else return super.useOn(context);
    }

}
