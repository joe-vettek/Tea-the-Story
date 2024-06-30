package cloud.lemonslice.teastory.recipe.drink;


import net.minecraft.world.effect.MobEffect;

public class DrinkEffectAttribute
{
    private final MobEffect potion;
    private final int duration;
    private final int level;

    public DrinkEffectAttribute(MobEffect potionIn, int durationIn, int level)
    {
        this.potion = potionIn;
        this.duration = durationIn;
        this.level = level;
    }

    public MobEffect getPotion()
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
