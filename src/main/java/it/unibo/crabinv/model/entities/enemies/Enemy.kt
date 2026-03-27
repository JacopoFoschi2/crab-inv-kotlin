package it.unibo.crabinv.model.entities.enemies

import it.unibo.crabinv.model.entities.entity.Entity
import it.unibo.crabinv.model.entities.entity.Movable
import it.unibo.crabinv.model.entities.entity.Shooter

/**
 * It's the interface that establish the enemy.
 */
interface Enemy :
    Entity,
    Movable,
    Shooter {
    /**
     * @return the enemy type of that specific enemy
     */
    val enemyType: EnemyType?

    /**
     * Gives the amount of currency to give back to the player.
     * @return the amount of the reward for enemy defeat
     */
    val reward: Int

    /**
     * Updates at the tick the single enemy.
     */
    fun tick()
}
