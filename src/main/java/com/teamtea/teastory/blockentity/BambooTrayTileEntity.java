package com.teamtea.teastory.blockentity;

import com.teamtea.teastory.block.craft.BambooTrayMode;
import com.teamtea.teastory.block.craft.CatapultBoardBlockWithTray;
import com.teamtea.teastory.block.craft.IStoveBlock;
import com.teamtea.teastory.container.BambooTrayContainer;
import com.teamtea.teastory.recipe.bamboo_tray.BambooTraySingleInRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;
import com.teamtea.teastory.RecipeRegister;
import com.teamtea.teastory.TileEntityTypeRegistry;
import com.teamtea.teastory.block.entity.NormalContainerTileEntity;

import javax.annotation.Nullable;
import java.util.Random;

public class BambooTrayTileEntity extends NormalContainerTileEntity {
    private int processTicks = 0;
    private int totalTicks = 0;

    private int randomSeed = new Random().nextInt(943943);
    private int doubleClickTicks = 0;

    private BambooTrayMode mode = BambooTrayMode.OUTDOORS;

    private final ItemStackHandler containerInventory = createHandler();
    private BambooTraySingleInRecipe currentRecipe;

    public BambooTrayTileEntity(BlockPos pos, BlockState state) {
        super(TileEntityTypeRegistry.BAMBOO_TRAY_TYPE.get(), pos, state);
    }


