package it.unibo.crabinv.model.powerups

/**
 * It's the interface that implements the methods for the powerUp.
 */
interface PowerUp {
    /**
     * @return the cost of the powerUp
     */
    val cost: Int

    /**
     * @return the max level of the powerUp
     */
    val maxLevel: Int

    /**
     * @return the powerUp name
     */
    val powerUpName: String

    /**
     * @return the type of the powerUp
     */
    val powerUpType: PowerUpType
}
