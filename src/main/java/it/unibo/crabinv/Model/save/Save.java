package it.unibo.crabinv.Model.save;

import java.util.UUID;

/**
 *  Retrieves all data from other save related interfaces ad keep identifiers
 */
public interface Save {

    /** @return UUID of the save */
    UUID getSaveId();

    /** @return timeStamp produced at the creation of the save */
    long getCreationTimeStamp();

    /** @return GameSession bound to save */
    GameSession getGameSession();

    /** @return UserProfile bound to save */
    UserProfile getUserProfile();

    /** @return PlayerMemorial bound to save */
    PlayerMemorial getPlayerMemorial();
}






