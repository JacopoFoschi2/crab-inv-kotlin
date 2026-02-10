package it.unibo.crabinv.Model.entities.enemies;

import it.unibo.crabinv.Model.entities.entity.EntitySprites;

/**
 * It's the factory for the base enemy.
 */
public final class BaseEnemyFactoryLogic implements EnemyFactory {
    private static final int ENEMY_MAX_HEALTH = 1;
    private static final double ENEMY_RADIUS = 0.015;
    private static final double ENEMY_SPEED = 0.00085;
    private static final int ENEMY_FIRERATE = 30;

    @Override
    public Enemy createEnemy(
            final EnemyType type,
            final double x,
            final double y,
            final double minBound,
            final double maxBound) {
        return new EnemyImpl(
                x,
                y,
                ENEMY_MAX_HEALTH,
                ENEMY_RADIUS,
                type,
                ENEMY_FIRERATE,
                ENEMY_SPEED,
                minBound,
                maxBound,
                EntitySprites.ENEMY_SERVANT);
    }
}
