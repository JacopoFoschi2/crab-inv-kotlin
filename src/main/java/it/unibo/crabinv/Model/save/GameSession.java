package it.unibo.crabinv.Model.save;

/**
 * Represents a single game session,
 * exposes level, currency, and player health.
 */

public interface GameSession {

    /**
     * @return the current level index
     */
    int getCurrentLevel();

    /**
     * @return the next level index
     */
    int getNextLevel();

    /**
     * @return true if the session has ended
     */
    boolean isGameOver();

    /**
     * @return timestamp (ms) when the session started
     */
    long getStartingTimeStamp();

    /**
     * @return current amount of in‑game currency
     */
    int getCurrency();

    /**
     * @return current player health
     * */
    int getPlayerHealth();

    /**
     * Advances CurrentLevel and NextLevel counters
     */
    void advanceLevel();

    /**
     * Sets GameOver to true
     */
    void markGameOver();

    /**
     * Adds an amount to current Currency
     * @param amount the currency to add
     */
    void addCurrency(int amount);

    /**
     * Subtracts an amount to current Currency
     * @param amount the currency to subtract
     */
    void subCurrency(int amount);

    /**
     * Adds an amount to current PlayerHealth
     * @param amount the amount to add
     */
    void addPlayerHealth(int amount);

    /**
     * Subtracts an amount from current PlayerHealth
     * @param amount the amount to subtract
     */
    void subPlayerHealth(int amount);
}

