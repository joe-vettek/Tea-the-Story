package com.teamtea.teastory.item;


import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxPlayable;
import net.minecraft.world.item.context.UseOnContext;


import java.util.function.Supplier;

public class SRecordItem extends Item {


    public SRecordItem(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result = super.useOn(context);
        if (context.getLevel().isClientSide() ) {
            JukeboxPlayable jukeboxPlayable = context.getItemInHand().get(DataComponents.JUKEBOX_PLAYABLE);
            if (jukeboxPlayable != null) {
                var accessor = Minecraft.getInstance().getSoundManager().getSoundEvent(jukeboxPlayable.song().key().location());
                if (accessor == null) {
                    if (context.getPlayer() != null) {
                        context.getPlayer().displayClientMessage(Component.translatable("info.teastory.record"), true);
                    }
                    return InteractionResult.FAIL;
                }
            }
        }
        return result;
    }
}
