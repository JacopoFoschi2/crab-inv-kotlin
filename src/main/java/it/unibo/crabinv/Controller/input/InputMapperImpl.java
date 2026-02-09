package it.unibo.crabinv.Controller.input;

import it.unibo.crabinv.Model.entities.entity.Delta;

/**
 * {@inheritDoc}
 *
 * <p>Uses {@link KeyCodeKeyboard} to bind keyboard keypresses to actions</p>
 */
public class InputMapperImpl implements InputMapper {

    @Override
    public Delta mapToXDelta(int inputCode) {
        KeyCodeKeyboard key = KeyCodeKeyboard.findKeyCode(inputCode);
        if (key == null) return Delta.NO_ACTION;

        return switch (key) {
            case LEFT -> Delta.DECREASE;
            case RIGHT -> Delta.INCREASE;
            default -> Delta.NO_ACTION;
        };
    }

    @Override
    public boolean mapToShoot(int inputCode) {
        KeyCodeKeyboard key = KeyCodeKeyboard.findKeyCode(inputCode);
        return key == KeyCodeKeyboard.SHOOT;
    }

    @Override
    public boolean mapToPause(int inputCode) {
        KeyCodeKeyboard key = KeyCodeKeyboard.findKeyCode(inputCode);
        return key == KeyCodeKeyboard.PAUSE;
    }

    @Override
    public boolean mapToUnPause(int inputCode) {
        KeyCodeKeyboard key = KeyCodeKeyboard.findKeyCode(inputCode);
        return key == KeyCodeKeyboard.UNPAUSE;
    }

}
