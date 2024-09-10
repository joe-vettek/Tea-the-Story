package com.teamtea.teastory.handler.event;


import com.teamtea.teastory.block.crops.MelonVineBlock;
import com.teamtea.teastory.block.crops.TeaPlantBlock;
import com.teamtea.teastory.config.ServerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import com.teamtea.teastory.BlockRegister;

import java.util.List;

public final class AgricultureEventHandler {
    public static void boneMealUsingLimit(BonemealEvent event) {
        if (!ServerConfig.Agriculture.canUseBoneMeal.get()) {
            if (event.getState().getBlock() instanceof TeaPlantBlock) {
                if (event.getState().getValue(TeaPlantBlock.AGE) == 7 || event.getState().getValue(TeaPlantBlock.AGE) == 6) {
                    // event.s(Event.Result.DEFAULT);
                } else {
                    event.setCanceled(true);
                }
            } else if (event.getState().getBlock() instanceof BonemealableBlock && event.getState().is(BlockTags.CROPS)) {
                event.setCanceled(true);
            } else {
                // event.setResult(Event.Result.DEFAULT);
            }
        }
    }

    public static void onAqueductShovelUsing(BlockEvent.BlockToolModificationEvent event) {

    }

    @SuppressWarnings("deprecation")
    public static void plantMelon(PlayerInteractEvent.RightClickBlock event) {
        BlockPos posToPlace = event.getPos().relative(event.getHitVec().getDirection());
        BlockState toPlace = event.getLevel().getBlockState(posToPlace);
        Block farmland = event.getLevel().getBlockState(posToPlace.below()).getBlock();

        ItemStack melon_seeds = event.getEntity().getItemInHand(event.getHand());
        if (farmland instanceof FarmBlock) {
            if (melon_seeds.getItem() == Items.MELON_SEEDS && toPlace.isAir()) {
                event.getLevel().setBlockAndUpdate(posToPlace, BlockRegister.WATERMELON_VINE.get().defaultBlockState());
                event.getLevel().playSound(null, posToPlace, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!event.getEntity().isCreative()) {
                    melon_seeds.shrink(1);
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }

    public static void cutMelon(PlayerInteractEvent.RightClickBlock event) {
        BlockState melon = event.getLevel().getBlockState(event.getPos());
        if (melon.getBlock() == Blocks.MELON && event.getEntity().getItemInHand(event.getHand()).getItem() instanceof SwordItem) {
            event.getLevel().setBlockAndUpdate(event.getPos(), Blocks.AIR.defaultBlockState());
            event.getEntity().getItemInHand(event.getHand()).hurtAndBreak(1, event.getEntity(), LivingEntity.getSlotForHand(event.getHand()));
            event.getLevel().playSound(null, event.getPos().above(), SoundEvents.STEM_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!event.getLevel().isClientSide()) {
                List<ItemStack> list = melon.getDrops(new LootParams.Builder((ServerLevel) event.getLevel()).withParameter(LootContextParams.ORIGIN, event.getPos().getCenter()).withParameter(LootContextParams.TOOL, event.getEntity().getItemInHand(event.getHand())));
                for (ItemStack drop : list) {
                    Block.popResource(event.getLevel(), event.getPos(), drop);
                }
            }
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }

    public static void stopTramplingMelonField(BlockEvent.FarmlandTrampleEvent event) {
        if (event.getLevel().getBlockState(event.getPos().above()).getBlock() instanceof MelonVineBlock) {
            event.setCanceled(true);
        }
    }
}
