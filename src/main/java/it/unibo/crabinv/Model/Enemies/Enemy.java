package it.unibo.crabinv.Model.Enemies;

import it.unibo.crabinv.Model.entity.Entity;

public interface Enemy extends Entity {
    /**
     * Getter for the enemy Type that the enemy has
     * @return the enemy type of that specific enemy
     */
    public EnemyType getEnemyType();

    /**
     * Getter for the imagePath of the enemy
     * @return the image path
     */
    String getImagePath();

    /**
     * Gives the amount of currency to give back to the player
     * @return the amount of the reward for enemy defeat
     */
    int getReward();

    /**
     * Updates at the tick the single enemy
     */
    void tick();

    /**
     *
     * @return true if the bullet is shot by another enemy is true else is false
     */
    boolean isFriendlyFire(Entity other);
}
