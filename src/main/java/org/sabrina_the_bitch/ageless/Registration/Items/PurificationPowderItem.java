package org.sabrina_the_bitch.ageless.Registration.Items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

// TODO: Make it remove vampirism/give the immunity effect

public class PurificationPowderItem extends Item {
    public PurificationPowderItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        // TODO: Implement with vampirism
        user.emitGameEvent(GameEvent.EAT);
        return stack;
    }
}
