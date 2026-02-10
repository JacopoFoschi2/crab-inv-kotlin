package it.unibo.crabinv.Model.entities.enemies;

import it.unibo.crabinv.Model.entities.entity.EntitySprites;

public class BaseEnemyFactoryLogic implements EnemyFactory{
    //TODO: make them better...
    final int ENEMY_MAX_HEALTH = 1;
    final double ENEMY_RADIUS = 0.015;
    final double ENEMY_SPEED = 0.00085;
    final int ENEMY_FIRERATE = 27;

    @Override
    public Enemy createEnemy(EnemyType type, double x, double y, double minBound, double maxBound) {
        return new EnemyImpl(x,y, ENEMY_MAX_HEALTH, ENEMY_RADIUS, type, ENEMY_FIRERATE, ENEMY_SPEED,
                minBound, maxBound, EntitySprites.ENEMY_SERVANT);
    }
}
