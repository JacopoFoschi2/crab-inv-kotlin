package it.unibo.crabinv.Controller.entity;

import it.unibo.crabinv.Model.entity.Delta;

/**
 * Provides an entity not capable of receiving input the update method it should implement
 */
public interface EntityNotCapableOfInputController {
    /**
     * Updates the status of something that doesn't receive inputs,
     * and therefore has constant movement
     * @param delta either -1, 0 or 1, the former moves to the left, the latter moves to the right
     */
    void update(Delta delta);
}
