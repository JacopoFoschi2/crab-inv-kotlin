package it.unibo.crabinv.model.core.input

import it.unibo.crabinv.model.entities.entity.Delta

/**
 * Constructor of the [InputSnapshotImpl].
 * @param isShooting true if the shoot action is requested
 * @param xMovementDelta the [Delta] of the input
 * @param isPause  true if the pause action is requested
 * @param isUnpause  true if the resume action is requested
 */
class InputSnapshotImpl(
    override val isShooting: Boolean,
    override val xMovementDelta: Delta?,
    override val isPause: Boolean,
    override val isUnpause: Boolean,
) : InputSnapshot
