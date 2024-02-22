package net.orcinus.hedgehog.entities;

import net.minecraft.world.effect.MobEffect;

import javax.annotation.Nullable;

public interface EffectCarrier {

    int getDuration();

    void setDuration(int duration);

    int getAmplifier();

    void setAmplifier(int amplifier);

    @Nullable
    MobEffect getStoredEffect();

    void setStoredEffect(@Nullable MobEffect mobEffect);

    boolean hasStoredEffect();

    default void transferValues(int duration, int amplifier) {
        this.setDuration(duration);
        this.setAmplifier(amplifier);
    }

}
