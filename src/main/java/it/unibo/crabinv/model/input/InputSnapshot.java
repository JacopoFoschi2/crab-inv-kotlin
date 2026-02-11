package it.unibo.crabinv.model.input;

import it.unibo.crabinv.model.entities.entity.Delta;

/**
 * Defines the input state at a tick.
 */
public interface InputSnapshot {

    /**
     * Flag for the shoot action.
     *
     * @return true if the shoot action is requested
     */
    boolean isShooting();

    /**
     * Return the {@link Delta} of the X-Axis.
     *
     * @return the {@link Delta} of the X-Axis
     */
    Delta getXMovementDelta();

    /**
     * Flag for the pause request.
     *
     * @return true if pause is requested
     */
    boolean isPause();

    /**
     * Flag for the resume request.
     *
     * @return true if resume is requested
     */
    boolean isUnpause();
}
