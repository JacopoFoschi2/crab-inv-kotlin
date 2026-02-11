package it.unibo.crabinv.model.wave;

import it.unibo.crabinv.model.entities.enemies.Enemy;
import it.unibo.crabinv.model.entities.enemies.EnemyFactory;
import it.unibo.crabinv.model.entities.enemies.EnemyType;
import it.unibo.crabinv.model.entities.enemies.rewardservice.RewardsService;
import it.unibo.crabinv.model.entities.enemies.wave.Wave;
import it.unibo.crabinv.model.entities.enemies.wave.WaveImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WaveTest {

    @Test
    void waveShouldFinishAfterAllSpawnedEnemiesDie() {
        final EnemyFactory enemyFactory = mock(EnemyFactory.class);
        final RewardsService rewardsService = mock(RewardsService.class);

        final AtomicBoolean alive = new AtomicBoolean(true);
        final Enemy enemy = mock(Enemy.class);
        when(enemy.isAlive()).thenAnswer(inv -> alive.get());

        when(enemyFactory.createEnemy(
                any(EnemyType.class),
                anyDouble(),
                anyDouble(),
                anyDouble(),
                anyDouble()
        )).thenReturn(enemy);

        final EnemyType type = EnemyType.values()[0];

        final double spawnYNorm = 0.2;
        final double bottomYNorm = 0.8;

        final Wave wave = new WaveImpl(
                List.of(type),
                List.of(0),
                enemyFactory,
                rewardsService,
                5,
                spawnYNorm,
                bottomYNorm
        );

        wave.tickUpdate();
        assertFalse(wave.isWaveFinished());
        assertEquals(1, wave.getAliveEnemies().size());

        alive.set(false);

        wave.tickUpdate();
        assertTrue(wave.isWaveFinished());
        assertTrue(wave.getAliveEnemies().isEmpty());

        verify(rewardsService, times(1)).rewardEnemyDeath(enemy);
    }
}
