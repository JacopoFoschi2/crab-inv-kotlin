package it.unibo.crabinv.Model.entities.bullets;

import it.unibo.crabinv.Model.core.collisions.CollisionGroups;
import it.unibo.crabinv.Model.entities.entity.AbstractEntity;
import it.unibo.crabinv.Model.entities.entity.Delta;
import it.unibo.crabinv.Model.entities.entity.EntitySprites;
import lombok.experimental.SuperBuilder;

/**
 * Provides the base of the bullet
 */
@SuperBuilder
public abstract class AbstractBullet extends AbstractEntity implements Bullet {
    private final double speed;
    private final double minBound;
    private final double maxBound;

    public AbstractBullet(double x, double y, int maxHealth, CollisionGroups collisionGroup, double radius, double speed, double minBound, double maxBound, EntitySprites sprite) {
        super(x, y, maxHealth, collisionGroup, radius, sprite);
        this.speed = speed;
        this.minBound = minBound;
        this.maxBound = maxBound;
    }

    @Override
    public void move(Delta delta) {
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
