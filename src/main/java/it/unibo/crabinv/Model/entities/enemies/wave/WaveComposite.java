package it.unibo.crabinv.Model.entities.enemies.wave;

import it.unibo.crabinv.Model.entities.enemies.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Contains multiple {@link Wave} allowing the Game Engine to treat them as one
 */
public class WaveComposite implements Wave{

    private final List<Wave> waves;

    public WaveComposite(List<Wave> waves) {
        Objects.requireNonNull(waves, "waves cannot be null");
        this.waves = List.copyOf(waves);
    }

    @Override
    public void tickUpdate() {
        for (final Wave wave : waves) {
            wave.tickUpdate();
        }

    }

    @Override
    public List<Enemy> getAliveEnemies() {
        final List<Enemy> allWavesEnemies = new ArrayList<>();
        for (final Wave wave : waves) {
            allWavesEnemies.addAll(wave.getAliveEnemies());
        }
        return allWavesEnemies;
    }

    @Override
    public boolean isWaveFinished() {
        for (final Wave wave : waves) {
            if (!wave.isWaveFinished()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setSpawnY(double spawnY) {
        //Does nothing, SpawnY should be already set before entering WaveComposite
    }
}
