package xueluoanping.teastory.manual;

import li.cil.manual.api.ManualModel;
import li.cil.manual.api.prefab.item.AbstractManualItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xueluoanping.teastory.registry.ManualRegistry;

import java.util.List;

public class ManualItem extends AbstractManualItem {

    public ManualItem(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull ManualModel getManualModel() {
        return ManualRegistry.MANUAL.get();
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.teastory.manual.tooltip").withStyle(ChatFormatting.DARK_GRAY));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
