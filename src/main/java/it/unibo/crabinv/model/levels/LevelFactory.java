package it.unibo.crabinv.model.levels;

import it.unibo.crabinv.model.entities.enemies.EnemyFactory;
import it.unibo.crabinv.model.entities.enemies.rewardService.RewardsService;

/**
 * It's the interface that makes the factory method.
 */
public interface LevelFactory {
    /**
     * It's the method which creates the level.
     *
     * @param levelId the level id
     * @param enemyFactory the enemy factory that populates it
     * @param rewardsService the service that gives the player the currency
     * @return Level: the instance of the level that you want
     */
    Level createLevel(int levelId, EnemyFactory enemyFactory, RewardsService rewardsService);
}
