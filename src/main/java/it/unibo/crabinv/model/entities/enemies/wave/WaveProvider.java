package it.unibo.crabinv.model.entities.enemies.wave;

import java.util.List;

/**
 * It is the interface that has all the methods that implements the WaveProvide that
 * should be able to act as a strategy pattern if there are other ways of the way
 */
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

    /**
     * Exposes the Waves of the Wave Provider
     * @return all the waves
     */
    List<Wave> getAllWaves();
}
