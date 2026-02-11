package it.unibo.crabinv.model.entities.enemies;

import it.unibo.crabinv.model.core.collisions.CollisionGroups;
import it.unibo.crabinv.model.entities.entity.AbstractEntity;
import it.unibo.crabinv.model.entities.entity.Delta;
import it.unibo.crabinv.model.entities.entity.EntitySprites;
import lombok.experimental.SuperBuilder;

/**
 * It's the enemy implementation.
 */
@SuperBuilder
public class EnemyImpl extends AbstractEntity implements Enemy {
    private final EnemyType type;
    private final int fireRate;
    private final double speed;
    private final double minBound;
    private final double maxBound;
    private int shootingCounter;

    /**
     * It's the enemyImpl constructor.
     *
     * @param x where is the enemy on the x-axis
     * @param y where is the enemy on the y-axis
     * @param maxHealth the maximum health of the enemy
     * @param radius the circle hitbox of the enemy
     * @param type the type of the enemy
     * @param fireRate the firerate of the enemy
     * @param speed the speed of the enemy
     * @param minBound the minimum bound gave by the engine
     * @param maxBound the maximum bound gave by the engine
     * @param sprite the sprite of the enemy
     */
    public EnemyImpl(double x, double y, int maxHealth, double radius, final EnemyType type, int fireRate,
                     double speed, double minBound, double maxBound, EntitySprites sprite) {
        super(x, y, maxHealth, CollisionGroups.HOSTILE, radius, sprite);
        this.type = type;
        this.fireRate = fireRate;
        this.speed = speed;
        this.minBound = minBound;
        this.maxBound = maxBound;
    }

    /**
     * {@inheritDoc}
     *
     * @return the enemyType of the single enemy
     */
    @Override
    public EnemyType getEnemyType() {
        return this.type;
    }

    /**
     * {@inheritDoc}
     *
     * @return the reward for the death of the enemy
     */
    @Override
    public int getReward() {
        return this.type.getRewardForKill();
    }

    /**
     * {@inheritDoc}
     *
     * @param delta the delta of movement, which is either +1, 0 or -1, to be then applied to either the x or y axis
     */
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

    /**
     * {@inheritDoc}
     *
     * @return true if the shooting counter is 0
     */
    @Override
    public boolean isAbleToShoot() {
        return shootingCounter == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return the fireRate of the enemy
     */
    @Override
    public int getFireRate() {
        return fireRate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shoot() {
        shootingCounter = fireRate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tick() {
        if (shootingCounter > 0) {
            shootingCounter--;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return the speed of the enemy
     */
    @Override
    public double getSpeed() {
        return speed;
    }
}
