package it.unibo.crabinv.model.entities.enemies;

/**
 * It's the interface that creates the factory for the enemies
 */
public interface EnemyFactory {
    /**
     * Function that creates the enemy based on the type
     * @param type it's the types of the enemy
     * @param x is the horizontal axis position that you want the enemy at
     * @param y is the vertical axis position that you want the enemy at
     * @return the created enemy
     */
    Enemy createEnemy(EnemyType type, double x, double y, double minBound, double maxBound);
}
