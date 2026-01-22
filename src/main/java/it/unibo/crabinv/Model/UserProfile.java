package it.unibo.crabinv.Model;

public interface UserProfile {
    int getCurrency();
    boolean hasPowerUp(powerUp PowUp);
    /**
     * Sums the amount to the stored currency
     * @param amount the amount of currency to add
     * */
    void addCurrency( int amount);
    /**
     * Sets the selected powerUp to locked or unlocked
     * @param powerUp Id of the power up
     * @param unlocked status to apply to the powerUp
     * */
    void managePowerUp ( String powerUp, boolean unlocked);
}
