package xueluoanping.teastory.entity;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import xueluoanping.teastory.EntityTypeRegistry;

import java.util.Collections;
import java.util.List;

public class ScarecrowEntity extends ArmorStand {
    public ScarecrowEntity(Level worldIn) {
        super(EntityTypeRegistry.SCARECROW_TYPE.get(), worldIn);
    }

    private ScarecrowEntity(Level world, BlockPos pos, double height, double x, double z) {
        // this(world);
        this(EntityTypeRegistry.SCARECROW_TYPE.get(), world);
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
    public boolean attackable() {
        return true;
    }

    @Override
    public void remove(RemovalReason pReason) {
        level().removeBlock(blockPosition(), false);
        super.remove(pReason);
    }

    @Override
    public void onRemovedFromLevel() {
        //
        super.onRemovedFromLevel();

    }
}
