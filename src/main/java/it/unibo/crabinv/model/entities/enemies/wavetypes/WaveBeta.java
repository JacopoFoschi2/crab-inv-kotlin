package it.unibo.crabinv.model.entities.enemies.wavetypes;

import it.unibo.crabinv.model.entities.enemies.EnemyFactory;
import it.unibo.crabinv.model.entities.enemies.EnemyType;
import it.unibo.crabinv.model.entities.enemies.rewardservice.RewardsService;
import it.unibo.crabinv.model.entities.enemies.wave.WaveImpl;

import java.util.List;

/**
 * Preset of a {@link WaveImpl}.
 */
public final class WaveBeta extends WaveImpl {

    /**
     * Constructor of the {@link WaveBeta}.
     *
     * @param enemyFactory   the {@link EnemyFactory} used by the {@link WaveBeta}
     * @param rewardsService {@link RewardsService} used by the {@link WaveBeta}
     * @param spawnYNorm     the Y-axis coordinates spawn
     * @param bottomXNorm    the Y-axis coordinates of the bottom border
     */
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
                        EnemyType.SERVANT),
                List.of(2, 3, 4, 5, 6, 7, 8, 9),
                enemyFactory,
                rewardsService,
                12,
                spawnYNorm,
                bottomXNorm
        );
    }
}
