package cloud.lemonslice.teastory.entity;


import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import xueluoanping.teastory.EntityTypeRegistry;

import java.util.List;

public class SeatEntity extends Entity {
    public SeatEntity(Level worldIn) {
        super(EntityTypeRegistry.SEAT_TYPE.get(), worldIn);
    }

    private SeatEntity(Level world, BlockPos pos, double height, double x, double z) {
        this(world);
        this.setPos(pos.getX() + 0.5 + x, pos.getY() + height, pos.getZ() + 0.5 + z);
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {

    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (this.getPassengers().isEmpty() || this.level().isEmptyBlock(this.blockPosition())) {
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    public static InteractionResult createSeat(Level world, BlockPos pos, Player player, double height, double x, double z) {
        if (!world.isClientSide()) {
            List<SeatEntity> seats = world.getEntitiesOfClass(SeatEntity.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 1.0, pos.getZ() + 1.0));
            if (seats.isEmpty()) {
                SeatEntity seat = new SeatEntity(world, pos, height, x, z);
                world.addFreshEntity(seat);
                player.startRiding(seat, false);
            }
        }
        return InteractionResult.SUCCESS;
    }


}
