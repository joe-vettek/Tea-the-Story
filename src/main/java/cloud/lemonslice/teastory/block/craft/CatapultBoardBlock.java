package cloud.lemonslice.teastory.block.craft;


import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3d;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.NormalHorizontalBlock;

import java.util.List;
import java.util.Random;

public class CatapultBoardBlock extends NormalHorizontalBlock
{
    private static final VoxelShape SHAPE = VoxelShapeHelper.createVoxelShape(0, 0, 0, 16, 2, 16);
    private static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;
    private final float motion;

    public CatapultBoardBlock(float motion, Properties properties)
    {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(ENABLED, false));
        this.motion = motion;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    @Override
    public boolean canSurvive(BlockState state, LevelReader pLevel, BlockPos pos) {
        // return state.isFaceSturdy(pLevel, pos.below(), Direction.UP);
        return pLevel.getBlockState(pos.below()).isFaceSturdy(pLevel, pos, Direction.UP);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.canSurvive(worldIn, currentPos) ?
                Blocks.AIR.defaultBlockState() :
                super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(ENABLED));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        worldIn.setBlock(pos, state.setValue(ENABLED, false), Block.UPDATE_CLIENTS);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand)
    {

        if (worldIn.hasNeighborSignal(pos))
        {
            double d0 = (double) pos.getX() + 0.5D + (rand.nextDouble() - 0.5D);
            double d1 = (double) pos.getY() + 0.125D + (rand.nextDouble() - 0.5D) * 0.2D;
            double d2 = (double) pos.getZ() + 0.5D + (rand.nextDouble() - 0.5D);
            worldIn.addParticle(DustParticleOptions.REDSTONE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }



    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos,  Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if (player.getItemInHand(handIn).getItem() == TileEntityTypeRegistry.BAMBOO_TRAY_ITEM.get())
        {
            worldIn.setBlockAndUpdate(pos, TileEntityTypeRegistry.STONE_CATAPULT_BOARD_WITH_TRAY.get().defaultBlockState().setValue(FACING, state.getValue(FACING)));
            SoundType soundtype = TileEntityTypeRegistry.BAMBOO_TRAY.get().defaultBlockState().getSoundType(worldIn, pos, player);
            worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
            return InteractionResult.SUCCESS;
        }
        else
            return InteractionResult.FAIL;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn)
    {
        if (worldIn.hasNeighborSignal(pos))
        {
            worldIn.setBlock(pos, state.setValue(ENABLED, true), 2);
            worldIn.scheduleTick(pos, this, 5);
            Vec3 vec3d;
            switch (state.getValue(FACING))
            {
                case SOUTH:
                {
                    vec3d = new Vec3(0, motion, -motion);
                    break;
                }
                case EAST:
                {
                    vec3d = new Vec3(-motion, motion, 0);
                    break;
                }
                case WEST:
                {
                    vec3d = new Vec3(motion, motion, 0);
                    break;
                }
                default:
                    vec3d = new Vec3(0, motion, motion);
            }
            entityIn.fallDistance = 0.0F;
            entityIn.setDeltaMovement(vec3d);
        }
    }


}
