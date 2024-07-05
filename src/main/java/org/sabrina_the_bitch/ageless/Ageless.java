package org.sabrina_the_bitch.ageless;

import net.fabricmc.api.ModInitializer;
import org.sabrina_the_bitch.ageless.Registration.RegistrationHandler;
import org.sabrina_the_bitch.ageless.VampireAbilities.VampireAbilities;

public class Ageless implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        VampireAbilities.init();
        RegistrationHandler.register();
    }
}
