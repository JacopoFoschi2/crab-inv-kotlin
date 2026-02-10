package it.unibo.crabinv.model.powerUpsShop;

import java.util.List;

/**
 * It's the factory for the powerUps.
 */
public final class PowerUpFactory {
    private static final int SPEED_POWER_UP_COST = 150;
    private static final int FIRERATE_POWER_UP_COST = 100;
    private static final int HEALTH_POWER_UP_COST = 150;
    private static final int SPEED_POWER_UP_MAXLEVEL = 3;
    private static final int FIRERATE_POWER_UP_MAXLEVEL = 5;
    private static final int HEALTH_POWER_UP_MAXLEVEL = 4;

    /**
     * It's the constructor for the factory.
     */
    private PowerUpFactory() {
        throw new AssertionError("Utility class");
    }

    /**
     * Creates the powerUps for the shop.
     *
     * @return the list of powerUps
     */
    public static List<PowerUp> createShopPowerUps() {
        return List.of(
                new PowerUpLogic(PowerUpType.SPEED_UP, SPEED_POWER_UP_COST, SPEED_POWER_UP_MAXLEVEL),
                new PowerUpLogic(PowerUpType.FIRERATE_UP, FIRERATE_POWER_UP_COST, FIRERATE_POWER_UP_MAXLEVEL),
                new PowerUpLogic(PowerUpType.HEALTH_UP, HEALTH_POWER_UP_COST, HEALTH_POWER_UP_MAXLEVEL)
        );
    }
}
