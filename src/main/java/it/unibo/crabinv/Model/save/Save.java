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
     * Creates a new GameSession based on the UserProfile, does nothing if a GameSession is already present
     */
    void newGameSession();

    /**
     * Closes the GameSession
     */
    void closeGameSession();

    /** @return GameSession bound to save */
    GameSession getGameSession();

    /** @return UserProfile bound to save */
    UserProfile getUserProfile();

    /** @return PlayerMemorial bound to save */
    PlayerMemorial getPlayerMemorial();
}






