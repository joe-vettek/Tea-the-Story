package cloud.lemonslice.teastory.block.crops;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import xueluoanping.teastory.BlockRegister;
import xueluoanping.teastory.ItemRegister;

import java.util.ArrayList;
import java.util.List;

public class ChineseCabbageBlock extends CropBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 6);

    public ChineseCabbageBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 6;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return BlockRegister.CHINESE_CABBAGE_SEEDS.get();
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> list = new ArrayList<>();
        if (state.getValue(AGE) == this.getMaxAge()) {
            list.add(new ItemStack(BlockRegister.CHINESE_CABBAGE.get(), 2 + builder.getLevel().getRandom().nextInt(3)));
            list.add(new ItemStack(getBaseSeedId(), builder.getLevel().getRandom().nextInt(2)));
        }
        list.add(new ItemStack(getBaseSeedId()));
        return list;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}
