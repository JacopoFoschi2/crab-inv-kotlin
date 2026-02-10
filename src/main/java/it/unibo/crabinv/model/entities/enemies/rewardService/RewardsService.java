package it.unibo.crabinv.model.entities.enemies.rewardService;

import it.unibo.crabinv.model.entities.enemies.Enemy;

/**
 * It's the interface that implements the rewardService communicating the actual amount
 * of what the player should get at the death of an enemy.
 */
public interface RewardsService {
    /**
     * Gives the currency based on the enemy.
     *
     * @param enemy the dead enemy
     */
    void rewardEnemyDeath(final Enemy enemy);
}
