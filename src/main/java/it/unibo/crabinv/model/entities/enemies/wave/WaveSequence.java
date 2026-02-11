package it.unibo.crabinv.model.entities.enemies.wave;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;

/**
 * It's the handler of the wave, made for future procedural Waves.
 */
public final class WaveSequence implements WaveProvider {

    private final List<Wave> waveList;
    private int currentWaveIndex;

    /**
     * It's the WaveSequence constructor method.
     *
     * @param waveList it's the wave list that the WaveSequence handles
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2") //dependencies are injected and owned by caller
    public WaveSequence(final List<Wave> waveList) {
        this.waveList = waveList;
    }

    @Override
    public Wave getNextWave() {
        if (hasMoreWaves()) {
            final Wave nextWave = this.waveList.get(currentWaveIndex);
            this.currentWaveIndex++;
            return nextWave;
        } else {
            throw new IndexOutOfBoundsException("Out of bounds index:" + this.currentWaveIndex);
        }

    }

    /**
     * {@inheritDoc}
     *
     * @return true if the wave index didn't arrive to the limit
     */
    @Override
    public boolean hasMoreWaves() {
        return this.currentWaveIndex < this.waveList.size();
    }

    /**
     * {@inheritDoc}
     *
     * @return the list of all the waves
     */
    @Override
    public List<Wave> getAllWaves() {
        return List.copyOf(waveList);
    }
}
