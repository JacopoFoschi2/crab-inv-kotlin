package it.unibo.crabinv.Controller.save;

import it.unibo.crabinv.Model.save.GameSession;
import it.unibo.crabinv.Model.save.Save;

public interface SessionController {

    /**
     * Returns the Save used by the SessionController
     * @return the Save used by the SessionController
     */
    Save getSave();

    /**
     * Returns the current GameSession (if it exists)
     * @return the current GameSession, can return null
     */
    GameSession getGameSession();

    /**
     * Checks if there is an active GameSession bound to the save and returns it,
     * if there is no active GameSession creates a new GameSession and binds it to the save
     * @return the loaded or newly created GameSession
     */
    GameSession newGameSession();

    /**
     * Manages the GameSession logic after a GameOver,
     * saves a SessionRecord,
     * inserts in into the PlayerMemorial
     * and deletes the GameSession
     */
    void gameOverGameSession();

}
