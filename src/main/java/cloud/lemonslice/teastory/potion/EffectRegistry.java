package cloud.lemonslice.teastory.potion;


import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import xueluoanping.teastory.TeaStory;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class EffectRegistry {
    public static final MobEffect AGILITY = new MobEffect(MobEffectCategory.BENEFICIAL, 0x828282);
    public static final MobEffect PHOTOSYNTHESIS = new PhotosynthesisEffect(MobEffectCategory.BENEFICIAL, 0x828282);
    public static final MobEffect LIFE_DRAIN = new MobEffect(MobEffectCategory.BENEFICIAL, 0x828282);
    public static final MobEffect DEFENCE = new MobEffect(MobEffectCategory.BENEFICIAL, 0x828282);
    public static final MobEffect EXCITEMENT = new MobEffect(MobEffectCategory.NEUTRAL, 0x828282);

    @SubscribeEvent
    public static void blockRegister(RegisterEvent event) {
        event.register(Registries.MOB_EFFECT, register -> {
            register.register(TeaStory.rl( "agility"), AGILITY);
            register.register(TeaStory.rl( "photosynthesis"), PHOTOSYNTHESIS);
            register.register(TeaStory.rl( "life_drain"), LIFE_DRAIN);
            register.register(TeaStory.rl( "defence"), DEFENCE);
            register.register(TeaStory.rl( "excitement"), EXCITEMENT);
        });
    }



}
