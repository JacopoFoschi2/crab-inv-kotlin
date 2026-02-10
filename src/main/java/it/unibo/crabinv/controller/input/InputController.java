package it.unibo.crabinv.controller.input;

import it.unibo.crabinv.model.input.InputSnapshot;

/**
 * Manages the User input, must use an {@link InputMapper} to bind keys to actions
 */
public interface InputController {

    /**
     * Manages the keypress
     * @param keyCode of the key pressed
     */
    void onKeyPressed(int keyCode);

    /**
     * Manages the key release
     * @param keyCode of the key released
     */
    void onKeyReleased(int keyCode);

    /**
     * Returns the {@link InputSnapshot} to be used by the game logic
     * @return the Input Snapshot
     */
    InputSnapshot getInputState();
}
