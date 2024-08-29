package cloud.lemonslice.teastory.item;


import cloud.lemonslice.teastory.block.crops.TrellisBlock;
import xueluoanping.teastory.block.crops.TrellisWithVineBlock;
import cloud.lemonslice.teastory.block.crops.VineInfoManager;
import cloud.lemonslice.teastory.block.crops.VineType;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;


public class VineSeedsItem extends BlockItem {
    private final VineType type;

    public VineSeedsItem(VineType type, Item.Properties pProperties) {
        super(type.getFruit(), pProperties);
        this.type = type;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof TrellisBlock && !(state.getBlock() instanceof TrellisWithVineBlock)) {
            if (world.getBlockState(pos.below()).is(BlockTags.DIRT)) {
                world.setBlockAndUpdate(pos, VineInfoManager.getVineTrellis(type, (TrellisBlock) state.getBlock()).getRelevantState(state));
                context.getItemInHand().shrink(1);
                return InteractionResult.SUCCESS;
            }
        }
        return this.use(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
    }


}
