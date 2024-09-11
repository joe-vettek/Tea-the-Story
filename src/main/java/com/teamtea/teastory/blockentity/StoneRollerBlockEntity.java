package com.teamtea.teastory.blockentity;

import com.teamtea.teastory.container.StoneRollerContainer;
import com.teamtea.teastory.recipe.stone_mill.StoneRollerRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import com.teamtea.teastory.registry.RecipeRegister;
import com.teamtea.teastory.registry.BlockEntityRegister;
import com.teamtea.teastory.blockentity.base.NormalContainerTileEntity;

public class StoneRollerBlockEntity extends NormalContainerTileEntity {
    private int woodenFrameAngel = 0;
    private int stoneAngel = 0;
    private boolean isWorking = false;

    private int processTicks = 0;
    private StoneRollerRecipe currentRecipe;

    private final ItemStackHandler inputInventory;
    private final ItemStackHandler outputInventory;

    public StoneRollerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegister.STONE_ROLLER_TYPE.get(), pos, state);
        this.inputInventory = new SyncedItemStackHandler();
        this.outputInventory = new SyncedItemStackHandler(3);
    }


    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider pRegistries) {
        super.loadAdditional(nbt, pRegistries);
        this.inputInventory.deserializeNBT(pRegistries, nbt.getCompound("InputInventory"));
        this.outputInventory.deserializeNBT(pRegistries, nbt.getCompound("OutputInventory"));
        this.processTicks = nbt.getInt("ProcessTicks");
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        compound.put("InputInventory", this.inputInventory.serializeNBT(pRegistries));
        compound.put("OutputInventory", this.outputInventory.serializeNBT(pRegistries));
        compound.putInt("ProcessTicks", this.processTicks);
        super.saveAdditional(compound, pRegistries);
    }


    public ItemStackHandler getInputInventory() {
        return inputInventory;
    }

    public ItemStackHandler getOutputInventory() {
        return outputInventory;
    }

    public boolean isEmpty() {
        return inputInventory.getStackInSlot(0).isEmpty();
    }


    public ItemStack getStackInSlot(int index) {
        if (index == 0) {
            return this.inputInventory.getStackInSlot(0);
        } else if (index > 0 && index < 4) {
            return this.outputInventory.getStackInSlot(index - 1);
        }
        return ItemStack.EMPTY;
    }


    public static void tick(Level worldIn, BlockPos pos, BlockState blockState, StoneRollerBlockEntity stoneRollerTileEntity) {
        ItemStack input = stoneRollerTileEntity.getStackInSlot(0);
        if (input.isEmpty()) {
            stoneRollerTileEntity.setProcessTicks(0);
            stoneRollerTileEntity.currentRecipe = null;
            return;
        }
        var warp = new RecipeWrapper(stoneRollerTileEntity.inputInventory);
        if (stoneRollerTileEntity.currentRecipe == null || !stoneRollerTileEntity.currentRecipe.matches(warp, stoneRollerTileEntity.getLevel())) {
            var cc = stoneRollerTileEntity.getLevel().getRecipeManager().getRecipeFor(RecipeRegister.STONE_ROLLER.get(), warp, stoneRollerTileEntity.getLevel()).orElse(null);
            stoneRollerTileEntity.currentRecipe = cc == null ? null : cc.value();
        }

        if (stoneRollerTileEntity.currentRecipe != null) {
            stoneRollerTileEntity.woodenFrameAngel += 3;
            stoneRollerTileEntity.woodenFrameAngel %= 360;
            stoneRollerTileEntity.stoneAngel += 4;
            stoneRollerTileEntity.stoneAngel %= 360;
            stoneRollerTileEntity.isWorking = true;
            boolean flag = true;
            for (ItemStack out : stoneRollerTileEntity.currentRecipe.getOutputItems()) {
                if (!ItemHandlerHelper.insertItem(stoneRollerTileEntity.outputInventory, out.copy(), true).isEmpty()) {
                    flag = false;
                }
            }
            if (flag) {
                if (++stoneRollerTileEntity.processTicks >= stoneRollerTileEntity.currentRecipe.getWorkTime()) {
                    for (ItemStack out : stoneRollerTileEntity.currentRecipe.getOutputItems()) {
                        ItemHandlerHelper.insertItem(stoneRollerTileEntity.outputInventory, out.copy(), false);
                    }
                    stoneRollerTileEntity.inputInventory.extractItem(0, 1, false);

                    stoneRollerTileEntity.processTicks = 0;
                }
            }
        } else {
            stoneRollerTileEntity.setProcessTicks(0);
            stoneRollerTileEntity.isWorking = false;
        }
    }

    private void setProcessTicks(int ticks) {
        if (ticks != this.processTicks) {
            this.processTicks = ticks;
            setChanged();
        }
    }

    public boolean isCompleted() {
        return this.currentRecipe == null;
    }

    public int getWoodenFrameAngel() {
        return woodenFrameAngel;
    }

    public int getStoneAngel() {
        return stoneAngel;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public int getProcessTicks() {
        return processTicks;
    }

    public StoneRollerRecipe getCurrentRecipe() {
        return currentRecipe;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player p_39956_) {
        return new StoneRollerContainer(id, playerInventory, worldPosition, level);
    }
}
