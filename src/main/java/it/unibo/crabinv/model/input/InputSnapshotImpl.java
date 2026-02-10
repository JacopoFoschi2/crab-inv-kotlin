package it.unibo.crabinv.model.input;

import it.unibo.crabinv.model.entities.entity.Delta;

public final class InputSnapshotImpl implements InputSnapshot {
    private final boolean shootPressed;
    private final Delta xMovementDelta;
    private final boolean pausePressed;
    private final boolean unPausePressed;

    public InputSnapshotImpl(boolean shootPressed, Delta xMovementDelta, boolean pausePressed, boolean unPausePressed) {
        this.shootPressed = shootPressed;
        this.xMovementDelta = xMovementDelta;
        this.pausePressed = pausePressed;
        this.unPausePressed = unPausePressed;
    }

    @Override
    public boolean isShooting() {
        return shootPressed;
    }

    @Override
    public Delta getXMovementDelta() {
        return xMovementDelta;
    }

    @Override
    public boolean isPause() {
        return pausePressed;
    }

    @Override
    public boolean isUnpause() {
        return unPausePressed;
    }

}
