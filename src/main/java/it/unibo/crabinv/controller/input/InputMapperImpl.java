package it.unibo.crabinv.controller.input;

import it.unibo.crabinv.model.entities.entity.Delta;

/**
 * {@inheritDoc}
 *
 * <p>Uses {@link KeyCodeKeyboard} to bind keyboard keypresses to actions</p>
 */
public class InputMapperImpl implements InputMapper {

    @Override
    public final Delta mapToXDelta(int inputCode) {
        KeyCodeKeyboard key = KeyCodeKeyboard.findKeyCode(inputCode);
        if (key == null) return Delta.NO_ACTION;

        return switch (key) {
            case LEFT -> Delta.DECREASE;
            case RIGHT -> Delta.INCREASE;
            default -> Delta.NO_ACTION;
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean mapToShoot(final int inputCode) {
        final KeyCodeKeyboard key = KeyCodeKeyboard.findKeyCode(inputCode);
        return key == KeyCodeKeyboard.SHOOT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean mapToPause(final int inputCode) {
        final KeyCodeKeyboard key = KeyCodeKeyboard.findKeyCode(inputCode);
        return key == KeyCodeKeyboard.PAUSE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean mapToUnPause(final int inputCode) {
        final KeyCodeKeyboard key = KeyCodeKeyboard.findKeyCode(inputCode);
        return key == KeyCodeKeyboard.UNPAUSE;
    }
}
