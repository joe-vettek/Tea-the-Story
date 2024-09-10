package com.teamtea.teastory.block.drink;

import com.teamtea.teastory.blockentity.TeapotTileEntity;
import com.teamtea.teastory.fluid.HotWaterFlowingFluidBlock;
import com.teamtea.teastory.helper.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import com.teamtea.teastory.BlockEntityRegistry;
import com.teamtea.teastory.block.NormalHorizontalBlock;

public class IronKettleBlock extends TeapotBlock implements EntityBlock {
    private static final VoxelShape SHAPE = VoxelShapeHelper.createVoxelShape(2, 0, 2, 12, 11, 12);

    public IronKettleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        var tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof TeapotTileEntity && ((TeapotTileEntity) tileentity).getFluid().getFluidType().getTemperature() >= 333) {
            double d0 = pos.getX() + 0.5D;
            double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = pos.getZ() + 0.5D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            worldIn.addParticle(ParticleTypes.CLOUD, false, d0 + d4, d1 + 0.5D, d2 + d4, 0.0D, 0.1D, 0.0D);
        }
    }


    @Override
    public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.causeFallDamage(fallDistance, 1.0F, worldIn.damageSources().fall());
    }


    @Override
    public void entityInside(BlockState p_60495_, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            var te = worldIn.getBlockEntity(pos);
            if (te instanceof TeapotTileEntity && ((TeapotTileEntity) te).getFluid().getFluidType().getTemperature() >= 333) {
                entityIn.hurt(HotWaterFlowingFluidBlock.getBoiling(worldIn), 1.0F);
            }
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return BlockEntityRegistry.IRON_KETTLE_TYPE.get().create(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return !pLevel.isClientSide ?
                NormalHorizontalBlock.createTickerHelper(pBlockEntityType, BlockEntityRegistry.IRON_KETTLE_TYPE.get(), TeapotTileEntity::tick) : null;

    }
}
