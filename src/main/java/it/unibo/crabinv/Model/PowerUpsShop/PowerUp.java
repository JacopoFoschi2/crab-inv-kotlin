package it.unibo.crabinv.Model.PowerUpsShop;

public interface PowerUp {
    int getCost();
    int getMaxLevel();
    String getPowerUpName();
    /**
    * Method needed to take the statistic to modify, that needs to be taken from the player
     */
    String getStatToModify();
    int getStatModifier();
}
