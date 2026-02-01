package it.unibo.crabinv.Model.Enemies;

import it.unibo.crabinv.Model.entity.AbstractEntity;
import it.unibo.crabinv.Model.entity.Delta;
import it.unibo.crabinv.Model.entity.Movable;

public class BasicCrab extends AbstractEntity implements Enemy, Movable {
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
        return this.type.getImagePath();
    }

    @Override
    public int getReward() {
        return this.currencyToGive;
    }

    @Override
    public void move(Delta delta, double minBound, double maxBound) {
        double newY = this.getY() + delta.getValue();
        if (newY<minBound){newY=minBound;}
        if (newY>maxBound){newY=maxBound;}
        this.setPosition(this.getX(), newY);
    }
}
