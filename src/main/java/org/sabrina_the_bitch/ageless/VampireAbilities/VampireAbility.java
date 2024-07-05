package org.sabrina_the_bitch.ageless.VampireAbilities;

import net.minecraft.entity.player.PlayerEntity;

import java.util.function.Function;

public class VampireAbility {
    String id;
    // i.e. onUse, tick, tickMovement, etc.
    String triggerEvent;
    Function<PlayerEntity, Boolean> callback;

    /**
     * Init a vampire ability (this should only be called to add to the registry of these objects)
     * @param Id The id of this ability, used to reference it without the actual object
     * @param TriggerEvent The function the callback is called in (i.e. onUse, tick, tickMovement, etc.)
     * @param Callback The callback function called when this ability is triggered. Stops the triggering of other abilities if returns true
     */
    public VampireAbility(String Id, String TriggerEvent, Function<PlayerEntity, Boolean> Callback) {
        id = Id;
        triggerEvent = TriggerEvent;
        callback = Callback;
    }
}
