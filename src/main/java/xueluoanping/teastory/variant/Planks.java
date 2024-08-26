package xueluoanping.teastory.variant;

import cloud.lemonslice.teastory.block.crops.TrellisBlock;
import cloud.lemonslice.teastory.block.crops.TrellisWithVineBlock;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.world.level.block.Block;
import xueluoanping.teastory.TeaStory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Planks {
    // A is old, and B is new one
    public static final Map<ResourceLocation, PlankHolders> TrellisBlockMap = new HashMap<>();

    // All Block
    // public static final Map<ResourceLocation, Block> resourceLocationBlockMap=new HashMap<>();

    public record PlankHolders(Block plank, TrellisBlock trellisBlock,
                               ArrayList<TrellisWithVineBlock> trellisWithVineBlocks) {

    }

    public static KnownPack knowPack(String pName) {
        return new KnownPack(TeaStory.MODID, pName, SharedConstants.getCurrentVersion().getId());
    }

    public static final PackSelectionConfig FEATURE_SELECTION_CONFIG = new PackSelectionConfig(true, Pack.Position.BOTTOM, false);

}
