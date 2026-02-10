package it.unibo.crabinv.save;

import it.unibo.crabinv.model.powerUpsShop.PowerUpType;
import it.unibo.crabinv.model.save.PlayerBaseStats;
import it.unibo.crabinv.model.save.UserProfileImpl;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestUserProfileApplyPowerUp {

    private static final double EPS = 1e-12;

    @Test
    void applyMultiplyPowerUpComputesBaseTimesMultiplierTimesLevel() {
        final Map<PowerUpType, Integer> levels = new EnumMap<>(PowerUpType.class);
        levels.put(PowerUpType.SPEED_UP, 4);
        levels.put(PowerUpType.FIRERATE_UP, 2);
        levels.put(PowerUpType.HEALTH_UP, 5);

        final UserProfileImpl profile = new UserProfileImpl(levels);

        assertMultiply(profile, PowerUpType.SPEED_UP, 4);
        assertMultiply(profile, PowerUpType.FIRERATE_UP, 2);
        assertMultiply(profile, PowerUpType.HEALTH_UP, 5);
    }

    private static void assertMultiply(final UserProfileImpl profile, final PowerUpType type, final int level) {
        final double expected =
                PlayerBaseStats.getDoubleValueOf(type) * type.getStatMultiplier() * level;

        final double actual = profile.applyMultiplyPowerUp(type);

        assertEquals(expected, actual, EPS, "Wrong result for " + type);
    }
}