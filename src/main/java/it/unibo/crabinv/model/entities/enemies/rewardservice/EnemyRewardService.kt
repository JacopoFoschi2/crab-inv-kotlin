package it.unibo.crabinv.model.entities.enemies.rewardservice

import it.unibo.crabinv.model.core.save.GameSession
import it.unibo.crabinv.model.entities.enemies.Enemy

/**
 * It's the implementation of the reward service.
 * @param profile it's the profile needed to give the reward
 */
class EnemyRewardService(
    private val profile: GameSession,
) : RewardsService {
    override fun rewardEnemyDeath(enemy: Enemy) {
        profile.addCurrency(enemy.reward)
    }
}
