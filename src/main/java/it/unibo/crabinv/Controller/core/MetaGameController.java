package it.unibo.crabinv.Controller.core;

import it.unibo.crabinv.Controller.save.SessionController;
import it.unibo.crabinv.Model.core.GameEngine;

/**
 * Orchestrates SessionController, GameLoop, GameEngine
 */
public interface MetaGameController {

    /**
     * Manages and instructs the components when a new game must be created
     */
    GameLoopController startGame();

    /**
     * Controls the status of the components
     */
    void stepCheck(GameLoopController gameLoopController);
}
