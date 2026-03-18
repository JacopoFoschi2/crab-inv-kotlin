package it.unibo.crabinv.model.entities.entity

/**
 * Provides the basic methods that a shooter entity should implement.
 */
interface Shooter {
    /**
     * Checks if the entity is currently able to shoot.
     * @return true if it can shoot, false if it can't
     */
    val isAbleToShoot: Boolean

    /**
     * Returns the fireRate of the entity.
     * @return the amount of ticks needed between two shots
     */
    val fireRate: Int

    /**
     * Tells the model that it has shot, prompting it to handle the isAbleToShoot pipeline.
     */
    fun shoot()
}
