package it.unibo.crabinv.Model.Enemies;

public interface WaveProvider {
    /**
     * Getter for the next available wave
     * @return the next available wave, so if you call it a second time it gives the next one,
     * not the one that you called and had before
     */
    Wave getNextWave();

    /**
     *Method that checks if there are more waves
     * @return true when there are more waves, so that the level can check if it is finished or not
     */
    boolean hasMoreWaves();
}
