package it.unibo.crabinv.model.entities.bullets;

/**
 * Interface that establishes the factory of the bullet.
 */
@FunctionalInterface
public interface BulletFactory {
    /**
     * The method that creates the bullet.
     *
     * @param x the horizontal position of the bullet
     * @param y the vertical position of the bullet
     * @param minBound the min bound that must not be touched
     * @param maxBound the max bound that must not be touched
     * @return Bullet, the actual bullet
     */
    Bullet createBullet(double x, double y, double minBound, double maxBound);
}
