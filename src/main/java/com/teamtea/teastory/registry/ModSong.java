package com.teamtea.teastory.registry;

import com.teamtea.teastory.TeaStory;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

public class ModSong {
    public static final ResourceKey<JukeboxSong> PICKING_TEA = createKey("picking_tea");
    public static final ResourceKey<JukeboxSong> SPRING_FESTIVAL_OVERTURE = createKey("spring_festival_overture");
    public static final ResourceKey<JukeboxSong> FLOWERS_AND_MOON = createKey("flowers_moon");
    public static final ResourceKey<JukeboxSong> MOVING_UP = createKey("moving_up");
    public static final ResourceKey<JukeboxSong> JOYFUL = createKey("joyful");
    public static final ResourceKey<JukeboxSong> DANCING_GOLDEN_SNAKE = createKey("dancing_golden_snake");
    public static final ResourceKey<JukeboxSong> GREEN_WILLOW = createKey("green_willow");
    public static final ResourceKey<JukeboxSong> PURPLE_BAMBOO_MELODY = createKey("purple_bamboo_melody");
    public static final ResourceKey<JukeboxSong> WELCOME_MARCH = createKey("welcome_march");

    private static ResourceKey<JukeboxSong> createKey(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, TeaStory.rl(name));
    }


    private static void register2(
            BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key, SoundEvent soundEvent, int lengthInSeconds, int comparatorOutput
    ) {
        context.register(
                key,
                new JukeboxSong(BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(BuiltInRegistries.SOUND_EVENT.getResourceKey(soundEvent).get()), Component.translatable(Util.makeDescriptionId("jukebox_song", key.location())), (float) lengthInSeconds, comparatorOutput)
        );
    }

    public static void bootstrap(BootstrapContext<JukeboxSong> context) {
        register2(context, PICKING_TEA, SoundEventsRegistry.RECORD_PICKING_TEA, 10, 1);
        register2(context, SPRING_FESTIVAL_OVERTURE, SoundEventsRegistry.RECORD_SPRING_FESTIVAL_OVERTURE, 10, 2);
        register2(context, FLOWERS_AND_MOON, SoundEventsRegistry.RECORD_FLOWERS_AND_MOON, 10, 3);
        register2(context, MOVING_UP, SoundEventsRegistry.RECORD_MOVING_UP, 10, 4);
        register2(context, JOYFUL, SoundEventsRegistry.RECORD_JOYFUL, 10, 5);
        register2(context, DANCING_GOLDEN_SNAKE, SoundEventsRegistry.RECORD_DANCING_GOLDEN_SNAKE, 10, 6);
        register2(context, GREEN_WILLOW, SoundEventsRegistry.RECORD_GREEN_WILLOW, 10, 7);
        register2(context, PURPLE_BAMBOO_MELODY, SoundEventsRegistry.RECORD_PURPLE_BAMBOO_MELODY, 10, 8);
        register2(context, WELCOME_MARCH, SoundEventsRegistry.RECORD_WELCOME_MARCH, 10, 9);
    }
}
