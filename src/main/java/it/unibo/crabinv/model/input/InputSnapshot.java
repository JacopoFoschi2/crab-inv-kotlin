package it.unibo.crabinv.model.input;

import it.unibo.crabinv.model.entities.entity.Delta;

/**
 * Defines the input state at a tick
 */
public interface InputSnapshot {
    boolean isShooting();
    Delta getXMovementDelta();
    boolean isPause();
    boolean isUnpause();
}
