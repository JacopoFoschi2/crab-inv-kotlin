package it.unibo.crabinv.model.entities.enemies;

import it.unibo.crabinv.model.core.collisions.CollisionGroups;
import it.unibo.crabinv.model.entities.entity.AbstractEntity;
import it.unibo.crabinv.model.entities.entity.Delta;
import it.unibo.crabinv.model.entities.entity.EntitySprites;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class EnemyImpl extends AbstractEntity implements Enemy {
    private final EnemyType type;
    private final int fireRate;
    private final double speed;
    private final double minBound;
    private final double maxBound;
    private int shootingCounter;

    public EnemyImpl(double x, double y, int maxHealth, double radius, final EnemyType type, int fireRate,
                     double speed, double minBound, double maxBound, EntitySprites sprite) {
        super(x, y, maxHealth, CollisionGroups.HOSTILE, radius, sprite);
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
        double movement = delta.getValue() * speed;
        double newY = this.getY() + movement;
        if (newY < minBound) {
            newY = minBound;
        } else if (newY > maxBound) {
            newY = maxBound;
        }
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
    public double getSpeed() {
        return speed;
    }
}
