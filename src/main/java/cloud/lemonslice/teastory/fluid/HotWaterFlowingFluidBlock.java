package cloud.lemonslice.teastory.fluid;


import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import xueluoanping.teastory.FluidRegistry;
import xueluoanping.teastory.TeaStory;

import java.util.function.Supplier;

public class HotWaterFlowingFluidBlock extends LiquidBlock {
    // public static final DamageSource BOILING = new DamageSource(new DamageType("boiling",1.0f)).setFireDamage();

    public static final ResourceKey<DamageType> BOILING = register("boiling");

    private static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, TeaStory.rl(name));
    }

    public static DamageSource getBoiling(Level level) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(BOILING));
    }


    public HotWaterFlowingFluidBlock(Supplier<? extends FlowingFluid> supplier, Properties copy) {
        super(supplier, copy);
    }


    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        double d0 = pos.getX() + 0.5D;
        double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
        double d2 = pos.getZ() + 0.5D;
        double d4 = rand.nextDouble() * 0.6D - 0.3D;
        if (this.getFluid().getFluidType().getTemperature() >= 373) {
            worldIn.addParticle(ParticleTypes.BUBBLE, false, d0 + d4, d1, d2 + d4, 0.0D, 0.5D, 0.0D);
            if (rand.nextInt(32) == 0) {
                worldIn.playSound((Player) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundSource.BLOCKS, rand.nextFloat() * 0.25F + 0.75F, 0.1F);
            }
        }
        if (worldIn.isEmptyBlock(pos.above()) && (this.getFluid().getFluidType().getTemperature() - 273) / 100F >= rand.nextFloat()) {
            worldIn.addParticle(ParticleTypes.CLOUD, false, d0 + d4, d1 + 1.25D, d2 + d4, 0.0D, 0.1D, 0.0D);
        }
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            if (this.getFluid().getFluidType().getTemperature() >= 373) {
                entityIn.hurt(getBoiling(worldIn), 4.0F);
            } else if (this.getFluid().getFluidType().getTemperature() >= 353) {
                entityIn.hurt(getBoiling(worldIn), 2.0F);
            } else if (this.getFluid().getFluidType().getTemperature() >= 318) {
                entityIn.hurt(getBoiling(worldIn), 1.0F);
            } else {
                ((LivingEntity) entityIn).addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0));
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (state.getBlock() == FluidRegistry.WARM_WATER.get() && worldIn.getBlockState(pos.below(2)).getBlock() instanceof CampfireBlock) {

        } else if (state.getFluidState().getAmount() == 8 && random.nextInt(10) == 0) {
            if (state.getFluidState().getFluidType().getTemperature() >= 373) {
                worldIn.setBlockAndUpdate(pos, FluidRegistry.HOT_WATER_80.get().defaultBlockState());
            } else if (state.getFluidState().getFluidType().getTemperature() >= 353) {
                worldIn.setBlockAndUpdate(pos, FluidRegistry.HOT_WATER_60.get().defaultBlockState());
            } else if (state.getFluidState().getFluidType().getTemperature() >= 318) {
                worldIn.setBlockAndUpdate(pos, FluidRegistry.WARM_WATER.get().defaultBlockState());
            } else {
                worldIn.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
            }
        }
    }
}
