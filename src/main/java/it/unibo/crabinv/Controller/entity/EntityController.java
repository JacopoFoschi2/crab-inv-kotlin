package it.unibo.crabinv.Controller.entity;

import it.unibo.crabinv.Model.entity.Entity;

/**
 * Provides methods to control an entity standardizing the way they should be controlled
 */
public interface EntityController {

    /**
     * @return if the entity is alive
     */
    public boolean isAlive();

    /**
     * Make the entity suffer the inputted amount of damage.
     * @param damage the damage the entity should suffer
     */
    public void takeDamage(int damage);

    /**
     * @return the current health of the entity
     */
    public int getHealth();

    /**
     * @return the max health of the entity
     */
    public int getMaxHealth();

    /**
     * @return the x coordinate of the entity
     */
    public double getX();

    /**
     * @return the y coordinate of the entity
     */
    public double getY();
}
