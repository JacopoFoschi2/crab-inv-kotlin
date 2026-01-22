package it.unibo.crabinv.Model;

public interface GameSession {
    /*Level managing*/
    int getCurrentLevel();
    boolean isGameOver();
    int getNextLevel();

    /*Active run player stats*/
    int getCurrency();
    int getPlayerHealth();

    /**
     * Advances CurrentLevel and NextLevel
     */
    void advanceLevel();

    /**
     * Sets GameOver to true
     */
    void setGameOver();

    /**
     * Adds an amount to current Currency
     * @param amount the currency to add
     */
    void addCurrency(int amount);

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

