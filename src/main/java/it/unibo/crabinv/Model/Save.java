package it.unibo.crabinv.Model;

/**
 *  Retrieves all data from other save related interfaces, to be used by a SaveController
 */
public interface Save {
    GameSession getGameSession();
    UserProfile getUserProfile();
    PlayerMemorial getPlayerMemorial();
}






