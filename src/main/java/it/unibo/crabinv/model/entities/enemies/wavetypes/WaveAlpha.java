package it.unibo.crabinv.model.entities.enemies.wavetypes;

import it.unibo.crabinv.model.entities.enemies.EnemyFactory;
import it.unibo.crabinv.model.entities.enemies.EnemyType;
import it.unibo.crabinv.model.entities.enemies.rewardService.RewardsService;
import it.unibo.crabinv.model.entities.enemies.wave.WaveImpl;

import java.util.List;

public final class WaveAlpha extends WaveImpl {
    public WaveAlpha(final EnemyFactory enemyFactory,
                     final RewardsService rewardsService,
                     final double spawnYNorm,
                     final double bottomXNorm) {
        super(
                List.of(EnemyType.SERVANT, EnemyType.SERVANT, EnemyType.SERVANT, EnemyType.SERVANT ),
                List.of(4, 5, 6, 7),
                enemyFactory,
                rewardsService,
                12,
                spawnYNorm,
                bottomXNorm

        );
    }
}
