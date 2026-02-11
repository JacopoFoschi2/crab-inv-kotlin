package it.unibo.crabinv.model.save;

/**
 * Represents a snapshot of a {@link GameSession}.
 */
public interface SessionRecord {

    /**
     * Returns the starting timeStamp of the {@link GameSession}.
     *
     * @return the starting timeStamp of the game
     */
    long getStartingTimeStamp();

    /**
     * Returns the current level at the moment of the creation of the record.
     *
     * @return the last level entered
     */
    int getLastLevel();

    /**
     * Returns the currency at the moment of the creation of the record.
     *
     * @return the currency obtained
     */
    int getLastCurrency();

    /**
     * Returns whether the {@link GameSession} has been won.
     *
     * @return if the game has been won
     */
    boolean getWonGame();
}
