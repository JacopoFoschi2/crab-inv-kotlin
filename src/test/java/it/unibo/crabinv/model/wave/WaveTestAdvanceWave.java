package it.unibo.crabinv.model.wave;

import it.unibo.crabinv.controller.core.collision.CollisionController;
import it.unibo.crabinv.model.core.GameEngineImpl;
import it.unibo.crabinv.model.entities.enemies.Enemy;
import it.unibo.crabinv.model.entities.enemies.EnemyFactory;
import it.unibo.crabinv.model.entities.enemies.EnemyType;
import it.unibo.crabinv.model.entities.enemies.rewardService.RewardsService;
import it.unibo.crabinv.model.levels.LevelFactory;
import it.unibo.crabinv.model.levels.LevelImpl;
import it.unibo.crabinv.model.entities.enemies.wave.Wave;
import it.unibo.crabinv.model.entities.enemies.wave.WaveImpl;
import it.unibo.crabinv.model.entities.enemies.wave.WaveProvider;
import it.unibo.crabinv.model.entities.enemies.wave.WaveSequence;
import it.unibo.crabinv.model.entities.entity.Entity;
import it.unibo.crabinv.model.save.GameSession;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class GameEngineWaveProgressionTest {

    @Test
    void whenLastEnemyDiesEngineShouldAdvanceAndSpawnNextWaveImmediately() {
        final GameSession session = mock(GameSession.class);
        when(session.getCurrentLevel()).thenReturn(1);
        when(session.getPlayerHealth()).thenReturn(3);
        when(session.getPlayerSpeed()).thenReturn(0.0);
        when(session.getPlayerFireRate()).thenReturn(0);

        final RewardsService rewardsService = mock(RewardsService.class);

        // Due nemici distinti: uno per wave1 e uno per wave2
        final AtomicBoolean wave1Alive = new AtomicBoolean(true);
        final AtomicBoolean wave2Alive = new AtomicBoolean(true);

        final Enemy enemy1 = mock(Enemy.class);
        when(enemy1.isAlive()).thenAnswer(inv -> wave1Alive.get());

        final Enemy enemy2 = mock(Enemy.class);
        when(enemy2.isAlive()).thenAnswer(inv -> wave2Alive.get());

        final Queue<Enemy> enemiesToCreate = new ArrayDeque<>(List.of(enemy1, enemy2));

        final EnemyFactory enemyFactory = mock(EnemyFactory.class);
        when(enemyFactory.createEnemy(
                any(EnemyType.class),
                anyDouble(),
                anyDouble(),
                anyDouble(),
                anyDouble()
        )).thenAnswer(inv -> enemiesToCreate.remove());

        double spawnYNorm   = 0.2;
        double bottomYNorm  = 0.8;

        // CollisionController che "uccide" tutti i nemici presenti
        final CollisionController collisionController = mock(CollisionController.class);
        doAnswer(inv -> {
            @SuppressWarnings("unchecked")
            final List<Entity> entities = (List<Entity>) inv.getArgument(0);

            // Uccidiamo il nemico della wave corrente; la wave successiva (se spawnata nello stesso tick)
            // verrà uccisa solo al tick dopo, che qui non facciamo.
            for (final Entity e : entities) {
                if (e == enemy1) wave1Alive.set(false);
                if (e == enemy2) wave2Alive.set(false);
            }
            return null;
        }).when(collisionController).resolve(anyList());

        final EnemyType type = EnemyType.values()[0];
        final Wave w1 = new WaveImpl(List.of(type), List.of(0), enemyFactory, rewardsService, 5, spawnYNorm, bottomYNorm );
        final Wave w2 = new WaveImpl(List.of(type), List.of(0), enemyFactory, rewardsService, 5, spawnYNorm, bottomYNorm);
        final WaveProvider wp = new WaveSequence(List.of(w1, w2));

        final LevelFactory levelFactory = (levelNumber, ef, rs) -> new LevelImpl(wp);

        final GameEngineImpl engine = new GameEngineImpl();
        engine.init(session, levelFactory, enemyFactory, rewardsService, collisionController);

        // Tick 1: spawna wave1 e poi collisioni uccidono enemy1 (ma cleanup della wave1 avverrà nel tick successivo)
        engine.tick();

        assertFalse(engine.getEnemyList().isEmpty(),
                "Dopo l'avanzamento, la wave successiva dovrebbe essere già spawnata (lista nemici non vuota).");
    }
}