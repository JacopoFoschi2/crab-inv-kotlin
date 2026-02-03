package it.unibo.crabinv.Model.Enemies.wavetypes;

import it.unibo.crabinv.Model.Enemies.EnemyFactory;
import it.unibo.crabinv.Model.Enemies.EnemyType;
import it.unibo.crabinv.Model.Enemies.RewardsService;
import it.unibo.crabinv.Model.Enemies.WaveImpl;

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
