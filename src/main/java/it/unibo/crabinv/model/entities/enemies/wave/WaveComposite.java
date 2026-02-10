package it.unibo.crabinv.model.entities.enemies.wave;

import it.unibo.crabinv.model.entities.enemies.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Contains multiple {@link Wave} allowing the Game Engine to treat them as one.
 */
public final class WaveComposite implements Wave {
    private final List<Wave> waves;

    /**
     * It's the constructor for the WaveComposite.
     *
     * @param waves list of all the waves
     */
    public WaveComposite(final List<Wave> waves) {
        Objects.requireNonNull(waves, "waves cannot be null");
        this.waves = List.copyOf(waves);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tickUpdate() {
        for (final Wave wave : waves) {
            wave.tickUpdate();
        }

    }

    /**
     * {@inheritDoc}
     *
     * @return the list of the enemies
     */
    @Override
    public List<Enemy> getAliveEnemies() {
        final List<Enemy> allWavesEnemies = new ArrayList<>();
        for (final Wave wave : waves) {
            allWavesEnemies.addAll(wave.getAliveEnemies());
        }
        return allWavesEnemies;
    }

    /**
     * {@inheritDoc}
     *
     * @return true if the wave is finished
     */
    @Override
    public boolean isWaveFinished() {
        for (final Wave wave : waves) {
            if (!wave.isWaveFinished()) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @param spawnY the new Y spawn coordinates
     */
    @Override
    public void setSpawnY(final double spawnY) {
        //Does nothing, SpawnY should be already set before entering WaveComposite
    }
}
