package it.unibo.crabinv.Model.entities.entity;

import it.unibo.crabinv.Model.core.collisions.CollisionGroups;

/**
 * Provides all the methods that an entity should implement.
 */
public interface Entity {
    int CONTACT_DAMAGE = 1;

    /**
     * @return the current amount of HP
     */
    int getHealth();

    /**
     * @return the max amount of HP an entity has
     */
    int getMaxHealth();

    /**
     * Checks if the entity is still alive.
     *
     * @return true if alive, false if not
     */
    boolean isAlive();

    /**
     * @return the x coordinate of the entity
     */
    double getX();

    /**
     * @return the y coordinate of the entity
     */
    double getY();

    /**
     * Make the entity suffer the amount of damage in input.
     *
     * @param damage the damage it should receive
     */
    void takeDamage(int damage);

    /**
     * Make the entity die.
     */
    void destroy();

    /**
     * @return the collision group the entity is part of
     */
    CollisionGroups getCollisionGroup();

    /**
     * @return the radius of the entity used to solve collision
     */
    double getRadius();

    /**
     * @return the sprite of the entity
     */
    String getSprite();
}
