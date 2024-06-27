package cloud.lemonslice.teastory.item;


import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import xueluoanping.teastory.ModContents;


public class RiceSeedlingItem extends Item
{
    public RiceSeedlingItem(Item.Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        HitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);
        if (raytraceresult.getType() == HitResult.Type.MISS)
        {
            return InteractionResultHolder.pass(itemstack);
        }
        else
        {
            if (raytraceresult.getType() == HitResult.Type.BLOCK)
            {
                BlockHitResult blockraytraceresult = (BlockHitResult) raytraceresult;
                BlockPos blockpos = ((BlockHitResult) raytraceresult).getBlockPos();
                Direction direction = blockraytraceresult.getDirection();
                if (!worldIn.mayInteract(playerIn, blockpos) || !playerIn.mayUseItemAt(blockpos.relative(direction), direction, itemstack))
                {
                    return InteractionResultHolder.fail(itemstack);
                }

                BlockPos blockpos1 = blockpos.above();
                BlockState blockstate = worldIn.getBlockState(blockpos);
                FluidState ifluidstate = worldIn.getFluidState(blockpos);
                if ((ifluidstate.getType() == Fluids.WATER || blockstate.getBlock() == ModContents.RiceSeedlingBlock.get()) && worldIn.isEmptyBlock(blockpos1))
                {
                    net.minecraftforge.common.util.BlockSnapshot blocksnapshot = net.minecraftforge.common.util.BlockSnapshot.create(worldIn.dimension(), worldIn, blockpos1);
                    worldIn.setBlock(blockpos1, ModContents.ricePlant.get().defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                    if (net.minecraftforge.event.ForgeEventFactory.onBlockPlace(playerIn, blocksnapshot, Direction.UP))
                    {
                        blocksnapshot.restore(true, false);
                        return InteractionResultHolder.fail(itemstack);
                    }

                    if (playerIn instanceof ServerPlayer)
                    {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) playerIn, blockpos1, itemstack);
                    }

                    if (!playerIn.isCreative())
                    {
                        itemstack.shrink(1);
                    }

                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    worldIn.playSound(playerIn, blockpos, ModContents.ricePlant.get().getSoundType(ModContents.ricePlant.get().defaultBlockState(),worldIn, blockpos, playerIn).getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResultHolder.success(itemstack);
                }
            }

            return InteractionResultHolder.fail(itemstack);
        }
    }


}
