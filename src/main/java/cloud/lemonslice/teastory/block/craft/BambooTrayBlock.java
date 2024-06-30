package cloud.lemonslice.teastory.block.craft;

import cloud.lemonslice.teastory.blockentity.StoneMillTileEntity;
import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.NetworkHooks;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.NormalHorizontalBlock;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.List;

public class BambooTrayBlock extends Block implements EntityBlock
{
    protected static final VoxelShape SHAPE;

    public BambooTrayBlock()
    {
        super("bamboo_tray", Properties.create(Material.BAMBOO).sound(SoundType.BAMBOO).hardnessAndResistance(0.5F).notSolid());
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return hasSolidSideOnTop(worldIn, pos.down());
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    private void dropItems(World worldIn, BlockPos pos)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null)
        {
            te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(inv ->
            {
                for (int i = inv.getSlots() - 1; i >= 0; --i)
                {
                    if (inv.getStackInSlot(i) != ItemStack.EMPTY)
                    {
                        Block.spawnAsEntity(worldIn, pos, inv.getStackInSlot(i));
                        ((IItemHandlerModifiable) inv).setStackInSlot(i, ItemStack.EMPTY);
                    }
                }
            });
        }
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face)
    {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face)
    {
        return 60;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (state.hasTileEntity() && !(newState.getBlock() == this))
        {
            ((NormalContainerTileEntity) worldIn.getTileEntity(pos)).prepareForRemove();
            dropItems(worldIn, pos);
            worldIn.removeTileEntity(pos);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof BambooTrayTileEntity)
        {
            if (worldIn.isRemote)
            {
                ((BambooTrayTileEntity) te).refreshSeed();
                return ActionResultType.SUCCESS;
            }
            if (!player.isSneaking())
            {
                if (((BambooTrayTileEntity) te).isDoubleClick())
                {
                    dropItems(worldIn, pos);
                    return ActionResultType.SUCCESS;
                }
                if (!((BambooTrayTileEntity) te).isWorking())
                {
                    dropItems(worldIn, pos);
                    te.markDirty();
                }
                if (!player.getHeldItem(handIn).isEmpty())
                {
                    te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(inv ->
                    {
                        BambooTraySingleInRecipe recipe = null;
                        for (IRecipe<?> r : worldIn.getRecipeManager().getRecipes())
                        {
                            if (r.getType().equals(((BambooTrayTileEntity) te).getRecipeType()) && ((BambooTraySingleInRecipe) r).getIngredient().test(player.getHeldItem(handIn)))
                            {
                                recipe = (BambooTraySingleInRecipe) r;
                                break;
                            }
                        }
                        if (recipe != null && !recipe.getRecipeOutput().isEmpty())
                        {
                            player.setHeldItem(handIn, inv.insertItem(0, player.getHeldItem(handIn), false));
                            te.markDirty();
                        }
                        else ((BambooTrayTileEntity) te).singleClickStart();
                    });
                    return ActionResultType.SUCCESS;
                }
                else
                {
                    if (((BambooTrayTileEntity) te).isWorking())
                    {
                        ((BambooTrayTileEntity) te).singleClickStart();
                    }
                }
            }
            else
            {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) te, te.getPos());
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return TileEntityTypeRegistry.BAMBOO_TRAY.create();
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        return Lists.newArrayList(new ItemStack(this));
    }

    static
    {
        VoxelShape side_north = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 2.0D);
        VoxelShape side_south = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 14.0D, 16.0D, 3.0D, 2.0D);
        VoxelShape side_west = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 2.0D, 3.0D, 16.0D);
        VoxelShape side_east = VoxelShapeHelper.createVoxelShape(14.0D, 0.0D, 0.0D, 2.0D, 3.0D, 16.0D);
        VoxelShape bottom = VoxelShapeHelper.createVoxelShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
        SHAPE = VoxelShapes.or(side_north, side_south, side_east, side_west, bottom);
    }
}
