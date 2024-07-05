package org.sabrina_the_bitch.ageless.Registration.Items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.sabrina_the_bitch.ageless.Registration.RegistrationHandler;

import java.util.Objects;
import java.util.Set;

public class NeedleItem extends Item {
    public NeedleItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user.getEntityWorld().isClient()) {
            return ActionResult.PASS;
        }
        Inventory playerInventory = user.getInventory();
        if (playerInventory.containsAny(Set.of(RegistrationHandler.BLOOD_BOTTLE))) {
            // Add a level to the bottle if it's the same entity, otherwise pass
            for (int i = 0; i < 36; i++) {
                ItemStack bloodBottleStack = playerInventory.getStack(i);
                if (bloodBottleStack.isOf(RegistrationHandler.BLOOD_BOTTLE) && bloodBottleStack.contains(BloodBottleItem.BLOOD_ORIGIN_ID)) {
                    if (Objects.equals(bloodBottleStack.get(BloodBottleItem.BLOOD_ORIGIN_ID), entity.getName().getString())) {
                        if (bloodBottleStack.getOrDefault(BloodBottleItem.BLOOD_LEVEL, 5) >= 3) {
                            continue;
                        }
                        BloodBottleItem.addBloodLevel(bloodBottleStack, 1);
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }
        if (playerInventory.containsAny(Set.of(Items.GLASS_BOTTLE))) {
            // Convert it to a blood bottle with 1 level
            for (int i = 0; i < 36; i++) {
                ItemStack bottle = playerInventory.getStack(i);
                if (bottle.isOf(Items.GLASS_BOTTLE)) {
                    ItemStack bloodBottle = new ItemStack(RegistrationHandler.BLOOD_BOTTLE);
                    bloodBottle.set(BloodBottleItem.BLOOD_ORIGIN_ID, entity.getName().getString());
                    bloodBottle.set(BloodBottleItem.BLOOD_LEVEL, 1);
                    playerInventory.removeStack(i, 1);
                    user.getInventory().insertStack(bloodBottle);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.FAIL;
    }
}
