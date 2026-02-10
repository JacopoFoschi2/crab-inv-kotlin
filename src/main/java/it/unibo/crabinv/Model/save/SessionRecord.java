package it.unibo.crabinv.Model.save;

/**
 * Represents a snapshot of a {@link GameSession}
 */

public interface SessionRecord {

    /** @return the starting timeStamp of the {@link GameSession} */
    long getStartingTimeStamp();

    /** @return the currentLevel at the moment of the record */
    int getLastLevel();

    /** @return the currency at the moment of the record */
    int getLastCurrency();

    /**
     * @return whether the {@link GameSession} has been won
     */
    boolean getWonGame();
}
