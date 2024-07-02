package cloud.lemonslice.teastory.blockentity;

import cloud.lemonslice.teastory.container.DrinkMakerContainer;
import net.minecraft.world.item.Items;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.craft.BlockEntityRecipeWrapper;
import cloud.lemonslice.teastory.recipe.drink.DrinkRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import xueluoanping.teastory.RecipeRegister;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.entity.NormalContainerTileEntity;
import xueluoanping.teastory.craft.MultiRecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple.FLUID_NBT_KEY;

// import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

public class DrinkMakerTileEntity extends NormalContainerTileEntity {
    private final LazyOptional<ItemStackHandler> ingredientsInventory = LazyOptional.of(() -> this.createItemHandler(4));
    private final LazyOptional<ItemStackHandler> residuesInventory = LazyOptional.of(() -> this.createItemHandler(4));
    private final LazyOptional<ItemStackHandler> containerInventory = LazyOptional.of(() -> this.createContainerItemHandler(1));
    private final LazyOptional<ItemStackHandler> inputInventory = LazyOptional.of(() -> this.createItemHandler(1));
    private final LazyOptional<ItemStackHandler> outputInventory = LazyOptional.of(() -> this.createContainerItemHandler(1));

    private int processTicks = 0;
    private static final int totalTicks = 200;

    private DrinkRecipe currentRecipe;

    public DrinkMakerTileEntity(BlockPos pos, BlockState state) {
        super(TileEntityTypeRegistry.DRINK_MAKER_TYPE.get(), pos, state);
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (!this.isRemoved()) {
            if (ForgeCapabilities.ITEM_HANDLER.equals(cap)) {
                if (side == Direction.DOWN)
                    return residuesInventory.cast();
                else
                    return ingredientsInventory.cast();
            } else if (ForgeCapabilities.FLUID_HANDLER.equals(cap)) {
                return getFluidHandler().cast();
            }
        }
        return super.getCapability(cap, side);
    }

