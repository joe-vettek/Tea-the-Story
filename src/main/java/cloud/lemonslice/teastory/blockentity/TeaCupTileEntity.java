package cloud.lemonslice.teastory.blockentity;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import xueluoanping.teastory.TileEntityTypeRegistry;
import xueluoanping.teastory.block.entity.SyncedBlockEntity;

import java.util.List;


public class TeaCupTileEntity extends SyncedBlockEntity {
    private final List<FluidTank> fluidTanks = Lists.newArrayList();

    public TeaCupTileEntity(int capacity, BlockPos pos, BlockState state) {
        super(TileEntityTypeRegistry.WOODEN_TRAY_TYPE.get(), pos, state);
        for (int i = 0; i < 3; i++) {
            fluidTanks.add(createFluidHandler(capacity));
        }
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(tag, pRegistries);
        fluidTanks.get(0).readFromNBT(pRegistries, tag.getCompound("FluidTank_0"));
        fluidTanks.get(1).readFromNBT(pRegistries, tag.getCompound("FluidTank_1"));
        fluidTanks.get(2).readFromNBT(pRegistries, tag.getCompound("FluidTank_2"));
    }

    // write
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider pRegistries) {
        tag.put("FluidTank_0",fluidTanks.get(0).writeToNBT(pRegistries,new CompoundTag()));
        tag.put("FluidTank_1",fluidTanks.get(1).writeToNBT(pRegistries,new CompoundTag()));
        tag.put("FluidTank_2",fluidTanks.get(2).writeToNBT(pRegistries,new CompoundTag()));
        super.saveAdditional(tag,pRegistries);
    }

    private FluidTank createFluidHandler(int capacity) {
        return new FluidTank(capacity) {
            @Override
            protected void onContentsChanged() {
                // TeaCupTileEntity.this.refresh();
                // TeaCupTileEntity.this.markDirty();
                // super.onContentsChanged();
                TeaCupTileEntity.this.inventoryChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return !stack.getFluid().getFluidType().isLighterThanAir() && stack.getFluid().getFluidType().getTemperature() < 500;
            }
        };
    }

    public FluidTank getFluidTank(int index) {
        return this.fluidTanks.get(index);
    }

    public Fluid getFluid(int index) {
        return this.fluidTanks.get(index).getFluid().getFluid();
    }

    public int getFluidAmount(int index) {
        return getFluidTank(index).getFluidAmount();
    }

    public void setFluidTank(int index, FluidStack stack) {
        this.fluidTanks.get(index).setFluid(stack);
    }

    public void setFluid(int index, Fluid fluid) {
        this.fluidTanks.get(index).setFluid(new FluidStack(fluid, getFluidAmount(index)));
        // this.refresh();
        inventoryChanged();
    }


}
