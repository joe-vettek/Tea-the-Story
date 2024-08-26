package cloud.lemonslice.teastory.client.color.item;


import cloud.lemonslice.teastory.block.crops.HybridizableFlowerBlock;
import cloud.lemonslice.teastory.block.crops.flower.FlowerColor;
import net.minecraft.ChatFormatting;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class HybridizableFlowerItemColor implements ItemColor {

    @Override
    public int getColor(ItemStack itemStack, int tintIndex) {
        if (tintIndex == 1) {
            if (itemStack.has(DataComponents.BLOCK_STATE)) {
                return itemStack.get(DataComponents.BLOCK_STATE).get(HybridizableFlowerBlock.FLOWER_COLOR).getColorValue();
            }
        }
        return -1;
    }
}
