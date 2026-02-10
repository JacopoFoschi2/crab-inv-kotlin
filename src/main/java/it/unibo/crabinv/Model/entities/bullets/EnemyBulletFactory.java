package it.unibo.crabinv.Model.entities.bullets;

public class EnemyBulletFactory  implements BulletFactory{
    private final double ENEMY_BULLET_RADIUS = 0.01;
    private final double ENEMY_BULLET_SPEED = 0.01;
    private final int ENEMY_BULLET_MAX_HEALTH = 1;
    @Override
    public Bullet createBullet(double x, double y, double minBound, double maxBound) {
        return new BulletEnemy(ENEMY_BULLET_MAX_HEALTH, x,y,ENEMY_BULLET_RADIUS,ENEMY_BULLET_SPEED,minBound,maxBound);
    }
}
