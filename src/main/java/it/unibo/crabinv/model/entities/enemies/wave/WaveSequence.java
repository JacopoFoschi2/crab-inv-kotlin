package it.unibo.crabinv.model.entities.enemies.wave;

import java.util.List;

/**
 * It's the implementation of waveProvider.
 */
public class WaveSequence implements WaveProvider {
    private final List<Wave> waveList;
    private int currentWaveIndex = 0;

    /**
     * Constructor of WaveSequence
     *
     * @param waveList the list of the waves
     */
    public WaveSequence(final List<Wave> waveList) {
        this.waveList = waveList;
    }

    /**
     * {@inheritDoc}
     *
     * @return the next wave
     */
    @Override
    public Wave getNextWave() {
        if (hasMoreWaves()) {
            Wave nextWave = this.waveList.get(currentWaveIndex);
            this.currentWaveIndex++;
            return nextWave;
        } else {
            throw new IndexOutOfBoundsException("Out of bounds index:" + this.currentWaveIndex);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return true if it has more waves
     */
    @Override
    public boolean hasMoreWaves() {
        return this.currentWaveIndex < this.waveList.size();
    }

    /**
     * {@inheritDoc}
     *
     * @return the list of the waves
     */
    @Override
    public List<Wave> getAllWaves() {
        return List.copyOf(waveList);
    }
}
