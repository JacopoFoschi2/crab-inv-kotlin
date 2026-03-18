package it.unibo.crabinv.model.entities.entity

/**
 * provides the method that a movable entity should implement.
 */
interface Movable {
    /**
     * Handles unidimensional movement for every tick based on given delta.
     * @param delta the delta of movement, which is either +1, 0 or -1, to be then applied to either the x or y axis
     */
    fun move(delta: Delta)

    /**
     * @return the speed of the entity
     */
    val speed: Double
}
