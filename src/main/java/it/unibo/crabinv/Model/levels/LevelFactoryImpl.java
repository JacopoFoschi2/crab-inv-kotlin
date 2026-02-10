package it.unibo.crabinv.Model.levels;

import it.unibo.crabinv.Model.entities.enemies.*;
import it.unibo.crabinv.Model.entities.enemies.rewardService.RewardsService;
import it.unibo.crabinv.Model.entities.enemies.wave.Wave;
import it.unibo.crabinv.Model.entities.enemies.wave.WaveComposite;
import it.unibo.crabinv.Model.entities.enemies.wave.WaveProvider;
import it.unibo.crabinv.Model.entities.enemies.wave.WaveSequence;
import it.unibo.crabinv.Model.entities.enemies.wavetypes.WaveAlpha;
import it.unibo.crabinv.Model.entities.enemies.wavetypes.WaveBeta;

import java.util.List;
import java.util.Objects;

public class LevelFactoryImpl implements LevelFactory {

    private static final double DEFAULT_TOP_MARGIN = 0.05;
    private static final double DEFAULT_BOT_MARGIN = 0.90;

    @Override
    public Level createLevel(int levelId, EnemyFactory enemyFactory, RewardsService rewardsService) {
        if (levelId <= 0) {
            throw new IllegalArgumentException("levelNumber must be > 0");
        }
        Objects.requireNonNull(enemyFactory, "enemyFactory cannot be null");
        Objects.requireNonNull(rewardsService, "rewardsService cannot be null");

        double spawnStep = 0.05;

        final List<Wave> initWaves = switch (levelId) {
            case 1 -> List.of(
                    new WaveAlpha(enemyFactory, rewardsService, DEFAULT_TOP_MARGIN, DEFAULT_BOT_MARGIN)
            );
            /*
            case 2 -> List.of(
                    new WaveAlpha(enemyFactory, rewardsService, DEFAULT_TOP_MARGIN, DEFAULT_BOT_MARGIN),
                    new WaveBeta(enemyFactory, rewardsService, DEFAULT_TOP_MARGIN, DEFAULT_BOT_MARGIN)
            );
            case 3 -> List.of(
                    new WaveAlpha(enemyFactory, rewardsService, DEFAULT_TOP_MARGIN, DEFAULT_BOT_MARGIN),
                    new WaveBeta(enemyFactory, rewardsService, DEFAULT_TOP_MARGIN, DEFAULT_BOT_MARGIN),
                    new WaveBeta(enemyFactory, rewardsService, DEFAULT_TOP_MARGIN, DEFAULT_BOT_MARGIN)
            );

             */
            default -> List.of();
        };

        final List<Wave> waves = initWaves.stream()
                .peek((Wave wave) -> {
                    int i = initWaves.indexOf(wave);
                    wave.setSpawnY(spawnStep * i + DEFAULT_TOP_MARGIN);
                })
                .toList();

        final Wave allWaves = new WaveComposite(waves);

        final WaveProvider provider = new WaveSequence(List.of(allWaves));
        return new LevelImpl(provider);
    }
}

