package it.unibo.crabinv.Model.input;

import it.unibo.crabinv.Model.entity.Delta;

public final class InputSnapshotImpl implements InputSnapshot {
    private final boolean shootPressed;
    private final Delta xMovementDelta;
    private final Delta yMovementDelta;

    public InputSnapshotImpl (boolean shootPressed, Delta xMovementDelta, Delta yMovementDelta){
        this.shootPressed = shootPressed;
        this.xMovementDelta = xMovementDelta;
        this.yMovementDelta = yMovementDelta;
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

}
