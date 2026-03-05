package it.unibo.crabinv.model.level

import it.unibo.crabinv.model.entities.enemies.wave.Wave
import it.unibo.crabinv.model.entities.enemies.wave.WaveProvider
import it.unibo.crabinv.model.entities.enemies.wave.WaveSequence
import it.unibo.crabinv.model.levels.Level
import it.unibo.crabinv.model.levels.LevelImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

/**
 * Tests if the wave advances correctly.
 */
internal class LevelWaveAdvancementTest {
    @Test
    fun whenFirstWaveBecomesFinishedLevelMustAdvanceToSecondWave() {
        val w1 = Mockito.mock(Wave::class.java)
        val w2 = Mockito.mock(Wave::class.java)

        Mockito.`when`(w1.isWaveFinished()).thenReturn(true)
        Mockito.`when`(w2.isWaveFinished()).thenReturn(false)

        val provider: WaveProvider = WaveSequence(listOf<Wave?>(w1, w2))
        val level: Level = LevelImpl(provider)

        Assertions.assertSame(w1, level.getCurrentWave())

        waveCheckLikeEngine(level)

        Assertions.assertSame(w2, level.getCurrentWave(), "After the first wave finishes, the second should start")
        Mockito.verify(w1, Mockito.atLeastOnce()).tickUpdate()
        Mockito.verify(w1, Mockito.atLeastOnce()).isWaveFinished()
    }

    @Test
    fun whenSecondWaveAlsoFinishesLevelMustBecomeFinished() {
        val w1 = Mockito.mock(Wave::class.java)
        val w2 = Mockito.mock(Wave::class.java)

        Mockito.`when`(w1.isWaveFinished()).thenReturn(true)
        Mockito.`when`(w2.isWaveFinished()).thenReturn(true)

        val provider: WaveProvider = WaveSequence(listOf<Wave?>(w1, w2))
        val level: Level = LevelImpl(provider)

        waveCheckLikeEngine(level)
        Assertions.assertSame(w2, level.getCurrentWave())

        waveCheckLikeEngine(level)
        Assertions.assertTrue(level.isLevelFinished(), "After all waves are finished, the level should end")
        Assertions.assertNull(level.getCurrentWave())
    }

    companion object {
        /**
         * Simulates the core logic of GameEngineImpl.WaveCheck().
         * - tickUpdate()
         * - if isWaveFinished() then advanceWave()
         * @param level the level it should run
         */
        private fun waveCheckLikeEngine(level: Level) {
            if (!level.isLevelFinished()) {
                val currentWave = level.getCurrentWave()
                if (currentWave != null) {
                    currentWave.tickUpdate()
                    if (currentWave.isWaveFinished()) {
                        level.advanceWave()
                    }
                }
            }
        }
    }
}
