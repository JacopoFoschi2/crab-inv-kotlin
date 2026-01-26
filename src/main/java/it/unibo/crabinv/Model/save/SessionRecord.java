package it.unibo.crabinv.Model.save;

/**
 * Represents a snapshot of a GameSession
 */

public interface SessionRecord {

    /** @return the starting timeStamp of the gameSession */
    long getStartingTimeStamp();

    /** @return the currentLevel at the moment of the record */
    int getLastLevel();

    /** @return the currency at the moment of the record */
    int getLastCurrency();
}
