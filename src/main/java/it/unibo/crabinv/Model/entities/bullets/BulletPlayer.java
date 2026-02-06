package it.unibo.crabinv.Model.entities.bullets;

import it.unibo.crabinv.Model.core.collisions.CollisionGroups;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class BulletPlayer extends AbstractBullet {
    /**
     * Creates the bullet by setting its basic parameters
     *
     * @param maxHealth
     * @param x
     * @param y
     */
    public BulletPlayer(int maxHealth, double x, double y, double radius, double speedY, double minBound, double maxBound) {
        super( x, y, maxHealth, CollisionGroups.FRIENDLY,radius, speedY, minBound, maxBound);
    }
}
