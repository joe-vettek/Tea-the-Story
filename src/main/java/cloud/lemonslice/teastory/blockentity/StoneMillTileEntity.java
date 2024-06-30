package cloud.lemonslice.teastory.blockentity;


import cloud.lemonslice.teastory.container.StoneMillContainer;
import cloud.lemonslice.teastory.recipe.stone_mill.StoneMillRecipe;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import xueluoanping.teastory.RecipeRegister;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class StoneMillTileEntity extends NormalContainerTileEntity {
    private int angel = 0;
    private boolean isWorking = false;

    private int processTicks = 0;
    private StoneMillRecipe currentRecipe;

    private ItemStackHandler inputInventory = new ItemStackHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            StoneMillTileEntity.this.inventoryChanged();
        }
    };
    private ItemStackHandler outputInventory = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            StoneMillTileEntity.this.inventoryChanged();
        }
    };
    private FluidTank fluidTank = new FluidTank(2000) {
        @Override
        protected void onContentsChanged() {
            StoneMillTileEntity.this.inventoryChanged();
        }
    };

    public StoneMillTileEntity() {
        super(TileEntityTypeRegistry.STONE_MILL);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.inputInventory.deserializeNBT(nbt.getCompound("InputInventory"));
        this.outputInventory.deserializeNBT(nbt.getCompound("OutputInventory"));
        this.fluidTank.readFromNBT(nbt.getCompound("FluidTank"));
        this.processTicks = nbt.getInt("ProcessTicks");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("InputInventory", this.inputInventory.serializeNBT());
        compound.put("OutputInventory", this.outputInventory.serializeNBT());
        compound.put("FluidTank", this.fluidTank.writeToNBT(new CompoundNBT()));
        compound.putInt("ProcessTicks", this.processTicks);
        return super.write(compound);
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


    @Override
    public int getSizeInventory() {
        return 4;
    }

    @Override
    public boolean isEmpty() {
        return inputInventory.getStackInSlot(0).isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (index == 0) {
            return this.inputInventory.getStackInSlot(0);
        } else if (index > 0 && index < 4) {
            return this.outputInventory.getStackInSlot(index - 1);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return getStackInSlot(index).split(count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = getStackInSlot(index).copy();
        setInventorySlotContents(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index == 0) {
            inputInventory.setStackInSlot(0, stack);
        } else if (index > 0 && index < 4) {
            outputInventory.setStackInSlot(index - 1, stack);
        }
    }


    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < getSizeInventory(); i++) {
            removeStackFromSlot(i);
        }
    }

    public FluidTank getFluidTank() {
        return fluidTank;
    }


    public void tick() {
        if (this.getLevel() == null || this.isRemoved()) return;

        ItemStack input = getStackInSlot(0);
        if (input.isEmpty()) {
            setProcessTicks(0);
            this.currentRecipe = null;
            return;
        }

        if (this.currentRecipe == null || !this.currentRecipe.matches(this, getLevel())) {
            this.currentRecipe = this.getLevel().getRecipeManager().getRecipeFor(RecipeRegister.STONE_MILL.get(), this, this.getLevel()).orElse(null);
        }

        if (this.currentRecipe != null) {
            angel += 3;
            angel %= 360;
            boolean flag = true;
            for (ItemStack out : this.currentRecipe.getOutputItems()) {
                if (!ItemHandlerHelper.insertItem(this.outputInventory, out.copy(), true).isEmpty()) {
                    flag = false;
                }
            }
            if (flag) {
                if (++this.processTicks >= currentRecipe.getWorkTime()) {
                    for (ItemStack out : this.currentRecipe.getOutputItems()) {
                        ItemHandlerHelper.insertItem(this.outputInventory, out.copy(), false);
                    }
                    this.inputInventory.extractItem(0, 1, false);

                    if (!currentRecipe.getInputFluid().getMatchingFluidStacks().isEmpty()) {
                        var fluidStacks = currentRecipe.getInputFluid().getMatchingFluidStacks();
                        for (FluidStack fluidStack : fluidStacks) {
                            if (fluidStack.getFluid() == this.fluidTank.getFluid().getFluid()) {
                                this.fluidTank.drain(fluidStack.getAmount(), IFluidHandler.FluidAction.EXECUTE);
                            }
                        }
                    }

                    if (!this.currentRecipe.getOutputFluid().isEmpty()) {
                        FluidUtil.getFluidHandler(world, pos.down().offset(this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)), Direction.UP).ifPresent(handler ->
                                handler.fill(this.currentRecipe.getOutputFluid(), IFluidHandler.FluidAction.EXECUTE));
                    }
                    processTicks = 0;
                }
            }
        } else {
            setProcessTicks(0);
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


    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player p_39956_) {
        return new StoneMillContainer(id, playerInventory, pos, world);
    }
}
