package it.unibo.crabinv.Controller.core;

import it.unibo.crabinv.Controller.input.InputController;
import it.unibo.crabinv.Controller.save.SessionController;
import it.unibo.crabinv.Model.core.GameEngine;
import it.unibo.crabinv.Model.core.GameSnapshot;
import it.unibo.crabinv.Model.save.GameSession;

import java.io.IOException;

/**
 * Orchestrates SessionController, GameLoop, GameEngine
 */
public interface MetaGameController {

    /**
     * Manages and instructs the components when a new {@link GameSession} must be created
     */
    void startGame();

    /**
     * Controls the status of the components, calls a save update when the game ends
     * @exception IOException if an IO error occurs during the save update
     */
    GameSnapshot stepCheck(long frameElapsedMillis) throws IOException;

    /**
     * Exposes the InputController to make it usable
     * @return the InputController
     */
    InputController getInputController();

    /**
     * Exposes the GameLoopController
     * @return the GameLoopController
     */
    GameLoopController getGameLoopController();

    /**
     * Closes the game, triggers a Game Over, to be used by the pause menu
     */
    void endGame();

    /**
     * Updates the save
     * @exception IOException if an IO error occurs during the save update
     */
    void updateSave() throws IOException;
}
