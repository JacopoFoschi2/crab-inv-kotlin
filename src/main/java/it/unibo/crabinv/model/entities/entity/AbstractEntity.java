package it.unibo.crabinv.model.entities.entity;

import it.unibo.crabinv.model.core.collisions.CollisionGroups;
import lombok.experimental.SuperBuilder;

/**
 * Provides a basic implementation of what an entity is and what it should do by default.
 */
@SuperBuilder
public abstract class AbstractEntity implements Entity {
    private double x;
    private double y;
    private final int maxHealth;
    private int health;
    private final CollisionGroups collisionGroup;
    private final double radius;
    private final EntitySprites sprite;

    /**
     * Creates the entity by setting its basic parameters.
     *
     * @param x the x coordinate of spawn
     * @param y the y coordinate of spawn
     * @param maxHealth the max health of the entity
     * @param collisionGroup the collision group the entity is part of
     * @param radius the radius of the entity used to compute the collision
     * @param sprite the path to the sprite in resources
     */
    public AbstractEntity(
            final double x,
            final double y,
            final int maxHealth,
            final CollisionGroups collisionGroup,
            final double radius,
            final EntitySprites sprite) {
        this.x = x;
        this.y = y;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.collisionGroup = collisionGroup;
        this.radius = radius;
        this.sprite = sprite;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHealth() {
        return health;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void takeDamage(final int damage) {
        health -= damage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        health = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CollisionGroups getCollisionGroup() {
        return collisionGroup;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getRadius() {
        return radius;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSprite() {
        return sprite.getImagePath();
    }

    /**
     * "Teleports" the entity to the set position, to be used with caution.
     *
     * @param newX the new x coordinate
     * @param newY the new y coordinate
     */
    protected void setPosition(final double newX, final double newY) {
        this.x = newX;
        this.y = newY;
    }
}
