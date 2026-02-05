package it.unibo.crabinv.Model.entity;

/**
 * Provides a basic implementation of what an entity is and what it should do by default
 */
public abstract class AbstractEntity implements Entity {
    /**
     * The damage that all entities currently suffer upon collision with a shot.
     * Can be modified upon further development
     */
    private final static int DAMAGE = 1;
    private final int maxHealth;
    private int health;
    private double x;
    private double y;

    /**
     * Creates the entity by setting its basic parameters
     */
    public AbstractEntity(int maxHealth, double x, double y) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.x = x;
        this.y = y;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void onCollisionWith(Entity other) {
        health -= DAMAGE;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * "Teleports" the entity to the set position, to be used with caution
     */
    protected void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
