package org.sabrina_the_bitch.ageless.MixinInterfaces;

import java.util.Optional;

public interface HungerManagerVampirism {
    default Optional<Boolean> isVampire() {
        return Optional.empty();
    }

    default Optional<Boolean> setVampire(boolean vampire) {
        return Optional.empty();
    }
}
