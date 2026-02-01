package it.unibo.crabinv.Model.Levels;

import it.unibo.crabinv.Model.Enemies.EnemyFactory;
import it.unibo.crabinv.Model.Enemies.RewardsService;

public interface LevelFactory {
    Level createLevel(int levelId, EnemyFactory enemyFactory, RewardsService rewardsService);
}
