package it.unibo.crabinv.controller.core.input

import it.unibo.crabinv.model.entities.entity.Delta

/**
 * Checks if the key pressed is bound to any action.
 */
interface InputMapper {
    /**
     * Checks if the inputCode of the key pressed is bound to X-axis movement.
     * @param inputCode of the key pressed
     * @return the X-axis movement
     */
    fun mapToXDelta(inputCode: Int): Delta?

    /**
     * Checks if the inputCode of the key pressed is bound to shoot action.
     * @param inputCode of the key pressed
     * @return boolean based on the action being requested or not
     */
    fun mapToShoot(inputCode: Int): Boolean

    /**
     * Checks if the inputCode of the key pressed is bound to pause action.
     * @param inputCode of the key pressed
     * @return boolean based on the action being requested or not
     */
    fun mapToPause(inputCode: Int): Boolean

    /**
     * Checks if the inputCode of the key pressed is bound to resume action.
     * @param inputCode of the key pressed
     * @return boolean based on the action being requested or not
     */
    fun mapToUnPause(inputCode: Int): Boolean
}
