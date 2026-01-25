package it.unibo.crabinv.Model.Save;

import java.util.UUID;

/**
 *  Retrieves all data from other save related interfaces ad keep identifiers
 */
public interface Save {

    /** @return UUID of the save */
    UUID getSaveId();

    /** @return timeStamp produced at the creation of the Save */
    long getCreationTimeStamp();

    /** @return GameSession bound to Save */
    GameSession getGameSession();

    /** @return UserProfile bound to Save */
    UserProfile getUserProfile();

    /** @return PlayerMemorial bound to Save */
    PlayerMemorial getPlayerMemorial();
}






