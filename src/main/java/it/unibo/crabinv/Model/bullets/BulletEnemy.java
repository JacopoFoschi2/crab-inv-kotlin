package it.unibo.crabinv.Model.bullets;

import it.unibo.crabinv.Model.core.collisions.CollisionGroups;
import it.unibo.crabinv.Model.entities.entity.AbstractEntity;
import it.unibo.crabinv.Model.entities.entity.Delta;

public class BulletEnemy extends AbstractEntity implements Bullet {
    private final double speedY;
    /**
     * Creates the bullet by setting its basic parameters
     *
     * @param maxHealth
     * @param x
     * @param y
     */
    public BulletEnemy(int maxHealth, double x, double y, double speedY) {
        super(maxHealth, CollisionGroups.HOSTILE, x, y);
        this.speedY = speedY;
    }

    @Override
    public void move(Delta delta, double minBound, double maxBound) {
        setPosition(getX(), getY() + speedY * delta.getValue());

        if (getY() < minBound || getY() > maxBound) {
            destroy();
        }
    }

    @Override
    public double getSpeed() {
        return speedY;
    }
}
