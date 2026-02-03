package it.unibo.crabinv.Model.Enemies;

public class BaseEnemyFactoryLogic implements  EnemyFactory{
    @Override
    public Enemy createEnemy(EnemyType type, double x, double y) {
        return switch (type){
            case SERVANT -> new EnemyImpl(EnemyType.SERVANT,1,x,y,1);
        };
    }
}
