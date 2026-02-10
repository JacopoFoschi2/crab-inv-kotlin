package it.unibo.crabinv.model.entities.enemies;

import it.unibo.crabinv.model.entities.entity.Entity;
import it.unibo.crabinv.model.entities.entity.Movable;
import it.unibo.crabinv.model.entities.entity.Shooter;

/**
 * It's the interface that establish the enemy
 */
public interface Enemy extends Entity, Movable,Shooter {
    /**
     * Getter for the enemy Type that the enemy has
     * @return the enemy type of that specific enemy
     */
    public EnemyType getEnemyType();

    /**
     * Gives the amount of currency to give back to the player
     * @return the amount of the reward for enemy defeat
     */
    int getReward();

    /**
     * Updates at the tick the single enemy
     */
    void tick();
}
