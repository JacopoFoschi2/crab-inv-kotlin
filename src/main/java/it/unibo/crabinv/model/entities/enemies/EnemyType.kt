package it.unibo.crabinv.model.entities.enemies

/**
 * It's the identifier of the enemy.
 * @param rewardForKill the reward for the death of the enemy
 */
enum class EnemyType(
    /**
     * @return the actual value for the reward
     */
    val rewardForKill: Int,
) {
    SERVANT(10),
}
