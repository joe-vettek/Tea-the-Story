package cloud.lemonslice.teastory.recipe.special;


import cloud.lemonslice.teastory.block.crops.flower.FlowerColor;
import cloud.lemonslice.teastory.item.HybridizableFlowerBlockItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import xueluoanping.teastory.RecipeRegister;

public class FlowerDyeRecipe extends CustomRecipe {


    public FlowerDyeRecipe(ResourceLocation pId, CraftingBookCategory pCategory) {
        super(pId, pCategory);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level pLevel) {
        ItemStack itemstack = ItemStack.EMPTY;

        for (int i = 0; i < inv.getContainerSize(); ++i) {
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
    public ItemStack assemble(CraftingContainer inv, RegistryAccess pRegistryAccess) {
        ItemStack itemstack = ItemStack.EMPTY;

        for (int i = 0; i < inv.getContainerSize(); ++i) {
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
        FlowerColor color = FlowerColor.getFlowerColor(itemstack.getOrCreateTag().getString("color"));
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
