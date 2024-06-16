package org.sabrina_the_bitch.ageless.Registration.Items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import javax.swing.*;

public class NeedleItem extends Item {
    public NeedleItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user.getEntityWorld().isClient()) {
            return ActionResult.PASS;
        }
        user.sendMessage(Text.of("Hi"));
        return ActionResult.SUCCESS;
    }
}
