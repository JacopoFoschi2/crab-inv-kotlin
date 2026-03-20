package it.unibo.crabinv.model.entities.entity

/**
 * Provides all the deltas that the entity that wishes to move should handle.
 * The selected delta affects the movement by either decreasing or increasing its coordinate in the axis.
 */
enum class Delta(
    /**
     * @return the value of delta
     */
    val value: Int,
) {
    DECREASE(-1),
    NO_ACTION(0),
    INCREASE(1),
}
