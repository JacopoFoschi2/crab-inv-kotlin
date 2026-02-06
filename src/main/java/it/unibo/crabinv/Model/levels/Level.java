package it.unibo.crabinv.Model.levels;

import it.unibo.crabinv.Model.entities.enemies.wave.Wave;

public interface Level {
    /**
     * Returns the wave that it is happening
     * @return the current wave
     */
    Wave getCurrentWave();

    /**
     * It's a method that makes the wave advance
     */
    void advanceWave();

    /**
     * Sees if the level is finished if the WaveProvider has not anymore waves
     * @return true if level is finished, false if not
     */
    boolean isLevelFinished();
}
