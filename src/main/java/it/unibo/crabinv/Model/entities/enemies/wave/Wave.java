package it.unibo.crabinv.Model.entities.enemies.wave;

import it.unibo.crabinv.Model.entities.enemies.Enemy;

import java.util.List;

/**
 * Is the interface that has all the methods that the wave has
 */

public interface Wave {

    /**
     * Updates the Wave status by one tick.
     */
    void tickUpdate();

    /**
     * Gives the entire list of all the enemies that are alive:
     *
     * @return a list of all enemies still alive
     */
    List<Enemy> getAliveEnemies();

    /**
     * Checks if the wave is finished
     *
     * @return true if wave is finished, false if it isn't
     */
    boolean isWaveFinished();

    /**
     * Sets the Y coordinates spawn
     *
     * @param spawnY the new Y spawn coordinates
     */
    void setSpawnY(double spawnY);
}
