package it.unibo.crabinv.Model.input;

import it.unibo.crabinv.Model.entities.entity.Delta;

public final class InputSnapshotImpl implements InputSnapshot {
    private final boolean shootPressed;
    private final Delta xMovementDelta;
    private final Delta yMovementDelta;
    private final boolean pausePressed;
    private final boolean unPausePressed;

    public InputSnapshotImpl(boolean shootPressed, Delta xMovementDelta, Delta yMovementDelta, boolean pausePressed, boolean unPausePressed) {
        this.shootPressed = shootPressed;
        this.xMovementDelta = xMovementDelta;
        this.yMovementDelta = yMovementDelta;
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
    public Delta getYMovementDelta() {
        return yMovementDelta;
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
