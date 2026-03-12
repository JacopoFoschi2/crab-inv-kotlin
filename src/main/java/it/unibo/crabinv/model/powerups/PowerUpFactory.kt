package it.unibo.crabinv.model.powerups

/**
 * It's the factory of PowerUps.
 */
object PowerUpFactory {
    private const val SPEED_COST = 150
    private const val SPEED_MAXLEVEL = 3
    private const val FIRERATE_COST = 100
    private const val FIRERATE_MAXLEVEL = 5
    private const val HEALTH_COST = 200
    private const val HEALTH_MAXLEVEL = 4

    /**
     * Creates the power Ups and makes them a list.
     * @return the list of avaible power ups
     */
    fun createShopPowerUps(): List<PowerUp> =
        listOf<PowerUp>(
            PowerUpLogic(PowerUpType.SPEED_UP, SPEED_COST, SPEED_MAXLEVEL),
            PowerUpLogic(PowerUpType.FIRERATE_UP, FIRERATE_COST, FIRERATE_MAXLEVEL),
            PowerUpLogic(PowerUpType.HEALTH_UP, HEALTH_COST, HEALTH_MAXLEVEL),
        )
}
