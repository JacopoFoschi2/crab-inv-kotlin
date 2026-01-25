package it.unibo.crabinv.Model.Save;

public interface GameSession {

    /* Level managing */
    int getCurrentLevel();

    int getNextLevel();

    boolean isGameOver();

    /* Active run player stats */

    long getStartingTimeStamp();

    int getCurrency();

    int getPlayerHealth();

    /* Logic */
    /**
     * Advances CurrentLevel and NextLevel
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
     * @param amount the currency to add
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

