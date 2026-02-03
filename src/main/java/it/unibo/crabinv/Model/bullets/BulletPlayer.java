package it.unibo.crabinv.Model.bullets;

import it.unibo.crabinv.Model.entity.AbstractEntity;
import it.unibo.crabinv.Model.entity.Delta;
import it.unibo.crabinv.Model.entity.Entity;
import it.unibo.crabinv.Model.entity.Movable;

public class BulletPlayer extends AbstractEntity implements Movable {
    private final double speedY;
    /**
     * Creates the bullet by setting its basic parameters
     *
     * @param maxHealth
     * @param x
     * @param y
     */
    public BulletPlayer(int maxHealth, double x, double y, double speedY) {
        super(maxHealth, x, y);
        this.speedY = speedY;
    }

    @Override
    public void move(Delta delta, double minBound, double maxBound) {
        setPosition(getX(), getY() + speedY * delta.getValue());

        if (getY() < minBound || getY() > maxBound) {
            onCollisionWith(this);
        }
    }

    @Override
    public void onCollisionWith(Entity entity) {
        super.onCollisionWith(entity);
    }
}
