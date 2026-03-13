package it.unibo.crabinv.controller.core.save

import it.unibo.crabinv.model.core.save.GameSession
import it.unibo.crabinv.model.core.save.Save

/**
 * Contract for a controller to manage Session-related operations.
 */
interface SessionController {
    /**
     * @return the Save used by the SessionController
     */
    val save: Save

    /**
     * @return the current GameSession, can return null
     */
    val gameSession: GameSession

    /**
     * Checks if there is an active GameSession bound to the save and returns it.
     * If there is no active GameSession creates a new GameSession and binds it to the save
     * @return the loaded or newly created GameSession
     */
    fun newGameSession(): GameSession

    /**
     * Manages the GameSession logic after a GameOver.
     * Saves a SessionRecord, inserts into the PlayerMemorial and deletes the GameSession
     */
    fun gameOverGameSession()
}
