package it.unibo.crabinv.Model.entities.enemies.wavetypes;

import it.unibo.crabinv.Model.entities.enemies.EnemyFactory;
import it.unibo.crabinv.Model.entities.enemies.EnemyType;
import it.unibo.crabinv.Model.entities.enemies.rewardService.RewardsService;
import it.unibo.crabinv.Model.entities.enemies.wave.WaveImpl;

import java.util.List;

public final class WaveBeta extends WaveImpl {
    public WaveBeta(final EnemyFactory enemyFactory,
                    final RewardsService rewardsService,
                    final double spawnYNorm,
                    final double bottomXNorm) {
        super(
                List.of(EnemyType.SERVANT,
                        EnemyType.SERVANT,
                        EnemyType.SERVANT,
                        EnemyType.SERVANT,
                        EnemyType.SERVANT,
                        EnemyType.SERVANT,
                        EnemyType.SERVANT,
                        EnemyType.SERVANT ),
                List.of(2, 3, 4, 5, 6, 7, 8, 9),
                enemyFactory,
                rewardsService,
                12,
                spawnYNorm,
                bottomXNorm

        );
    }
}
