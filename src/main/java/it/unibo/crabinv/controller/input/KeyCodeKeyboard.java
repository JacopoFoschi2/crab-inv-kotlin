package it.unibo.crabinv.controller.input;

/**
 * Enum of keyboard key to be used by the {@link InputMapper} and the {@link InputController}.
 */
public enum KeyCodeKeyboard {
    LEFT(37),
    RIGHT(39),
    UP(38),
    DOWN(40),
    SHOOT(32), //SPACEBAR
    PAUSE(27), //ESC
    UNPAUSE(8); //BACKSPACE

    private final int keyCode;

    /**
     * Creates a {@link KeyCodeKeyboard} constant with the key.
     *
     * @param keyCode the keyCode of the key
     */
    KeyCodeKeyboard(final int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * Returns the {@link KeyCodeKeyboard} associated with the key code input.
     *
     * @param code the KeyCode of the key to look up
     * @return the corresponding {@link KeyCodeKeyboard} or null if it doesn't exist
     */
    public static KeyCodeKeyboard findKeyCode(final int code) {
        for (final KeyCodeKeyboard k : values()) {
            if (k.keyCode == code) {
                return k;
            }
        }
        return null;
    }

    /**
     * Returns the keyCode associated with the enum constant.
     *
     * @return the keycode associated with the enum constant
     */
    public int getKeyCode() {
        return keyCode;
    }
}
