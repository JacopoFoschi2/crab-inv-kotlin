package it.unibo.crabinv.Controller.input;

import it.unibo.crabinv.Model.entities.entity.Delta;

/**
 * Checks if the key pressed is bound to any action
 */
public interface InputMapper {

    /**
     * Checks if the inputCode of the key pressed is bound to X-axis movement
     *
     * @param inputCode of the key pressed
     * @return the X-axis movement
     */
    Delta mapToXDelta(int inputCode);

    /**
     * Checks if the inputCode of the key pressed is bound to shoot action
     *
     * @param inputCode of the key pressed
     * @return boolean based on the action being requested or not
     */
    boolean mapToShoot(int inputCode);

    /**
     * Checks if the inputCode of the key pressed is bound to pause action
     *
     * @param inputCode of the key pressed
     * @return boolean based on the action being requested or not
     */
    boolean mapToPause(int inputCode);

    /**
     * Checks if the inputCode of the key pressed is bound to resume action
     *
     * @param inputCode of the key pressed
     * @return boolean based on the action being requested or not
     */
    boolean mapToUnPause(int inputCode);
}