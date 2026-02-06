package it.unibo.crabinv.Model.entities.enemies;

import it.unibo.crabinv.Model.entities.entity.EntitySprites;

public class BaseEnemyFactoryLogic implements EnemyFactory{
    @Override
    public Enemy createEnemy(EnemyType type, double x, double y) {
        return switch (type){
            case SERVANT ->  EnemyImpl.builder()
                    .x(x)
                    .y(y)
                    .maxHealth(1)
                    .health(1)
                    .speed(0.5)
                    .fireRate(1)
                    .type(type)
                    .radius(10)
                    .sprite(EntitySprites.ENEMY_SERVANT)
                    .build();
        };
    }
}
