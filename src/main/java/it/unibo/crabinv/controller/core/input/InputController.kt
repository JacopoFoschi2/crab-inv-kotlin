package it.unibo.crabinv.controller.core.input

import it.unibo.crabinv.model.core.input.InputSnapshot

/**
 * Contract for a controller for the User input.
 * Must use an [InputMapper] to bind keys to actions.
 */
interface InputController {
    /**
     * Manages the keypress.
     * @param keyCode of the key pressed
     */
    fun onKeyPressed(keyCode: Int)

    /**
     * Manages the key release.
     * @param keyCode of the key released
     */
    fun onKeyReleased(keyCode: Int)

    /**
     * Returns the [InputSnapshot] to be used by the game logic.
     * @return the Input Snapshot
     */
    val inputState: InputSnapshot?
}
