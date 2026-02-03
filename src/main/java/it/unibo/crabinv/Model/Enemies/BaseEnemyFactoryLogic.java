package it.unibo.crabinv.Model.Enemies;

public class BaseEnemyFactoryLogic implements  EnemyFactory{
    @Override
    public Enemy createEnemy(EnemyType type, int maxHelth, double x, double y, int fireRate, double speed) {
        return switch (type){
            case SERVANT -> new EnemyImpl(EnemyType.SERVANT,1,x,y,1,0.5);
        };
    }
}
