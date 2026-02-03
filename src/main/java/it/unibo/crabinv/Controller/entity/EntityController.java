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
     * Calls on collisionWith method of the entity.
     * @param other the other entity it collided with
     */
    public void onCollisionWith(Entity other);

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
     * @return the y coordinate of the enity
     */
    public double getY();
}
