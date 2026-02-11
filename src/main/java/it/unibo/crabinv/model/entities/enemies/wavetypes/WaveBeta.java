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
     * @param maxSpawnSlots  the max number of spawn slots
     */
    public WaveBeta(final EnemyFactory enemyFactory,
                    final RewardsService rewardsService,
                    final double spawnYNorm,
                    final double bottomXNorm,
                    final int maxSpawnSlots) {
        super(
                List.of(EnemyType.SERVANT,
                        EnemyType.SERVANT,
                        EnemyType.SERVANT,
                        EnemyType.SERVANT,
                        EnemyType.SERVANT,
                        EnemyType.SERVANT,
                        EnemyType.SERVANT,
                        EnemyType.SERVANT),
                List.of(WaveSlot.S2.getWaveSlot(),
                        WaveSlot.S3.getWaveSlot(),
                        WaveSlot.S4.getWaveSlot(),
                        WaveSlot.S5.getWaveSlot(),
                        WaveSlot.S6.getWaveSlot(),
                        WaveSlot.S7.getWaveSlot(),
                        WaveSlot.S8.getWaveSlot(),
                        WaveSlot.S9.getWaveSlot()),
                enemyFactory,
                rewardsService,
                maxSpawnSlots,
                spawnYNorm,
                bottomXNorm
        );
    }
}
