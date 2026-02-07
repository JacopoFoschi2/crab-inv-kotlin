package it.unibo.crabinv.Model.entities.enemies.wavetypes;

import it.unibo.crabinv.Model.entities.enemies.EnemyFactory;
import it.unibo.crabinv.Model.entities.enemies.EnemyType;
import it.unibo.crabinv.Model.entities.enemies.rewardService.RewardsService;
import it.unibo.crabinv.Model.entities.enemies.wave.WaveImpl;

import java.util.List;

public final class WaveAlpha extends WaveImpl {
    public WaveAlpha(final EnemyFactory enemyFactory,
                     final RewardsService rewardsService) {
        super(
                List.of(EnemyType.SERVANT, EnemyType.SERVANT, EnemyType.SERVANT, EnemyType.SERVANT ),
                List.of(4, 5, 6, 7),
                enemyFactory,
                rewardsService,
                12
        );
    }
}
