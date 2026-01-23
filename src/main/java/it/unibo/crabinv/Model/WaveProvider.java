package it.unibo.crabinv.Model;

public interface WaveProvider {
    /**
     * @return the next available wave, so if you call it a second time it gives the next one,
     * not the one that you called and had before
     */
    Wave getNextWave();

    /**
     *
     * @return when there are more waves, so that the level can check if it is finished or not
     */

    boolean hasMoreWaves();
}
