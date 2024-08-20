package cloud.lemonslice.teastory.blockentity;

import cloud.lemonslice.teastory.container.StoneRollerContainer;
import cloud.lemonslice.teastory.recipe.stone_mill.StoneRollerRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import xueluoanping.teastory.RecipeRegister;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.entity.NormalContainerTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StoneRollerTileEntity extends NormalContainerTileEntity {
    private int woodenFrameAngel = 0;
    private int stoneAngel = 0;
    private boolean isWorking = false;

    private int processTicks = 0;
    private StoneRollerRecipe currentRecipe;

    private ItemStackHandler inputInventory = new SyncedItemStackHandler();
    private ItemStackHandler outputInventory = new SyncedItemStackHandler(3);

    public StoneRollerTileEntity(BlockPos pos, BlockState state) {
        super(TileEntityTypeRegistry.STONE_ROLLER_TYPE.get(), pos, state);
    }

    // read
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.inputInventory.deserializeNBT(nbt.getCompound("InputInventory"));
        this.outputInventory.deserializeNBT(nbt.getCompound("OutputInventory"));
        this.processTicks = nbt.getInt("ProcessTicks");
    }

    // write
    @Override
    protected void saveAdditional(CompoundTag compound) {
        compound.put("InputInventory", this.inputInventory.serializeNBT());
        compound.put("OutputInventory", this.outputInventory.serializeNBT());
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
            }
        }
        return super.getCapability(cap, side);
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


    public static void tick(Level worldIn, BlockPos pos, BlockState blockState, StoneRollerTileEntity stoneRollerTileEntity) {
        ItemStack input = stoneRollerTileEntity.getStackInSlot(0);
        if (input.isEmpty()) {
            stoneRollerTileEntity.setProcessTicks(0);
            stoneRollerTileEntity.currentRecipe = null;
            return;
        }
        var warp = new RecipeWrapper(stoneRollerTileEntity.inputInventory);
        if (stoneRollerTileEntity.currentRecipe == null || !stoneRollerTileEntity.currentRecipe.matches(warp, stoneRollerTileEntity.getLevel())) {
            stoneRollerTileEntity.currentRecipe = stoneRollerTileEntity.getLevel().getRecipeManager().getRecipeFor(RecipeRegister.STONE_ROLLER.get(), warp, stoneRollerTileEntity.getLevel()).orElse(null);
        }

        if (stoneRollerTileEntity.currentRecipe != null) {
            stoneRollerTileEntity.woodenFrameAngel += 3;
            stoneRollerTileEntity.woodenFrameAngel %= 360;
            stoneRollerTileEntity.stoneAngel += 4;
            stoneRollerTileEntity.stoneAngel %= 360;
            stoneRollerTileEntity.isWorking=true;
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
            stoneRollerTileEntity.isWorking=false;
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
