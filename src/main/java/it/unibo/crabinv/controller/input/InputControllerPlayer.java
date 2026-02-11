package it.unibo.crabinv.controller.input;

import it.unibo.crabinv.model.entities.entity.Delta;
import it.unibo.crabinv.model.entities.player.Player;
import it.unibo.crabinv.model.input.InputSnapshot;
import it.unibo.crabinv.model.input.InputSnapshotImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of the {@link InputController} for controlling the {@link Player}.
 */
public class InputControllerPlayer implements InputController {

    private final Set<Integer> pressedKeys;
    private final InputMapper mapper;

    /**
     * Constructor for the {@link InputControllerPlayer}.
     *
     * @param mapper the {@link InputMapper} used by the {@link InputControllerPlayer}
     */
    public InputControllerPlayer(final InputMapper mapper) {
        if (mapper == null) {
            throw new NullPointerException("mapper is null");
        }
        this.mapper = mapper;
        this.pressedKeys = new HashSet<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onKeyPressed(final int keyCode) {
        final boolean validKey =
                mapper.mapToShoot(keyCode)
                        || mapper.mapToXDelta(keyCode) != Delta.NO_ACTION
                        || mapper.mapToPause(keyCode)
                        || mapper.mapToUnPause(keyCode);
        if (validKey) {
            this.pressedKeys.add(keyCode);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void onKeyReleased(final int keyCode) {
        this.pressedKeys.remove(keyCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final InputSnapshot getInputState() {
        final boolean isPausePressed = pressedKeys.stream()
                .anyMatch(mapper::mapToPause);
        final boolean isUnPausePressed = pressedKeys.stream()
                .anyMatch(mapper::mapToUnPause);
        final boolean isShooting = pressedKeys.stream()
                .anyMatch(mapper::mapToShoot);
        final boolean isInputRight = pressedKeys.stream()
                .map(mapper::mapToXDelta)
                .anyMatch(delta -> delta == Delta.INCREASE);
        final boolean isInputLeft = pressedKeys.stream()
                .map(mapper::mapToXDelta)
                .anyMatch(delta -> delta == Delta.DECREASE);
        Delta xMovementDelta = Delta.NO_ACTION;
        if (isInputRight && !isInputLeft) {
            xMovementDelta = Delta.INCREASE;
        } else if (isInputLeft && !isInputRight) {
            xMovementDelta = Delta.DECREASE;
        }
        return new InputSnapshotImpl(isShooting, xMovementDelta, isPausePressed, isUnPausePressed);
    }
}
