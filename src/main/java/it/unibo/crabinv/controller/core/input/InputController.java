package it.unibo.crabinv.controller.core.input;

import it.unibo.crabinv.model.core.input.InputSnapshot;

/**
 * Contract for a controller for the User input.
 * Must use an {@link InputMapper} to bind keys to actions.
 */
public interface InputController {

    /**
     * Manages the keypress.
     *
     * @param keyCode of the key pressed
     */
    void onKeyPressed(int keyCode);

    /**
     * Manages the key release.
     *
     * @param keyCode of the key released
     */
    void onKeyReleased(int keyCode);

    /**
     * Returns the {@link InputSnapshot} to be used by the game logic.
     *
     * @return the Input Snapshot
     */
    InputSnapshot getInputState();
}
