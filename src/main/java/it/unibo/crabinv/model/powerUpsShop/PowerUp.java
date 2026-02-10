package it.unibo.crabinv.model.powerUpsShop;

/**
 * It's the interface that implements the methods for the powerUp.
 */
public interface PowerUp {
    /**
     * Method to get the cost of the single powerUp.
     *
     * @return the cost of the powerUp
     */
    int getCost();

    /**
     * Method to get to the maxLevel of the powerUp.
     *
     * @return the max level of the powerUp
     */
    int getMaxLevel();

    /**
     * Method to get the powerUp's name.
     *
     * @return the powerUp name
     */
    String getPowerUpName();

    /**
     * Gets the type of the type of the power up.
     *
     * @return the type of the powerUp
     */
    it.unibo.crabinv.model.powerUpsShop.PowerUpType getPowerUpType();
}
