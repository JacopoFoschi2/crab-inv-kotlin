package it.unibo.crabinv.Model.wave;

import it.unibo.crabinv.Model.entities.enemies.Enemy;
import it.unibo.crabinv.Model.entities.enemies.EnemyFactory;
import it.unibo.crabinv.Model.entities.enemies.EnemyType;
import it.unibo.crabinv.Model.entities.enemies.rewardService.RewardsService;
import it.unibo.crabinv.Model.entities.enemies.wave.Wave;
import it.unibo.crabinv.Model.entities.enemies.wave.WaveImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        double spawnYNorm   = 0.2;
        double bottomYNorm  = 0.8;

        final Wave wave = new WaveImpl(
                List.of(type),
                List.of(0),
                enemyFactory,
                rewardsService,
                5,
                spawnYNorm,
                bottomYNorm
        );

        // 1) primo tick: spawna 1 nemico, quindi non può essere finita
        wave.tickUpdate();
        assertFalse(wave.isWaveFinished());
        assertEquals(1, wave.getAliveEnemies().size());

        // 2) il nemico muore "nel gioco"
        alive.set(false);

        // 3) tick successivo: cleanup lo rimuove e la wave finisce
        wave.tickUpdate();
        assertTrue(wave.isWaveFinished());
        assertTrue(wave.getAliveEnemies().isEmpty());

        verify(rewardsService, times(1)).rewardEnemyDeath(enemy);
    }
}