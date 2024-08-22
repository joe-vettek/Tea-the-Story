package cloud.lemonslice.teastory.block.decorations;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootParams;


import java.util.List;

public class BambooDoorBlock extends DoorBlock {
    public BambooDoorBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties,BlockSetType.BAMBOO);

    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }

    // @Override
    // public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
    //     List<ItemStack> list = Lists.newArrayList();
    //     if (pState.getValue(HALF).equals(DoubleBlockHalf.UPPER))
    //         list.add(new ItemStack(this));
    //     return list;
    // }

}
