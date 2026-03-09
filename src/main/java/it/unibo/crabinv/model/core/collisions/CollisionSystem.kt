package it.unibo.crabinv.model.core.collisions

import it.unibo.crabinv.model.entities.entity.Entity

/**
 * Provides the method to resolve all collisions in a set tick.
 * It's time complexity limits it to an amount of entities that shouldn't
 * be larger than a few hundreds
 */
class CollisionSystem {
    /**
     * Resolves all collisions between entities.
     * @param entities the list of all the entities on the screen
     * @return true if there has been at least one collision, false if there hasn't
     */
    fun resolve(entities: List<Entity>): Boolean {
        var collisionOccurred = false
        for (first in entities.indices) {
            for (second in first + 1..<entities.size) {
                val entity1 = entities[first]
                val entity2 = entities[second]
                if (isColliding(entity1, entity2)) {
                    entity1.takeDamage(Entity.CONTACT_DAMAGE)
                    entity2.takeDamage(Entity.CONTACT_DAMAGE)
                    collisionOccurred = true
                }
            }
        }
        return collisionOccurred
    }

    /**
     * Checks all cases needed to determine if two entities are colliding or not.
     * @param entity1 the first entity
     * @param entity2 the second entity
     * @return true if the two entities collided, false if not
     */
    private fun isColliding(
        entity1: Entity,
        entity2: Entity,
    ): Boolean {
        if (entity1.isAlive() && entity2.isAlive() && entity1.getCollisionGroup() != entity2.getCollisionGroup()) {
            return solveCollision(entity1, entity2)
        }
        return false
    }

    /**
     * Solves collision by computing the squared distance between the two centers,
     * and then compares it with the squared sum of the radius.
     * @param entity1 the first entity
     * @param entity2 the second entity
     * @return if the collision happened or not
     */
    private fun solveCollision(
        entity1: Entity,
        entity2: Entity,
    ): Boolean {
        val dx = entity2.getX() - entity1.getX()
        val dy = entity2.getY() - entity1.getY()
        val distanceSquared = dx * dx + dy * dy
        val radiusSum = entity1.getRadius() + entity2.getRadius()
        val radiusSumSquared = radiusSum * radiusSum
        return distanceSquared <= radiusSumSquared
    }
}
