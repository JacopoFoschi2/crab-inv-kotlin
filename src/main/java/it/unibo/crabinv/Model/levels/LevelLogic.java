package it.unibo.crabinv.Model.levels;

import it.unibo.crabinv.Model.entities.enemies.Wave;
import it.unibo.crabinv.Model.entities.enemies.WaveProvider;

public class LevelLogic implements Level {
    private  final WaveProvider waveProvider;
    private Wave currentWave;

    public LevelLogic(WaveProvider waveProvider) {
        this.waveProvider = waveProvider;
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
