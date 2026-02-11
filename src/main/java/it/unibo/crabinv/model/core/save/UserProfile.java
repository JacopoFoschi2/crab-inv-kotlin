package it.unibo.crabinv.model.core.save;

import it.unibo.crabinv.model.powerups.PowerUpType;

/**
 * Represents the meta-data of a single player's profile, exposes currency and powerUp's data.
 */

public interface UserProfile {

    /**
     * Returns the current currency.
     *
     * @return the current currency
     */
    int getCurrency();

    /**
     * Sums the amount to the stored currency.
     *
     * @param amount the amount of currency to add
     */
    void addCurrency(int amount);

    /**
     * Subtracts the amount to the stored currency.
     *
     * @param amount the amount of currency to subtract
     */
    void subCurrency(int amount);

    /**
     * Returns the level of a powerUp.
     *
     * @param powUpType the type of the PowerUp to check
     * @return the level of the powerUp
     */
    int getPowerUpLevel(PowerUpType powUpType);

    /**
     * Sets the selected powerUp to the updated level number.
     *
     * @param powerUpType the name of the power up
     * @param level       the level to apply to the powerUp
     */
    void updatePowerUp(PowerUpType powerUpType, int level);

    /**
     * Applies the power ups by multiplication.
     *
     * <p>Created by Mose Barbieri, moved and adapted by Jonathan Crescentini
     *
     * @param powerUpType the {@link PowerUpType} to be modified
     * @return the modified value of the {@link PowerUpType}
     */
    double applyMultiplyPowerUp(PowerUpType powerUpType);

    /**
     * Applies the power ups by division.
     *
     * <p>Created by Mose Barbieri, moved and adapted by Jonathan Crescentini
     *
     * @param powerUpType the {@link PowerUpType} to be modified
     * @return the modified value of the {@link PowerUpType}
     */
    double applyDividePowerUp(PowerUpType powerUpType);

    /**
     * Applies the power ups by addition.
     *
     * <p>Created by Mose Barbieri, moved and adapted by Jonathan Crescentini
     *
     * @param powerUpType the {@link PowerUpType} to be modified
     * @return the modified value of the {@link PowerUpType}
     */
    double applyAddPowerUp(PowerUpType powerUpType);
}
