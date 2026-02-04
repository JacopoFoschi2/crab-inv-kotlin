package it.unibo.crabinv.Model.Enemies;

import java.util.List;

public interface Wave {

    /**
     * Advances the wave by one gameEngine tick
     */
    void tickLogicUpdate();

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
