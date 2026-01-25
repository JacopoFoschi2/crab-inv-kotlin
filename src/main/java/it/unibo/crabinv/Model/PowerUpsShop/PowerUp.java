package it.unibo.crabinv.Model.PowerUpsShop;

public interface PowerUp {
    /**
     *
     * @return the cost of the powerUp
     */
    int getCost();

    /**
     *
     * @return the max level of the powerUp
     */
    int getMaxLevel();

    /**
     *
     * @return the powerUp
     */
    String getPowerUpName();
    /**
    * Method needed to take the statistic to modify, that needs to be taken from the player
     */
    String getStatToModify();
    int getStatModifier();
}
