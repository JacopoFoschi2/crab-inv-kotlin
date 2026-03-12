package it.unibo.crabinv.model.powerups

import it.unibo.crabinv.model.core.i18n.TextKeys

/**
 * It describes the types of powerUps.
 */
enum class PowerUpType(
    val statMultiplier: Double,
    val description: TextKeys,
) {
    SPEED_UP(0.25, TextKeys.SPEED_DESC),
    FIRERATE_UP(0.1, TextKeys.FIRERATE_DESC),
    HEALTH_UP(1.0, TextKeys.HEALTH_DESC),
}
