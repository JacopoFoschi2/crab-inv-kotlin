package it.unibo.crabinv.Model.entity;

import it.unibo.crabinv.Model.core.collisions.CollisionGroups;

/**
 * Provides a basic implementation of what an entity is and what it should do by default
 */
public abstract class AbstractEntity implements Entity {
    private final int maxHealth;
    private final CollisionGroups collisionGroup;
    private int health;
    private double x;
    private double y;

    /**
     * Creates the entity by setting its basic parameters
     */
    public AbstractEntity(int maxHealth, CollisionGroups collisionGroup, double x, double y) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.collisionGroup = collisionGroup;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public void destroy() {
        health = 0;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public CollisionGroups getCollisionGroup() {
        return collisionGroup;
    }

    /**
     * "Teleports" the entity to the set position, to be used with caution
     */
    protected void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
