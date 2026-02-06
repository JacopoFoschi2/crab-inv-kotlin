package it.unibo.crabinv.Model.entities.bullets;

import it.unibo.crabinv.Model.core.collisions.CollisionGroups;
import it.unibo.crabinv.Model.entities.entity.AbstractEntity;
import it.unibo.crabinv.Model.entities.entity.Delta;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class AbstractBullet extends AbstractEntity implements Bullet {
    private final double speed;

    public AbstractBullet(double x, double y, int maxHealth, CollisionGroups collisionGroup, double radius, double speed) {
        super(x, y, maxHealth, collisionGroup, radius);
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
