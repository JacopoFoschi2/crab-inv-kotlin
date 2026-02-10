package it.unibo.crabinv.Model.level;

import it.unibo.crabinv.Model.entities.enemies.wave.Wave;
import it.unibo.crabinv.Model.entities.enemies.wave.WaveProvider;
import it.unibo.crabinv.Model.entities.enemies.wave.WaveSequence;
import it.unibo.crabinv.Model.levels.Level;
import it.unibo.crabinv.Model.levels.LevelImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class LevelTest {

    /**
     * WaveProvider deterministico per testare l’avanzamento delle waves.
     */
    private static final class QueueWaveProvider implements WaveProvider {
        private final Queue<Wave> waves;

        private QueueWaveProvider(final List<Wave> waves) {
            this.waves = new ArrayDeque<>(waves);
        }

        @Override
        public boolean hasMoreWaves() {
            return !waves.isEmpty();
        }

        @Override
        public Wave getNextWave() {
            return waves.remove();
        }
    }

    @Test
    void constructorShouldStartFromFirstWave() {
        final Wave w1 = mock(Wave.class);
        final Wave w2 = mock(Wave.class);
        final WaveProvider provider = new QueueWaveProvider(List.of(w1, w2));

        final Level level = new LevelImpl(provider);

        assertSame(w1, level.getCurrentWave(), "Il costruttore deve posizionare il livello sulla prima wave");
        assertFalse(level.isLevelFinished(), "Un livello con wave corrente non deve essere finito");
    }

    @Test
    void advanceWaveShouldMoveToNextWaveAndThenFinish() {
        final Wave w1 = mock(Wave.class);
        final Wave w2 = mock(Wave.class);
        final WaveProvider provider = new QueueWaveProvider(List.of(w1, w2));

        final Level level = new LevelImpl(provider);
        assertSame(w1, level.getCurrentWave());

        level.advanceWave();
        assertSame(w2, level.getCurrentWave(), "advanceWave() deve passare alla wave successiva");
        assertFalse(level.isLevelFinished());

        level.advanceWave();
        assertNull(level.getCurrentWave(), "Finite le wave, la currentWave deve diventare null");
        assertTrue(level.isLevelFinished(), "Con currentWave == null il livello deve risultare finito");

        level.advanceWave();
        assertNull(level.getCurrentWave(), "Dopo la fine del livello, advanceWave() deve mantenere lo stato finito");
        assertTrue(level.isLevelFinished());
    }

    @Test
    void levelWithNoWavesShouldBeImmediatelyFinished() {
        final WaveProvider provider = new QueueWaveProvider(List.of());

        final Level level = new LevelImpl(provider);

        assertNull(level.getCurrentWave(), "Se il provider non ha wave, currentWave deve essere null");
        assertTrue(level.isLevelFinished(), "Se non ci sono wave, il livello deve risultare finito");
    }

    @Test
    void shouldReturnWavesInOrderAndThenReportNoMoreWaves() {
        final Wave w1 = mock(Wave.class);
        final Wave w2 = mock(Wave.class);

        final WaveProvider provider = new WaveSequence(List.of(w1, w2));

        assertTrue(provider.hasMoreWaves());
        assertSame(w1, provider.getNextWave());

        assertTrue(provider.hasMoreWaves());
        assertSame(w2, provider.getNextWave());

        assertFalse(provider.hasMoreWaves());
    }
}

