package cloud.lemonslice.teastory.item;


import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;


import java.util.function.Supplier;

public class SRecordItem extends net.minecraft.world.item.RecordItem {
    public SRecordItem(int comparatorValue, Supplier<SoundEvent> soundSupplier, Properties pProperties, int i) {

        super(comparatorValue, soundSupplier,pProperties,i);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result = super.useOn(context);
        if (context.getLevel().isClientSide() && result == InteractionResult.SUCCESS) {
            var accessor = Minecraft.getInstance().getSoundManager().getSoundEvent(getSound().getLocation());
            if (accessor == null) {
                if (context.getPlayer() != null) {
                    context.getPlayer().displayClientMessage(Component.translatable("info.teastory.record"), true);
                }
                return InteractionResult.FAIL;
            }
        }
        return result;
    }
}
