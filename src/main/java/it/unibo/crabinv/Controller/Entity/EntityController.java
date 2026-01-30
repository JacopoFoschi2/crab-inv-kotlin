package it.unibo.crabinv.Controller.Entity;

import it.unibo.crabinv.Model.entity.Delta;

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
}
