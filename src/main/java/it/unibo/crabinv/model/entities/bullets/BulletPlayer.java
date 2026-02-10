package it.unibo.crabinv.model.entities.bullets;

import it.unibo.crabinv.model.core.collisions.CollisionGroups;
import it.unibo.crabinv.model.entities.entity.EntitySprites;
import lombok.experimental.SuperBuilder;

/**
 * It's the bullet of the player.
 */
@SuperBuilder
public final class BulletPlayer extends AbstractBullet {
    /**
     * Creates the bullet by setting its basic parameters.
     *
     * @param maxHealth the maxHealth of the buller
     * @param x the position in the horizontal axis where it starts from
     * @param y the position im the vertical axis where it starts from
     * @param radius the actual hitbox of the bullet
     * @param speedY the speed of the bullet, that is used to calculate the movement
     * @param minBound the minimum bound of the screen
     * @param maxBound the maximum bound of the screen
     */
    public BulletPlayer(final int maxHealth, final double x, final double y,
                        final double radius,
                        final double speedY,
                        final double minBound,
                        final double maxBound) {
        super(x, y, maxHealth,
                CollisionGroups.FRIENDLY,
                radius,
                speedY,
                minBound,
                maxBound,
                EntitySprites.PLAYER_BULLET);
    }

    /**
     * {@inheritDoc}
     *
     * @return the sprite of the entity
     */
    @Override
    public EntitySprites getSprites() {
        return EntitySprites.PLAYER_BULLET;
    }
}
