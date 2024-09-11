package com.teamtea.teastory.blockentity;

import com.teamtea.teastory.container.DrinkMakerContainer;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.items.ItemStackHandler;
import com.teamtea.teastory.recipe.drink.DrinkRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import com.teamtea.teastory.registry.RecipeRegister;
import com.teamtea.teastory.registry.BlockEntityRegister;
import com.teamtea.teastory.blockentity.base.NormalContainerTileEntity;
import com.teamtea.teastory.recipe.MultiRecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class DrinkMakerBlockEntity extends NormalContainerTileEntity {
    private final ItemStackHandler ingredientsInventory;
    private final ItemStackHandler residuesInventory;
    private final ItemStackHandler containerInventory;
    private final ItemStackHandler inputInventory;
    private final ItemStackHandler outputInventory;

    private int processTicks = 0;
    private static final int totalTicks = 200;

    private DrinkRecipe currentRecipe;

    public DrinkMakerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegister.DRINK_MAKER_TYPE.get(), pos, state);
        ingredientsInventory = createItemHandler(4);
        residuesInventory = createItemHandler(4);
        containerInventory = createContainerItemHandler(1);
        inputInventory = createItemHandler(1);
        outputInventory = createContainerItemHandler(1);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(tag, pRegistries);
        this.ingredientsInventory.deserializeNBT(pRegistries, tag.getCompound("Ingredients"));
        this.residuesInventory.deserializeNBT(pRegistries, tag.getCompound("Residues"));
        this.containerInventory.deserializeNBT(pRegistries, tag.getCompound("Container"));
        this.inputInventory.deserializeNBT(pRegistries, tag.getCompound("Input"));
        this.outputInventory.deserializeNBT(pRegistries, tag.getCompound("Output"));
        this.processTicks = tag.getInt("ProcessTicks");
    }

    // write
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider pRegistries) {
        tag.put("Ingredients", (ingredientsInventory).serializeNBT(pRegistries));
        tag.put("Residues", (residuesInventory).serializeNBT(pRegistries));
        tag.put("Container", (containerInventory).serializeNBT(pRegistries));
        tag.put("Input", (inputInventory).serializeNBT(pRegistries));
        tag.put("Output", (outputInventory).serializeNBT(pRegistries));

        tag.putInt("ProcessTicks", processTicks);
        super.saveAdditional(tag, pRegistries);
    }


    private boolean isEnoughAmount() {
        if (currentRecipe != null) {
            int n = (int) Math.ceil(1.0F * getFluidAmount() / currentRecipe.getFluidAmount());
            for (int i = 0; i < 4; i++) {
                if (!getStackInSlot(i).isEmpty() && getStackInSlot(i).getCount() < n) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public ItemStackHandler getIngredientsInventory() {
        return ingredientsInventory;
    }

    public ItemStackHandler getResiduesInventory() {
        return residuesInventory;
    }

    public static void tick(Level worldIn, BlockPos pos, BlockState blockState, DrinkMakerBlockEntity tileEntity) {
        // TeaStory.logger(worldIn.getGameTime());
        if (tileEntity.getFluidAmount() != 0 && !tileEntity.isIngredientsEmpty()) {
            var warp = new MultiRecipeWrapper(tileEntity.ingredientsInventory, tileEntity.residuesInventory, tileEntity.containerInventory);
            if (tileEntity.currentRecipe == null || !tileEntity.currentRecipe.matches(warp, worldIn)) {
                worldIn.getRecipeManager().getRecipeFor(RecipeRegister.DRINK_MAKER.get(), warp, worldIn).ifPresent(drinkRecipeRecipeHolder -> tileEntity.currentRecipe = drinkRecipeRecipeHolder.value());

            }
            if (tileEntity.currentRecipe != null && tileEntity.isEnoughAmount()) {
                tileEntity.processTicks++;
                tileEntity.inventoryChanged();
                if (tileEntity.processTicks >= totalTicks) {
                    var inv = tileEntity.ingredientsInventory;
                    if (tileEntity.getFluidHandler() != null) {
                        var fluid = tileEntity.getFluidHandler();
                        var h = tileEntity.residuesInventory;
                        int n = (int) Math.ceil(tileEntity.getFluidAmount() * 1.0F / tileEntity.currentRecipe.getFluidIngredient().getFluids()[0].getAmount());
                        for (int i = 0; i < 4; i++) {
                            ItemStack residue = inv.getStackInSlot(i).getCraftingRemainingItem();
                            inv.extractItem(i, n, false);
                            if (!residue.isEmpty()) {
                                residue.setCount(n);
                                h.insertItem(i, residue, false);
                            }
                        }
                        if (tileEntity.containerInventory != null) {
                            var con = tileEntity.containerInventory;
                            if (con.getStackInSlot(0).getItem().getCraftingRemainingItem() == Items.BUCKET) {
                                con.setStackInSlot(0, tileEntity.currentRecipe.getFluidResult().getBucket().getDefaultInstance());
                            } else {
                                var f = new FluidStack(tileEntity.currentRecipe.getFluidResult(), tileEntity.getFluidAmount());
                                var c = fluid.getContainer();
                                c.getCapability(Capabilities.FluidHandler.ITEM).fill(f, IFluidHandler.FluidAction.EXECUTE);
                                // fluid.getContainer().getOrCreateTag().put(FLUID_NBT_KEY, nbt);
                            }
                        }
                        tileEntity.setToZero();
                    }

                }
            } else {
                tileEntity.setToZero();
            }
        } else {
            tileEntity.currentRecipe = null;
            tileEntity.setToZero();
        }

        Optional.ofNullable(tileEntity.getFluidHandler()).ifPresent(fluid ->
                Optional.of(tileEntity.inputInventory).ifPresent(in ->
                        Optional.of(tileEntity.outputInventory).ifPresent(out ->
                        {
                            {
                                ItemStack inputCup = in.getStackInSlot(0).copy();
                                ItemStack outputCup = out.getStackInSlot(0);
                                if (outputCup.isEmpty()) {
                                    FluidActionResult fluidActionResult = FluidUtil.tryFillContainerAndStow(inputCup, fluid, out, Integer.MAX_VALUE, null, true);
                                    if (!fluidActionResult.isSuccess()) {
                                        fluidActionResult = FluidUtil.tryEmptyContainerAndStow(inputCup, fluid, out, Integer.MAX_VALUE, null, true);
                                    }
                                    if (fluidActionResult.isSuccess()) {
                                        out.setStackInSlot(0, fluidActionResult.getResult());
                                        in.getStackInSlot(0).shrink(1);
                                    }
                                    if (fluid.getFluidInTank(0).isEmpty()) {
                                        Optional.of(tileEntity.containerInventory).ifPresent(container ->
                                        {
                                            if (container.getStackInSlot(0).hasCraftingRemainingItem())
                                                container.setStackInSlot(0, container.getStackInSlot(0).getCraftingRemainingItem());
                                        });
                                    }
                                }
                            }
                        })));
    }


    private ItemStackHandler createItemHandler(int size) {
        return new SyncedItemStackHandler(size);
    }

    private ItemStackHandler createContainerItemHandler(int size) {
        return new SyncedItemStackHandler(size) {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                // return !(stack.getItem() instanceof BucketItem);
                return FluidUtil.getFluidHandler(stack).isPresent();
            }
        };
    }

    private void setToZero() {
        if (this.processTicks != 0) {
            this.processTicks = 0;
            this.setChanged();
        }
    }

    public int getProcessTicks() {
        return processTicks;
    }

    public int getTotalTicks() {
        return totalTicks;
    }

    public int getFluidAmount() {
        return Optional.ofNullable(getFluidHandler()).map(h -> h.getFluidInTank(0).getAmount()).orElse(0);
    }

    @Nullable
    public DrinkRecipe getCurrentRecipe() {
        return currentRecipe;
    }

    public int getNeededAmount() {
        if (currentRecipe != null)
            return (int) Math.ceil(this.getFluidAmount() * 1.0F / currentRecipe.getFluidIngredient().getFluids()[0].getAmount());
        else
            return 0;
    }

    @Nullable
    public Component getFluidTranslation() {
        return getFluidHandler() != null ? getFluidHandler().getFluidInTank(0).getHoverName() : null;
    }

    public IFluidHandlerItem getFluidHandler() {
        return FluidUtil.getFluidHandler(this.containerInventory.getStackInSlot(0)).orElse(null);
    }

    public ItemStackHandler getContainerInventory() {
        return containerInventory;
    }

    public ItemStackHandler getInputInventory() {
        return inputInventory;
    }

    public ItemStackHandler getOutputInventory() {
        return outputInventory;
    }

    public List<ItemStack> getContent() {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(ingredientsInventory.getStackInSlot(i));
        }
        list.add(containerInventory.getStackInSlot(0));
        return list;
    }

    public Direction getFacing() {
        return this.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
    }


    public boolean isIngredientsEmpty() {
        return Optional.ofNullable(ingredientsInventory).map(h ->
        {
            for (int i = 0; i < 4; i++) {
                if (!h.getStackInSlot(i).isEmpty()) {
                    return false;
                }
            }
            return true;
        }).orElse(true);
    }


    public boolean isEmpty() {
        return isIngredientsEmpty() &&
                Optional.ofNullable(residuesInventory).map(h ->
                {
                    for (int i = 0; i < 4; i++) {
                        if (!h.getStackInSlot(i).isEmpty()) {
                            return false;
                        }
                    }
                    return true;
                }).orElse(true) &&
                Optional.ofNullable(containerInventory).map(h -> h.getStackInSlot(0).isEmpty()).orElse(true) &&
                Optional.ofNullable(inputInventory).map(h -> h.getStackInSlot(0).isEmpty()).orElse(true) &&
                Optional.ofNullable(outputInventory).map(h -> h.getStackInSlot(0).isEmpty()).orElse(true);
    }


    public ItemStack getStackInSlot(int index) {
        if (index < 4) {
            return Optional.ofNullable(ingredientsInventory).map(h -> h.getStackInSlot(index)).orElse(ItemStack.EMPTY);
        } else if (index < 8) {
            return Optional.ofNullable(residuesInventory).map(h -> h.getStackInSlot(index - 4)).orElse(ItemStack.EMPTY);
        } else if (index == 8) {
            return Optional.ofNullable(containerInventory).map(h -> h.getStackInSlot(0)).orElse(ItemStack.EMPTY);
        } else if (index == 9) {
            return Optional.ofNullable(inputInventory).map(h -> h.getStackInSlot(0)).orElse(ItemStack.EMPTY);
        } else {
            return Optional.ofNullable(outputInventory).map(h -> h.getStackInSlot(0)).orElse(ItemStack.EMPTY);
        }
    }


    public ItemStack decrStackSize(int index, int count) {
        if (index < 4) {
            return Optional.ofNullable(ingredientsInventory).map(h -> h.getStackInSlot(index).split(count)).orElse(ItemStack.EMPTY);
        } else if (index < 8) {
            return Optional.ofNullable(residuesInventory).map(h -> h.getStackInSlot(index - 4).split(count)).orElse(ItemStack.EMPTY);
        } else if (index == 8) {
            return Optional.ofNullable(containerInventory).map(h -> h.getStackInSlot(0).split(count)).orElse(ItemStack.EMPTY);
        } else if (index == 9) {
            return Optional.ofNullable(inputInventory).map(h -> h.getStackInSlot(0).split(count)).orElse(ItemStack.EMPTY);
        } else {
            return Optional.ofNullable(outputInventory).map(h -> h.getStackInSlot(0).split(count)).orElse(ItemStack.EMPTY);
        }
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new DrinkMakerContainer(id, playerInventory, getBlockPos(), getLevel());
    }
}
