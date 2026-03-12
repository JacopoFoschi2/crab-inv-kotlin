package it.unibo.crabinv.model.levels

import it.unibo.crabinv.model.entities.enemies.wave.Wave

/**
 * Interface that establishes how a level should be and which methods has.
 */
interface Level {
    /**
     * @return the current wave
     */
    val currentWave: Wave?

    /**
     * It's a method that makes the wave advance.
     */
    fun advanceWave()

    /**
     * Sees if the level is finished if the WaveProvider has not anymore waves.
     * @return true if level is finished, false if not
     */
    val isLevelFinished: Boolean
}
