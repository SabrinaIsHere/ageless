package org.sabrina_the_bitch.ageless.Registration.Items;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.function.UnaryOperator;

// TODO: Implement consequences of blood consumption (vampirism if bat blood, poison if otherwise if not a vampire)
// TODO: Add nbt for filled level instead of using custom_model_data
// TODO: Add nbt for entity blood type

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
    /*public static final ComponentType<Text> BLOOD_ORIGIN = register("blood_origin", (builder) -> {
        return builder.codec(TextCodecs.STRINGIFIED_CODEC).packetCodec(TextCodecs.REGISTRY_PACKET_CODEC).cache();
    });

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return (ComponentType<T>)Registry.register(Registries.DATA_COMPONENT_TYPE, id, ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }*/

    private static void writeBloodLevel(ItemStack item, int amount) {
        item.set(BLOOD_LEVEL, amount);
        item.set(BLOOD_ORIGIN_ID, "");
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
                value = 3;
            } else {
                value -= 1;
            }
            stack.set(BLOOD_LEVEL, value);
        } else {
            writeBloodLevel(stack, 3);
        }
        System.out.println(readBloodLevel(stack));
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
