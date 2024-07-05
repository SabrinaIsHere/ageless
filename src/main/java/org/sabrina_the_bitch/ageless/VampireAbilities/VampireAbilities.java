package org.sabrina_the_bitch.ageless.VampireAbilities;

import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Handler class for vampiric abilities
 */
public class VampireAbilities {
    public static List<VampireAbility> abilitiesRegistry;
    private static boolean isInitialized = false;
    public static void init() {
        if (isInitialized) return;
        isInitialized = true;
        /*abilitiesRegistry.add(new VampireAbility("sunlight_immunity", "tick", (PlayerEntity p) -> {
            return false;
        }));*/
    }

    public boolean immuneToSunlight;

    public VampireAbilities() {
        immuneToSunlight = false;
    }

    public void save(PlayerEntity p) {
        // Save to nbt
    }

    public void load(PlayerEntity p) {
        // Load from nbt
    }

    @Nullable
    public VampireAbility has(String Id) {
        for (VampireAbility v : abilitiesRegistry) {
            if (v.id.equals(Id)) {
                return v;
            }
        }
        return null;
    }
}
