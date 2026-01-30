package it.unibo.crabinv.Model.PowerUpsShop;

public class PowerUpLogic implements PowerUp{
    private final String powerUpName;
    private final int cost;
    private final int maxLevel;


    public PowerUpLogic(String powerUpName, int cost , int maxLevel) {
        this.powerUpName = powerUpName;
        this.cost = cost;
        this.maxLevel = maxLevel;
    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public String getPowerUpName() {
        return this.powerUpName;
    }

}
