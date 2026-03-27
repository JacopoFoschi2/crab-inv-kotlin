package it.unibo.crabinv.model.entities.enemies

import it.unibo.crabinv.model.entities.entity.EntitySprites

/**
 * It's the implementation of the Enemy factory.
 */
class BaseEnemyFactoryLogic : EnemyFactory {
    override fun createEnemy(
        type: EnemyType?,
        x: Double,
        y: Double,
        minBound: Double,
        maxBound: Double,
    ): Enemy =
        EnemyImpl(
            x,
            y,
            ENEMY_MAX_HEALTH,
            ENEMY_RADIUS,
            type,
            ENEMY_FIRERATE,
            ENEMY_SPEED,
            minBound,
            maxBound,
            EntitySprites.ENEMY_SERVANT,
        )

    companion object {
        private const val ENEMY_MAX_HEALTH = 1
        private const val ENEMY_RADIUS = 0.015
        private const val ENEMY_SPEED = 0.00085
        private const val ENEMY_FIRERATE = 30
    }
}
