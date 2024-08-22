package cloud.lemonslice.teastory.block.crops;

import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.blockentity.VineEntity;

import java.util.ArrayList;
import java.util.List;

public class TrellisWithVineBlock extends TrellisBlock implements EntityBlock {
    // public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    // public static final IntegerProperty DISTANCE = IntegerProperty.create("distance", 0, 7);
    private static final VoxelShape[] SHAPES;
    private final VineType type;

    public TrellisWithVineBlock(VineType type, Properties properties) {
        super(properties);
        this.type = type;
        // this.registerDefaultState(defaultBlockState().setValue(AGE, 0).setValue(DISTANCE, 0).setValue(POST, false).setValue(UP, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(getEmptyTrellis(state));
        // return ItemStack.EMPTY;
    }

    // 此处state不一定为本类型
    private void onSignal(ServerLevel level, BlockPos pos, BlockState state, int energy, Direction from, RandomSource random) {
        if (energy <= 0 || !(state.getBlock() instanceof TrellisBlock)) return;
        energy--;
        if (!(state.getBlock() instanceof TrellisWithVineBlock)) {
            if (random.nextBoolean()) {
                level.setBlock(pos, VineInfoManager.getVineTrellis(type, (TrellisBlock) state.getBlock()).getRelevantState(state), 2);
                if (level.getBlockEntity(pos) instanceof VineEntity vineEntity) {
                    vineEntity.setDistance(7 - energy);
                }
            }
            return;
        } else {
            if (level.getBlockEntity(pos) instanceof VineEntity vineEntity) {
                if (vineEntity.getAge() < 3) {
                    vineEntity.setAge(vineEntity.getAge() + 1);
                } else {
                    var a = new ArrayList<>(List.of(Direction.EAST, Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.UP));
                    a.remove(from);
                    var b = new ArrayList<Direction>();
                    for (Direction direction : a) {
                        if ((level.getBlockState(pos.relative(direction)).getBlock() instanceof TrellisBlock)) {
                            b.add(direction);
                        }
                    }
                    if (!b.isEmpty()) {
                        Direction select = b.get(random.nextInt(b.size()));
                        onSignal(level, pos.relative(select), level.getBlockState(pos.relative(select)), energy, select.getOpposite(), random);
                    }
                    if (hasHorizontalBar(state)) {
                        // int i = state.getValue(AGE);
                        int i = vineEntity.getAge();
                        float f = 5.0F; // TODO Connected setValue humidity.
                        if (ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                            if (!hasPost(state)) {
                                if (random.nextBoolean()) // Leaves grow up.
                                {
                                    if (level.getBlockState(pos.below()).getBlock() != type.getFruit()) {
                                        // level.setBlock(pos, state.setValue(AGE, (i + 1) % 4), 2);
                                        vineEntity.setAge(((i + 1) % 4));
                                    }
                                } else // Bear fruit.
                                {
                                    if (level.getBlockState(pos.below()).isAir() && !hasNearFruit(level, pos.below(), type.getFruit())) {
                                        // level.setBlockAndUpdate(pos, state.setValue(AGE, (i + 1) % 4));
                                        vineEntity.setAge(((i + 1) % 4));
                                        level.setBlock(pos.below(), type.getFruit().defaultBlockState(),Block.UPDATE_CLIENTS);
                                        ForgeHooks.onCropsGrowPost(level, pos, state);
                                        return;
                                    }
                                }
                            }
                            ForgeHooks.onCropsGrowPost(level, pos, state);
                        }
                    }
                }
            }
            return;
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Grow vertically. 垂直方向生长。
        if (!(level.getBlockEntity(pos) instanceof VineEntity vineEntity))
            return;
        if (!level.getBlockState(pos.below()).is(BlockTags.DIRT)) {
            return;
        }
        vineEntity.setDistance(0);
        int energy = 7;
        if (vineEntity.getAge() == 3) {
            for (Direction direction : List.of(Direction.EAST, Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.UP)) {
                var nextState = level.getBlockState(pos.relative(direction));
                if (nextState.getBlock() instanceof TrellisBlock) {
                    onSignal(level, pos.relative(direction), nextState, energy, direction.getOpposite(), random);
                }
            }
        } else vineEntity.setAge(vineEntity.getAge() + 1);
        // if (true) return;
        //
        // if (hasPost(state)) {
        //     // int i = state.getValue(AGE);
        //     int i = vineEntity.getAge();
        //     float f = 8.0F; // TODO Connected setValue humidity.
        //     if (ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
        //         if (i < 3) {
        //             // level.setBlock(pos, state.setValue(AGE, i + 1), 2);
        //             vineEntity.setAge(++i);
        //         } else {
        //             BlockState up = level.getBlockState(pos.above());
        //             if (up.getBlock() instanceof TrellisBlock && !(up.getBlock() instanceof TrellisWithVineBlock)) {
        //                 level.setBlock(pos.above(), VineInfoManager.getVineTrellis(type, (TrellisBlock) up.getBlock()).getRelevantState(up), 2);
        //                 // .setValue(DISTANCE, state.getValue(DISTANCE) + 1)
        //                 if (level.getBlockEntity(pos.above()) instanceof VineEntity upVine) {
        //                     upVine.setDistance(vineEntity.getDistance() + 1);
        //
        //                 }
        //             }
        //         }
        //         ForgeHooks.onCropsGrowPost(level, pos, state);
        //         return;
        //     }
        // }
        // // Grow horizontally and bear fruit. 水平方向蔓延和结果。
        // if (hasHorizontalBar(state)) {
        //     // int i = state.getValue(AGE);
        //     int i = vineEntity.getAge();
        //     float f = 5.0F; // TODO Connected setValue humidity.
        //     if (ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
        //         if (!hasPost(state)) {
        //             if (random.nextBoolean()) // Leaves grow up.
        //             {
        //                 if (level.getBlockState(pos.below()).getBlock() != type.getFruit()) {
        //                     // level.setBlock(pos, state.setValue(AGE, (i + 1) % 4), 2);
        //                     vineEntity.setAge(((i + 1) % 4));
        //                 }
        //             } else // Bear fruit.
        //             {
        //                 if (level.getBlockState(pos.below()).isAir() && !hasNearFruit(level, pos.below(), type.getFruit())) {
        //                     // level.setBlockAndUpdate(pos, state.setValue(AGE, (i + 1) % 4));
        //                     vineEntity.setAge(((i + 1) % 4));
        //                     level.setBlockAndUpdate(pos.below(), type.getFruit().defaultBlockState());
        //                     ForgeHooks.onCropsGrowPost(level, pos, state);
        //                     return;
        //                 }
        //             }
        //         }
        //         BlockPos nextPos = pos;
        //         switch (random.nextInt(4)) {
        //             case 0:
        //                 nextPos = nextPos.north();
        //                 break;
        //             case 1:
        //                 nextPos = nextPos.south();
        //                 break;
        //             case 2:
        //                 nextPos = nextPos.east();
        //                 break;
        //             default:
        //                 nextPos = nextPos.west();
        //         }
        //         BlockState next = level.getBlockState(nextPos);
        //         if (next.getBlock() instanceof TrellisBlock
        //                 && !(next.getBlock() instanceof TrellisWithVineBlock)
        //                 && vineEntity.getDistance() < 7) {
        //             level.setBlock(nextPos, VineInfoManager.getVineTrellis(type, (TrellisBlock) next.getBlock()).getRelevantState(next), 2);
        //             // .setValue(DISTANCE, state.getValue(DISTANCE) + 1)
        //             if (level.getBlockEntity(nextPos) instanceof VineEntity nextVine) {
        //                 nextVine.setDistance(vineEntity.getDistance() + 1);
        //             }
        //         }
        //         ForgeHooks.onCropsGrowPost(level, pos, state);
        //     }
        // }
    }


    public static boolean hasNearFruit(Level worldIn, BlockPos pos, Block fruit) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState state = worldIn.getBlockState(pos.relative(direction));
            if (state.is(fruit)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int bar = hasHorizontalBar(state) ? 4 : 0;
        int post = hasPost(state) ? 2 : 0;
        int up = state.getValue(UP) ? 1 : 0;
        return SHAPES[bar + post + up];
    }

    public int getNearDistance(LevelAccessor world, BlockPos pos) {
        int distance = 7;

        for (Direction direction : List.of(Direction.EAST, Direction.NORTH, Direction.WEST, Direction.SOUTH)) {
            distance = Math.min(distance, getDistance(world.getBlockEntity(pos.relative(direction))));
        }

        return distance;
    }

    public int getNearDistance2(LevelAccessor world, BlockPos pos) {
        int distance = 7;

        for (Direction direction : List.of(Direction.EAST, Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.DOWN)) {
            distance = Math.min(distance, getDistance(world.getBlockEntity(pos.relative(direction))));
        }

        return distance;
    }

    public int getDistance(BlockEntity blockEntity) {
        // if (blockEntity.getBlock() instanceof TrellisWithVineBlock
        //         && ((TrellisWithVineBlock) blockEntity.getBlock()).type == type) {
        //     return blockEntity.getValue(DISTANCE);
        // }aq
        if (blockEntity instanceof VineEntity otherVineEntity
                && ((TrellisWithVineBlock) otherVineEntity.getBlockState().getBlock()).type == type) {
            // return blockEntity.getValue(DISTANCE);
            return otherVineEntity.getDistance();
        }
        return 7;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        // Update the connecting state of trellis. 更新棚架方块的连接状态。
        if (!(level instanceof ServerLevel)) return stateIn;
        if (facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL) {
            stateIn = stateIn.setValue(FACING_TO_PROPERTY_MAP.get(facing), this.canConnect(facingState, facingState.isFaceSturdy(level, facingPos, facing.getOpposite())));
        } else if (facing == Direction.DOWN) {
            BlockPos posDown = pos.relative(facing);
            BlockState state = level.getBlockState(posDown);
            stateIn = stateIn.setValue(POST, state.getBlock() instanceof TrellisWithVineBlock || state.is(BlockTags.WOODEN_FENCES) || state.isFaceSturdy(level, posDown, Direction.UP));
        } else if (facing == Direction.UP) {
            BlockPos posUp = pos.relative(facing);
            BlockState state = level.getBlockState(posUp);
            stateIn = stateIn.setValue(UP, state.getBlock() instanceof TrellisWithVineBlock || state.is(BlockTags.WOODEN_FENCES) || state.isFaceSturdy(level, posUp, Direction.DOWN));
        }
        // if (true) return stateIn;

        // To judge whether vines can be stay here. 判断缠绕藤（棚架）作物在此是否合理。
        // Distance should be setValuein 7. 攀爬距离应该小于等于7。


        boolean valid = false;

        if (level.getBlockState(pos.below()).is(BlockTags.DIRT)) {
            valid = true;
        } else if (level.getBlockEntity(pos) instanceof VineEntity vineEntity) {
            int nearD=getNearDistance2(level, pos);
            if (nearD < vineEntity.getDistance()) {
                valid = true;
                if (nearD+1<vineEntity.getDistance()){
                    // TeaStory.logger(nearD,vineEntity.getDistance());
                }
                // if (nearD+1<vineEntity.getDistance()){
                //     vineEntity.setDistance(nearD+1);
                // }
            }
        }


        // if ((level.getBlockEntity(pos) instanceof VineEntity vineEntity)) {
        //     int distance = 8;
        //     if (hasHorizontalBar(stateIn)) {
        //         distance = getNearDistance(level, pos);
        //         if (distance < 7) {
        //             // stateIn = stateIn.setValue(DISTANCE, distance + 1);
        //             // vineEntity.setDistance(distance+1);
        //             distance += 1;
        //             valid = true;
        //         }
        //     }
        //     if (hasPost(stateIn)) {
        //         BlockState down = level.getBlockState(pos.below());
        //         if (down.is(BlockTags.DIRT)) {
        //             // stateIn = stateIn.setValue(DISTANCE, 0);
        //             // vineEntity.setDistance(0);
        //             distance = 0;
        //             valid = true;
        //         } else if (down.getBlock() instanceof TrellisWithVineBlock
        //                 && level.getBlockEntity(pos.below()) instanceof VineEntity belowEntity
        //         ) {
        //             // down.getValue(AGE) == 3
        //             if (belowEntity.getAge() == 3 && ((TrellisWithVineBlock) down.getBlock()).type == type) {
        //                 // stateIn = stateIn.setValue(DISTANCE, down.getValue(DISTANCE));
        //                 // vineEntity.setDistance(belowEntity.getDistance());
        //                 distance = belowEntity.getDistance();
        //                 valid = true;
        //             }
        //         }
        //
        //     }
        //     if (valid) {
        //         // vineEntity.setDistance(distance);
        //     }
        //
        // }

        if (!valid) {
            stateIn = VineInfoManager.getEmptyTrellis(stateIn.getBlock()).getRelevantState(stateIn);
        }
        return stateIn;
    }


    // @Override
    // protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    //     super.createBlockStateDefinition(builder.add(AGE, DISTANCE));
    // }

    public Block getEmptyTrellis(BlockState state) {
        return VineInfoManager.getEmptyTrellis(state.getBlock());
    }

    @Override
    public BlockState getRelevantState(BlockState old) {
        BlockState newState = super.getRelevantState(old);
        return newState;
        // return newState.setValue(AGE, 0);
    }


    public VineType getType() {
        return type;
    }

    static {
        VoxelShape TOP_SHAPE = VoxelShapeHelper.createVoxelShape(0.0D, 4.0D, 0.0D, 16.0D, 9.0D, 16.0D);
        VoxelShape POST_SHAPE = VoxelShapeHelper.createVoxelShape(6.0D, 0.0D, 6.0D, 4.0D, 12.0D, 4.0D);
        VoxelShape POST_UP_SHAPE = VoxelShapeHelper.createVoxelShape(6.0D, 7.0D, 6.0D, 4.0D, 9.0D, 4.0D);
        SHAPES = new VoxelShape[]{TOP_SHAPE, POST_UP_SHAPE, POST_SHAPE, Shapes.or(POST_UP_SHAPE, POST_SHAPE),
                TOP_SHAPE, Shapes.or(TOP_SHAPE, POST_UP_SHAPE), Shapes.or(TOP_SHAPE, POST_SHAPE), Shapes.or(TOP_SHAPE, POST_UP_SHAPE, POST_SHAPE)};
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return TileEntityTypeRegistry.VINE_TYPE.get().create(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return Lists.newArrayList(new ItemStack(getEmptyTrellis(state)));
    }
}
