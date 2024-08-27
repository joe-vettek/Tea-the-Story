package xueluoanping.teastory.recipe.special;


import cloud.lemonslice.teastory.block.crops.HybridizableFlowerBlock;
import cloud.lemonslice.teastory.block.crops.flower.FlowerColor;
import cloud.lemonslice.teastory.item.HybridizableFlowerBlockItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import xueluoanping.teastory.RecipeRegister;

public class FlowerDyeRecipe extends CustomRecipe {


    public FlowerDyeRecipe(CraftingBookCategory pCategory) {
        super(pCategory);
    }

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
    public ItemStack assemble(CraftingInput inv, HolderLookup.Provider pRegistryAccess) {
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
        FlowerColor color = itemstack.get(DataComponents.BLOCK_STATE).get(HybridizableFlowerBlock.FLOWER_COLOR);
        if (color.getDye() != null) {
            return new ItemStack(color.getDye(), 2);
        }
        return ItemStack.EMPTY;
    }




    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegister.CRAFTING_SPECIAL_FLOWERDYE.get();
    }
}
