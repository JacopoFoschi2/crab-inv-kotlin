package it.unibo.crabinv.model.powerups;

import java.util.List;

/**
 * It's the factory of PowerUps.
 */
public final class PowerUpFactory {
    private static final int SPEED_COST = 150;
    private static final int SPEED_MAXLEVEL = 3;
    private static final int FIRERATE_COST = 100;
    private static final int FIRERATE_MAXLEVEL = 5;
    private static final int HEALTH_COST = 200;
    private static final int HEALTH_MAXLEVEL = 4;

    /**
     * It's the constructor of the power up factory.
     */
    private PowerUpFactory() {

    }

    /**
     * Creates the power Ups and makes them a list.
     *
     * @return the list of avaible power ups
     */
    public static List<PowerUp> createShopPowerUps() {
        return List.of(
                new PowerUpLogic(PowerUpType.SPEED_UP, SPEED_COST, SPEED_MAXLEVEL),
                new PowerUpLogic(PowerUpType.FIRERATE_UP, FIRERATE_COST, FIRERATE_MAXLEVEL),
                new PowerUpLogic(PowerUpType.HEALTH_UP, HEALTH_COST, HEALTH_MAXLEVEL)
        );
    }
}
