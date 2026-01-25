package it.unibo.crabinv.Model.entity;

public abstract class AbstractEntity implements Entity {
    private final int maxHealth;
    private int health;
    private double x;
    private double y;

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
        health -= 1;
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
}
