package it.unibo.crabinv.Model.levels;

import it.unibo.crabinv.Model.entities.enemies.*;
import it.unibo.crabinv.Model.entities.enemies.wavetypes.WaveAlpha;

import java.util.List;
import java.util.Objects;

public class LevelFactoryImpl implements LevelFactory {

    @Override
    public Level createLevel(int levelId, EnemyFactory enemyFactory, RewardsService rewardsService) {
        if (levelId <= 0) {
            throw new IllegalArgumentException("levelNumber must be > 0");
        }
        Objects.requireNonNull(enemyFactory, "enemyFactory cannot be null");
        Objects.requireNonNull(rewardsService, "rewardsService cannot be null");

        final List<Wave> waves = switch (levelId) {
            case 1 -> List.of(
                    new WaveAlpha(enemyFactory, rewardsService)
            );
            case 2 -> List.of(
                    new WaveAlpha(enemyFactory, rewardsService),
                    new WaveAlpha(enemyFactory, rewardsService)
            );
            case 3 -> List.of(
                    new WaveAlpha(enemyFactory, rewardsService),
                    new WaveAlpha(enemyFactory, rewardsService),
                    new WaveAlpha(enemyFactory, rewardsService)
            );
            default -> throw new IllegalArgumentException("Unsupported levelNumber: " + levelId);
            // TODO if levelId = last case + 1 = win
        };

        final WaveProvider provider = new WaveSequence(waves);
        return new LevelImpl(provider);
    }
}

