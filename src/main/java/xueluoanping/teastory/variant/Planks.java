package xueluoanping.teastory.variant;

import cloud.lemonslice.teastory.block.crops.TrellisBlock;
import cloud.lemonslice.teastory.block.crops.TrellisWithVineBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Planks {
   // A is old, and B is new one
   public static final Map<ResourceLocation, PlankHolders> TrellisBlockMap =new HashMap<>();

   // All Block
   // public static final Map<ResourceLocation, Block> resourceLocationBlockMap=new HashMap<>();

   public record PlankHolders(Block plank, TrellisBlock trellisBlock, ArrayList<TrellisWithVineBlock> trellisWithVineBlocks){

   }
}
