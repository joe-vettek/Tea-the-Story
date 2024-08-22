package cloud.lemonslice.teastory.block.crops;


import cloud.lemonslice.teastory.block.crops.flower.FlowerColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import xueluoanping.teastory.FluidRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;


public class HybridizableFlowerBlock extends BushBlock implements BonemealableBlock {
    public static final EnumProperty<FlowerColor> FLOWER_COLOR = EnumProperty.create("color", FlowerColor.class);
    protected static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 14.0D, 13.0D);

    public HybridizableFlowerBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FLOWER_COLOR, FlowerColor.WHITE));
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(FLOWER_COLOR));
    }


    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 60;
    }


    // we use copyState Now
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var state = defaultBlockState();
        // if (context.getItemInHand().hasTag() && context.getItemInHand().getOrCreateTag().contains("color")) {
        //     state = state.setValue(FLOWER_COLOR, FlowerColor.getFlowerColor(context.getItemInHand().getTag().getString("color")));
        // }
        // if (context.getItemInHand().getTag().contains("BlockStateTag")
        //         && context.getItemInHand().getTag().getCompound("BlockStateTag").contains("color")) {
        //     state = state.setValue(FLOWER_COLOR, FlowerColor.getFlowerColor(context.getItemInHand().getTag().getCompound("BlockStateTag").getString("color")));
        // }
        return state;
    }


    public boolean isSameKind(Level worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).is(this);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        ItemStack stack = new ItemStack(this);
        var nbt = new CompoundTag();
        nbt.putString("color", state.getValue(FLOWER_COLOR).getString());
        var compoundtag1 = new CompoundTag();
        compoundtag1.put("BlockStateTag", nbt);
        stack.setTag(compoundtag1);
        return stack;
    }


    @Override
    public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState pState, boolean pIsClient) {
        List<BlockPos> positions = new ArrayList<>();
        Collections.addAll(positions, pos.east(), pos.west(), pos.north(), pos.south());
        for (BlockPos p : positions) {
            if (worldIn.getBlockState(p).isAir() && worldIn.getBlockState(p.below()).is(BlockTags.DIRT)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, RandomSource pRandom, BlockPos pos, BlockState state) {
        List<BlockPos> growPositions = new ArrayList<>();
        List<BlockPos> canGrowPositions = new ArrayList<>();
        Collections.addAll(growPositions, pos.east(), pos.west(), pos.north(), pos.south());
        for (BlockPos p : growPositions) {
            if (worldIn.getBlockState(p).isAir() && worldIn.getBlockState(p.below()).is(BlockTags.DIRT))
                ;
            {
                canGrowPositions.add(p);
            }
        }
        final double randomD1 = Math.random();
        final int growPosI = (int) (randomD1 * canGrowPositions.size());
        List<BlockPos> isHybFlowersPos = new ArrayList<>();
        List<BlockPos> hybFlowersPos = new ArrayList<>();
        BlockPos growPos = canGrowPositions.get(growPosI);
        Collections.addAll(isHybFlowersPos, growPos.east(), growPos.west(), growPos.north(), growPos.south());
        for (BlockPos p : isHybFlowersPos) {
            if (isSameKind(worldIn, p)) {
                hybFlowersPos.add(p);
            }
        }
        if (hybFlowersPos.size() >= 2) {
            hybFlowersPos.remove(pos);
        }
        final double randomD2 = Math.random();
        final int hybPosI = (int) (randomD2 * hybFlowersPos.size());

        worldIn.setBlockAndUpdate(growPos, defaultBlockState().setValue(FLOWER_COLOR, FlowerColor.getHybColor(state.getValue(FLOWER_COLOR), worldIn.getBlockState(hybFlowersPos.get(hybPosI)).getValue(FLOWER_COLOR))));

    }


}
