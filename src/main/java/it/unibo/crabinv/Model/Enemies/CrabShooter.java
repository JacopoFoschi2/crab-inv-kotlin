package it.unibo.crabinv.Model.Enemies;

import it.unibo.crabinv.Model.Enemy;

public class CrabShooter implements Enemy {
    
    @Override
    public EnemyType getEnemyType() {
        return EnemyType.SERVANT;
    }
}
