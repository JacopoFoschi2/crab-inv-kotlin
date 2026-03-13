package it.unibo.crabinv.model.core.input

import it.unibo.crabinv.model.entities.entity.Delta

/**
 * Defines the input state at a tick.
 */
interface InputSnapshot {
    /**
     * @return true if the shoot action is requested
     */
    val isShooting: Boolean

    /**
     * @return the [Delta] of the X-Axis
     */
    val xMovementDelta: Delta?

    /**
     * @return true if pause is requested
     */
    val isPause: Boolean

    /**
     * @return true if resume is requested
     */
    val isUnpause: Boolean
}
