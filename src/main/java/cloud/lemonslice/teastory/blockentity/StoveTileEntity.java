package cloud.lemonslice.teastory.blockentity;

import cloud.lemonslice.teastory.block.craft.StoveBlock;
import cloud.lemonslice.teastory.container.StoveContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import xueluoanping.teastory.ItemRegister;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.entity.NormalContainerTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;


public class StoveTileEntity extends NormalContainerTileEntity {
    private int remainTicks = 0;
    private int fuelTicks = 0;
    private int workTicks = 0;
    private boolean lit = false;

    private int doubleClickTicks = 0;

    private final ItemStackHandler fuelInventory = createFuelHandler();
    private final ItemStackHandler ashInventory = createAshHandler();

    public StoveTileEntity(BlockPos pos, BlockState state) {
        super(TileEntityTypeRegistry.STOVE_TYPE.get(), pos, state);
    }


    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(tag, pRegistries);
        this.fuelInventory.deserializeNBT(pRegistries, tag.getCompound("Fuel"));
        this.ashInventory.deserializeNBT(pRegistries, tag.getCompound("Ash"));
        this.fuelTicks = tag.getInt("FuelTicks");
        this.remainTicks = tag.getInt("RemainTicks");
        this.workTicks = tag.getInt("WorkTicks");
        this.lit = tag.getBoolean("Lit");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider pRegistries) {
        tag.put("Fuel", this.fuelInventory.serializeNBT(pRegistries));
        tag.put("Ash", this.ashInventory.serializeNBT(pRegistries));
        tag.putInt("FuelTicks", fuelTicks);
        tag.putInt("RemainTicks", remainTicks);
        tag.putInt("WorkTicks", workTicks);
        tag.putBoolean("Lit", lit);
        super.saveAdditional(tag, pRegistries);

    }

    public ItemStackHandler getAshInventory() {
        return ashInventory;
    }

    public ItemStackHandler getFuelInventory() {
        return fuelInventory;
    }


    public static void tick(Level worldIn, BlockPos pos, BlockState blockState, StoveTileEntity stoveTileEntity) {
        if (stoveTileEntity.doubleClickTicks > 0) {
            stoveTileEntity.doubleClickTicks--;
        }
        if (!stoveTileEntity.isBurning()) {
            stoveTileEntity.addFuel();
        }else {
            if(worldIn.getRandom().nextInt(60)==0) {
                var ash=new ItemStack(ItemRegister.ASH.get());
                ItemHandlerHelper.insertItem(stoveTileEntity.getAshInventory(),ash,false);
            }
        }
        if (stoveTileEntity.remainTicks > 0) {
            stoveTileEntity.remainTicks--;
            // stoveTileEntity.setChanged();
            stoveTileEntity.inventoryChanged();
        } else {
            if (worldIn instanceof ServerLevel && !stoveTileEntity.isBurning() && blockState.getValue(StoveBlock.LIT)) {
                StoveBlock.setState(false, worldIn, pos);
                stoveTileEntity.inventoryChanged();
            }
        }
    }

    private boolean addFuel() {
        if (this.isBurning()) {
            this.lit = true;
            return true;
        } else {
            ItemStack itemFuel = this.fuelInventory.extractItem(0, 1, true);
            if (itemFuel.getBurnTime( RecipeType.SMELTING) > 0) {
                this.fuelTicks = itemFuel.getBurnTime( RecipeType.SMELTING) * 2;
                this.remainTicks = itemFuel.getBurnTime( RecipeType.SMELTING) * 2;
                ItemStack cItem = this.fuelInventory.getStackInSlot(0).getCraftingRemainingItem();
                if (!cItem.isEmpty()) {
                    this.fuelInventory.extractItem(0, 1, false);
                    this.fuelInventory.insertItem(0, cItem, false);
                } else {
                    this.fuelInventory.extractItem(0, 1, false);
                }
                this.ashInventory.insertItem(0, new ItemStack(ItemRegister.ASH.get()), false);
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
        Optional.ofNullable(this.fuelInventory).ifPresent(inv ->
        {
            ItemStack con = inv.getStackInSlot(0).copy();
            con.setCount(1);
            for (int i = this.fuelInventory.getStackInSlot(0).getCount(); i > 0; i -= 4) {
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
                return stack.is(ItemRegister.ASH);
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
