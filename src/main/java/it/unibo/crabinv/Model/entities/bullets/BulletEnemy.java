package it.unibo.crabinv.Model.entities.bullets;

import it.unibo.crabinv.Model.core.collisions.CollisionGroups;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class BulletEnemy extends AbstractBullet {
    /**
     * Creates the bullet by setting its basic parameters
     *
     * @param maxHealth
     * @param x
     * @param y
     */
    public BulletEnemy(int maxHealth, double x, double y, double radius, double speedY, double minBound, double maxBound) {
        super( x, y, maxHealth, CollisionGroups.HOSTILE,radius, speedY, minBound, maxBound);
    }
}
