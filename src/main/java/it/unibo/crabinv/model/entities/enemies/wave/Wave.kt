package it.unibo.crabinv.model.entities.enemies.wave

import it.unibo.crabinv.model.entities.enemies.Enemy

/**
 * Is the interface that has all the methods that the wave has.
 */
interface Wave {
    /**
     * Updates the Wave status by one tick.
     */
    fun tickUpdate()

    /**
     * Gives the entire list of all the enemies that are alive.
     * @return a list of all enemies still alive
     */
    val aliveEnemies: MutableList<Enemy>

    /**
     * Checks if the wave is finished.
     * @return true if wave is finished, false if it isn't
     */
    val isWaveFinished: Boolean

    /**
     * Sets the Y coordinates spawn.
     * @param spawnY the new Y spawn coordinates
     */
    fun setSpawnY(spawnY: Double)
}
