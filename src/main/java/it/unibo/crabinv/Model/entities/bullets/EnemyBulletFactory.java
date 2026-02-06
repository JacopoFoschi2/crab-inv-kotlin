package it.unibo.crabinv.Model.entities.bullets;

public class EnemyBulletFactory  implements BulletFactory{
    @Override
    public Bullet createBullet(double x, double y, double radius, double minBound, double maxBound) {
        return new BulletEnemy(1, x,y,radius,0.3,minBound,maxBound);
    }
}
