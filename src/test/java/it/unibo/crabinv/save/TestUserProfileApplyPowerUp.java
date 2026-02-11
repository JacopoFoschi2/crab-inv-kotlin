package it.unibo.crabinv.save;

import it.unibo.crabinv.model.powerups.PowerUpType;
import it.unibo.crabinv.model.save.PlayerBaseStats;
import it.unibo.crabinv.model.save.UserProfileImpl;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestUserProfileApplyPowerUp {

    private static final double EPS = 1e-12;
    private static final int MAX_SPEED_UP = 4;
    private static final int MAX_FIRERATE_UP = 2;
    private static final int MAX_HEALTH_UP = 5;

    @Test
    void applyMultiplyPowerUpComputesBaseTimesMultiplierTimesLevel() {
        final Map<PowerUpType, Integer> levels = new EnumMap<>(PowerUpType.class);
        levels.put(PowerUpType.SPEED_UP, MAX_SPEED_UP);
        levels.put(PowerUpType.FIRERATE_UP, MAX_FIRERATE_UP);
        levels.put(PowerUpType.HEALTH_UP, MAX_HEALTH_UP);

        final UserProfileImpl profile = new UserProfileImpl(levels);

        assertMultiply(profile, PowerUpType.SPEED_UP, MAX_SPEED_UP);
        assertMultiply(profile, PowerUpType.FIRERATE_UP, MAX_FIRERATE_UP);
        assertMultiply(profile, PowerUpType.HEALTH_UP, MAX_HEALTH_UP);
    }

    private static void assertMultiply(final UserProfileImpl profile, final PowerUpType type, final int level) {
        final double expected =
                PlayerBaseStats.getDoubleValueOf(type) * type.getStatMultiplier() * level;

        final double actual = profile.applyMultiplyPowerUp(type);

        assertEquals(expected, actual, EPS, "Wrong result for " + type);
    }
}
