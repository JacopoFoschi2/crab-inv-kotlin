package it.unibo.crabinv.model.input;

import it.unibo.crabinv.model.entities.entity.Delta;

/**
 * Constructor of the {@link InputSnapshotImpl}.
 */
public final class InputSnapshotImpl implements InputSnapshot {
    private final boolean shootPressed;
    private final Delta xMovementDelta;
    private final boolean pausePressed;
    private final boolean unPausePressed;

    /**
     * Constructor of {@link InputSnapshotImpl}.
     *
     * @param shootPressed true if the shoot action is requested
     * @param xMovementDelta the {@link Delta} of the input
     * @param pausePressed  true if the pause action is requested
     * @param unPausePressed  true if the resume action is requested
     */
    public InputSnapshotImpl(final boolean shootPressed,
                             final Delta xMovementDelta,
                             final boolean pausePressed,
                             final boolean unPausePressed) {
        this.shootPressed = shootPressed;
        this.xMovementDelta = xMovementDelta;
        this.pausePressed = pausePressed;
        this.unPausePressed = unPausePressed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isShooting() {
        return shootPressed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Delta getXMovementDelta() {
        return xMovementDelta;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPause() {
        return pausePressed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUnpause() {
        return unPausePressed;
    }
}
