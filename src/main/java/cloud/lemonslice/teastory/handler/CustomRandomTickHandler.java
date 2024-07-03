package cloud.lemonslice.teastory.handler;


import cloud.lemonslice.teastory.config.ServerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xueluoanping.teastory.TeaStory;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = TeaStory.MODID)
public final class CustomRandomTickHandler
{
    private static final CustomRandomTick SNOW_MELT = (state, world, pos) ->
    {
        BlockPos blockpos =new BlockPos(pos.getX(), world.getHeight(Heightmap.Types.MOTION_BLOCKING, pos.getX(), pos.getZ()),pos.getZ());
        if (world.isAreaLoaded(blockpos, 1) && world.getBiome(blockpos).get().getTemperature(pos) >= 0.15F)
        {
            BlockState topState = world.getBlockState(blockpos);
            if (topState.getBlock().equals(Blocks.SNOW))
            {
                world.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
            }
            else
            {
                BlockState belowState = world.getBlockState(blockpos.below());
                if (belowState.getBlock().equals(Blocks.ICE))
                {
                    world.setBlockAndUpdate(blockpos.below(), Blocks.WATER.defaultBlockState());
                }
            }
        }
    };

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event)
    {
        if (event.phase.equals(TickEvent.Phase.END) && ServerConfig.Temperature.iceMelt.get() && !event.level.isClientSide())
        {
            ServerLevel world = (ServerLevel) event.level;
            int randomTickSpeed = world.getGameRules().getInt(GameRules.RULE_RANDOMTICKING);
            if (randomTickSpeed > 0)
            {
                world.getChunkSource().chunkMap.getChunks().forEach(chunkHolder ->
                {
                    Optional<Chunk> optional = chunkHolder.getEntityTickingFuture().getNow(ChunkHolder.UNLOADED_CHUNK).left();
                    if (optional.isPresent())
                    {
                        Chunk chunk = optional.get();
                        for (ChunkSection chunksection : chunk.getSections())
                        {
                            if (chunksection != Chunk.EMPTY_SECTION && chunksection.needsRandomTickAny())
                            {
                                int x = chunk.getPos().getXStart();
                                int y = chunksection.getYLocation();
                                int z = chunk.getPos().getZStart();

                                for (int l = 0; l < randomTickSpeed; ++l)
                                {
                                    if (world.getRandom().nextInt(32) == 0)
                                    {
                                        doCustomRandomTick(world, x, y, z);
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private static void doCustomRandomTick(ServerLevel world, int x, int y, int z)
    {
        if (ServerConfig.Temperature.iceMelt.get())
        {
            SNOW_MELT.tick(null, world, new BlockPos(x, y, z));
        }
    }
}
