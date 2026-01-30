package it.unibo.crabinv.Model.Enemies;

import it.unibo.crabinv.Model.entity.AbstractEntity;
import it.unibo.crabinv.Model.entity.Delta;
import it.unibo.crabinv.Model.entity.Movable;
import it.unibo.crabinv.Model.entity.Shooter;

public class BasicCrab extends AbstractEntity implements Enemy, Shooter, Movable {
    private final EnemyType type;

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
    public void move(Delta delta, double minBound, double maxBound) {

    }

    @Override
    public boolean isAbleToShoot() {
        return false;
    }

    @Override
    public int getFireRate() {
        return 0;
    }

    @Override
    public void shoot() {

    }
}
