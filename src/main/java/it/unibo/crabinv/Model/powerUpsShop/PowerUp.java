package it.unibo.crabinv.Model.powerUpsShop;

public interface PowerUp {
    /**
     *Method to get the cost of the single powerUp
     * @return the cost of the powerUp
     */
    int getCost();

    /**
     *Method to get to the maxLevel of the powerUp
     * @return the max level of the powerUp
     */
    int getMaxLevel();

    /**
     *Method to get the powerUp's name
     * @return the powerUp name
     */
    String getPowerUpName();

    /**
     * Gets the type of the type of the power up
     * @return the type of the powerUp
     */
    it.unibo.crabinv.Model.powerUpsShop.PowerUpType getPowerUpType();
}
