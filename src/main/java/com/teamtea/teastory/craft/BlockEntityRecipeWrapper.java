package com.teamtea.teastory.craft;


import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;


import java.lang.ref.WeakReference;

public class BlockEntityRecipeWrapper extends RecipeWrapper {

    public final WeakReference<BlockEntity> blockEntity;

    public BlockEntityRecipeWrapper(IItemHandlerModifiable inv, BlockEntity blockEntity) {
        super(inv);
        this.blockEntity = new WeakReference<>(blockEntity);
    }


    public BlockEntity getBlockEntity() {
        return blockEntity.get();
    }
}
