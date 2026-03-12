package it.unibo.crabinv.model.level

import it.unibo.crabinv.model.entities.enemies.wave.Wave
import it.unibo.crabinv.model.entities.enemies.wave.WaveProvider
import it.unibo.crabinv.model.entities.enemies.wave.WaveSequence
import it.unibo.crabinv.model.levels.Level
import it.unibo.crabinv.model.levels.LevelImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.ArrayDeque
import java.util.Queue

internal class LevelTest {
    @Test
    fun constructorShouldStartFromFirstWave() {
        val w1 = Mockito.mock(Wave::class.java)
        val w2 = Mockito.mock(Wave::class.java)
        val provider: WaveProvider = QueueWaveProvider(listOf<Wave?>(w1, w2) as MutableList<Wave?>)

        val level: Level = LevelImpl(provider)

        Assertions.assertSame(w1, level.currentWave, "The constructor should position itself in the first wave")
        Assertions.assertFalse(level.isLevelFinished, "A level with a wave ongoing shouldn't be finished")
    }

    @Test
    fun advanceWaveShouldMoveToNextWaveAndThenFinish() {
        val w1 = Mockito.mock(Wave::class.java)
        val w2 = Mockito.mock(Wave::class.java)
        val provider: WaveProvider = QueueWaveProvider(listOf<Wave?>(w1, w2) as MutableList<Wave?>)

        val level: Level = LevelImpl(provider)
        Assertions.assertSame(w1, level.currentWave)

        level.advanceWave()
        Assertions.assertSame(w2, level.currentWave, "advanceWave() should go to the next wave")
        Assertions.assertFalse(level.isLevelFinished)

        level.advanceWave()
        Assertions.assertNull(
            level.currentWave,
            "After all waves are finished, the current wave should become null",
        )
        Assertions.assertTrue(level.isLevelFinished, "If currentWave == null the level should be finished")

        level.advanceWave()
        Assertions.assertNull(
            level.currentWave,
            "After level is finished, advanceWave() should maintain a finite status",
        )
        Assertions.assertTrue(level.isLevelFinished)
    }

    @Test
    fun levelWithNoWavesShouldBeImmediatelyFinished() {
        val provider: WaveProvider = QueueWaveProvider(mutableListOf())

        val level: Level = LevelImpl(provider)

        Assertions.assertNull(level.currentWave, "If the provider hasn't got any waves, it should be null")
        Assertions.assertTrue(level.isLevelFinished, "If there aren't any waves, the level should be finished")
    }

    @Test
    fun shouldReturnWavesInOrderAndThenReportNoMoreWaves() {
        val w1 = Mockito.mock(Wave::class.java)
        val w2 = Mockito.mock(Wave::class.java)

        val provider: WaveProvider = WaveSequence(listOf<Wave?>(w1, w2))

        Assertions.assertTrue(provider.hasMoreWaves())
        Assertions.assertSame(w1, provider.getNextWave())

        Assertions.assertTrue(provider.hasMoreWaves())
        Assertions.assertSame(w2, provider.getNextWave())

        Assertions.assertFalse(provider.hasMoreWaves())
    }

    /**
     * WaveProvider used to test the advancement of waves.
     */
    private class QueueWaveProvider(
        waves: MutableList<Wave?>,
    ) : WaveProvider {
        private val waves: Queue<Wave?> = ArrayDeque<Wave?>(waves)

        override fun hasMoreWaves(): Boolean = !waves.isEmpty()

        override fun getAllWaves(): MutableList<Wave?> = mutableListOf()

        override fun getNextWave(): Wave? = waves.remove()
    }
}
