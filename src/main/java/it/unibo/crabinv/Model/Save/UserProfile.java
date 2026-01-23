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
     * Used to get the level of a powerUp
     * @param powUpName the name of the PowerUp to check
     * @return the level of the powerUp (0 = not yet unlocked)
     */
    int getPowerUpLevel(String powUpName);


    /**
     * Sets the selected powerUp to locked or unlocked
     * @param powUp the name of the power up
     * @param level the level to apply to the powerUp (0 = not yet unlocked)
     * */
    void updatePowerUp (String powUp, int level);

}
