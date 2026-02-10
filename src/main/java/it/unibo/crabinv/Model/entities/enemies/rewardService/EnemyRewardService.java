package it.unibo.crabinv.Model.entities.enemies.rewardService;

import it.unibo.crabinv.Model.entities.enemies.Enemy;
import it.unibo.crabinv.Model.save.GameSession;

/**
 * It's the implementation of the EnemyRewards.
 */
public class EnemyRewardService implements RewardsService {
    private final GameSession profile;

    /**
     * It's the constructor of the enemyRewardService.
     *
     * @param profile profile needed to update
     */
    public EnemyRewardService(final GameSession profile) {
        this.profile = profile;
    }

    /**
     * {@inheritDoc}
     *
     * @param enemy the dead enemy
     */
    @Override
    public void rewardEnemyDeath(final Enemy enemy) {
        profile.addCurrency(enemy.getReward());
    }
}
