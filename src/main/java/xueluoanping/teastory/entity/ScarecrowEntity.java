package xueluoanping.teastory.entity;


import cloud.lemonslice.teastory.block.decorations.ScarecrowBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import xueluoanping.teastory.registry.EntityTypeRegistry;

import java.util.List;

public class ScarecrowEntity extends ArmorStand {
    public ScarecrowEntity(Level worldIn) {
        super(EntityTypeRegistry.SCARECROW_TYPE.get(), worldIn);
    }

    private ScarecrowEntity(Level world, BlockPos pos, double height, double x, double z) {
        // this(world);
        this(EntityTypeRegistry.SCARECROW_TYPE.get(),world);
        this.setPos(pos.getX() + 0.5 + x, pos.getY() + height, pos.getZ() + 0.5 + z);
    }

    public ScarecrowEntity(EntityType<ScarecrowEntity> scarecrowEntityEntityType, Level level) {
        super(scarecrowEntityEntityType, level);
    }

    @Override
    public void tick() {
        // this.baseTick();
        super.tick();
        if (!this.level().isClientSide()) {
            if (this.level().isEmptyBlock(this.blockPosition())) {
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }


    public static boolean create(Level world, BlockPos pos, double height, double x, double z) {
        if (!world.isClientSide()) {
            List<ScarecrowEntity> seats = world.getEntitiesOfClass(ScarecrowEntity.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 1.0, pos.getZ() + 1.0));
            if (seats.isEmpty()) {
                ScarecrowEntity seat = new ScarecrowEntity(world, pos, height, x, z);
                // ArmorStand a=new ArmorStand(world, pos.getX() + 0.5 + x, pos.getY() + height, pos.getZ() + 0.5 + z);
                world.addFreshEntity(seat);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean isDeadOrDying() {
        return this.level().isEmptyBlock(this.blockPosition());
    }

    @Override
    public void remove(RemovalReason pReason) {
        BlockPos below = blockPosition().below();
        if(level().getBlockState(below).getBlock() instanceof ScarecrowBlock)
            level().removeBlock(below, false);
        super.remove(pReason);
    }

}
