package it.unibo.crabinv.Model.entities.enemies;

import it.unibo.crabinv.Model.entities.bullets.BulletEnemy;
import it.unibo.crabinv.Model.core.collisions.CollisionGroups;
import it.unibo.crabinv.Model.entities.entity.AbstractEntity;
import it.unibo.crabinv.Model.entities.entity.Delta;
import it.unibo.crabinv.Model.entities.entity.Entity;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class EnemyImpl extends AbstractEntity implements Enemy {
    private final EnemyType type;
    private int shootingCounter;
    private final int fireRate;
    private final double speed;
    private final double minBound;
    private final double maxBound;

    public EnemyImpl( double x, double y, int maxHealth, double radius, final EnemyType type, int fireRate,
                      double speed, double minBound, double maxBound) {
        super( x, y, maxHealth, CollisionGroups.HOSTILE, radius);
        this.type = type;
        this.fireRate = fireRate;
        this.speed = speed;
        this.minBound = minBound;
        this.maxBound = maxBound;
    }

    @Override
    public EnemyType getEnemyType() {
        return this.type;
    }

    @Override
    public int getReward() {
        return this.type.getRewardForKill();
    }

    @Override
    public void move(Delta delta) {
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