    // read
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(tag, pRegistries);
        ((INBTSerializable<CompoundTag>) this.containerInventory).deserializeNBT(pRegistries, tag.getCompound("Inv"));
        // this.processTicks = tag.getInt("ProcessTicks");
        this.totalTicks = tag.getInt("TotalTicks");
        this.mode = BambooTrayMode.values()[tag.getInt("Mode")];
    }

    // write
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider pRegistries) {
        tag.put("Inv", containerInventory.serializeNBT(pRegistries));
        tag.putInt("ProcessTicks", processTicks);
        tag.putInt("TotalTicks", totalTicks);
        tag.putInt("Mode", mode.ordinal());
        super.saveAdditional(tag, pRegistries);
    }


    public ItemStackHandler getContainerInventory() {
        return containerInventory;
    }


    public static void tick(Level worldIn, BlockPos pos, BlockState blockState, BambooTrayTileEntity tileEntity) {
        if (tileEntity.doubleClickTicks > 0) {
            tileEntity.doubleClickTicks--;
        }
        float temp = tileEntity.getLevel().getBiome(tileEntity.getBlockPos()).value().getTemperature(tileEntity.getBlockPos());
        float rainfall = tileEntity.getLevel().getBiome(tileEntity.getBlockPos()).value().getModifiedClimateSettings().downfall();
        switch (BambooTrayMode.getMode(tileEntity.getLevel(), tileEntity.getBlockPos())) {
            case IN_RAIN:
                tileEntity.process(RecipeRegister.BAMBOO_TRAY_IN_RAIN.get(), 1);
                tileEntity.mode = BambooTrayMode.IN_RAIN;
                return;
            case OUTDOORS:
                if (!tileEntity.isWorldRaining())
                    tileEntity.process(RecipeRegister.BAMBOO_TRAY_OUTDOORS.get(), 1);
                tileEntity.mode = BambooTrayMode.OUTDOORS;
                return;
            case INDOORS:
                tileEntity.process(RecipeRegister.BAMBOO_TRAY_INDOORS.get(), 1);
                tileEntity.mode = BambooTrayMode.INDOORS;
                return;
            case BAKE:
                tileEntity.process(RecipeRegister.BAMBOO_TRAY_BAKE.get(), 1);
                tileEntity.mode = BambooTrayMode.BAKE;
                return;
            case PROCESS:
                // TODO
                tileEntity.mode = BambooTrayMode.PROCESS;
        }
    }

    private boolean process(RecipeType<? extends BambooTraySingleInRecipe> recipeType, float coefficient) {
        ItemStack input = getInput();
        if (input.isEmpty()) {
            setToZero();
            return false;
        }
        if (this.currentRecipe == null || !this.currentRecipe.getIngredient().test(input) || mode != BambooTrayMode.getMode(this.getLevel(), this.getBlockPos())) {
            this.getLevel().getRecipeManager().getRecipeFor(recipeType, new RecipeWrapper(this.containerInventory), this.getLevel()).ifPresent(r -> this.currentRecipe = r.value());
        }
        if (currentRecipe != null && !getOutput().isEmpty()) {
            this.refreshTotalTicks(currentRecipe.getWorkTime(), coefficient);
            if (this.mode == BambooTrayMode.BAKE) {
                this.processTicks += ((IStoveBlock) this.getLevel().getBlockState(getBlockPos().below()).getBlock()).getFuelPower();
            } else {
                this.processTicks++;
            }
            if (this.processTicks >= this.totalTicks) {
                ItemStack output = this.getOutput();
                output.setCount(input.getCount());
                this.containerInventory.setStackInSlot(0, output);
                this.processTicks = 0;
                if (this.getBlockState().getBlock() instanceof CatapultBoardBlockWithTray && getLevel().getBlockState(getBlockPos()).isRedstoneConductor(getLevel(), getBlockPos())) {
                    level.setBlockAndUpdate(getBlockPos(), this.getBlockState().setValue(CatapultBoardBlockWithTray.ENABLED, true));
                    CatapultBoardBlockWithTray.shoot(getLevel(), getBlockPos());
                    getLevel().scheduleTick(getBlockPos(), this.getBlockState().getBlock(), 5);
                }
                this.setChanged();
                this.currentRecipe=null;
            }
            return true;
        }
        setToZero();
        return false;
    }

    public ItemStack getInput() {
        return this.containerInventory.getStackInSlot(0).copy();
    }

    public ItemStack getOutput() {
        if (currentRecipe == null) {
            return ItemStack.EMPTY;
        }
        return this.currentRecipe.getRecipeOutput().copy();
    }

    public int getTotalTicks() {
        return this.totalTicks;
    }

    public int getProcessTicks() {
        return this.processTicks;
    }

    public BambooTrayMode getMode() {
        return this.mode;
    }

    public @NotNull RecipeType<?> getRecipeType() {
        switch (this.mode) {
            case IN_RAIN:
                return RecipeRegister.BAMBOO_TRAY_IN_RAIN.get();
            case OUTDOORS:
                return RecipeRegister.BAMBOO_TRAY_OUTDOORS.get();
            case INDOORS:
                return RecipeRegister.BAMBOO_TRAY_INDOORS.get();
            default:
                return RecipeRegister.BAMBOO_TRAY_BAKE.get();
        }
    }

    public int getRandomSeed() {
        return this.randomSeed;
    }

    public void refreshSeed() {
        this.randomSeed = (int) (Math.random() * 10000);
    }

    public boolean isWorldRaining() {
        return this.getLevel().isRaining();
    }

    public boolean isWorking() {
        if (this.currentRecipe == null) {
            return false;
        }
        return !this.currentRecipe.getRecipeOutput().isEmpty();
    }

    public void singleClickStart() {
        this.doubleClickTicks = 10;
        this.setChanged();
    }

    public boolean isDoubleClick() {
        return doubleClickTicks > 0;
    }

    private void refreshTotalTicks(int basicTicks, float coefficient) {
        this.totalTicks = (int) (this.getInput().getCount() * basicTicks * coefficient);
    }

    private void setToZero() {
        if (this.processTicks != 0) {
            this.processTicks = 0;
            this.setChanged();
        }
    }


    private ItemStackHandler createHandler() {
        return new SyncedItemStackHandler();
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player p_39956_) {
        return new BambooTrayContainer(id, playerInventory, worldPosition, level);
    }
}
