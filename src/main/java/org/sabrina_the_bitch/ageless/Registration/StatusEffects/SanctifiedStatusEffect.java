package org.sabrina_the_bitch.ageless.Registration.StatusEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class SanctifiedStatusEffect extends StatusEffect {
    public SanctifiedStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xfffed7);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }

    // Has no active effect, only prevents vampirism
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        return true;
    }
}
