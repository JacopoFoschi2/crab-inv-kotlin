package it.unibo.crabinv.model.level;

import it.unibo.crabinv.model.entities.enemies.wave.Wave;
import it.unibo.crabinv.model.entities.enemies.wave.WaveProvider;
import it.unibo.crabinv.model.entities.enemies.wave.WaveSequence;
import it.unibo.crabinv.model.levels.Level;
import it.unibo.crabinv.model.levels.LevelImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LevelWaveAdvancementTest {

    /**
     * Simula la logica essenziale di GameEngineImpl.WaveCheck():
     * - tickUpdate()
     * - se isWaveFinished() allora advanceWave()
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

        // w1 risulta "finita" (anche subito) -> l'engine deve avanzare
        when(w1.isWaveFinished()).thenReturn(true);
        when(w2.isWaveFinished()).thenReturn(false);

        final WaveProvider provider = new WaveSequence(List.of(w1, w2));
        final Level level = new LevelImpl(provider);

        assertSame(w1, level.getCurrentWave());

        waveCheckLikeEngine(level);

        assertSame(w2, level.getCurrentWave(), "Dopo che la prima wave è finita, deve partire la seconda");
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
        assertTrue(level.isLevelFinished(), "Finite tutte le wave, il livello deve risultare finito");
        assertNull(level.getCurrentWave());
    }
}