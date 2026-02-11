package it.unibo.crabinv.controller.save;

import it.unibo.crabinv.model.save.GameSession;
import it.unibo.crabinv.model.save.Save;

/**
 * Contract for a controller to manage Session-related operations.
 */
public interface SessionController {

    /**
     * Returns the Save used by the SessionController.
     *
     * @return the Save used by the SessionController
     */
    Save save();

    /**
     * Returns the current GameSession (if it exists).
     *
     * @return the current GameSession, can return null
     */
    GameSession getGameSession();

    /**
     * Checks if there is an active GameSession bound to the save and returns it.
     * If there is no active GameSession creates a new GameSession and binds it to the save
     *
     * @return the loaded or newly created GameSession
     */
    GameSession newGameSession();

    /**
     * Manages the GameSession logic after a GameOver.
     * Saves a SessionRecord, inserts into the PlayerMemorial and deletes the GameSession
     */
    void gameOverGameSession();
}
