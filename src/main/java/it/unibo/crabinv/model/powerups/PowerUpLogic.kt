package it.unibo.crabinv.model.powerups

/**
 * It's the implementation of the powerUp.
 * @param powerUpType the type of powerUp
 * @param cost the cost of the powerUp
 * @param maxLevel the maximum level of the powerUp
 */
class PowerUpLogic(
    override val powerUpType: PowerUpType,
    override val cost: Int,
    override val maxLevel: Int,
) : PowerUp {
    override val powerUpName: String
        get() = this.powerUpType.name
}
