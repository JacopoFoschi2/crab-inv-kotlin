package it.unibo.crabinv.Model.powerUpsShop;

import java.util.List;

public final class PowerUpFactory {

    private PowerUpFactory() {}

    public static List<PowerUp> createShopPowerUps() {
        return List.of(
                new PowerUpLogic(PowerUpType.SPEED_UP, 150, 3),
                new PowerUpLogic(PowerUpType.FIRERATE_UP, 100, 5),
                new PowerUpLogic(PowerUpType.HEALTH_UP, 200, 4)
        );
    }
}
