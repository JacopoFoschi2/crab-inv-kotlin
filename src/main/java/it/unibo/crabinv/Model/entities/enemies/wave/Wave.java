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
     * @return a list of all enemies still alive
     */
    List<Enemy> getAliveEnemies();

    /**
     *Checks if the wave is finished
     * @return true if wave is finished, false if it isn't
     */
    boolean isWaveFinished();

    /**
     * Gets the maximum of enemies that are available to spawn
     * @return the number of available spawn slots for the enemies
     */
    int getMaxSpawnSlots();

}