    // read
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.ingredientsInventory.ifPresent(h -> ((INBTSerializable<CompoundTag>) h).deserializeNBT(tag.getCompound("Ingredients")));
        this.residuesInventory.ifPresent(h -> ((INBTSerializable<CompoundTag>) h).deserializeNBT(tag.getCompound("Residues")));
        this.containerInventory.ifPresent(h -> ((INBTSerializable<CompoundTag>) h).deserializeNBT(tag.getCompound("Container")));
        this.inputInventory.ifPresent(h -> ((INBTSerializable<CompoundTag>) h).deserializeNBT(tag.getCompound("Input")));
        this.outputInventory.ifPresent(h -> ((INBTSerializable<CompoundTag>) h).deserializeNBT(tag.getCompound("Output")));
        this.processTicks = tag.getInt("ProcessTicks");
    }

    // write
    @Override
    protected void saveAdditional(CompoundTag tag) {
        ingredientsInventory.ifPresent(h -> tag.put("Ingredients", ((INBTSerializable<CompoundTag>) h).serializeNBT()));
        residuesInventory.ifPresent(h -> tag.put("Residues", ((INBTSerializable<CompoundTag>) h).serializeNBT()));
        containerInventory.ifPresent(h -> tag.put("Container", ((INBTSerializable<CompoundTag>) h).serializeNBT()));
        inputInventory.ifPresent(h -> tag.put("Input", ((INBTSerializable<CompoundTag>) h).serializeNBT()));
        outputInventory.ifPresent(h -> tag.put("Output", ((INBTSerializable<CompoundTag>) h).serializeNBT()));
        tag.putInt("ProcessTicks", processTicks);
        super.saveAdditional(tag);
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


    public static void tick(Level worldIn, BlockPos pos, BlockState blockState, DrinkMakerTileEntity tileEntity) {
        // TeaStory.logger(worldIn.getGameTime());
        if (tileEntity.getFluidAmount() != 0 && !tileEntity.isIngredientsEmpty()) {
            var warp = new MultiRecipeWrapper(tileEntity.ingredientsInventory.resolve().get(), tileEntity.residuesInventory.resolve().get(), tileEntity.containerInventory.resolve().get());
            if (tileEntity.currentRecipe == null || !tileEntity.currentRecipe.matches(warp, worldIn)) {
                tileEntity.currentRecipe = worldIn.getRecipeManager().getRecipeFor(RecipeRegister.DRINK_MAKER.get(), warp, worldIn).orElse(null);
            }
            if (tileEntity.currentRecipe != null && tileEntity.isEnoughAmount()) {
                tileEntity.processTicks++;
                tileEntity.inventoryChanged();
                if (tileEntity.processTicks >= totalTicks) {
                    if (tileEntity.ingredientsInventory.resolve().isPresent()) {
                        var inv = tileEntity.ingredientsInventory.resolve().get();
                        if (tileEntity.getFluidHandler().resolve().isPresent()){
                            var fluid=tileEntity.getFluidHandler().resolve().get();
                            if (tileEntity.residuesInventory.resolve().isPresent()){
                                var h=tileEntity.residuesInventory.resolve().get();
                                int n = (int) Math.ceil(tileEntity.getFluidAmount() * 1.0F / tileEntity.currentRecipe.getFluidIngredient().getMatchingFluidStacks().get(0).getAmount());
                                for (int i = 0; i < 4; i++) {
                                    ItemStack residue = inv.getStackInSlot(i).getCraftingRemainingItem();
                                    inv.extractItem(i, n, false);
                                    if (!residue.isEmpty()) {
                                        residue.setCount(n);
                                        h.insertItem(i, residue, false);
                                    }
                                }
                            }
                            if (tileEntity.containerInventory.resolve().isPresent()){
                                var con=tileEntity.containerInventory.resolve().get();
                                if (con.getStackInSlot(0).getItem().getCraftingRemainingItem() == Items.BUCKET){
                                    con.setStackInSlot(0, tileEntity.currentRecipe.getFluidResult().getBucket().getDefaultInstance());
                                }else {
                                    CompoundTag nbt = new FluidStack(tileEntity.currentRecipe.getFluidResult(), tileEntity.getFluidAmount()).writeToNBT(new CompoundTag());
                                    fluid.getContainer().getOrCreateTag().put(FLUID_NBT_KEY, nbt);
                                }
                            }
                            tileEntity.setToZero();
                        }

                    }

                }
            } else {
                tileEntity.setToZero();
            }
        } else {
            tileEntity.currentRecipe = null;
            tileEntity.setToZero();
        }

        tileEntity.getFluidHandler().ifPresent(fluid ->
                tileEntity.inputInventory.ifPresent(in ->
                        tileEntity.outputInventory.ifPresent(out ->
                        {
                            {
                                ItemStack inputCup = ItemHandlerHelper.copyStackWithSize(in.getStackInSlot(0), 1);
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
                                        tileEntity.containerInventory.ifPresent(container ->
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
                return true;
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
        return getFluidHandler().map(h -> h.getFluidInTank(0).getAmount()).orElse(0);
    }

    @Nullable
    public DrinkRecipe getCurrentRecipe() {
        return currentRecipe;
    }

    public int getNeededAmount() {
        if (currentRecipe != null)
            return (int) Math.ceil(this.getFluidAmount() * 1.0F / currentRecipe.getFluidIngredient().getMatchingFluidStacks().get(0).getAmount());
        else
            return 0;
    }

    @Nullable
    public Component getFluidTranslation() {
        return getFluidHandler().map(h -> h.getFluidInTank(0).getDisplayName()).orElse(Component.empty());
    }

    public LazyOptional<IFluidHandlerItem> getFluidHandler() {
        return this.containerInventory.map(h -> FluidUtil.getFluidHandler(h.getStackInSlot(0))).orElse(LazyOptional.empty());
    }

    public LazyOptional<ItemStackHandler> getContainerInventory() {
        return containerInventory;
    }

    public LazyOptional<ItemStackHandler> getInputInventory() {
        return inputInventory;
    }

    public LazyOptional<ItemStackHandler> getOutputInventory() {
        return outputInventory;
    }

    public List<ItemStack> getContent() {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int index = i;
            ingredientsInventory.ifPresent(h -> list.add(h.getStackInSlot(index)));
        }
        containerInventory.ifPresent(h -> list.add(h.getStackInSlot(0)));
        return list;
    }

    public Direction getFacing() {
        return this.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
    }


    public boolean isIngredientsEmpty() {
        return ingredientsInventory.map(h ->
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
                residuesInventory.map(h ->
                {
                    for (int i = 0; i < 4; i++) {
                        if (!h.getStackInSlot(i).isEmpty()) {
                            return false;
                        }
                    }
                    return true;
                }).orElse(true) &&
                containerInventory.map(h -> h.getStackInSlot(0).isEmpty()).orElse(true) &&
                inputInventory.map(h -> h.getStackInSlot(0).isEmpty()).orElse(true) &&
                outputInventory.map(h -> h.getStackInSlot(0).isEmpty()).orElse(true);
    }


    public ItemStack getStackInSlot(int index) {
        if (index < 4) {
            return ingredientsInventory.map(h -> h.getStackInSlot(index)).orElse(ItemStack.EMPTY);
        } else if (index < 8) {
            return residuesInventory.map(h -> h.getStackInSlot(index - 4)).orElse(ItemStack.EMPTY);
        } else if (index == 8) {
            return containerInventory.map(h -> h.getStackInSlot(0)).orElse(ItemStack.EMPTY);
        } else if (index == 9) {
            return inputInventory.map(h -> h.getStackInSlot(0)).orElse(ItemStack.EMPTY);
        } else {
            return outputInventory.map(h -> h.getStackInSlot(0)).orElse(ItemStack.EMPTY);
        }
    }


    public ItemStack decrStackSize(int index, int count) {
        if (index < 4) {
            return ingredientsInventory.map(h -> h.getStackInSlot(index).split(count)).orElse(ItemStack.EMPTY);
        } else if (index < 8) {
            return residuesInventory.map(h -> h.getStackInSlot(index - 4).split(count)).orElse(ItemStack.EMPTY);
        } else if (index == 8) {
            return containerInventory.map(h -> h.getStackInSlot(0).split(count)).orElse(ItemStack.EMPTY);
        } else if (index == 9) {
            return inputInventory.map(h -> h.getStackInSlot(0).split(count)).orElse(ItemStack.EMPTY);
        } else {
            return outputInventory.map(h -> h.getStackInSlot(0).split(count)).orElse(ItemStack.EMPTY);
        }
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player p_39956_) {
        return new DrinkMakerContainer(id, playerInventory, worldPosition, level);
    }
}
