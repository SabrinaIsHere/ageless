package org.sabrina_the_bitch.ageless.Registration.Items;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

// TODO: Make it consumable
// TODO: Implement consequences of blood consumption (vampirism if bat blood, poison if otherwise if not a vampire)

public class BloodBottleItem extends Item {
    // 0-3, how many levels of blood this item is filled with
    public BloodBottleItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack items = user.getStackInHand(hand);
        if (world.isClient) {
            return TypedActionResult.pass(items);
        }
        if (items.get(DataComponentTypes.CUSTOM_MODEL_DATA) == null) {
            items.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(3));
        } else {
            int value = items.get(DataComponentTypes.CUSTOM_MODEL_DATA).value();
            if (value <= 1) {
                value = 3;
            } else {
                value -= 1;
            }
            items.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(value));
        }
        System.out.println(items.get(DataComponentTypes.CUSTOM_MODEL_DATA).value());
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
