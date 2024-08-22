package xueluoanping.teastory.data.loot;



import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;


public class LFTLootTableProvider extends LootTableProvider {

    private final PackOutput generator;

    public LFTLootTableProvider(PackOutput generator) {
        super(generator,Set.of(), List.of(new LootTableProvider.SubProviderEntry(
                TeaStotryBlockLootSubProvider::new,
                // Loot table generator for the 'empty' param set
                LootContextParamSets.BLOCK
        )));
        this.generator = generator;

    }

}
