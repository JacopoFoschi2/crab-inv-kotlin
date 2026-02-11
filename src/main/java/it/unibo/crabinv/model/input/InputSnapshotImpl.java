package it.unibo.crabinv.model.input;

import it.unibo.crabinv.model.entities.entity.Delta;

/**
 * Constructor of the {@link InputSnapshotImpl}
 */
public final class InputSnapshotImpl implements InputSnapshot {
    private final boolean shootPressed;
    private final Delta xMovementDelta;
    private final boolean pausePressed;
    private final boolean unPausePressed;

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
