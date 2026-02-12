package it.unibo.crabinv.controller.core.metagame;

import it.unibo.crabinv.controller.core.gameloop.GameLoopController;
import it.unibo.crabinv.controller.core.input.InputController;
import it.unibo.crabinv.controller.core.save.SessionController;
import it.unibo.crabinv.model.core.engine.GameEngine;
import it.unibo.crabinv.model.core.engine.GameEngineState;
import it.unibo.crabinv.model.core.snapshot.GameSnapshot;
import it.unibo.crabinv.model.core.save.GameSession;
import it.unibo.crabinv.model.core.save.Save;

import java.io.IOException;

/**
 * Orchestrates {@link SessionController}, {@link GameLoopController}, {@link GameEngine}.
 */
public interface MetaGameController {

    /**
     * Manages and instructs the components when a new {@link GameSession} must be created.
     */
    void startGame();

    /**
     * Controls the status of the components, calls a save update when the game ends.
     *
     * @param frameElapsedMillis the milliseconds elapsed of the frame
     * @return the GameSnapshot to check and save when needed
     * @throws IOException if an IO error occurs during the save update
     */
    GameSnapshot stepCheck(long frameElapsedMillis) throws IOException;

    /**
     * Exposes the {@link InputController} to make it usable.
     *
     * @return the {@link InputController}
     */
    InputController getInputController();

    /**
     * Exposes the {@link GameLoopController}.
     *
     * @return the {@link GameLoopController}
     */
    GameLoopController getGameLoopController();

    /**
     * Exposes the {@link GameEngineState} of the underlying {@link GameEngine}.
     *
     * @return a {@link GameEngineState}
     */
    GameEngineState getGameEngineState();

    /**
     * Closes the game, triggers a Game Over, to be used by the pause menu.
     */
    void endGame();

    /**
     * Updates the {@link Save}.
     *
     * @throws IOException if an IO error occurs during the save update
     */
    void updateSave() throws IOException;
}
