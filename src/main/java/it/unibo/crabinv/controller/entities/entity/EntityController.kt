package it.unibo.crabinv.controller.entities.entity;

/**
 * Provides methods to control an entity standardizing the way they should be controlled.
 */
public interface EntityController {

    /**
     * @return if the entity is alive
     */
    boolean isAlive();

    /**
     * Make the entity suffer the inputted amount of damage.
     *
     * @param damage the damage the entity should suffer
     */
    void takeDamage(int damage);

    /**
     * @return the current health of the entity
     */
    int getHealth();

    /**
     * @return the max health of the entity
     */
    int getMaxHealth();

    /**
     * @return the x coordinate of the entity
     */
    double getX();

    /**
     * @return the y coordinate of the entity
     */
    double getY();
}
