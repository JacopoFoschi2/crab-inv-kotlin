package it.unibo.crabinv.Model.entities.bullets;

import it.unibo.crabinv.Model.core.collisions.CollisionGroups;
import it.unibo.crabinv.Model.entities.entity.AbstractEntity;
import it.unibo.crabinv.Model.entities.entity.Delta;
import it.unibo.crabinv.Model.entities.entity.EntitySprites;
import lombok.experimental.SuperBuilder;

/**
 * Provides the base of the bullet.
 */
@SuperBuilder
public abstract class AbstractBullet extends AbstractEntity implements Bullet {
    private final double speed;
    private final double minBound;
    private final double maxBound;

    /**
     * Constructs the bullet.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param maxHealth the max health of the bullet
     * @param collisionGroup the collision group of the bullet
     * @param radius the radius of the bullet, used to compute collisions
     * @param speed the speed of the bullet
     * @param minBound the minimal bounds of the bullet
     * @param maxBound the maximum bounds of the bullet
     * @param sprite the bullet's sprite
     */
    public AbstractBullet(
            final double x,
            final double y,
            final int maxHealth,
            final CollisionGroups collisionGroup,
            final double radius,
            final double speed,
            final double minBound,
            final double maxBound,
            final EntitySprites sprite) {
        super(x, y, maxHealth, collisionGroup, radius, sprite);
        this.speed = speed;
        this.minBound = minBound;
        this.maxBound = maxBound;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(final Delta delta) {
        setPosition(getX(), getY() + speed * delta.getValue());

        if (getY() < minBound || getY() > maxBound) {
            destroy();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getSpeed() {
        return speed;
    }
}
