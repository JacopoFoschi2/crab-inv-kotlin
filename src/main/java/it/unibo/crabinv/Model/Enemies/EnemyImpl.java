package it.unibo.crabinv.Model.Enemies;

import it.unibo.crabinv.Model.bullets.BulletEnemy;
import it.unibo.crabinv.Model.collisions.CollisionGroups;
import it.unibo.crabinv.Model.entity.*;

public class EnemyImpl extends AbstractEntity implements Enemy {
    private final EnemyType type;
    private int currencyToGive = 10;
    private int shootingCounter;
    private final int fireRate;
    private final double speed;

    public EnemyImpl(final EnemyType type, int maxHealth, double x, double y, int fireRate, double speed) {
        super(maxHealth, CollisionGroups.HOSTILE, x, y);
        this.type = type;
        this.fireRate = fireRate;
        this.speed = speed;
    }

    @Override
    public EnemyType getEnemyType() {
        return this.type;
    }

    @Override
    public String getImagePath() {
        return this.type.getImagePath();
    }

    @Override
    public int getReward() {
        return this.currencyToGive;
    }

    @Override
    public void move(Delta delta, double minBound, double maxBound) {
        double newY = this.getY() + delta.getValue();
        if (newY<minBound){newY=minBound;}
        if (newY>maxBound){newY=maxBound;}
        this.setPosition(this.getX(), newY);
    }

    @Override
    public boolean isAbleToShoot() {
        return shootingCounter == 0;
    }

    @Override
    public int getFireRate() {
        return fireRate;
    }

    @Override
    public void shoot() {
        shootingCounter = fireRate;
    }

    /**
     * Updates the state of the internal counters of Player
     */
    @Override
    public void tick() {
        if (shootingCounter > 0) {
            shootingCounter--;
        }
    }

    @Override
    public boolean isFriendlyFire(Entity other) {
        return other instanceof BulletEnemy;
    }

    @Override
    public double getSpeed() {
        return speed;
    }
}
