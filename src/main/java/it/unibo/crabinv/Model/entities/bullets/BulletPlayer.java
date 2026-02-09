package it.unibo.crabinv.Model.entities.bullets;

import it.unibo.crabinv.Model.core.collisions.CollisionGroups;
import it.unibo.crabinv.Model.entities.entity.EntitySprites;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class BulletPlayer extends AbstractBullet {
    /**
     * Creates the bullet by setting its basic parameters
     * @param maxHealth the maxHealth of the buller
     * @param x the position in the horizontal axis where it starts from
     * @param y the position im the vertical axis where it starts from
     * @param radius the actual hitbox of the bullet
     * @param speedY the speed of the bullet, that is used to calculate the movement
     */
    public BulletPlayer(int maxHealth, double x, double y, double radius, double speedY, double minBound, double maxBound) {
        super( x, y, maxHealth, CollisionGroups.FRIENDLY,radius, speedY, minBound, maxBound, EntitySprites.PLAYER_BULLET);
    }

    @Override
    public EntitySprites getSprites() {
        return EntitySprites.PLAYER_BULLET;
    }
}
