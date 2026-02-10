package it.unibo.crabinv.model.levels;

import it.unibo.crabinv.model.entities.enemies.wave.Wave;
import it.unibo.crabinv.model.entities.enemies.wave.WaveProvider;
// Adapted from MoseBarbieri's LevelLogic

/**
 * Implementation of {@link Level}.
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Wave getCurrentWave() {
        return this.currentWave;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void advanceWave() {
        this.currentWave = waveProvider.hasMoreWaves() ? waveProvider.getNextWave() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLevelFinished() {
        return currentWave == null;
    }
}
