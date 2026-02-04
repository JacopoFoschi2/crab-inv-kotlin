package it.unibo.crabinv.Model.save;

import java.util.UUID;

/**
 *  Retrieves all data from other save-related interfaces and keep identifiers
 */
public interface Save {

    /** @return UUID of the save */
    UUID getSaveId();

    /** @return timeStamp produced at the creation of the save */
    long getCreationTimeStamp();

    /**
     * Assigns a gameSession to the save
     * @param gameSession the GameSession to assign to the save
     */
    void setGameSession(GameSession gameSession);

    /** @return GameSession bound to save */
    GameSession getGameSession();

    /**
     * Creates the UserProfile of the save
     */
    void createUserProfile();

    /** @return UserProfile bound to save */
    UserProfile getUserProfile();

    /**
     * Creates the PlayerMemorial of the save
     */
    void createPlayerMemorial();

    /** @return PlayerMemorial bound to save */
    PlayerMemorial getPlayerMemorial();
}






