package it.unibo.crabinv.model.entities.enemies;

import it.unibo.crabinv.model.core.collisions.CollisionGroups;
import it.unibo.crabinv.model.entities.entity.AbstractEntity;
import it.unibo.crabinv.model.entities.entity.Delta;
import it.unibo.crabinv.model.entities.entity.EntitySprites;
import lombok.experimental.SuperBuilder;

/**
 * It's the implementation of the enemy, taking from the abstract entity.
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
     * @param x it's the x where the enemy stays
     * @param y it's the y where the enemy moves on
     * @param maxHealth it's the maxHealth of the enemy
     * @param radius it's the radius of the enemy
     * @param type it's the type of the enemy
     * @param fireRate it's the firerate of the enemy
     * @param speed it's the speed of the enemy
     * @param minBound it's the minbound taken from the engine
     * @param maxBound it's the maxbound takend from the engine
     * @param sprite it's the sprite taken from the enum of {@link EntitySprites}
     */
    public EnemyImpl(
            final double x,
            final double y,
            final int maxHealth,
            final double radius,
            final EnemyType type,
            final int fireRate,
            final double speed,
            final double minBound,
            final double maxBound,
            final EntitySprites sprite) {
        super(x, y, maxHealth, CollisionGroups.HOSTILE, radius, sprite);
        this.type = type;
        this.fireRate = fireRate;
        this.speed = speed;
        this.minBound = minBound;
        this.maxBound = maxBound;
    }

    /**
     * {@inheritDoc}.
     *
     * @return the {@link EnemyType}
     */
    @Override
    public EnemyType getEnemyType() {
        return this.type;
    }

    /**
     * {@inheritDoc}.
     *
     * @return the reward for the death of the enemy
     */
    @Override
    public int getReward() {
        return this.type.getRewardForKill();
    }

    /**
     * {@inheritDoc}.
     *
     * @param delta the delta of movement, which is either +1, 0 or -1, to be then applied to either the x or y axis
     */
    @Override
    public void move(final Delta delta) {
        final double movement = delta.getValue() * speed;
        double newY = this.getY() + movement;
        if (newY < minBound) {
            newY = minBound;
        } else if (newY > maxBound) {
            newY = maxBound;
        }
        this.setPosition(this.getX(), newY);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean isAbleToShoot() {
        return shootingCounter == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return the fireRate
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
     * @return the speed of the enemyImpl
     */
    @Override
    public double getSpeed() {
        return speed;
    }
}
