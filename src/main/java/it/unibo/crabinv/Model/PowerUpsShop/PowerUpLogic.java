package it.unibo.crabinv.Model.PowerUpsShop;

public class PowerUpLogic implements PowerUp{
    private final PowerUpType type;
    private final int cost;
    private final int maxLevel;


    public PowerUpLogic(PowerUpType type, int cost , int maxLevel) {
        this.type = type;
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
        return this.type.name();
    }

    @Override
    public PowerUpType getPowerUpType() {
        return type;
    }
}
