package cloud.lemonslice.teastory.item;


import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;


import java.util.function.Supplier;

public class SRecordItem extends Item {

    public final Supplier<SoundEvent> soundSupplier;

    public SRecordItem(int comparatorValue, Supplier<SoundEvent> soundSupplier, Properties pProperties, int i) {
        super(pProperties);
        this.soundSupplier = soundSupplier;
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result = super.useOn(context);
        if (context.getLevel().isClientSide() && result == InteractionResult.SUCCESS) {
            var accessor = Minecraft.getInstance().getSoundManager().getSoundEvent(soundSupplier.get().getLocation());
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
