package it.unibo.crabinv.Model.entities.bullets;

/**
 * It's the playerBulletFactory that creates the bullets of the player.
 */
public final class PlayerBulletFactory implements BulletFactory {
    private static final double PLAYER_BULLET_RADIUS = 0.01;
    private static final double PLAYER_BULLET_SPEED = 0.01;
    private static final int PLAYER_BULLET_MAX_HEALTH = 1;

    /**
     * {@inheritDoc}
     *
     * @param x the horizontal position of the bullet
     * @param y the vertical position of the bullet
     * @param minBound the min bound that must not be touched
     * @param maxBound the max bound that must not be touched
     *
     * @return Bullet of the player
     */
    @Override
    public Bullet createBullet(
            final double x,
            final double y,
            final double minBound,
            final double maxBound) {
        return new BulletPlayer(
                PLAYER_BULLET_MAX_HEALTH,
                x,
                y,
                PLAYER_BULLET_RADIUS,
                PLAYER_BULLET_SPEED,
                minBound,
                maxBound);
    }
}
