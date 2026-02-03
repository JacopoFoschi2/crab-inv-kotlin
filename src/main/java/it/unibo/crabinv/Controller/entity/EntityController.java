package it.unibo.crabinv.Controller.entity;

import it.unibo.crabinv.Model.entity.Delta;
import it.unibo.crabinv.Model.entity.Entity;

/**
 * Provides methods to control an entity standardizing the way they should be controlled
 */
public interface EntityController {
    /**
     * Updates the status of something that receives inputs, such as the player
     * @param firePressed
     * @param delta
     */
    void update(boolean firePressed, Delta delta);

    /**
     * Updates the status of something that doesn't receive inputs,
     * and therefore has constant movement
     */
    void update(Delta delta);

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
     * @return the max ealth of the entity
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
