package com.teamtea.teastory.block.crops;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WildCropBlock extends BushBlock {

    public static final MapCodec<WildCropBlock> CODEC = RecordCodecBuilder
            .mapCodec(cropBlockInstance -> cropBlockInstance.group(
                            propertiesCodec(),
                            Codec.BOOL.fieldOf("isLush").forGetter(b->b.isLush),
                            Codec.BOOL.fieldOf("isShort").forGetter(b->b.isShort)
                    )
                    .apply(cropBlockInstance, WildCropBlock::new));

    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    protected static final VoxelShape SHAPE_LOW = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6.5D, 14.0D);

    private final boolean isShort;
    private final boolean isLush;

    public WildCropBlock(BlockBehaviour.Properties pProperties, boolean isLush, boolean isShort) {
        super(pProperties);
        this.isLush = isLush;
        this.isShort = isShort;
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        VoxelShape shape=isShort ? SHAPE_LOW : SHAPE;
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        return shape.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (isLush && entityIn instanceof LivingEntity) {
            entityIn.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
        }
        super.entityInside(state, worldIn, pos, entityIn);
    }

}
