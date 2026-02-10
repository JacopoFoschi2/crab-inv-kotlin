package it.unibo.crabinv.model.core.collisions;

import it.unibo.crabinv.model.entities.entity.Entity;

import java.util.List;

/**
 * Provides the method to resolve all collisions in a set tick.
 * It's time complexity limits it to an amount of entities that shouldn't
 * be larger than a few hundreds
 */
public class CollisionSystem {
    /**
     * Resolves all collisions between entities.
     *
     * @param entities the list of all the entities on the screen
     * @return true if there has been at least one collision, false if there hasn't
     */
    public boolean resolve(final List<Entity> entities) {
        boolean collisionOccurred = false;
        for (int first = 0; first < entities.size(); first++) {
            for (int second = first + 1; second < entities.size(); second++) {
                Entity entity1 = entities.get(first);
                Entity entity2 = entities.get(second);
                if (isColliding(entity1, entity2)) {
                    entity1.takeDamage(Entity.CONTACT_DAMAGE);
                    entity2.takeDamage(Entity.CONTACT_DAMAGE);
                    collisionOccurred = true;
                }
            }
        }
        return collisionOccurred;
    }

    /**
     * Checks all cases needed to determine if two entities are colliding or not.
     *
     * @return true if the two entities collided, false if not
     */
    private boolean isColliding(final Entity entity1, final Entity entity2) {
        if(entity1.isAlive() && entity2.isAlive()) {
            if(entity1.getCollisionGroup() != entity2.getCollisionGroup()) {
                return solveCollision(entity1, entity2);
            }
        }
        return false;
    }

    /**
     * Solves collision by computing the squared distance between the two centers,
     * and then compares it with the squared sum of the radius.
     *
     * @return if the collision happened or not
     */
    private boolean solveCollision(final Entity entity1, final Entity entity2) {
        double dx = entity2.getX() - entity1.getX();
        double dy = entity2.getY() - entity1.getY();
        double distanceSquared = dx * dx + dy * dy;
        double radiusSum = entity1.getRadius() + entity2.getRadius();
        double radiusSumSquared = radiusSum * radiusSum;
        return distanceSquared <= radiusSumSquared;
    }
}
