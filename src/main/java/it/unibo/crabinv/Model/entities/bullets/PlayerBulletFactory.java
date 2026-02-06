package it.unibo.crabinv.Model.entities.bullets;

public class PlayerBulletFactory implements BulletFactory {

    @Override
    public Bullet createBullet(double x, double y, double radius, double minBound, double maxBound) {
        return new BulletPlayer(1, x,y,radius,0.3,minBound,maxBound);
    }
}
