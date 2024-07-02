package cloud.lemonslice.teastory.item;

import cloud.lemonslice.silveroak.helper.BlockHelper;
import cloud.lemonslice.teastory.block.drink.DrinkMakerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.teastory.TileEntityTypeRegistry;


public class DrinkMakerItem extends BlockItem {
    public DrinkMakerItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var worldIn = context.getLevel();
        if (worldIn.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else if (context.getClickedFace() != Direction.UP) {
            return InteractionResult.FAIL;
        } else {
            BlockPos pos = context.getClickedPos();
            BlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();
            boolean flag = block.canBeReplaced(iblockstate, new BlockPlaceContext(context));

            if (!flag) {
                pos = pos.above();
            }
            Player player = context.getPlayer();
            ItemStack itemstack = player.getItemInHand(context.getHand());
            if (player.mayUseItemAt(pos, context.getClickedFace(), itemstack) && (flag || worldIn.isEmptyBlock(pos)) && worldIn.getBlockState(pos.below()).isFaceSturdy(worldIn, pos.below(), Direction.UP)) {
                int i = Mth.floor((double) (player.getVisualRotationYInDegrees() * 4.0F / 360.0F) + 0.5D) & 3;
                Direction enumfacing = Direction.from2DDataValue(i);

                BlockPos blockpos = pos.relative(BlockHelper.getNextHorizontal(enumfacing));
                boolean left = false, right = false;
                if (player.mayUseItemAt(blockpos, context.getClickedFace(), itemstack) && (worldIn.getBlockState(blockpos).getBlock().canBeReplaced(worldIn.getBlockState(blockpos), new BlockPlaceContext(context)) || worldIn.isEmptyBlock(blockpos)) && worldIn.getBlockState(blockpos.below()).isFaceSturdy(worldIn, blockpos.below(), Direction.UP)) {
                    left = true;
                } else {
                    blockpos = pos.relative(BlockHelper.getPreviousHorizontal(enumfacing));
                    if (player.mayUseItemAt(blockpos, context.getClickedFace(), itemstack) && (worldIn.getBlockState(blockpos).getBlock().canBeReplaced(worldIn.getBlockState(blockpos), new BlockPlaceContext(context)) || worldIn.isEmptyBlock(blockpos)) && worldIn.getBlockState(blockpos.below()).isFaceSturdy(worldIn, blockpos.below(), Direction.UP)) {
                        right = true;
                    }
                }
                if (left || right) {
                    BlockState iblockstate1 = worldIn.getBlockState(blockpos);
                    BlockState iblockstate2 = TileEntityTypeRegistry.DRINK_MAKER.get().defaultBlockState().setValue(DrinkMakerBlock.LEFT, left).setValue(DrinkMakerBlock.FACING, enumfacing);
                    worldIn.setBlock(pos, iblockstate2, 10);
                    worldIn.setBlock(blockpos, iblockstate2.setValue(DrinkMakerBlock.LEFT, !left), 10);
                    SoundType soundtype = iblockstate2.getBlock().getSoundType(iblockstate2, worldIn, pos, player);
                    worldIn.playSound(null, pos, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    worldIn.updateNeighborsAt(pos, block);
                    worldIn.updateNeighborsAt(blockpos, iblockstate1.getBlock());

                    itemstack.shrink(1);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.FAIL;
    }


}
