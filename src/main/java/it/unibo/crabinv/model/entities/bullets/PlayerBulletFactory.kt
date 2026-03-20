package it.unibo.crabinv.model.entities.bullets

/**
 * It's the playerBulletFactory that creates the bullets of the player.
 */
class PlayerBulletFactory : BulletFactory {
    override fun createBullet(
        x: Double,
        y: Double,
        minBound: Double,
        maxBound: Double,
    ): Bullet =
        BulletPlayer(
            PLAYER_BULLET_MAX_HEALTH,
            x,
            y,
            PLAYER_BULLET_RADIUS,
            PLAYER_BULLET_SPEED,
            minBound,
            maxBound,
        )

    companion object {
        private const val PLAYER_BULLET_RADIUS = 0.01
        private const val PLAYER_BULLET_SPEED = 0.01
        private const val PLAYER_BULLET_MAX_HEALTH = 1
    }
}
