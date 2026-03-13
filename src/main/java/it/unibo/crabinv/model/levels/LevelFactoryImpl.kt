package it.unibo.crabinv.model.levels

import it.unibo.crabinv.model.entities.enemies.EnemyFactory
import it.unibo.crabinv.model.entities.enemies.rewardservice.RewardsService
import it.unibo.crabinv.model.entities.enemies.wave.Wave
import it.unibo.crabinv.model.entities.enemies.wave.WaveComposite
import it.unibo.crabinv.model.entities.enemies.wave.WaveProvider
import it.unibo.crabinv.model.entities.enemies.wave.WaveSequence
import it.unibo.crabinv.model.entities.enemies.wavetypes.WaveAlpha
import it.unibo.crabinv.model.entities.enemies.wavetypes.WaveBeta
import java.util.Objects

/**
 * Implementation of [LevelFactory].
 */
class LevelFactoryImpl : LevelFactory {
    override fun createLevel(
        levelId: Int,
        enemyFactory: EnemyFactory?,
        rewardsService: RewardsService?,
    ): Level {
        require(levelId > 0) { "levelNumber must be > 0" }
        Objects.requireNonNull<EnemyFactory?>(enemyFactory, "enemyFactory cannot be null")
        Objects.requireNonNull<RewardsService?>(rewardsService, "rewardsService cannot be null")
        val initWaves =
            when (levelId) {
                1 ->
                    listOf<Wave?>(
                        WaveAlpha(enemyFactory, rewardsService, DEFAULT_TOP_MARGIN, DEFAULT_BOT_MARGIN, MAX_SLOTS),
                    )

                2 ->
                    listOf<Wave?>(
                        WaveAlpha(enemyFactory, rewardsService, DEFAULT_TOP_MARGIN, DEFAULT_BOT_MARGIN, MAX_SLOTS),
                        WaveBeta(enemyFactory, rewardsService, DEFAULT_TOP_MARGIN, DEFAULT_BOT_MARGIN, MAX_SLOTS),
                    )

                3 ->
                    listOf<Wave?>(
                        WaveAlpha(enemyFactory, rewardsService, DEFAULT_TOP_MARGIN, DEFAULT_BOT_MARGIN, MAX_SLOTS),
                        WaveBeta(enemyFactory, rewardsService, DEFAULT_TOP_MARGIN, DEFAULT_BOT_MARGIN, MAX_SLOTS),
                        WaveBeta(enemyFactory, rewardsService, DEFAULT_TOP_MARGIN, DEFAULT_BOT_MARGIN, MAX_SLOTS),
                    )

                else -> mutableListOf()
            }
        val waves =
            initWaves
                .stream()
                .peek { wave: Wave? ->
                    val i = initWaves.indexOf(wave)
                    wave!!.setSpawnY(SPAWN_STEP * i + DEFAULT_TOP_MARGIN)
                }.toList()
        val allWaves: Wave = WaveComposite(waves)
        val provider: WaveProvider = WaveSequence(listOf<Wave?>(allWaves))
        return LevelImpl(provider)
    }

    companion object {
        private const val SPAWN_STEP = 0.05
        private const val DEFAULT_TOP_MARGIN = 0.05
        private const val DEFAULT_BOT_MARGIN = 0.90
        private const val MAX_SLOTS = 12
    }
}
