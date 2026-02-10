package it.unibo.crabinv.model.entities.bullets;

/**
 * It's the enemyBulletFactory that handles it.
 */
public final class EnemyBulletFactory implements BulletFactory {
    private static final double ENEMY_BULLET_RADIUS = 0.01;
    private static final double ENEMY_BULLET_SPEED = 0.01;
    private static final int ENEMY_BULLET_MAX_HEALTH = 1;

    /**
     * {@inheritDoc}
     *
     * @param x the horizontal position of the bullet
     * @param y the vertical position of the bullet
     * @param minBound the min bound that must not be touched
     * @param maxBound the max bound that must not be touched
     *
     * @return BulLet, the bullet created
     */
    @Override
    public Bullet createBullet(
            final double x,
            final double y,
            final double minBound,
            final double maxBound) {
        return new BulletEnemy(
                ENEMY_BULLET_MAX_HEALTH,
                x,
                y,
                ENEMY_BULLET_RADIUS,
                ENEMY_BULLET_SPEED,
                minBound,
                maxBound);
    }
}
