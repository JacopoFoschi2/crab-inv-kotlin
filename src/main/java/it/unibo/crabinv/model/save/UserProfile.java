package it.unibo.crabinv.model.save;

import it.unibo.crabinv.model.powerups.PowerUpType;

/**
 * Represents the meta-data of a single player's profile,
 * exposes currency and powerUp's levels
 */

public interface UserProfile {

    /**
     * @return the current currency
     */
    int getCurrency();

    /**
     * Sums the amount to the stored currency
     *
     * @param amount the amount of currency to add
     *
     */
    void addCurrency(int amount);

    /**
     * Subtracts the amount to the stored currency
     *
     * @param amount the amount of currency to subtract
     *
     */
    void subCurrency(int amount);

    /**
     * Used to get the level of a powerUp
     *
     * @param powUpType the type of the PowerUp to check
     * @return the level of the powerUp (defaults to 0 if null or invalid)
     */
    int getPowerUpLevel(PowerUpType powUpType);


    /**
     * Sets the selected powerUp the updated level number
     *
     * @param powerUpType the name of the power up
     * @param level       the level to apply to the powerUp
     *
     */
    void updatePowerUp(PowerUpType powerUpType, int level);

    /**
     * Applies the power ups by multiplication
     * <p>Created by Mose Barbieri, moved and adapted by Jonathan Crescentini
     */
    double applyMultiplyPowerUp(PowerUpType powerUpType);

    double applyDividePowerUp(PowerUpType powerUpType);

    double applyAddPowerUp(PowerUpType powerUpType);

}
