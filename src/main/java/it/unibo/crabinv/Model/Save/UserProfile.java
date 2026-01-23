package it.unibo.crabinv.Model.Save;

import it.unibo.crabinv.Model.PowerUp;


public interface UserProfile {

    int getCurrentPlayerCurrency();

    /**
     * Sums the amount to the stored currency
     * @param amount the amount of currency to add
     * */
    void increaseCurrency (int amount);

    /**
     * Subtracts the amount to the stored currency
     * @param amount the amount of currency to subtract
     * */
    void subtractCurrency (int amount);

    /**
     * Checks if a powerUp is already acquired
     * @param powUp the PowUp to check
     * @param level the level of the powerUp (0 = not yet unlocked)
     */
    void hasPowerUp(PowerUp powUp, int level);


    /**
     * Sets the selected powerUp to locked or unlocked
     * @param powUp Id of the power up
     * @param level level to apply to the powerUp (0 = not yet unlocked)
     * */
    void updatePowerUp (PowerUp powUp, int level);
}
