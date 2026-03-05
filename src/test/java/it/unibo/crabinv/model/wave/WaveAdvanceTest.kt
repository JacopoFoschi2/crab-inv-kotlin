package it.unibo.crabinv.model.wave

import it.unibo.crabinv.controller.core.collision.CollisionController
import it.unibo.crabinv.model.core.engine.GameEngineImpl
import it.unibo.crabinv.model.core.save.GameSession
import it.unibo.crabinv.model.entities.enemies.Enemy
import it.unibo.crabinv.model.entities.enemies.EnemyFactory
import it.unibo.crabinv.model.entities.enemies.EnemyType
import it.unibo.crabinv.model.entities.enemies.rewardservice.RewardsService
import it.unibo.crabinv.model.entities.enemies.wave.Wave
import it.unibo.crabinv.model.entities.enemies.wave.WaveImpl
import it.unibo.crabinv.model.entities.enemies.wave.WaveProvider
import it.unibo.crabinv.model.entities.enemies.wave.WaveSequence
import it.unibo.crabinv.model.levels.LevelFactory
import it.unibo.crabinv.model.levels.LevelImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import java.util.ArrayDeque
import java.util.Queue
import java.util.concurrent.atomic.AtomicBoolean

internal class WaveAdvanceTest {
    @Test
    fun whenLastEnemyDiesEngineShouldAdvanceAndSpawnNextWaveImmediately() {
        val session = Mockito.mock(GameSession::class.java)
        Mockito.`when`(session.getCurrentLevel()).thenReturn(1)
        Mockito.`when`(session.getPlayerHealth()).thenReturn(3)
        Mockito.`when`(session.getPlayerSpeed()).thenReturn(0.0)
        Mockito.`when`(session.getPlayerFireRate()).thenReturn(0)

        val rewardsService = Mockito.mock(RewardsService::class.java)

        val wave1Alive = AtomicBoolean(true)
        val wave2Alive = AtomicBoolean(true)

        val enemy1 = Mockito.mock(Enemy::class.java)
        Mockito.`when`(enemy1.isAlive()).thenAnswer(Answer { _: InvocationOnMock? -> wave1Alive.get() })

        val enemy2 = Mockito.mock(Enemy::class.java)
        Mockito.`when`(enemy2.isAlive()).thenAnswer(Answer { _: InvocationOnMock? -> wave2Alive.get() })

        val enemiesToCreate: Queue<Enemy?> = ArrayDeque<Enemy?>(listOf<Enemy?>(enemy1, enemy2))

        val enemyFactory = Mockito.mock(EnemyFactory::class.java)
        Mockito
            .`when`(
                enemyFactory.createEnemy(
                    ArgumentMatchers.any(EnemyType::class.java),
                    ArgumentMatchers.anyDouble(),
                    ArgumentMatchers.anyDouble(),
                    ArgumentMatchers.anyDouble(),
                    ArgumentMatchers.anyDouble(),
                ),
            ).thenAnswer(Answer { _: InvocationOnMock? -> enemiesToCreate.remove() })

        val spawnYNorm = 0.2
        val bottomYNorm = 0.8

        val collisionController = Mockito.mock(CollisionController::class.java)
        Mockito
            .doAnswer(
                Answer { inv: InvocationOnMock? ->
                    val entities = inv!!.getArgument<Any?>(0) as MutableList<*>
                    for (e in entities) {
                        if (e == enemy1) {
                            wave1Alive.set(false)
                        }
                        if (e == enemy2) {
                            wave2Alive.set(false)
                        }
                    }
                    null
                },
            ).`when`(collisionController)
            .resolve(ArgumentMatchers.anyList())

        val type = EnemyType.entries[0]
        val w1: Wave =
            WaveImpl(
                listOf<EnemyType?>(type),
                mutableListOf<Int?>(0),
                enemyFactory,
                rewardsService,
                5,
                spawnYNorm,
                bottomYNorm,
            )
        val w2: Wave =
            WaveImpl(
                listOf<EnemyType?>(type),
                mutableListOf<Int?>(0),
                enemyFactory,
                rewardsService,
                5,
                spawnYNorm,
                bottomYNorm,
            )
        val wp: WaveProvider = WaveSequence(listOf<Wave?>(w1, w2))

        val levelFactory = LevelFactory { _: Int, _: EnemyFactory?, _: RewardsService? -> LevelImpl(wp) }

        val engine = GameEngineImpl()
        engine.init(session, levelFactory, enemyFactory, rewardsService, collisionController)

        engine.tick()

        Assertions.assertFalse(
            engine.getEnemyList().isEmpty(),
            "Dopo l'avanzamento, la wave successiva dovrebbe essere già spawnata (lista nemici non vuota).",
        )
    }
}
