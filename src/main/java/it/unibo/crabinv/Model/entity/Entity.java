package it.unibo.crabinv.Model.entity;

/**
 * Provides all the methods that an entity should implement
 */
public interface Entity {

    /**
     * @return the current amount of HP
     */
    public int getHealth();

    /**
     * @return the max amount of HP an entity has
     */
    public int getMaxHealth();

    /**
     * Checks if the entity is still alive
     * @return true if alive, false if not
     */
    public boolean isAlive();

    /**
     * @return the x coordinate of the entity
     */
    public double getX();

    /**
     * @return the y coordinate of the entity
     */
    public double getY();

    /**
     * Make the entity suffer the amount of damage in input
     * @param damage the damage it should receive
     */
    public void takeDamage(int damage);
}
