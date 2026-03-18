package it.unibo.crabinv.model.entities.enemies.wavetypes

import it.unibo.crabinv.model.entities.enemies.EnemyFactory
import it.unibo.crabinv.model.entities.enemies.EnemyType
import it.unibo.crabinv.model.entities.enemies.rewardservice.RewardsService
import it.unibo.crabinv.model.entities.enemies.wave.WaveImpl

/**
 * Preset of a [WaveImpl].
 * @param enemyFactory   the [EnemyFactory] used by the [WaveBeta]
 * @param rewardsService [RewardsService] used by the [WaveBeta]
 * @param spawnYNorm     the Y-axis coordinates spawn
 * @param bottomXNorm    the Y-axis coordinates of the bottom border
 * @param maxSpawnSlots  the max number of spawn slots
 */
class WaveAlpha(
    enemyFactory: EnemyFactory,
    rewardsService: RewardsService,
    spawnYNorm: Double,
    bottomXNorm: Double,
    maxSpawnSlots: Int,
) : WaveImpl(
        listOf(
            EnemyType.SERVANT,
            EnemyType.SERVANT,
            EnemyType.SERVANT,
            EnemyType.SERVANT,
        ),
        listOf(
            WaveSlot.S4.waveSlot,
            WaveSlot.S5.waveSlot,
            WaveSlot.S6.waveSlot,
            WaveSlot.S7.waveSlot,
        ),
        enemyFactory,
        rewardsService,
        maxSpawnSlots,
        spawnYNorm,
        bottomXNorm,
    )
