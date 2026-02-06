package it.unibo.crabinv.Model.entities.enemies;

public class BaseEnemyFactoryLogic implements EnemyFactory{
    @Override
    public Enemy createEnemy(EnemyType type, double x, double y, double maxBound, double minBound) {
        return switch (type){
            case SERVANT ->  new EnemyImpl(x,y,1,0.01,type,1,0.01,maxBound,minBound);
        };
    }
}
