package xueluoanping.teastory.recipe.drink;


import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public class DrinkEffectAttribute
{
    private final Holder<MobEffect>  potion;
    private final int duration;
    private final int level;

    public DrinkEffectAttribute(Holder<MobEffect> potionIn, int durationIn, int level)
    {
        this.potion = potionIn;
        this.duration = durationIn;
        this.level = level;
    }

    public Holder<MobEffect>  getPotion()
    {
        return potion;
    }

    public int getDuration()
    {
        return duration;
    }

    public int getLevel()
    {
        return level;
    }
}
