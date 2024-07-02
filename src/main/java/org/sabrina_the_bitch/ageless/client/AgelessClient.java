package org.sabrina_the_bitch.ageless.client;

import net.fabricmc.api.ClientModInitializer;
import org.sabrina_the_bitch.ageless.Registration.RegistrationHandler;

public class AgelessClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        RegistrationHandler.clientRegister();
    }
}
