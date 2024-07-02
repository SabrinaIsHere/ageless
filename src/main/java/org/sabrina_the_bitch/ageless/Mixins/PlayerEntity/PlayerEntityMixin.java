package org.sabrina_the_bitch.ageless.Mixins.PlayerEntity;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.sabrina_the_bitch.ageless.MixinInterfaces.HungerManagerVampirism;
import org.sabrina_the_bitch.ageless.MixinInterfaces.PlayerVampirism;
import org.sabrina_the_bitch.ageless.Networking.VampirismUpdatePayload;
import org.sabrina_the_bitch.ageless.VampireAbilities.VampireAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerVampirism {
    // Vampiric data
    @Unique
    public boolean isVampire;
    @Unique
    public VampireAbilities abilities;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    public void initOverride(CallbackInfo ci) {
        abilities = new VampireAbilities();
    }

    @Override
    public Optional<Boolean> isVampire() {
        return Optional.of(isVampire);
    }

    @Override
    public Optional<Boolean> setVampire(boolean vampire) {
        PlayerEntity self = (PlayerEntity) (Object) this;
        isVampire = vampire;
        ((HungerManagerVampirism) self.getHungerManager()).setVampire(vampire);
        if (!self.getWorld().isClient) {
            ServerPlayNetworking.send((ServerPlayerEntity) self, new VampirismUpdatePayload(vampire));
        }
        return Optional.empty();
    }

    // Helper functions to store/retrieve data about vampirism

    @Unique
    private void readVampireData(NbtCompound nbt) {
        isVampire = nbt.getBoolean("IsVampire");
        abilities.load((PlayerEntity) (Object) this);
    }

    @Unique
    private void writeVampireData(NbtCompound nbt) {
        nbt.putBoolean("IsVampire", isVampire);
        abilities.save((PlayerEntity) (Object) this);
    }

    // Injections

    @Inject(at = @At("TAIL"), method = "readCustomDataFromNbt")
    public void readCustomDataFromNbtInjection(NbtCompound nbt, CallbackInfo ci) {
        readVampireData(nbt);
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToNbt")
    public void writeCustomDataToNbtInjection(NbtCompound nbt, CallbackInfo ci) {
        writeVampireData(nbt);
    }

    @Inject(at = @At("HEAD"), method = "eatFood", cancellable = true)
    public void eatFoodInjection(World world, ItemStack stack, FoodComponent foodComponent, CallbackInfoReturnable<ItemStack> cir) {
        PlayerEntity self = (PlayerEntity) (Object) this;
        if (((PlayerVampirism) self).isVampire().get()) {
            // Making food poisonous
            self.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 10 * 20));
            cir.setReturnValue(stack);
            cir.cancel();
        }
    }

    @Inject(at = @At("TAIL"), method = "tickMovement")
    public void tickMovementInjection(CallbackInfo ci) {
        PlayerEntity self = (PlayerEntity) (Object) this;
        if (this.getWorld().isDay() && !this.getWorld().isClient && !abilities.immuneToSunlight) {
            float f = this.getBrightnessAtEyes();
            BlockPos blockPos = BlockPos.ofFloored(this.getX(), this.getEyeY(), this.getZ());
            boolean bl = this.isWet() || this.inPowderSnow || this.wasInPowderSnow;
            if (f > 0.5F && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && !bl && this.getWorld().isSkyVisible(blockPos)) {
                self.setOnFireFor(5);
            }
        }
    }
}
