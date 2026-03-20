package it.unibo.crabinv.model.entities.bullets

/**
 * Interface that establishes the factory of the bullet.
 */
fun interface BulletFactory {
    /**
     * The method that creates the bullet.
     * @param x the horizontal position of the bullet
     * @param y the vertical position of the bullet
     * @param minBound the min bound that must not be touched
     * @param maxBound the max bound that must not be touched
     * @return Bullet, the actual bullet
     */
    fun createBullet(
        x: Double,
        y: Double,
        minBound: Double,
        maxBound: Double,
    ): Bullet
}
