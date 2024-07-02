package cloud.lemonslice.teastory.block.crops;


import cloud.lemonslice.teastory.block.HorizontalConnectedBlock;
import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LowAqueductBlock extends AqueductBlock {

    public LowAqueductBlock(Properties properties) {
        super(properties);
    }


    static {
        VoxelShape POST_0 = VoxelShapeHelper.createVoxelShape(0.0D, 4.0D, 0.0D, 4.0D, 11.0D, 4.0D);
        VoxelShape POST_1 = VoxelShapeHelper.createVoxelShape(12.0D, 4.0D, 0.0D, 4.0D, 11.0D, 4.0D);
        VoxelShape POST_2 = VoxelShapeHelper.createVoxelShape(0.0D, 4.0D, 12.0D, 4.0D, 11.0D, 4.0D);
        VoxelShape POST_3 = VoxelShapeHelper.createVoxelShape(12.0D, 4.0D, 12.0D, 4.0D, 11.0D, 4.0D);
        VoxelShape BOTTOM = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
        VoxelShape BOTTOM_OPTIONAL = VoxelShapeHelper.createVoxelShape(4.0D, 0.0D, 4.0D, 8.0D, 4.0D, 8.0D);
        VoxelShape DEFAULT = Shapes.or(POST_0, POST_1, POST_2, POST_3, Shapes.join(BOTTOM, BOTTOM_OPTIONAL, BooleanOp.ONLY_FIRST));
        VoxelShape WALL_NORTH = VoxelShapeHelper.createVoxelShape(4.0D, 4.0D, 0.0D, 8.0D, 11.0D, 4.0D);
        VoxelShape WALL_SOUTH = VoxelShapeHelper.createVoxelShape(4.0D, 4.0D, 12.0D, 8.0D, 11.0D, 4.0D);
        VoxelShape WALL_WEST = VoxelShapeHelper.createVoxelShape(0.0D, 4.0D, 4.0D, 4.0D, 11.0D, 8.0D);
        VoxelShape WALL_EAST = VoxelShapeHelper.createVoxelShape(12.0D, 4.0D, 4.0D, 4.0D, 11.0D, 8.0D);
        SHAPES = new VoxelShape[]{DEFAULT, Shapes.or(DEFAULT, WALL_EAST).optimize(), Shapes.or(DEFAULT, WALL_WEST).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_EAST).optimize(),
                Shapes.or(DEFAULT, WALL_SOUTH).optimize(), Shapes.or(DEFAULT, WALL_EAST, WALL_SOUTH).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_SOUTH).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_EAST, WALL_SOUTH).optimize(),
                Shapes.or(DEFAULT, WALL_NORTH).optimize(), Shapes.or(DEFAULT, WALL_EAST, WALL_NORTH).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_NORTH).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_EAST, WALL_NORTH).optimize(),
                Shapes.or(DEFAULT, WALL_SOUTH, WALL_NORTH).optimize(), Shapes.or(DEFAULT, WALL_EAST, WALL_SOUTH, WALL_NORTH).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_SOUTH, WALL_NORTH).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_EAST, WALL_SOUTH, WALL_NORTH).optimize(),
                Shapes.or(DEFAULT, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_EAST, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_WEST, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_EAST, BOTTOM_OPTIONAL).optimize(),
                Shapes.or(DEFAULT, WALL_SOUTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_EAST, WALL_SOUTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_SOUTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_EAST, WALL_SOUTH, BOTTOM_OPTIONAL).optimize(),
                Shapes.or(DEFAULT, WALL_NORTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_EAST, WALL_NORTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_NORTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_EAST, WALL_NORTH, BOTTOM_OPTIONAL).optimize(),
                Shapes.or(DEFAULT, WALL_SOUTH, WALL_NORTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_EAST, WALL_SOUTH, WALL_NORTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_SOUTH, WALL_NORTH, BOTTOM_OPTIONAL).optimize(), Shapes.or(DEFAULT, WALL_WEST, WALL_EAST, WALL_SOUTH, WALL_NORTH, BOTTOM_OPTIONAL).optimize()};
    }
}
