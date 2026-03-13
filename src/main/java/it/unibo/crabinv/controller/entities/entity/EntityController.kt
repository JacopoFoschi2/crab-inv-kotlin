package it.unibo.crabinv.controller.entities.entity

/**
 * Provides methods to control an entity standardizing the way they should be controlled.
 */
interface EntityController {
    /**
     * @return if the entity is alive
     */
    val isAlive: Boolean

    /**
     * Make the entity suffer the inputted amount of damage.
     * @param damage the damage the entity should suffer
     */
    fun takeDamage(damage: Int)

    /**
     * @return the current health of the entity
     */
    val health: Int

    /**
     * @return the max health of the entity
     */
    val maxHealth: Int

    /**
     * @return the x coordinate of the entity
     */
    val x: Double

    /**
     * @return the y coordinate of the entity
     */
    val y: Double
}
