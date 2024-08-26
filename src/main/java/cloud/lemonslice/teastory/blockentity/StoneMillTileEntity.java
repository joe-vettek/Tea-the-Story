package cloud.lemonslice.teastory.blockentity;


import cloud.lemonslice.teastory.container.StoneMillContainer;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import xueluoanping.teastory.craft.BlockEntityRecipeWrapper;
import cloud.lemonslice.teastory.recipe.stone_mill.StoneMillRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import xueluoanping.teastory.RecipeRegister;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.entity.NormalContainerTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class StoneMillTileEntity extends NormalContainerTileEntity {
    private int angel = 0;
    private boolean isWorking = false;

    private int processTicks = 0;
    private StoneMillRecipe currentRecipe;

    private final ItemStackHandler inputInventory = new SyncedItemStackHandler();
    private final ItemStackHandler outputInventory = new SyncedItemStackHandler(3);
    private final FluidTank fluidTank = new SyncedFluidTank(2000);

    public StoneMillTileEntity(BlockPos pos, BlockState state) {
        super(TileEntityTypeRegistry.STONE_MILL_TYPE.get(), pos, state);
    }


    // read
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.inputInventory.deserializeNBT(nbt.getCompound("InputInventory"));
        this.outputInventory.deserializeNBT(nbt.getCompound("OutputInventory"));
        this.fluidTank.readFromNBT(nbt.getCompound("FluidTank"));
        // this.processTicks = nbt.getInt("ProcessTicks");
    }

    // write
    @Override
    protected void saveAdditional(CompoundTag compound) {
        compound.put("InputInventory", this.inputInventory.serializeNBT());
        compound.put("OutputInventory", this.outputInventory.serializeNBT());
        compound.put("FluidTank", this.fluidTank.writeToNBT(new CompoundTag()));
        compound.putInt("ProcessTicks", this.processTicks);
        super.saveAdditional(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (!this.isRemoved()) {
            if (ForgeCapabilities.ITEM_HANDLER.equals(cap)) {
                if (side == Direction.DOWN)
                    return LazyOptional.of(() -> outputInventory).cast();
                else
                    return LazyOptional.of(() -> inputInventory).cast();
            } else if (ForgeCapabilities.FLUID_HANDLER.equals(cap)) {
                return LazyOptional.of(this::getFluidTank).cast();
            }
        }
        return super.getCapability(cap, side);
    }


    public FluidTank getFluidTank() {
        return fluidTank;
    }


    public static void tick(Level worldIn, BlockPos pos, BlockState blockState, StoneMillTileEntity stoneMillTileEntity) {
        if (worldIn == null || stoneMillTileEntity.isRemoved()) return;

        ItemStack input = stoneMillTileEntity.inputInventory.getStackInSlot(0);
        if (input.isEmpty()) {
            stoneMillTileEntity.setProcessTicks(0);
            stoneMillTileEntity.currentRecipe = null;
            return;
        }
        var warp = new BlockEntityRecipeWrapper(stoneMillTileEntity.inputInventory, stoneMillTileEntity);
        if (stoneMillTileEntity.currentRecipe == null || !stoneMillTileEntity.currentRecipe.matches(warp, worldIn)) {
            stoneMillTileEntity.currentRecipe = worldIn.getRecipeManager().getRecipeFor(RecipeRegister.STONE_MILL.get(), warp, worldIn).orElse(null);
        }

        if (stoneMillTileEntity.currentRecipe != null) {
            stoneMillTileEntity.angel += 3;
            stoneMillTileEntity.angel %= 360;
            boolean flag = true;
            for (ItemStack out : stoneMillTileEntity.currentRecipe.getOutputItems()) {
                if (!ItemHandlerHelper.insertItem(stoneMillTileEntity.outputInventory, out.copy(), true).isEmpty()) {
                    flag = false;
                }
            }
            if (flag) {
                if (++stoneMillTileEntity.processTicks >= stoneMillTileEntity.currentRecipe.getWorkTime()) {
                    for (ItemStack out : stoneMillTileEntity.currentRecipe.getOutputItems()) {
                        ItemHandlerHelper.insertItem(stoneMillTileEntity.outputInventory, out.copy(), false);
                    }
                    stoneMillTileEntity.inputInventory.extractItem(0, 1, false);

                    if (!stoneMillTileEntity.currentRecipe.getOutputFluid().isEmpty()) {
                        var fluidStacks = stoneMillTileEntity.currentRecipe.getOutputFluid();
                        if (stoneMillTileEntity.fluidTank.isEmpty()
                                || stoneMillTileEntity.fluidTank.getFluid().getFluid() == fluidStacks.getFluid())
                        // for (FluidStack fluidStack : fluidStacks)
                        {
                            stoneMillTileEntity.fluidTank.fill(fluidStacks, IFluidHandler.FluidAction.EXECUTE);
                        }
                    }

                    if (!stoneMillTileEntity.currentRecipe.getOutputFluid().isEmpty()) {
                        FluidUtil.getFluidHandler(worldIn, pos.below().relative(stoneMillTileEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)), Direction.UP).ifPresent(handler ->
                                handler.fill(stoneMillTileEntity.currentRecipe.getOutputFluid(), IFluidHandler.FluidAction.EXECUTE));
                    }
                    stoneMillTileEntity.processTicks = 0;
                }
            }
        } else {
            stoneMillTileEntity.setProcessTicks(0);
        }
    }

    public Fluid getOutputFluid() {
        if (currentRecipe != null) {
            return currentRecipe.getOutputFluid().getFluid();
        } else return Fluids.EMPTY;
    }

    private void setProcessTicks(int ticks) {
        if (ticks != this.processTicks) {
            this.processTicks = ticks;
            inventoryChanged();
        }
    }

    public boolean isCompleted() {
        return this.currentRecipe == null;
    }

    public int getAngel() {
        return angel;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public int getProcessTicks() {
        return processTicks;
    }

    public StoneMillRecipe getCurrentRecipe() {
        return currentRecipe;
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player p_39956_) {
        return new StoneMillContainer(id, playerInventory, worldPosition, level);
    }
}
