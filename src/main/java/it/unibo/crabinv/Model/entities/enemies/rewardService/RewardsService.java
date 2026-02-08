package it.unibo.crabinv.Model.entities.enemies.rewardService;

import it.unibo.crabinv.Model.entities.enemies.Enemy;

/**
 * It's the interface that implements the rewardService communicating the actual amount
 * of what the player should get at the death of an enemy
 */
public interface RewardsService {
    /**
     *Gives the currency based on the enemy
     * @param enemy the dead enemy
     */
    void rewardEnemyDeath(Enemy enemy);
}
