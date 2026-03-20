package it.unibo.crabinv.model.entities.bullets

/**
 * It's the enemyBulletFactory that handles it.
 */
class EnemyBulletFactory : BulletFactory {
    override fun createBullet(
        x: Double,
        y: Double,
        minBound: Double,
        maxBound: Double,
    ): Bullet =
        BulletEnemy(
            ENEMY_BULLET_MAX_HEALTH,
            x,
            y,
            ENEMY_BULLET_RADIUS,
            ENEMY_BULLET_SPEED,
            minBound,
            maxBound,
        )

    companion object {
        private const val ENEMY_BULLET_RADIUS = 0.01
        private const val ENEMY_BULLET_SPEED = 0.01
        private const val ENEMY_BULLET_MAX_HEALTH = 1
    }
}
