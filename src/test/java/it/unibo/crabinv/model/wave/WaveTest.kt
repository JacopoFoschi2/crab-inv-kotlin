package it.unibo.crabinv.model.wave

import it.unibo.crabinv.model.entities.enemies.Enemy
import it.unibo.crabinv.model.entities.enemies.EnemyFactory
import it.unibo.crabinv.model.entities.enemies.EnemyType
import it.unibo.crabinv.model.entities.enemies.rewardservice.RewardsService
import it.unibo.crabinv.model.entities.enemies.wave.Wave
import it.unibo.crabinv.model.entities.enemies.wave.WaveImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import java.util.concurrent.atomic.AtomicBoolean

internal class WaveTest {
    @Test
    fun waveShouldFinishAfterAllSpawnedEnemiesDie() {
        val enemyFactory = Mockito.mock(EnemyFactory::class.java)
        val rewardsService = Mockito.mock(RewardsService::class.java)

        val alive = AtomicBoolean(true)
        val enemy = Mockito.mock(Enemy::class.java)
        Mockito.`when`(enemy.isAlive()).thenAnswer(Answer { _: InvocationOnMock? -> alive.get() })

        Mockito
            .`when`(
                enemyFactory.createEnemy(
                    ArgumentMatchers.any(EnemyType::class.java),
                    ArgumentMatchers.anyDouble(),
                    ArgumentMatchers.anyDouble(),
                    ArgumentMatchers.anyDouble(),
                    ArgumentMatchers.anyDouble(),
                ),
            ).thenReturn(enemy)

        val type = EnemyType.entries[0]

        val spawnYNorm = 0.2
        val bottomYNorm = 0.8

        val wave: Wave =
            WaveImpl(
                listOf<EnemyType?>(type),
                mutableListOf<Int?>(0),
                enemyFactory,
                rewardsService,
                5,
                spawnYNorm,
                bottomYNorm,
            )

        wave.tickUpdate()
        Assertions.assertFalse(wave.isWaveFinished())
        Assertions.assertEquals(1, wave.getAliveEnemies().size)

        alive.set(false)

        wave.tickUpdate()
        Assertions.assertTrue(wave.isWaveFinished())
        Assertions.assertTrue(wave.getAliveEnemies().isEmpty())

        Mockito.verify(rewardsService, Mockito.times(1)).rewardEnemyDeath(enemy)
    }
}
