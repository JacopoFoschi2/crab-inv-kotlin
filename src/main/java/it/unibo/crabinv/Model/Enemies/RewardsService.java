package it.unibo.crabinv.Model.Enemies;

import it.unibo.crabinv.Model.save.UserProfile;

public interface RewardsService {
    /**
     *Gives the currency based on the enemy
     * @param enemy the dead enemy
     */
    void rewardEnemyDeath(Enemy enemy);
}
