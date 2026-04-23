package com.teamtea.teastory.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;
import com.teamtea.teastory.TeaStory;

@EventBusSubscriber
public class SoundEventsRegistry {
    public final static SoundEvent CUP_BROKEN = SoundEvent.createVariableRangeEvent(TeaStory.rl( "block.cup_broken"));
    public final static SoundEvent RECORD_MOVING_UP = SoundEvent.createVariableRangeEvent(TeaStory.rl( "record.moving_up"));
    public final static SoundEvent RECORD_PICKING_TEA = SoundEvent.createVariableRangeEvent(TeaStory.rl( "record.picking_tea"));
    public final static SoundEvent RECORD_SPRING_FESTIVAL_OVERTURE = SoundEvent.createVariableRangeEvent(TeaStory.rl( "record.spring_festival_overture"));
    public final static SoundEvent RECORD_FLOWERS_AND_MOON = SoundEvent.createVariableRangeEvent(TeaStory.rl( "record.flowers_and_moon"));
    public final static SoundEvent RECORD_DANCING_GOLDEN_SNAKE = SoundEvent.createVariableRangeEvent(TeaStory.rl( "record.dancing_golden_snake"));
    public final static SoundEvent RECORD_JOYFUL = SoundEvent.createVariableRangeEvent(TeaStory.rl( "record.joyful"));
    public final static SoundEvent RECORD_GREEN_WILLOW = SoundEvent.createVariableRangeEvent(TeaStory.rl( "record.green_willow"));
    public final static SoundEvent RECORD_PURPLE_BAMBOO_MELODY = SoundEvent.createVariableRangeEvent(TeaStory.rl( "record.purple_bamboo_melody"));
    public final static SoundEvent RECORD_WELCOME_MARCH = SoundEvent.createVariableRangeEvent(TeaStory.rl( "record.welcome_march"));

    @SubscribeEvent
    public static void blockRegister(RegisterEvent event) {
        event.register(Registries.SOUND_EVENT, soundEventRegisterHelper -> {
            soundEventRegisterHelper.register(CUP_BROKEN.location(), CUP_BROKEN);
            soundEventRegisterHelper.register(RECORD_MOVING_UP.location(), RECORD_MOVING_UP);
            soundEventRegisterHelper.register(RECORD_PICKING_TEA.location(), RECORD_PICKING_TEA);
            soundEventRegisterHelper.register(RECORD_SPRING_FESTIVAL_OVERTURE.location(), RECORD_SPRING_FESTIVAL_OVERTURE);
            soundEventRegisterHelper.register(RECORD_FLOWERS_AND_MOON.location(), RECORD_FLOWERS_AND_MOON);
            soundEventRegisterHelper.register(RECORD_DANCING_GOLDEN_SNAKE.location(), RECORD_DANCING_GOLDEN_SNAKE);
            soundEventRegisterHelper.register(RECORD_JOYFUL.location(), RECORD_JOYFUL);
            soundEventRegisterHelper.register(RECORD_GREEN_WILLOW.location(), RECORD_GREEN_WILLOW);
            soundEventRegisterHelper.register(RECORD_PURPLE_BAMBOO_MELODY.location(), RECORD_PURPLE_BAMBOO_MELODY);
            soundEventRegisterHelper.register(RECORD_WELCOME_MARCH.location(), RECORD_WELCOME_MARCH);
        });
    }
}
