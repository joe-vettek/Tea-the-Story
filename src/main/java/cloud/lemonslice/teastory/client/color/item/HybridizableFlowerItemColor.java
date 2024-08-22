package cloud.lemonslice.teastory.client.color.item;


import cloud.lemonslice.teastory.block.crops.flower.FlowerColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public class HybridizableFlowerItemColor implements ItemColor {

    @Override
    public int getColor(ItemStack itemStack, int tintIndex) {
        if (tintIndex == 1) {
            if (itemStack.hasTag()) {
                if (itemStack.getTag().contains("color"))
                    return FlowerColor.getFlowerColor(itemStack.getTag().getString("color")).getColorValue();
                if (itemStack.getTag().contains("BlockStateTag")
                        && itemStack.getTag().getCompound("BlockStateTag").contains("color"))
                    return FlowerColor.getFlowerColor(itemStack.getTag().getCompound("BlockStateTag").getString("color")).getColorValue();

            }
        }
        return -1;
    }
}
