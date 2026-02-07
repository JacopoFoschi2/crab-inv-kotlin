package it.unibo.crabinv.Model.entities.enemies;

import it.unibo.crabinv.Model.entities.entity.EntitySprites;

public class BaseEnemyFactoryLogic implements EnemyFactory{

    @Override
    public Enemy createEnemy(EnemyType type, double x, double y, double minBound, double maxBound) {
        return new EnemyImpl(x,y, 1, 1.0, type, 1, 0.01,
                minBound, maxBound, EntitySprites.ENEMY_SERVANT);
    }
}
