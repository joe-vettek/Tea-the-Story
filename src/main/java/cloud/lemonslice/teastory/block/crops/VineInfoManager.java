package cloud.lemonslice.teastory.block.crops;


import xueluoanping.teastory.BlockRegister;

import java.util.HashMap;
import java.util.Map;


public final class VineInfoManager {
    private final static Map<VinePair, TrellisWithVineBlock> TRELLIS_VINES_INFO = new HashMap<>();
    private final static Map<TrellisWithVineBlock, TrellisBlock> TRELLIS_INFO = new HashMap<>();

    public static void registerVineTypeConnections(VineType typeIn, TrellisBlock blockIn, TrellisWithVineBlock vineOut) {
        TRELLIS_VINES_INFO.put(new VinePair(typeIn, blockIn), vineOut);
        TRELLIS_INFO.put(vineOut, blockIn);
    }

    public static void  initTrellisBlocks() {
        registerVineTypeConnections(VineType.GRAPE, BlockRegister.OAK_TRELLIS.get(), BlockRegister.OAK_TRELLIS_GRAPE.get());
        registerVineTypeConnections(VineType.GRAPE, BlockRegister.BIRCH_TRELLIS.get(), BlockRegister.BIRCH_TRELLIS_GRAPE.get());
        registerVineTypeConnections(VineType.GRAPE, BlockRegister.JUNGLE_TRELLIS.get(), BlockRegister.JUNGLE_TRELLIS_GRAPE.get());
        registerVineTypeConnections(VineType.GRAPE, BlockRegister.SPRUCE_TRELLIS.get(), BlockRegister.SPRUCE_TRELLIS_GRAPE.get());
        registerVineTypeConnections(VineType.GRAPE, BlockRegister.DARK_OAK_TRELLIS.get(), BlockRegister.DARK_OAK_TRELLIS_GRAPE.get());
        registerVineTypeConnections(VineType.GRAPE, BlockRegister.ACACIA_TRELLIS.get(), BlockRegister.ACACIA_TRELLIS_GRAPE.get());
        registerVineTypeConnections(VineType.CUCUMBER, BlockRegister.OAK_TRELLIS.get(), BlockRegister.OAK_TRELLIS_CUCUMBER.get());
        registerVineTypeConnections(VineType.CUCUMBER, BlockRegister.BIRCH_TRELLIS.get(), BlockRegister.BIRCH_TRELLIS_CUCUMBER.get());
        registerVineTypeConnections(VineType.CUCUMBER, BlockRegister.JUNGLE_TRELLIS.get(), BlockRegister.JUNGLE_TRELLIS_CUCUMBER.get());
        registerVineTypeConnections(VineType.CUCUMBER, BlockRegister.SPRUCE_TRELLIS.get(), BlockRegister.SPRUCE_TRELLIS_CUCUMBER.get());
        registerVineTypeConnections(VineType.CUCUMBER, BlockRegister.DARK_OAK_TRELLIS.get(), BlockRegister.DARK_OAK_TRELLIS_CUCUMBER.get());
        registerVineTypeConnections(VineType.CUCUMBER, BlockRegister.ACACIA_TRELLIS.get(), BlockRegister.ACACIA_TRELLIS_CUCUMBER.get());
        registerVineTypeConnections(VineType.BITTER_GOURD, BlockRegister.OAK_TRELLIS.get(), BlockRegister.OAK_TRELLIS_BITTER_GOURD.get());
        registerVineTypeConnections(VineType.BITTER_GOURD, BlockRegister.BIRCH_TRELLIS.get(), BlockRegister.BIRCH_TRELLIS_BITTER_GOURD.get());
        registerVineTypeConnections(VineType.BITTER_GOURD, BlockRegister.JUNGLE_TRELLIS.get(), BlockRegister.JUNGLE_TRELLIS_BITTER_GOURD.get());
        registerVineTypeConnections(VineType.BITTER_GOURD, BlockRegister.SPRUCE_TRELLIS.get(), BlockRegister.SPRUCE_TRELLIS_BITTER_GOURD.get());
        registerVineTypeConnections(VineType.BITTER_GOURD, BlockRegister.DARK_OAK_TRELLIS.get(), BlockRegister.DARK_OAK_TRELLIS_BITTER_GOURD.get());
        registerVineTypeConnections(VineType.BITTER_GOURD, BlockRegister.ACACIA_TRELLIS.get(), BlockRegister.ACACIA_TRELLIS_BITTER_GOURD.get());
    }


    public static TrellisWithVineBlock getVineTrellis(VineType typeIn, TrellisBlock blockIn) {
        return TRELLIS_VINES_INFO.get(new VinePair(typeIn, blockIn));
    }

    public static TrellisBlock getEmptyTrellis(TrellisWithVineBlock blockIn) {
        return TRELLIS_INFO.get(blockIn);
    }
}
