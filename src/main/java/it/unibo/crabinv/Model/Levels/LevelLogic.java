package it.unibo.crabinv.Model.Levels;

import it.unibo.crabinv.Model.Enemies.Wave;
import it.unibo.crabinv.Model.Enemies.WaveProvider;
import it.unibo.crabinv.Model.Enemies.WaveSequence;

public class LevelLogic implements Level {
    private  final WaveProvider waveProvider;
    private Wave currentWave;

    public LevelLogic(WaveProvider waveProvider) {
        this.waveProvider = waveProvider;
        advanceWave();
    }

    @Override
    public Wave getCurrentWave() {
        return waveProvider.getNextWave();
    }

    @Override
    public void advanceWave() {
        if (waveProvider.hasMoreWaves()) {
            waveProvider.getNextWave();
        }
    }

    @Override
    public boolean isLevelFinished() {
        if (!waveProvider.hasMoreWaves() ) {
            return true;
        }
        else  {
            return false;
        }
    }
}
