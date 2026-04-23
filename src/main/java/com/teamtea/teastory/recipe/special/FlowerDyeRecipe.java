package com.teamtea.teastory.recipe.special;


import com.teamtea.teastory.block.crops.HybridizableFlowerBlock;
import com.teamtea.teastory.block.crops.flower.FlowerColor;
import com.teamtea.teastory.item.HybridizableFlowerBlockItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import com.teamtea.teastory.registry.RecipeRegister;

public class FlowerDyeRecipe extends CustomRecipe {



    @Override
    public boolean matches(CraftingInput inv, Level pLevel) {
        ItemStack itemstack = ItemStack.EMPTY;

        for (int i = 0; i < inv.size(); ++i) {
            ItemStack itemstack1 = inv.getItem(i);
            if (!itemstack1.isEmpty()) {
                if (itemstack1.getItem() instanceof HybridizableFlowerBlockItem) {
                    if (!itemstack.isEmpty()) {
                        return false;
                    }

                    itemstack = itemstack1;
                }
            }
        }

        return !itemstack.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput inv) {
        ItemStack itemstack = ItemStack.EMPTY;

        for (int i = 0; i < inv.size(); ++i) {
            ItemStack itemstack1 = inv.getItem(i);
            if (!itemstack1.isEmpty()) {
                if (itemstack1.getItem() instanceof HybridizableFlowerBlockItem) {
                    if (!itemstack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    itemstack = itemstack1;
                }
            }
        }
        BlockItemStateProperties color = itemstack.get(DataComponents.BLOCK_STATE);
        if (color != null
                && color.get(HybridizableFlowerBlock.FLOWER_COLOR) instanceof FlowerColor flowerColor
                && flowerColor.getDye() != null) {
            return new ItemStack(flowerColor.getDye(), 2);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);
    }
}
