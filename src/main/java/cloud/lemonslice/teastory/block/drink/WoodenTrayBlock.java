package cloud.lemonslice.teastory.block.drink;


import cloud.lemonslice.teastory.blockentity.TeaCupTileEntity;
import cloud.lemonslice.teastory.helper.VoxelShapeHelper;
import com.google.common.collect.Lists;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;
import xueluoanping.teastory.ItemRegister;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.client.SoundEventsRegistry;

import java.util.List;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

public class WoodenTrayBlock extends Block  implements EntityBlock
{
    public static final IntegerProperty DRINK = IntegerProperty.create("drink", 0, 3);
    public static final IntegerProperty CUP = IntegerProperty.create("cup", 0, 3);

    private static final VoxelShape TRAY = VoxelShapeHelper.createVoxelShape(0, 0, 0, 16, 2, 16);
    private static final VoxelShape WITH_CUP = VoxelShapeHelper.createVoxelShape(0, 0, 0, 16, 6, 16);

    public WoodenTrayBlock(BlockBehaviour.Properties pProperties)
    {
        super(pProperties);
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(CUP) > 0)
        {
            return WITH_CUP;
        }
        else
        {
            return TRAY;
        }
    }



    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos)
    {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(CUP, DRINK));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        ItemStack held = player.getItemInHand(handIn);
        var te = worldIn.getBlockEntity(pos);
        if (te instanceof TeaCupTileEntity)
        {
            if (held.isEmpty())
            {
                int index = state.getValue(CUP) - 1;
                if (index > -1)
                {
                    ItemStack itemStack = getCup((TeaCupTileEntity) te, index);
                    ItemHandlerHelper.giveItemToPlayer(player, itemStack);
                    if (itemStack.getItem() == ItemRegister.PORCELAIN_CUP.get())
                    {
                        worldIn.setBlockAndUpdate(pos, state.setValue(CUP, state.getValue(CUP) - 1));
                    }
                    else
                    {
                        worldIn.setBlockAndUpdate(pos, state.setValue(CUP, state.getValue(CUP) - 1).setValue(DRINK, state.getValue(DRINK) - 1));
                    }
                }
            }
            else
            {
                int index = state.getValue(CUP);
                if (!setCup((TeaCupTileEntity) te, index, held, worldIn, pos, state))
                {
                    if (held.getItem() == TileEntityTypeRegistry.PORCELAIN_TEAPOT.get())
                    {
                        FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(player.getItemInHand(handIn), 1)).ifPresent(item ->
                        {
                            for (int i = 0; i < 3; i++)
                            {
                                FluidTank tank = ((TeaCupTileEntity) te).getFluidTank(i);
                                if (tank.isEmpty())
                                {
                                    if (FluidUtil.interactWithFluidHandler(player, handIn, tank))
                                    {
                                        if (state.getValue(DRINK) + 1 <= 3)
                                        {
                                            worldIn.setBlockAndUpdate(pos, state.setValue(DRINK, state.getValue(DRINK) + 1));
                                        }
                                        worldIn.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 0.5F, 0.9F);
                                    }
                                    break;
                                }
                            }
                        });
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }


    public boolean setCup(TeaCupTileEntity te, int index, ItemStack itemStack, Level world, BlockPos pos, BlockState state)
    {
        if (index >= 3)
        {
            return false;
        }
        if (itemStack.getItem() == ItemRegister.PORCELAIN_CUP_DRINK.get() || itemStack.getItem() == ItemRegister.PORCELAIN_CUP.get() )
        {
            FluidStack stack = FluidUtil.getFluidContained(itemStack).orElse(FluidStack.EMPTY);
            if (!stack.isEmpty())
            {
                if (state.getValue(DRINK) + 1 <= 3)
                {
                    world.setBlockAndUpdate(pos, state.setValue(CUP, state.getValue(CUP) + 1).setValue(DRINK, state.getValue(DRINK) + 1));
                    world.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 0.5F, 0.9F);
                }
            }
            else
            {
                world.setBlockAndUpdate(pos, state.setValue(CUP, state.getValue(CUP) + 1));
                world.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 0.5F, 0.9F);
            }
            te.setFluidTank(index, stack);
            itemStack.shrink(1);
            return true;
        }
        else return false;
    }

    public ItemStack getCup(TeaCupTileEntity te, int index)
    {
        if (index > 3)
        {
            return ItemStack.EMPTY;
        }
        FluidStack fluidStack = te.getFluidTank(index).getFluidInTank(0);
        if (fluidStack.isEmpty())
        {
            return new ItemStack(ItemRegister.PORCELAIN_CUP.get() );
        }
        else
        {
            ItemStack itemStack = new ItemStack(ItemRegister.PORCELAIN_CUP_DRINK.get() );
            CompoundTag fluidTag = new CompoundTag();
            fluidStack.writeToNBT(fluidTag);
            itemStack.getOrCreateTag().put(FLUID_NBT_KEY, fluidTag);
            return itemStack;
        }
    }


    // onReplaced
    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean pMovedByPiston)
    {
        if (state.getBlock() != newState.getBlock())
        {
            var tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof TeaCupTileEntity)
            {
                for (int i = 0; i < 3; i++)
                {
                    if (!worldIn.isClientSide() && state.getValue(CUP) > i)
                    {
                        Block.popResource(worldIn, pos, getCup((TeaCupTileEntity) tileEntity, i));
                    }
                }
            }
        }
        super.onRemove(state, worldIn, pos, newState, pMovedByPiston);
    }



    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return TileEntityTypeRegistry.WOODEN_TRAY_TYPE.get().create(blockPos, blockState);
    }
}
