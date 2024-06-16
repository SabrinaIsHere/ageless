package org.sabrina_the_bitch.ageless.Registration;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.sabrina_the_bitch.ageless.Registration.Items.NeedleItem;
import org.sabrina_the_bitch.ageless.Registration.Items.PurificationPowderItem;
import org.sabrina_the_bitch.ageless.Registration.Items.VialItem;

public class RegistrationHandler {
    // Items
    public static final NeedleItem NEEDLE = new NeedleItem(new Item.Settings());
    public static final VialItem VIAL = new VialItem(new Item.Settings());
    public static final PurificationPowderItem PURIFICATION_POWDER = new PurificationPowderItem(new Item.Settings());
    // Blocks
    // Potion Effects
    public static void register() {
        // Items
        Registry.register(Registries.ITEM, Identifier.of("ageless", "needle"), NEEDLE);
        Registry.register(Registries.ITEM, Identifier.of("ageless", "vial"), VIAL);
        Registry.register(Registries.ITEM, Identifier.of("ageless", "purification_powder"), PURIFICATION_POWDER);
        // Adding to groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {content.add(NEEDLE);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {content.add(VIAL);});
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {content.add(PURIFICATION_POWDER);});
    }
}
