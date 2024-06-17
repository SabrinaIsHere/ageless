package org.sabrina_the_bitch.ageless.Registration;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.sabrina_the_bitch.ageless.Registration.Items.NeedleItem;
import org.sabrina_the_bitch.ageless.Registration.Items.PurificationPowderItem;
import org.sabrina_the_bitch.ageless.Registration.Items.BloodBottleItem;
import org.sabrina_the_bitch.ageless.Registration.StatusEffects.InfirmumSanguinemStatusEffect;
import org.sabrina_the_bitch.ageless.Registration.StatusEffects.SanctifiedStatusEffect;

public class RegistrationHandler {
    // Status Effects
    public static final InfirmumSanguinemStatusEffect INFIRMUM_SANGUINEM = new InfirmumSanguinemStatusEffect();
    public static final SanctifiedStatusEffect SANTIFIED = new SanctifiedStatusEffect();
    // Items
    public static final NeedleItem NEEDLE = new NeedleItem(new Item.Settings().maxCount(1));
    public static final BloodBottleItem BLOOD_BOTTLE = new BloodBottleItem(new Item.Settings().maxCount(1));
    // TODO: Add correct status effect
    public static final PurificationPowderItem PURIFICATION_POWDER = new PurificationPowderItem(new Item.Settings().
            food(new FoodComponent.Builder()
                    .alwaysEdible()
                    .nutrition(1)
                    .build()));
    // Blocks
    public static void register() {
        // Items
        Registry.register(Registries.ITEM, Identifier.of("ageless", "needle"), NEEDLE);
        Registry.register(Registries.ITEM, Identifier.of("ageless", "blood_bottle"), BLOOD_BOTTLE);
        Registry.register(Registries.ITEM, Identifier.of("ageless", "purification_powder"), PURIFICATION_POWDER);
        // Adding to groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {content.add(NEEDLE);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {content.add(BLOOD_BOTTLE);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {content.add(PURIFICATION_POWDER);});
        // Status Effects
        Registry.register(Registries.STATUS_EFFECT, Identifier.of("ageless", "infirmum_sanguinem"), INFIRMUM_SANGUINEM);
        Registry.register(Registries.STATUS_EFFECT, Identifier.of("ageless", "sanctified"), SANTIFIED);
        // Custom components
        Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of("ageless", "blood_level"), BloodBottleItem.BLOOD_LEVEL);
        Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of("ageless", "blood_origin_id"), BloodBottleItem.BLOOD_ORIGIN_ID);
    }

    public static void clientRegister() {
        ModelPredicateProviderRegistry.register(BLOOD_BOTTLE, Identifier.of("filled"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (itemStack.contains(BloodBottleItem.BLOOD_LEVEL)) {
                return (float) BloodBottleItem.readBloodLevel(itemStack) / 3;
            }
            return 1.0F;
        });
    }
}
