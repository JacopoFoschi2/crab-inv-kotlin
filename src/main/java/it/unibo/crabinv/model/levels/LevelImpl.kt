package it.unibo.crabinv.model.levels

import it.unibo.crabinv.model.entities.enemies.wave.Wave
import it.unibo.crabinv.model.entities.enemies.wave.WaveProvider

// Adapted from MoseBarbieri's LevelLogic

/**
 * Implementation of [Level].
 * @param waveProvider the provider of the wave
 */
class LevelImpl(
    private val waveProvider: WaveProvider,
) : Level {
    override var currentWave: Wave? = null
        private set

    init {
        advanceWave()
    }

    override fun advanceWave() {
        this.currentWave = if (waveProvider.hasMoreWaves()) waveProvider.getNextWave() else null
    }

    override val isLevelFinished: Boolean
        get() = currentWave == null
}
