package it.unibo.crabinv.Controller.save;

import it.unibo.crabinv.Model.save.GameSession;
import it.unibo.crabinv.Model.save.PlayerMemorial;
import it.unibo.crabinv.Model.save.Save;
import it.unibo.crabinv.Model.save.UserProfile;

public interface SessionController {

    /**
     * Checks if there is an active gameSession
     * @return
     */
    boolean gameSessionCheck();

    /**
     * Creates a new GameSession based on a UserProfile and assigns it to the Save
     * @param userProfile the UserProfile from which extract the necessary data to create the GameSession
     * @param save the Save that this GameSession will be bound to
     * @return the newly created GameSession
     */
    GameSession newGameSession(UserProfile userProfile, Save save);

    /**
     * Manages the GameSession logic after a GameOver,
     * saves a SessionRecord,
     * inserts in into the PlayerMemorial
     * and deletes the GameSession
     * @param gameSession the GameSession from which extract the necessary data to create the SessionRecord
     * @param playerMemorial the PlayerMemorial where the SessionRecord will be stored
     */
    void gameOverGameSession(GameSession gameSession, PlayerMemorial playerMemorial);

}
