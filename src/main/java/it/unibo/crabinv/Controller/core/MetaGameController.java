package it.unibo.crabinv.Controller.core;

import it.unibo.crabinv.Controller.input.InputController;
import it.unibo.crabinv.Controller.save.SessionController;
import it.unibo.crabinv.Model.core.GameEngine;
import it.unibo.crabinv.Model.core.GameSnapshot;

/**
 * Orchestrates SessionController, GameLoop, GameEngine
 */
public interface MetaGameController {

    /**
     * Manages and instructs the components when a new game must be created
     */
    void startGame();

    /**
     * Controls the status of the components
     */
    GameSnapshot stepCheck(long frameElapsedMillis);

    /**
     * Exposes the InputController to make it usable
     * @return the InputController
     */
    InputController getInputController();
}
