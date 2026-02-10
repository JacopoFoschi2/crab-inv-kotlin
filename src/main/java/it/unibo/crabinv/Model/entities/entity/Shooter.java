package it.unibo.crabinv.Model.entities.entity;

/**
 * Provides the basic methods that a shooter entity should implement.
 */
public interface Shooter {
    /**
     * Checks if the entity is currently able to shoot.
     *
     * @return true if it can shoot, false if it can't
     */
    boolean isAbleToShoot();

    /**
     * Returns the fireRate of the entity.
     *
     * @return the amount of ticks needed between two shots
     */
    int getFireRate();

    /**
     * Tells the model that it has shot, prompting it to handle the isAbleToShoot pipeline.
     */
    void shoot();
}
