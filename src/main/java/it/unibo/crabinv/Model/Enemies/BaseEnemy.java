package it.unibo.crabinv.Model.Enemies;

import it.unibo.crabinv.Model.Enemy;

public class BaseEnemy implements Enemy {
    private int health = 1;
    private int coordinateX;
    private int coordinateY;


    @Override
    public EnemyType getEnemyType() {
        return EnemyType.SERVANT;
    }
}
