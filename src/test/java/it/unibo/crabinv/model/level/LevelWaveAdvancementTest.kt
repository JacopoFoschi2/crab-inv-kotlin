package it.unibo.crabinv.model.level;

import it.unibo.crabinv.model.entities.enemies.wave.Wave;
import it.unibo.crabinv.model.entities.enemies.wave.WaveProvider;
import it.unibo.crabinv.model.entities.enemies.wave.WaveSequence;
import it.unibo.crabinv.model.levels.Level;
import it.unibo.crabinv.model.levels.LevelImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests if the wave advances correctly.
 */
class LevelWaveAdvancementTest {

    /**
     * Simulates the core logic of GameEngineImpl.WaveCheck().
     * - tickUpdate()
     * - if isWaveFinished() then advanceWave()
     *
     * @param level the level it should run
     */
    private static void waveCheckLikeEngine(final Level level) {
        if (!level.isLevelFinished()) {
            final Wave currentWave = level.getCurrentWave();
            if (currentWave != null) {
                currentWave.tickUpdate();
                if (currentWave.isWaveFinished()) {
                    level.advanceWave();
                }
            }
        }
    }

    @Test
    void whenFirstWaveBecomesFinishedLevelMustAdvanceToSecondWave() {
        final Wave w1 = mock(Wave.class);
        final Wave w2 = mock(Wave.class);

        when(w1.isWaveFinished()).thenReturn(true);
        when(w2.isWaveFinished()).thenReturn(false);

        final WaveProvider provider = new WaveSequence(List.of(w1, w2));
        final Level level = new LevelImpl(provider);

        assertSame(w1, level.getCurrentWave());

        waveCheckLikeEngine(level);

        assertSame(w2, level.getCurrentWave(), "After the first wave finishes, the second should start");
        verify(w1, atLeastOnce()).tickUpdate();
        verify(w1, atLeastOnce()).isWaveFinished();
    }

    @Test
    void whenSecondWaveAlsoFinishesLevelMustBecomeFinished() {
        final Wave w1 = mock(Wave.class);
        final Wave w2 = mock(Wave.class);

        when(w1.isWaveFinished()).thenReturn(true);
        when(w2.isWaveFinished()).thenReturn(true);

        final WaveProvider provider = new WaveSequence(List.of(w1, w2));
        final Level level = new LevelImpl(provider);

        waveCheckLikeEngine(level);
        assertSame(w2, level.getCurrentWave());

        waveCheckLikeEngine(level);
        assertTrue(level.isLevelFinished(), "After all waves are finished, the level should end");
        assertNull(level.getCurrentWave());
    }
}
