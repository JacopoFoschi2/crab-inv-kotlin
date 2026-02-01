package it.unibo.crabinv.Model.Levels;

import it.unibo.crabinv.Model.Enemies.EnemyFactory;
import it.unibo.crabinv.Model.Enemies.RewardsService;

import java.util.Objects;

public class LevelFactoryImpl implements LevelFactory{
    @Override
    public Level createLevel(int levelId, EnemyFactory enemyFactory, RewardsService rewardsService) {
        Objects.requireNonNull(enemyFactory, "enemyFactory cannot be null");
        Objects.requireNonNull(rewardsService, "rewardsService cannot be null");
        return null;
    }
}
