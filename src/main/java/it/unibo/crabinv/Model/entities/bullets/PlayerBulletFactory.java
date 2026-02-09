package it.unibo.crabinv.Model.entities.bullets;

public class PlayerBulletFactory implements BulletFactory {
    private final double PLAYER_BULLET_RADIUS = 0.01;
    private final double PLAYER_BULLET_SPEED = 0.01;
    private final int PLAYER_BULLET_MAX_HEALTH = 1;

    @Override
    public Bullet createBullet(double x, double y, double minBound, double maxBound) {
        return new BulletPlayer(PLAYER_BULLET_MAX_HEALTH, x,y,PLAYER_BULLET_RADIUS,PLAYER_BULLET_SPEED,minBound,maxBound);
    }
}
