package it.unibo.crabinv.Controller.input;

import it.unibo.crabinv.Model.entity.Delta;
import it.unibo.crabinv.Model.input.InputSnapshot;
import it.unibo.crabinv.Model.input.InputSnapshotImpl;

import java.util.HashSet;
import java.util.Set;


/**
 * Returns an InputSnapshot based on InputMapper and the key pressed/released
 */
public class InputControllerPlayer implements InputController {

    private final Set<Integer> pressedKeys;
    private final InputMapper mapper;


    public InputControllerPlayer(InputMapper mapper) {
        if (mapper == null) {
            throw new NullPointerException("mapper is null");
        }
        this.mapper = mapper;
        this.pressedKeys = new HashSet<>();
    }

    public void onKeyPressed(int keyCode) {
        boolean validKey = mapper.mapToShoot(keyCode) || mapper.mapToXDelta(keyCode) != Delta.NO_ACTION;
        if (validKey) {
            this.pressedKeys.add(keyCode);
        }
    }

    public void onKeyReleased(int keyCode) {
        this.pressedKeys.remove(keyCode);
    }

    @Override
    public InputSnapshot getInputState() {
        boolean isShooting = pressedKeys.stream()
                .anyMatch(mapper::mapToShoot);
        boolean isInputRight = pressedKeys.stream()
                .map(mapper::mapToXDelta)
                .anyMatch(delta -> delta == Delta.INCREASE);
        boolean isInputLeft = pressedKeys.stream()
                .map(mapper::mapToXDelta)
                .anyMatch(delta -> delta == Delta.DECREASE);
        Delta xMovementDelta = Delta.NO_ACTION;
        if (isInputRight && !isInputLeft) {
            xMovementDelta = Delta.INCREASE;
        } else if (isInputLeft && !isInputRight) {
            xMovementDelta = Delta.DECREASE;
        }
        return new InputSnapshotImpl(isShooting, xMovementDelta, Delta.NO_ACTION);
    }
}
