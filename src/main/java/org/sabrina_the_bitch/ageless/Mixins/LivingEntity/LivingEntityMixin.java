package org.sabrina_the_bitch.ageless.Mixins.LivingEntity;

import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.sabrina_the_bitch.ageless.MixinInterfaces.PlayerVampirism;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("TAIL"), method = "baseTick")
    public void baseTickInjection(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self instanceof PlayerEntity player) {
            if (((PlayerVampirism) player).isVampire().get()) {
                int fireTicks = self.getFireTicks();
                if (fireTicks > 0 && !self.isFireImmune() && fireTicks % 10 == 0) {
                    System.out.println("Damaging player");
                    this.damage(this.getDamageSources().onFire(), 0.25F);
                }
            }
        }
    }
}
