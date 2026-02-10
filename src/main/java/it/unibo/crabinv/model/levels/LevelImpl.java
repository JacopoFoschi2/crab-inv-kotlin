package it.unibo.crabinv.model.levels;

import it.unibo.crabinv.model.entities.enemies.wave.Wave;
import it.unibo.crabinv.model.entities.enemies.wave.WaveProvider;
// Adapted from MoseBarbieri's LevelLogic

/**
 * It's the implementation of the level.
 */
public final class LevelImpl implements Level {
    private final WaveProvider waveProvider;
    private Wave currentWave;

    /**
     * The levelImpl constructor.
     *
     * @param waveProvider the provider of the wave
     */
    public LevelImpl(final WaveProvider waveProvider) {
        this.waveProvider = waveProvider;
        advanceWave();
    }

    @Override
    public Wave getCurrentWave() {
        return this.currentWave;
    }

    @Override
    public void advanceWave() {
        this.currentWave = waveProvider.hasMoreWaves() ? waveProvider.getNextWave() : null;
    }

    @Override
    public boolean isLevelFinished() {
        return currentWave == null;
    }
}
