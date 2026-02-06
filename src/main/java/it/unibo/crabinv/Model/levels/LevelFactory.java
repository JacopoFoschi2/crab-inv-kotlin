package it.unibo.crabinv.Model.levels;

import it.unibo.crabinv.Model.entities.enemies.EnemyFactory;
import it.unibo.crabinv.Model.entities.enemies.RewardsService;

public interface LevelFactory {
    Level createLevel(int levelId, EnemyFactory enemyFactory, RewardsService rewardsService);
}
