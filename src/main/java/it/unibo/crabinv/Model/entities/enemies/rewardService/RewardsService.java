package it.unibo.crabinv.Model.entities.enemies.rewardService;

import it.unibo.crabinv.Model.entities.enemies.Enemy;

public interface RewardsService {
    /**
     *Gives the currency based on the enemy
     * @param enemy the dead enemy
     */
    void rewardEnemyDeath(Enemy enemy);
}
