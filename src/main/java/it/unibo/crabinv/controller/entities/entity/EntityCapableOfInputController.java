package it.unibo.crabinv.controller.entities.entity;

import it.unibo.crabinv.model.entities.entity.Delta;

/**
 * Provides an entity capable of receiving input the update method it should implement.
 */
public interface EntityCapableOfInputController {
    /**
     * Updates the status of something that receives inputs, such as the player.
     *
     * @param firePressed tells the controller if the user requested to fire
     * @param delta either -1, 0 or 1, the former moves to the left, the latter moves to the right
     */
    void update(boolean firePressed, Delta delta);
}
