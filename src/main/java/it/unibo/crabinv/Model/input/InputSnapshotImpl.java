package it.unibo.crabinv.Model.input;

public class InputSnapshotImpl implements InputSnapshot {
    private final CommandMovement movement;
    private final CommandAction action;

    public InputSnapshotImpl (CommandMovement movement, CommandAction action){
        this.movement = movement != null ? movement : CommandMovement.IDLE;
        this.action = action != null ? action : CommandAction.NO_ACTION;
    }

    @Override
    public CommandMovement getMovement() {
        return this.movement;
    }

    @Override
    public CommandAction getAction() {
        return this.action;
    }
}
