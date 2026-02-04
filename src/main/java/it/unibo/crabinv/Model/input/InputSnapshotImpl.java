package it.unibo.crabinv.Model.input;

import it.unibo.crabinv.Model.entity.Delta;

public final class InputSnapshotImpl implements InputSnapshot {
    private final boolean shootPressed;
    private final Delta xMovementDelta;
    private final Delta yMovementDelta;
    private final boolean pausePressed;

    public InputSnapshotImpl(boolean shootPressed, Delta xMovementDelta, Delta yMovementDelta, boolean pausePressed) {
        this.shootPressed = shootPressed;
        this.xMovementDelta = xMovementDelta;
        this.yMovementDelta = yMovementDelta;
        this.pausePressed = pausePressed;
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

}
