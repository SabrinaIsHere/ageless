package org.sabrina_the_bitch.ageless.Registration.StatusEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class InfirmumSanguinemStatusEffect extends StatusEffect {
    public InfirmumSanguinemStatusEffect() {
        // Lighter alternative: 0x6700b3
        super(StatusEffectCategory.NEUTRAL, 0x48007d);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }

    // Has no active effect because if the player dies with it they become a vampire
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        return true;
    }
}
