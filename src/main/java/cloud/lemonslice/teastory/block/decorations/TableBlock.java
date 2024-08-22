package cloud.lemonslice.teastory.block.decorations;


import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;


public class TableBlock extends Block {
    public static final VoxelShape SHAPE;

    public TableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }


    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0F;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }




    static {
        VoxelShape one = VoxelShapeHelper.createVoxelShape(1, 0, 1, 2, 14, 2);
        VoxelShape two = VoxelShapeHelper.createVoxelShape(13, 0, 1, 2, 14, 2);
        VoxelShape three = VoxelShapeHelper.createVoxelShape(1, 0, 13, 2, 14, 2);
        VoxelShape four = VoxelShapeHelper.createVoxelShape(13, 0, 13, 2, 14, 2);
        VoxelShape table = VoxelShapeHelper.createVoxelShape(0, 14, 0, 16, 2, 16);
        SHAPE = Shapes.or(one, two, three, four, table);
    }
}
