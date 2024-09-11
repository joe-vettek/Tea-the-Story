package com.teamtea.teastory.blockentity;

import com.teamtea.teastory.registry.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class StoneCampfireBlockEntity extends CampfireBlockEntity {

    public StoneCampfireBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        return BlockEntityRegister.stone_campfire_TYPE.get();
    }

    public static void particleTick(Level pLevel, BlockPos pPos, BlockState pState, CampfireBlockEntity pBlockEntity) {
        RandomSource randomsource = pLevel.random;
        if (randomsource.nextFloat() < 0.11F) {
            for (int i = 0; i < randomsource.nextInt(2) + 2; i++) {
                CampfireBlock.makeParticles(pLevel, pPos, pState.getValue(CampfireBlock.SIGNAL_FIRE), false);
            }
        }

        int l = pState.getValue(CampfireBlock.FACING).get2DDataValue();

        for (int j = 0; j < pBlockEntity.getItems().size(); j++) {
            if (!pBlockEntity.getItems().get(j).isEmpty() && randomsource.nextFloat() < 0.2F) {
                Direction direction = Direction.from2DDataValue(Math.floorMod(j + l, 4));
                float f = 0.3125F;
                double d0 = (double)pPos.getX()
                        + 0.5
                        - (double)((float)direction.getStepX() * 0.3125F)
                        + (double)((float)direction.getClockWise().getStepX() * 0.3125F);
                double d1 = (double)pPos.getY() + 0.19;
                double d2 = (double)pPos.getZ()
                        + 0.5
                        - (double)((float)direction.getStepZ() * 0.3125F)
                        + (double)((float)direction.getClockWise().getStepZ() * 0.3125F);

                for (int k = 0; k < 4; k++) {
                    pLevel.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0, 5.0E-4, 0.0);
                }
            }
        }
    }
}
