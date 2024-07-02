package org.sabrina_the_bitch.ageless.Mixins.PlayerEntity;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.sabrina_the_bitch.ageless.MixinInterfaces.PlayerVampirism;
import org.sabrina_the_bitch.ageless.Registration.RegistrationHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "onDeath", at = @At("HEAD"), cancellable = true)
    public void onDeathInjection(DamageSource source, CallbackInfo ci) {
        ServerPlayerEntity self = (ServerPlayerEntity) (Object) this;
        ServerWorld world = self.getServerWorld();
        if (self.hasStatusEffect(RegistrationHandler.INFIRMUM_SANGUINEM_ENTRY)) {
            System.out.println("Death cancelled [" + self.getName().getString() + "]");
            // Cancels death
            self.setHealth(1.0F);
            self.clearStatusEffects();
            self.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 30 * 20));
            self.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 30 * 20));
            self.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 2 * 60 * 20));
            // Pizzazz
            world.setWeather(0, 5 * 60 * 20, true, true);
            // Spawn a lightning bolt on the player
            LightningEntity lightningEntity = (LightningEntity)EntityType.LIGHTNING_BOLT.create(world);
            if (lightningEntity != null) {
                lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(self.getBlockPos()));
                lightningEntity.setCosmetic(true);
                world.spawnEntity(lightningEntity);
            }
            ((PlayerVampirism) self).setVampire(true);
            ci.cancel();
        }
    }
}
