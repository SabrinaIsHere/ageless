package org.sabrina_the_bitch.ageless.Registration.Items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.sabrina_the_bitch.ageless.MixinInterfaces.PlayerVampirism;
import org.sabrina_the_bitch.ageless.Registration.RegistrationHandler;

public class PurificationPowderItem extends Item {
    public PurificationPowderItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player) {
            if (((PlayerVampirism) player).isVampire().get()) {
                // If player is a vampire
                player.kill();
            } else {
                // Adds for an hour
                player.removeStatusEffect(RegistrationHandler.INFIRMUM_SANGUINEM_ENTRY);
                player.addStatusEffect(new StatusEffectInstance(RegistrationHandler.SANCTIFIED_ENTRY, 60*60*20));
            }
        }
        user.emitGameEvent(GameEvent.EAT);
        return stack;
    }
}
