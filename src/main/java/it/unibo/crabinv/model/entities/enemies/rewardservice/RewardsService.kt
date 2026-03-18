package it.unibo.crabinv.model.entities.enemies.rewardservice

import it.unibo.crabinv.model.entities.enemies.Enemy

/**
 * It's the interface that establishes the RewardService.
 */
fun interface RewardsService {
    /**
     * Gives the currency based on the enemy.
     * @param enemy the dead enemy
     */
    fun rewardEnemyDeath(enemy: Enemy)
}
