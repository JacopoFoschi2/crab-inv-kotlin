package it.unibo.crabinv.Model.entities.builder;

import it.unibo.crabinv.Model.core.collisions.CollisionGroups;
import it.unibo.crabinv.Model.entities.entity.AbstractEntity;

/**
 * Provides a basic standard builder for entities
 * @param <T> the implementation of AbstractEntity to build
 */
public interface EntityBuilder<T extends AbstractEntity> {
    /**
     * @param x the x coordinate
     */
    EntityBuilder<T> x(double x);

    /**
     * @param y the y coordinate
     */
    EntityBuilder<T> y(double y);

    /**
     * @param maxHealth the maximum health of the entity
     */
    EntityBuilder<T> MaxHealth(int maxHealth);

    /**
     * @param group the collision group of the entity, either friendly, neutral or hostile
     */
    EntityBuilder<T> collisionGroup(CollisionGroups group);

    /**
     * @param radius the radius of the hitbox
     */
    EntityBuilder<T> radius(double radius);

    /**
     * Builds the entity
     */
    T build();
}
