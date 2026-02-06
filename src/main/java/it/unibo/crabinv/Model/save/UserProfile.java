package it.unibo.crabinv.Model.save;

import java.util.List;

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
     * @param amount the amount of currency to add
     * */
    void addCurrency (int amount);

    /**
     * Subtracts the amount to the stored currency
     * @param amount the amount of currency to subtract
     * */
    void subCurrency (int amount);

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

    /**
     * Lists the owned powerUps
     * @return the list of the owned powerUps
     */
    List<String> getPowerUpList ();
}
