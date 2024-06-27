package xueluoanping.teastory.block.crops;


import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class AqueductConnectorBlock extends AqueductBlock
{
    public AqueductConnectorBlock( Properties properties)
    {
        super( properties);
    }


    @Override
    public boolean canConnect(BlockState state)
    {
        return isAqueduct(state) || state.getFluidState().getType() == Fluids.WATER || state.getBlock() instanceof PaddyFieldBlock;
    }

    @Override
    public InteractionResult fillAqueduct(Level worldIn, BlockPos pos, Player player, InteractionHand handIn) {
        if (player.getItemInHand(handIn).getItem() == Items.MOSSY_COBBLESTONE)
        {
            worldIn.setBlockAndUpdate(pos, Blocks.MOSSY_COBBLESTONE.defaultBlockState());
            return InteractionResult.SUCCESS;
        }
        else return InteractionResult.PASS;
    }
}
