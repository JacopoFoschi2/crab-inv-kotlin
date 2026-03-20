package it.unibo.crabinv.model.core.save

import it.unibo.crabinv.model.powerups.PowerUpType

/**
 * `enum` of all the admissible player stats.
 */
enum class PlayerBaseStats(
    /**
     * @return the `double` value selected
     */
    val doubleValue: Double,
    private val powerUpType: PowerUpType,
) {
    SPEED(0.01, PowerUpType.SPEED_UP),
    FIRE_RATE(8.0, PowerUpType.FIRERATE_UP),
    PLAYER_HEALTH(2.0, PowerUpType.HEALTH_UP), ;

    companion object {
        /**
         * Returns the `double` value to [Int] of the value associated with the `PowerUpType`.
         * @param powerUpType the `PowerUpType` to look up
         * @return the associated `double` value
         */
        @JvmStatic
        fun getDoubleValueOf(powerUpType: PowerUpType): Double {
            for (stat in entries) {
                if (stat.powerUpType == powerUpType) {
                    return stat.doubleValue
                }
            }
            throw IllegalArgumentException("PowerUpType $powerUpType is not associated to any PlayerBaseStat")
        }

        /**
         * Returns the value cast to [Int] of the value associated with the `PowerUpType`.
         * @param powerUpType the `PowerUpType` to look up
         * @return the associated value cast to [Int]
         */
        fun getIntValueOf(powerUpType: PowerUpType): Int = getDoubleValueOf(powerUpType).toInt()
    }
}
