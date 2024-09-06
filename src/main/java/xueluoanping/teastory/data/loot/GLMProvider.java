package xueluoanping.teastory.data.loot;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import xueluoanping.teastory.BlockRegister;
import xueluoanping.teastory.loot.AddItemModifier;

import java.util.List;

public class GLMProvider extends GlobalLootModifierProvider {
    public GLMProvider(PackOutput gen, String modid) {
        super(gen, modid);
    }

    @Override
    protected void start() {


        for (Block grass : List.of(Blocks.GRASS, Blocks.TALL_GRASS, Blocks.FERN, Blocks.LARGE_FERN)) {
            LootItemCondition lootItemCondition = LootTableIdCondition.builder(grass.getLootTable())
                    .and(LootItemRandomChanceCondition.randomChance(0.005f))
                    .build();

            String id = BuiltInRegistries.BLOCK.getKey(grass).getPath();
            add("add_" + BlockRegister.GRAPES.getId().getPath() + "_to_" + id, new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.GRAPES.get(), 1));
            add("add_" + BlockRegister.BITTER_GOURDS.getId().getPath() + "_to_" + id, new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.BITTER_GOURDS.get(), 1));
            add("add_" + BlockRegister.CUCUMBERS.getId().getPath() + "_to_" + id, new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.CUCUMBERS.get(), 1));
            add("add_" + BlockRegister.RICE_GRAINS.getId().getPath() + "_to_" + id, new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.RICE_GRAINS.get(), 1));
            add("add_" + BlockRegister.TEA_SEEDS.getId().getPath() + "_to_" + id, new AddItemModifier(new LootItemCondition[]{lootItemCondition}, BlockRegister.TEA_SEEDS.get(), 1));
        }

    }


}
