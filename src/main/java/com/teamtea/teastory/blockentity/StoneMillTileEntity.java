package com.teamtea.teastory.blockentity;


import com.teamtea.teastory.container.StoneMillContainer;
import net.minecraft.core.HolderLookup;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import com.teamtea.teastory.craft.BlockEntityRecipeWrapper;
import com.teamtea.teastory.recipe.stone_mill.StoneMillRecipe;
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
import com.teamtea.teastory.RecipeRegister;
import com.teamtea.teastory.TileEntityTypeRegistry;
import com.teamtea.teastory.block.entity.NormalContainerTileEntity;

import javax.annotation.Nullable;


public class StoneMillTileEntity extends NormalContainerTileEntity {
    private int angel = 0;
    private boolean isWorking = false;

    private int processTicks = 0;
    private StoneMillRecipe currentRecipe;

    private final ItemStackHandler inputInventory;
    private final ItemStackHandler outputInventory;
    private final FluidTank fluidTank;

    public StoneMillTileEntity(BlockPos pos, BlockState state) {
        super(TileEntityTypeRegistry.STONE_MILL_TYPE.get(), pos, state);
        this.inputInventory = new SyncedItemStackHandler();
        this.outputInventory = new SyncedItemStackHandler(3);
        this.fluidTank = new SyncedFluidTank(2000);
    }


    // read
    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider pRegistries) {
        super.loadAdditional(nbt, pRegistries);
        this.inputInventory.deserializeNBT(pRegistries, nbt.getCompound("InputInventory"));
        this.outputInventory.deserializeNBT(pRegistries, nbt.getCompound("OutputInventory"));
        this.fluidTank.readFromNBT(pRegistries, nbt.getCompound("FluidTank"));
        // this.processTicks = nbt.getInt("ProcessTicks");
    }

    // write
    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        compound.put("InputInventory", this.inputInventory.serializeNBT(pRegistries));
        compound.put("OutputInventory", this.outputInventory.serializeNBT(pRegistries));
        compound.put("FluidTank", this.fluidTank.writeToNBT(pRegistries, new CompoundTag()));
        compound.putInt("ProcessTicks", this.processTicks);
        super.saveAdditional(compound, pRegistries);
    }


    public ItemStackHandler getInputInventory() {
        return inputInventory;
    }

    public ItemStackHandler getOutputInventory() {
        return outputInventory;
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
            var c = worldIn.getRecipeManager().getRecipeFor(RecipeRegister.STONE_MILL.get(), warp, worldIn);
            c.ifPresent(stoneMillRecipeRecipeHolder -> stoneMillTileEntity.currentRecipe = stoneMillRecipeRecipeHolder.value());
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
