package it.unibo.crabinv.Model.entity;

public interface Entity {

    /**
     * Returns the current amount of HP
     * @return the current amount of HP
     */
    public int getHealth();

    /**
     * Checks if the entity is still alive
     * @return true if alive, false if not
     */
    public boolean isAlive();

    /**
     * Gets the X coordinate of the Entity
     * @return the x coordinate
     */
    public double getX();

    /**
     * Gets the Y coordinate of the Entity
     * @return the y coordinate
     */
    public double getY();

    /**
     * Manages the contact with another entity
     * @param otherEntity the entity that collides with it
     */
    public void onCollisionWith(Entity otherEntity);
}
