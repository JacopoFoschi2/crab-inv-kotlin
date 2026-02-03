package it.unibo.crabinv.Model.input;

import it.unibo.crabinv.Model.entity.Delta;

/**
 * Defines the input state at a tick
 */
public interface InputSnapshot {
    boolean isShooting();
    Delta getXMovementDelta();
    Delta getYMovementDelta();
    boolean isPause();
}
