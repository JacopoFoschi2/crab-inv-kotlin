package it.unibo.crabinv.model.entities.bullets;

import it.unibo.crabinv.model.core.collisions.CollisionGroups;
import it.unibo.crabinv.model.entities.entity.AbstractEntity;
import it.unibo.crabinv.model.entities.entity.Delta;
import it.unibo.crabinv.model.entities.entity.EntitySprites;
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
     * Constructs the basic bullet.
     *
     * @param x the x coordinate at spawn
     * @param y the y coordinate at spawn
     * @param maxHealth the max health
     * @param collisionGroup the collision group the bullet is part of
     * @param radius the radius needed to compute collision
     * @param speed the speed of the bullet
     * @param minBound the minimal bounds of the screen
     * @param maxBound the maximum bounds of the screen
     * @param sprite the path to the sprite in resources
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
