package it.unibo.crabinv.Model.bullets;

import it.unibo.crabinv.Model.core.collisions.CollisionGroups;
import it.unibo.crabinv.Model.entities.entity.AbstractEntity;
import it.unibo.crabinv.Model.entities.entity.Delta;

public abstract class AbstractBullet extends AbstractEntity implements Bullet {
    private final double speed;

    public AbstractBullet(int maxHealth, CollisionGroups collisionGroup, double x, double y, double radius, double speed) {
        super(maxHealth, collisionGroup, x, y, radius);
        this.speed = speed;
    }

    @Override
    public void move(Delta delta, double minBound, double maxBound) {
        setPosition(getX(), getY() + speed * delta.getValue());

        if (getY() < minBound || getY() > maxBound) {
            destroy();
        }
    }

    @Override
    public double getSpeed() {
        return speed;
    }
}
