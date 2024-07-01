package cloud.lemonslice.teastory.blockentity;

import cloud.lemonslice.teastory.block.craft.StoveBlock;
import cloud.lemonslice.teastory.container.StoveContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import net.minecraftforge.items.ItemStackHandler;
import xueluoanping.teastory.ItemRegister;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.entity.NormalContainerTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class StoveTileEntity extends NormalContainerTileEntity {
    private int remainTicks = 0;
    private int fuelTicks = 0;
    private int workTicks = 0;
    private boolean lit = false;

    private int doubleClickTicks = 0;

    private final LazyOptional<ItemStackHandler> fuelInventory = LazyOptional.of(this::createFuelHandler);
    private final LazyOptional<ItemStackHandler> ashInventory = LazyOptional.of(this::createAshHandler);

    public StoveTileEntity(BlockPos pos, BlockState state) {
        super(TileEntityTypeRegistry.STOVE_TYPE.get(), pos, state);
    }

    // read
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.fuelInventory.ifPresent(h -> ((INBTSerializable<CompoundTag>) h).deserializeNBT(tag.getCompound("Fuel")));
        this.ashInventory.ifPresent(h -> ((INBTSerializable<CompoundTag>) h).deserializeNBT(tag.getCompound("Ash")));
        this.fuelTicks = tag.getInt("FuelTicks");
        this.remainTicks = tag.getInt("RemainTicks");
        this.workTicks = tag.getInt("WorkTicks");
        this.lit = tag.getBoolean("Lit");

    }

    // write
    @Override
    protected void saveAdditional(CompoundTag tag) {
        fuelInventory.ifPresent(h -> tag.put("Fuel", ((INBTSerializable<CompoundTag>) h).serializeNBT()));
        ashInventory.ifPresent(h -> tag.put("Ash", ((INBTSerializable<CompoundTag>) h).serializeNBT()));
        tag.putInt("FuelTicks", fuelTicks);
        tag.putInt("RemainTicks", remainTicks);
        tag.putInt("WorkTicks", workTicks);
        tag.putBoolean("Lit", lit);
        super.saveAdditional(tag);
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (!this.isRemoved() && ForgeCapabilities.ITEM_HANDLER.equals(cap)) {
            if (side == Direction.DOWN) {
                return ashInventory.cast();
            } else {
                return fuelInventory.cast();
            }
        }
        return super.getCapability(cap, side);
    }


    public static void tick(Level worldIn, BlockPos pos, BlockState blockState, StoveTileEntity basinBlockEntity) {
        if (basinBlockEntity.doubleClickTicks > 0) {
            basinBlockEntity.doubleClickTicks--;
        }
        if (basinBlockEntity.lit) {
            basinBlockEntity.addFuel();
        }
        if (basinBlockEntity.remainTicks > 0) {
            basinBlockEntity.remainTicks--;
            basinBlockEntity.setChanged();
        } else {
            if (!basinBlockEntity.lit && basinBlockEntity.getBlockState().getValue(StoveBlock.LIT)) {
                StoveBlock.setState(false, basinBlockEntity.getLevel(), basinBlockEntity.getBlockPos());
                basinBlockEntity.inventoryChanged();
            }
        }
    }

    private boolean addFuel() {
        if (this.isBurning()) {
            this.lit = true;
            return true;
        } else {
            ItemStack itemFuel = this.fuelInventory.orElse(new ItemStackHandler()).extractItem(0, 1, true);
            if (ForgeHooks.getBurnTime(itemFuel, RecipeType.SMELTING) > 0) {
                this.fuelTicks = ForgeHooks.getBurnTime(itemFuel, RecipeType.SMELTING) * 2;
                this.remainTicks = ForgeHooks.getBurnTime(itemFuel, RecipeType.SMELTING) * 2;
                ItemStack cItem = this.fuelInventory.orElse(new ItemStackHandler()).getStackInSlot(0).getCraftingRemainingItem();
                if (!cItem.isEmpty()) {
                    this.fuelInventory.orElse(new ItemStackHandler()).extractItem(0, 1, false);
                    this.fuelInventory.orElse(new ItemStackHandler()).insertItem(0, cItem, false);
                } else {
                    this.fuelInventory.orElse(new ItemStackHandler()).extractItem(0, 1, false);
                }
                this.ashInventory.orElse(new ItemStackHandler()).insertItem(0, new ItemStack(ItemRegister.ASH.get()), false);
                this.setChanged();
                StoveBlock.setState(true, getLevel(), getBlockPos());
                this.lit = true;
                return true;
            } else {
                this.lit = false;
                return false;
            }
        }
    }

    public boolean isBurning() {
        return this.remainTicks > 0;
    }

    public NonNullList<ItemStack> getContents() {
        NonNullList<ItemStack> list = NonNullList.create();
        this.fuelInventory.ifPresent(inv ->
        {
            ItemStack con = inv.getStackInSlot(0).copy();
            con.setCount(1);
            for (int i = this.fuelInventory.orElse(new ItemStackHandler()).getStackInSlot(0).getCount(); i > 0; i -= 4) {
                list.add(con);
            }
        });
        return list;
    }

    public int getRemainTicks() {
        return this.remainTicks;
    }

    public int getFuelTicks() {
        return this.fuelTicks;
    }

    public void setToLit() {
        this.lit = true;
    }

    public void singleClickStart() {
        this.doubleClickTicks = 10;
        this.setChanged();
    }

    public boolean isDoubleClick() {
        return doubleClickTicks > 0;
    }

    private ItemStackHandler createAshHandler() {
        return new ItemStackHandler() {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem().equals(ItemRegister.ASH);
            }
        };
    }

    private ItemStackHandler createFuelHandler() {
        return new SyncedItemStackHandler();
    }


    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player p_39956_) {
        return new StoveContainer(id, playerInventory, worldPosition, level);
    }
}
