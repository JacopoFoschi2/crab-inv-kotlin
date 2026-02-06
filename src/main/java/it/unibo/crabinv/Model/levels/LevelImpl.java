package it.unibo.crabinv.Model.levels;

import it.unibo.crabinv.Model.entities.enemies.Wave;
import it.unibo.crabinv.Model.entities.enemies.WaveProvider;

// Adapted from MoseBarbieri's LevelLogic

public class LevelImpl implements Level{

    private final WaveProvider waveProvider;
    private Wave currentWave;

    public LevelImpl(WaveProvider waveProvider) {
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
