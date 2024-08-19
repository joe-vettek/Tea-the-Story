package xueluoanping.teastory.data.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import xueluoanping.teastory.BlockRegister;
import xueluoanping.teastory.loot.AddItemModifier;
import xueluoanping.teastory.loot.AddLootTableModifier;

import java.util.List;

public class GLMProvider extends GlobalLootModifierProvider {
    public GLMProvider(PackOutput gen, String modid) {
        super(gen, modid);
    }

    @Override
    protected void start() {

        var cond = new LootItemCondition[4];
        cond[0]=LootTableIdCondition.builder(Blocks.GRASS.getLootTable()).build();
        cond[1]=LootTableIdCondition.builder(Blocks.TALL_GRASS.getLootTable()).build();
        cond[2]=LootTableIdCondition.builder(Blocks.FERN.getLootTable()).build();
        cond[3]=LootTableIdCondition.builder(Blocks.LARGE_FERN.getLootTable()).build();

        add("add_" + BlockRegister.BITTER_GOURDS.getId().getPath() + "_to_grass", new AddItemModifier(cond, BlockRegister.CUCUMBERS.get(), 1));
        add("add_" + BlockRegister.CUCUMBERS.getId().getPath() + "_to_grass", new AddItemModifier(cond, BlockRegister.CUCUMBERS.get(), 1));
        add("add_" + BlockRegister.RICE_GRAINS.getId().getPath() + "_to_grass", new AddItemModifier(cond, BlockRegister.CUCUMBERS.get(), 1));

    }


}
