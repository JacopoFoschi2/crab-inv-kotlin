package it.unibo.crabinv.model.core.save

/**
 * `enum` of the starting values of the [GameSession]'s [Player].
 * @param intValue the value to associate with the constant
 */
enum class StartingSaveValues(
    /**
     * @return the value of the selected constant.
     */
    val intValue: Int,
) {
    LEVEL(1),
    CURRENCY(0),
    BASE_LEVEL_POWER_UP(1),
}
