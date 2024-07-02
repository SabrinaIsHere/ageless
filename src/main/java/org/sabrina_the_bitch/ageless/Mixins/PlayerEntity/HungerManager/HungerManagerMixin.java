package org.sabrina_the_bitch.ageless.Mixins.PlayerEntity.HungerManager;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.nbt.NbtCompound;
import org.sabrina_the_bitch.ageless.MixinInterfaces.HungerManagerVampirism;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin implements HungerManagerVampirism {
    public boolean isVampire = false;

    // Vampirism stuff

    @Override
    public Optional<Boolean> isVampire() {
        return Optional.of(isVampire);
    }

    @Override
    public Optional<Boolean> setVampire(boolean vampire) {
        this.isVampire = vampire;
        return Optional.empty();
    }

    // Injections

    @Inject(at = @At("HEAD"), method = "eat", cancellable = true)
    public void eatInjection(FoodComponent foodComponent, CallbackInfo ci) {
        if (isVampire) {
            ci.cancel();
        }
    }

    @Inject(at = @At("TAIL"), method = "readNbt")
    public void readNbtInjection(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("foodLevel", 99)) {
            this.isVampire = nbt.getBoolean("isVampire");
        }
    }

    @Inject(at = @At("TAIL"), method = "writeNbt")
    public void writeNbtInjection(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("isVampire", isVampire);
    }
}
