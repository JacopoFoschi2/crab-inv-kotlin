package it.unibo.crabinv.Model.Enemies;

public interface EnemyFactory {
    /**
     * Function that creates the enemy based on the type
     * @param type it's the types of the enemy
     * @param x is the horizontal axis position that you want the enemy at
     * @param y is the vertical axis position that you want the enemy at
     * @return the created enemy
     */
    Enemy createEnemy(EnemyType type,int maxHelth, double x, double y,  int fireRate, double speed);
    // EnemyImpl(EnemyType.SERVANT,1,x,y,1,0.5);
}
