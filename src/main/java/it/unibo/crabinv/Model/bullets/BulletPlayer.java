package it.unibo.crabinv.Model.bullets;

import it.unibo.crabinv.Model.core.collisions.CollisionGroups;
import it.unibo.crabinv.Model.entities.entity.AbstractEntity;
import it.unibo.crabinv.Model.entities.entity.Delta;

public class BulletPlayer extends AbstractBullet {
    /**
     * Creates the bullet by setting its basic parameters
     *
     * @param maxHealth
     * @param x
     * @param y
     */
    public BulletPlayer(int maxHealth, double x, double y, double radius, double speedY) {
        super(maxHealth, CollisionGroups.FRIENDLY, x, y, radius, speedY);
    }
}
