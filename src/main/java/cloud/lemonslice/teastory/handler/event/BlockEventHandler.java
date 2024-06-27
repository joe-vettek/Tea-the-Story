package cloud.lemonslice.teastory.handler.event;


import cloud.lemonslice.teastory.config.ServerConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import net.minecraftforge.event.level.BlockEvent;
import xueluoanping.teastory.AllItems;


public final class BlockEventHandler
{
    public static void dropAsh(BlockEvent.NeighborNotifyEvent event)
    {
        if (ServerConfig.Others.woodDropsAshWhenBurning.get())
            event.getNotifiedSides().forEach(direction ->
            {
                if (event.getLevel().getBlockState(event.getPos()).isFlammable(event.getLevel(), event.getPos(), direction) && event.getLevel().getBlockState(event.getPos().relative(direction)).getBlock() instanceof FireBlock)
                {
                    Block.popResource((Level) event.getLevel(), event.getPos(), new ItemStack(AllItems.ash.get()));
                }
            });
    }
}
