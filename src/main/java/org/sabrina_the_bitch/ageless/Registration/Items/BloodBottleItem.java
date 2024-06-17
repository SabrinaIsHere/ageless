package org.sabrina_the_bitch.ageless.Registration.Items;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.sabrina_the_bitch.ageless.Registration.RegistrationHandler;

import java.util.Objects;

// TODO: Darken blood textures

public class BloodBottleItem extends Item {
    // Component stuff
    public static final ComponentType<Integer> BLOOD_LEVEL =
            ComponentType.<Integer>builder()
                    .codec(Codec.INT)
                    .packetCodec(PacketCodecs.VAR_INT)
                    .build();
    // The id of the entity this blood came from
    public static  final ComponentType<String> BLOOD_ORIGIN_ID =
            ComponentType.<String>builder()
                    .codec(Codec.string(0, 99))
                    .packetCodec(PacketCodecs.string(99))
                    .build();

    private static void writeBloodLevel(ItemStack item, int amount) {
        item.set(BLOOD_LEVEL, amount);
    }
    public static int readBloodLevel(ItemStack item) {
        return item.getComponents().getOrDefault(BLOOD_LEVEL, 0);
    }

    public static void addBloodLevel(ItemStack item, int amount) {
        writeBloodLevel(item, readBloodLevel(item)+amount);
    }

    // 0-3, how many levels of blood this item is filled with
    public BloodBottleItem(Settings settings) {
        super(settings);
    }

    public ItemStack getDefaultStack() {
        ItemStack itemStack = super.getDefaultStack();
        itemStack.set(BLOOD_LEVEL, 3);
        return itemStack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.isClient) {
            return stack;
        }
        if (stack.get(BLOOD_LEVEL) != null) {
            int value = readBloodLevel(stack);
            if (value <= 1) {
                if (user instanceof PlayerEntity) {
                    PlayerInventory inventory = ((PlayerEntity) user).getInventory();
                    inventory.removeOne(stack);
                    inventory.insertStack(new ItemStack(Items.GLASS_BOTTLE));
                } else {
                    return new ItemStack(Items.GLASS_BOTTLE);
                }
            }
            value -= 1;
            stack.set(BLOOD_LEVEL, value);
        } else {
            writeBloodLevel(stack, 1);
        }

        // Handle blood drinking consequences
        if (user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;
            if (false) {
                // If is vampire. This is a placeholder bc vampirism isn't implemented yet
            } else {
                if (stack.get(BLOOD_ORIGIN_ID) != null) {
                    //System.out.println(stack.get(BLOOD_ORIGIN_ID));
                    if (Objects.equals(stack.get(BLOOD_ORIGIN_ID), "Bat")) {
                        if (!player.hasStatusEffect(RegistrationHandler.SANCTIFIED_ENTRY)) {
                            player.addStatusEffect(new StatusEffectInstance(RegistrationHandler.INFIRMUM_SANGUINEM_ENTRY, -1));
                        } else {
                            player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 4*20));
                        }
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 4*20));
                    }
                }
            }
        }

        user.emitGameEvent(GameEvent.DRINK);
        return stack;
    }

    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 32;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }
}
