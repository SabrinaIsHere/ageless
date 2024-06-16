package org.sabrina_the_bitch.ageless;

import net.fabricmc.api.ModInitializer;
import org.sabrina_the_bitch.ageless.Registration.RegistrationHandler;

public class Ageless implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        RegistrationHandler.register();
    }
}
