package it.unibo.crabinv.model.level;

import it.unibo.crabinv.model.entities.enemies.wave.Wave;
import it.unibo.crabinv.model.entities.enemies.wave.WaveProvider;
import it.unibo.crabinv.model.entities.enemies.wave.WaveSequence;
import it.unibo.crabinv.model.levels.Level;
import it.unibo.crabinv.model.levels.LevelImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class LevelTest {

    @Test
    void constructorShouldStartFromFirstWave() {
        final Wave w1 = mock(Wave.class);
        final Wave w2 = mock(Wave.class);
        final WaveProvider provider = new QueueWaveProvider(List.of(w1, w2));

        final Level level = new LevelImpl(provider);

        assertSame(w1, level.getCurrentWave(), "The constructor should position itself in the first wave");
        assertFalse(level.isLevelFinished(), "A level with a wave ongoing shouldn't be finished");
    }

    @Test
    void advanceWaveShouldMoveToNextWaveAndThenFinish() {
        final Wave w1 = mock(Wave.class);
        final Wave w2 = mock(Wave.class);
        final WaveProvider provider = new QueueWaveProvider(List.of(w1, w2));

        final Level level = new LevelImpl(provider);
        assertSame(w1, level.getCurrentWave());

        level.advanceWave();
        assertSame(w2, level.getCurrentWave(), "advanceWave() should go to the next wave");
        assertFalse(level.isLevelFinished());

        level.advanceWave();
        assertNull(level.getCurrentWave(), "After all waves are finished, the current wave should become null");
        assertTrue(level.isLevelFinished(), "If currentWave == null the level should be finished");

        level.advanceWave();
        assertNull(level.getCurrentWave(), "After level is finished, advanceWave() should maintain a finite status");
        assertTrue(level.isLevelFinished());
    }

    @Test
    void levelWithNoWavesShouldBeImmediatelyFinished() {
        final WaveProvider provider = new QueueWaveProvider(List.of());

        final Level level = new LevelImpl(provider);

        assertNull(level.getCurrentWave(), "If the provider hasn't got any waves, it should be null");
        assertTrue(level.isLevelFinished(), "If there aren't any waves, the level should be finished");
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

    /**
     * WaveProvider used to test the advancement of waves.
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
        public List<Wave> getAllWaves() {
            return List.of();
        }

        @Override
        public Wave getNextWave() {
            return waves.remove();
        }
    }
}

