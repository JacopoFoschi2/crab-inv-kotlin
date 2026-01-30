package it.unibo.crabinv.Controller.input;

import it.unibo.crabinv.Model.entity.Delta;

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
    public Delta mapToYDelta(int inputCode) {
        KeyCodeKeyboard key = KeyCodeKeyboard.findKeyCode(inputCode);
        if (key == null) return Delta.NO_ACTION;

        return switch (key) {
            case UP -> Delta.DECREASE;
            case DOWN -> Delta.INCREASE;
            default -> Delta.NO_ACTION;
        };
    }

    @Override
    public boolean mapToShoot(int inputCode) {
        KeyCodeKeyboard key = KeyCodeKeyboard.findKeyCode(inputCode);
        return key == KeyCodeKeyboard.SHOOT;
    }
}
