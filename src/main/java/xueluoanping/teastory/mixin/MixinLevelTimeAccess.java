package xueluoanping.teastory.mixin;



import cloud.lemonslice.teastory.handler.AsmHandler;
import net.minecraft.world.level.LevelTimeAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({LevelTimeAccess.class})
public interface MixinLevelTimeAccess extends LevelTimeAccess{

    @Shadow long dayTime();

    // @Inject(at = {@At("HEAD")}, method = {"getTimeOfDay"}, cancellable = true)
    // public default void mixin_getTimeOfDay(float p_46943_, CallbackInfoReturnable<Float> cir) {
    //     // cir.setReturnValue( AsmHandler.getSeasonCelestialAngle(p_63905_,(DimensionType)(Object)this));
    //
    // }

    @Override
    default float getTimeOfDay(float p_46943_) {
        // TeaStory.logger(p_46943_,dayTime());
        return AsmHandler.getSeasonCelestialAngle(dayTime(),(LevelTimeAccess)(Object)this);
    }
}
