package it.unibo.crabinv.Model.Enemies;

import it.unibo.crabinv.Model.entity.AbstractEntity;

public class BasicCrab extends AbstractEntity implements Enemy {
    private final EnemyType type;
    private int currencyToGive = 10;

    public BasicCrab(final EnemyType type, int maxHealth, double x, double y) {
        super(maxHealth, x, y);
        this.type = type;
    }

    @Override
    public EnemyType getEnemyType() {
        return this.type;
    }

    @Override
    public String getImagePath() {
        return type.getImagePath();
    }

    @Override
    public int getReward() {
        return currencyToGive;
    }
}
