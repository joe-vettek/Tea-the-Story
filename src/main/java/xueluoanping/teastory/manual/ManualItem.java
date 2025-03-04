package xueluoanping.teastory.manual;

import li.cil.manual.api.ManualModel;
import li.cil.manual.api.prefab.item.AbstractManualItem;
import org.jetbrains.annotations.NotNull;
import xueluoanping.teastory.registry.ManualRegistry;

public class ManualItem extends AbstractManualItem {

    public ManualItem(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull ManualModel getManualModel() {
        return ManualRegistry.MANUAL.get();
    }
}
