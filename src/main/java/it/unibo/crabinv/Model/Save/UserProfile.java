package it.unibo.crabinv.Model.Save;

import it.unibo.crabinv.Model.PowerUp;


public interface UserProfile {

    int getCurrency();

    /**
     * Sums the amount to the stored currency
     * @param amount the amount of currency to add
     * */
    void addCurrency (int amount);

    /**
     * Checks if a powerUp is already acquired
     * @param powUp the PowUp to check
     * @return if is acquired or not
     */
    boolean hasPowerUp(PowerUp powUp);


    /**
     * Sets the selected powerUp to locked or unlocked
     * @param powUp Id of the power up
     * @param unlocked status to apply to the powerUp
     * */
    void managePowerUp (PowerUp powUp, boolean unlocked);
}
